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
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.exec.navigation.MappingNavigator;
import org.orthodox.universel.exec.navigation.Navigator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

@Navigator
public class UniversalBeanNavigators {
    @MappingNavigator(axis = "default", name="*")
    public static Node propertyNavigator(final Class<?> fromType, final NavigationStep<NameTest> step) {
        TypePropertiesSource<?> properties = new TypePropertiesSource<>(fromType);
        if ( !properties.isReadableProperty(step.getNodeTest().getName()) ) return step;

        PropertyDescriptor propertyDescriptor = properties.assertAndGetReadablePropertyDescriptor(step.getNodeTest().getName());
        Method readMethod = propertyDescriptor.getReadMethod();
        return new InstanceMethodCall(readMethod.getReturnType(), step.getTokenImage(), readMethod.getDeclaringClass(), readMethod.getName());
    }
}
