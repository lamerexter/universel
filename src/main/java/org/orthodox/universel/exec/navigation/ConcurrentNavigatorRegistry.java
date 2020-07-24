/*
 *  MIT Licence:
 *
 *  Copyright (c) 2020 Orthodox Engineering Ltd
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *
 */
package org.orthodox.universel.exec.navigation;

import org.beanplanet.core.lang.TypeTree;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.models.tree.TreeNode;
import org.beanplanet.core.util.MultiValueListMapImpl;
import org.orthodox.universel.ast.MethodCall;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.ReductionNodeTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.beanplanet.core.lang.TypeUtil.ensureNonPrimitiveType;

/**
 * A highly concurrent implementation of registry of binary operator functions.
 */
public class ConcurrentNavigatorRegistry implements NavigatorRegistry {
    private Map<Class<?>, Map<String, Map<String, List<NavigatorFunction>>>> mappingNavigators = new ConcurrentHashMap<>();
    private Map<Class<?>, Map<String, Map<String, List<NavigatorFunction>>>> methodNavigators = new ConcurrentHashMap<>();
    private Map<Class<?>, Map<String, Map<Class<? extends ReductionNodeTest>, List<NavigatorFunction>>>> reductionNavigators = new ConcurrentHashMap<>();
    private static final NameTest WILDCARD_NAMETEST = new NameTest(null, "*");

    @Override
    public void addMappingNavigator(final Class<?> fromType, final List<String> axes, final List<String> names, final NavigatorFunction navigator) {
        for (String axis : axes) {
            for (String name : names) {
                this.mappingNavigators.computeIfAbsent(fromType, k -> new ConcurrentHashMap<>())
                                      .computeIfAbsent(axis, k -> new MultiValueListMapImpl<>())
                                      .computeIfAbsent(name, k -> new ArrayList<>(Collections.singletonList(navigator)));
            }
        }
    }

    @Override
    public void addMethodNavigator(final Class<?> fromType, final List<String> axes, final List<String> names, final NavigatorFunction navigator) {
        for (String axis : axes) {
            for (String name : names) {
                this.methodNavigators.computeIfAbsent(fromType, k -> new ConcurrentHashMap<>())
                                      .computeIfAbsent(axis, k -> new MultiValueListMapImpl<>())
                                      .computeIfAbsent(name, k -> new ArrayList<>(Collections.singletonList(navigator)));
            }
        }
    }

    @Override
    public void addReductionNavigator(final Class<?> fromType, final List<String> axes, final Class<? extends ReductionNodeTest> reductionType, final NavigatorFunction navigator) {
        for (String axis : axes) {
            this.reductionNavigators.computeIfAbsent(fromType, k -> new ConcurrentHashMap<>())
                                    .computeIfAbsent(axis, k -> new MultiValueListMapImpl<>())
                                    .computeIfAbsent(reductionType, k -> new ArrayList<>(Collections.singletonList(navigator)));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NavigatorFunction> lookup(final Class<?> fromType, final NavigationStep<?> step) {
        if ( step.getNodeTest() instanceof  NameTest ) {
            return lookupNameNavigators(fromType, (NavigationStep<NameTest>)step);
        } else if ( step.getNodeTest() instanceof MethodCall) {
            return lookupMethodCallNavigators(fromType, (NavigationStep<MethodCall>)step);
        } else if ( step.getNodeTest() instanceof ReductionNodeTest ) {
            return lookupReductionNavigators(fromType, (NavigationStep<ReductionNodeTest>)step);
        }

        return emptyList();
    }

    public List<NavigatorFunction> lookupNameNavigators(final Class<?> fromType, final NavigationStep<NameTest> step) {
        //--------------------------------------------------------------------------------------------------------------
        // Look for navigator matches in the following order:
        // 1) an exact match navigator on type, over the given axis and on the given name, then
        // 2) an exact match navigator on type, over the given axis and on the wildcard name.
        // 3) As above, but on the supertype hierarchy.
        //--------------------------------------------------------------------------------------------------------------
        TypeTree fromTypeTree = new TypeTree(null, fromType);
        List<NavigatorFunction> navigators = new ArrayList<>();
        for (TreeNode<Class<?>> fromTypeNode : fromTypeTree.postorderIterable()) {
            navigators.addAll(mappingNavigators.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                               .getOrDefault(step.getAxis(), emptyMap())
                                               .getOrDefault(step.getNodeTest().getName(), emptyList()));

            navigators.addAll(mappingNavigators.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                               .getOrDefault(step.getAxis(), emptyMap())
                                               .getOrDefault(WILDCARD_NAMETEST.getName(), emptyList()));
        }

        return navigators;
    }

    public List<NavigatorFunction> lookupMethodCallNavigators(final Class<?> fromType, final NavigationStep<MethodCall> step) {
        //--------------------------------------------------------------------------------------------------------------
        // Look for navigator matches in the following order:
        // 1) an exact match navigator on type, over the given axis and on the given name and parameters, then
        // 2) an exact match navigator on type, over the given axis and on the wildcard name and parameters, then
        // 3) As above, but on the supertype hierarchy.
        //--------------------------------------------------------------------------------------------------------------
        TypeTree fromTypeTree = new TypeTree(null, fromType);
        List<NavigatorFunction> navigators = new ArrayList<>();
        for (TreeNode<Class<?>> fromTypeNode : fromTypeTree.postorderIterable()) {
            navigators.addAll(methodNavigators.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                               .getOrDefault(step.getAxis(), emptyMap())
                                               .getOrDefault(step.getNodeTest().getName().getName(), emptyList()));

            navigators.addAll(methodNavigators.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                               .getOrDefault(step.getAxis(), emptyMap())
                                               .getOrDefault(WILDCARD_NAMETEST.getName(), emptyList()));
        }

        return navigators;
    }


    public List<NavigatorFunction> lookupReductionNavigators(final Class<?> fromType, final NavigationStep<? extends ReductionNodeTest> step) {
        //--------------------------------------------------------------------------------------------------------------
        // Look for navigator matches in the following order:
        // 1) an exact match navigator on type, over the given axis and on the given name, then
        // 2) an exact match navigator on type, over the given axis and on the wildcard name.
        // 3) As above, but on the supertype hierarchy.
        //--------------------------------------------------------------------------------------------------------------
        TypeTree fromTypeTree = new TypeTree(null, ensureNonPrimitiveType(fromType));
        List<NavigatorFunction> navigators = new ArrayList<>();
        for (TreeNode<Class<?>> fromTypeNode : fromTypeTree.postorderIterable()) {
            navigators.addAll(reductionNavigators.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                               .getOrDefault(step.getAxis(), emptyMap())
                                               .getOrDefault(step.getNodeTest().getClass(), emptyList()));
        }

        return navigators;
    }

    @Override
    public int size() {
        return (int) (mappingNavigators.values().stream()
                                       .flatMap(v -> v.values().stream())
                                       .flatMap(v -> v.values().stream())
                                       .mapToLong(Collection::size).sum() +
                      reductionNavigators.values().stream()
                                         .flatMap(v -> v.values().stream())
                                         .flatMap(v -> v.values().stream())
                                         .mapToLong(Collection::size).sum()
        );
    }

    @Override
    public void clear() {
        mappingNavigators.clear();
        reductionNavigators.clear();;
    }
}
