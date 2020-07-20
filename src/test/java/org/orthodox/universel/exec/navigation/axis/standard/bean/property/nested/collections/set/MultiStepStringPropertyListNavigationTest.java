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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.collections.set;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.exec.TypedValue;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedType;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiStepStringPropertyListNavigationTest {
    @Test
    void read_reduction() {
        // Given
        final String V1 = "Hello";
        final String V2 = "World";
        final Set<BeanWithProperties> list = new LinkedHashSet<>(asList(
            new BeanWithProperties().withStringProperty(V1),
            new BeanWithProperties().withStringProperty(V2)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(list);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\stringProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2)), LinkedHashSet.class, String.class);
    }
}
