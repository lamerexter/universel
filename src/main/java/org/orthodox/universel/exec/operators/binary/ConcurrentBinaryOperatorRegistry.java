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
package org.orthodox.universel.exec.operators.binary;

import org.beanplanet.core.lang.TypeTree;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.models.tree.TreeNode;
import org.beanplanet.core.util.MultiValueListMapImpl;
import org.orthodox.universel.cst.Operator;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

/**
 * A highly concurrent implementation of registry of binary operator functions.
 */
public class ConcurrentBinaryOperatorRegistry implements BinaryOperatorRegistry {
    private ConcurrentHashMap<Operator, Map<Class<?>, Map<Class<?>, List<Method>>>> operatorMap = new ConcurrentHashMap<>();

    @Override
    public void addOperatorMethod(final Method binaryOperatorMethod) {
        final BinaryOperator binaryOperatorAnnotation = binaryOperatorMethod.getAnnotation(BinaryOperator.class);
        stream(binaryOperatorAnnotation.value())
              .forEach(operator ->
                               operatorMap.computeIfAbsent(operator, operator1 -> new ConcurrentHashMap<>())
                                          .computeIfAbsent(binaryOperatorMethod.getParameterTypes()[0],  lhsClass -> new MultiValueListMapImpl<>())
                                          .computeIfAbsent(binaryOperatorMethod.getParameterTypes()[1], rhsClass -> new ArrayList<>(Collections.singletonList(binaryOperatorMethod)))
              );
    }

    @Override
    public Optional<Method> lookup(Operator operator, Class<?> lhsType, Class<?> rhsType) {
        Optional<Method> found = operatorMap.getOrDefault(operator, emptyMap()).getOrDefault(lhsType, emptyMap()).getOrDefault(rhsType, emptyList()).stream().findFirst();
        if ( found.isPresent() ) return found;

        //--------------------------------------------------------------------------------------------------------------
        // Look for generic implementation of lhs and rhs types.
        //--------------------------------------------------------------------------------------------------------------
        TypeTree lhsTypeTree = new TypeTree(null, lhsType);
        TypeTree rhsTypeTree = new TypeTree(null, rhsType);

        for (TreeNode<Class<?>> lhsGenericType : lhsTypeTree.postorderIterable()) {
            for (TreeNode<Class<?>> rhsGenericType : rhsTypeTree.postorderIterable()) {
                found = operatorMap.getOrDefault(operator, emptyMap()).getOrDefault(lhsGenericType.getManagedObject(), emptyMap()).getOrDefault(rhsGenericType.getManagedObject(), emptyList()).stream().findFirst();
                if ( found.isPresent() ) return found;
            }
        }

        return Optional.empty();
    }

    @Override
    public int size() {
        return (int)operatorMap.values().stream()
                               .flatMap(v -> v.values().stream())
                               .flatMap(v -> v.values().stream())
                               .mapToLong(Collection::size).sum();
    }

    @Override
    public void clear() {
        operatorMap.clear();
    }
}
