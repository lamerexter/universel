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

package org.orthodox.universel.exec.navigation.impl.map;

import org.beanplanet.core.beans.TypePropertiesSource;
import org.orthodox.universel.ast.InstanceMethodCall;
import org.orthodox.universel.ast.MethodCall;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationAxisAndNodeTest;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.exec.navigation.MappingNavigator;
import org.orthodox.universel.exec.navigation.MethodNavigator;
import org.orthodox.universel.exec.navigation.Navigator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.PROTECTED;
import static java.lang.reflect.Modifier.PUBLIC;
import static org.beanplanet.core.lang.TypeUtil.streamMethods;
import static org.orthodox.universel.ast.TypeInferenceUtil.resolveType;

@Navigator
public class UniversalBeanNavigators {
    @MappingNavigator(axis = "default", name = "*")
    public static Node universalPropertyNavigator(final Class<?> fromType,
                                                  final Node instanceReadAccessor,
                                                  final NavigationAxisAndNodeTest<NameTest> step) {
        TypePropertiesSource<?> properties = new TypePropertiesSource<>(fromType);
        if (!properties.isReadableProperty(step.getNodeTest().getName())) return step;

        PropertyDescriptor propertyDescriptor = properties.assertAndGetReadablePropertyDescriptor(step.getNodeTest().getName());
        Method readMethod = propertyDescriptor.getReadMethod();
        return new InstanceMethodCall(step.getTokenImage(),
                                      instanceReadAccessor,
                                      new ResolvedTypeReferenceOld(step.getTokenImage(), readMethod.getDeclaringClass()),
                                      (TypeReference) resolveType(readMethod.getGenericReturnType()),
                                      readMethod.getName()
        );
    }

    @MethodNavigator()
    public static Node universalMethodNavigator(final Class<?> fromType,
                                                final Node instanceReadAccessor,
                                                final NavigationAxisAndNodeTest<MethodCall> step) {
        final List<Method> matchingMethods = streamMethods(fromType, PUBLIC | PROTECTED, step.getNodeTest().getName().getName(), null, (Class<?>[]) null)
                                                 .filter(m -> parameterTypesCompatible(step.getNodeTest(), m))
                                                 .collect(Collectors.toList());
        if (matchingMethods.size() != 1) return step;
        final Method matchingMethod = matchingMethods.get(0);
        return new InstanceMethodCall(step.getTokenImage(),
                                      instanceReadAccessor,
                                      new ResolvedTypeReferenceOld(step.getTokenImage(), matchingMethod.getDeclaringClass()),
                                      (TypeReference) resolveType(matchingMethod.getGenericReturnType()),
                                      matchingMethod.getName(),
                                      Arrays.stream(matchingMethod.getParameterTypes()).map(TypeReference::forClass).collect(Collectors.toList()),
                                      step.getNodeTest().getParameters()
        );
    }

    private static boolean parameterTypesCompatible(MethodCall methodCall, Method method) {
        if (methodCall.getParameters().size() != method.getParameters().length) return false;

        for (int n = 0; n < methodCall.getParameters().size(); n++) {
            if (!parameterTypesMatch(methodCall.getParameters().get(n).getType().getTypeClass(), method.getParameterTypes()[n])) return false;
        }

        return true;
    }

    private static boolean parameterTypesMatch(Class<?> caller, Class<?> callee) {
        return callee.isAssignableFrom(caller);
    }
}
