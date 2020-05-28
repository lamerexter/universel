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
import org.beanplanet.core.models.tree.TreeNode;
import org.beanplanet.core.util.MultiValueListMapImpl;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NodeTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

/**
 * A highly concurrent implementation of registry of binary operator functions.
 */
public class ConcurrentNavigatorRegistry implements NavigatorRegistry {
    private ConcurrentHashMap<Class<?>, Map<String, Map<NodeTest, List<NavigatorFunction>>>> navigatorMap = new ConcurrentHashMap<>();
    private static final NameTest WILDCARD_NAMETEST = new NameTest(null, "*");

    @Override
    public void addNavigator(final Class<?> fromType, final List<String> axes, final List<String> names, final NavigatorFunction navigator) {
        for (String axis : axes) {
            for (String name : names) {
                navigatorMap.computeIfAbsent(fromType, k -> new ConcurrentHashMap<>())
                            .computeIfAbsent(axis, k -> new MultiValueListMapImpl<>())
                            .computeIfAbsent(new NameTest(name), k -> new ArrayList<>(Collections.singletonList(navigator)));
            }
        }
    }

    @Override
    public List<NavigatorFunction> lookup(final Class<?> fromType, final NavigationStep step) {
        //--------------------------------------------------------------------------------------------------------------
        // Look for navigator matches in the following order:
        // 1) an exact match navigator on type, over the given axis and on the given name, then
        // 2) an exact match navigator on type, over the given axis and on the wildcard name.
        // 3) As above, but on the supertype hierarchy.
        //--------------------------------------------------------------------------------------------------------------
        TypeTree fromTypeTree = new TypeTree(null, fromType);
        List<NavigatorFunction> navigators = new ArrayList<>();
        for (TreeNode<Class<?>> fromTypeNode : fromTypeTree.postorderIterable()) {
            navigators.addAll(navigatorMap.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                          .getOrDefault(step.getAxis(), emptyMap())
                                          .getOrDefault(step.getNodeTest(), emptyList()));

            if ( step.getNodeTest() instanceof NameTest ) {
                navigators.addAll(navigatorMap.getOrDefault(fromTypeNode.getManagedObject(), emptyMap())
                                              .getOrDefault(step.getAxis(), emptyMap())
                                              .getOrDefault(WILDCARD_NAMETEST, emptyList()));
            }
        }

        return navigators;
    }

    @Override
    public int size() {
        return (int)navigatorMap.values().stream()
                               .flatMap(v -> v.values().stream())
                               .flatMap(v -> v.values().stream())
                               .mapToLong(Collection::size).sum();
    }

    @Override
    public void clear() {
        navigatorMap.clear();
    }
}
