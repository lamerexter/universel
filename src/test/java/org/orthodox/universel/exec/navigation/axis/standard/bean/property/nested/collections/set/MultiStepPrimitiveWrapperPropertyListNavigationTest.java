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

public class MultiStepPrimitiveWrapperPropertyListNavigationTest {
    @Test
    void booleanRead_reduction() {
        // Given
        final Boolean V1 = true;
        final Boolean V2 = false;
        final Boolean V3 = true;
        final Boolean V4 = true;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withBooleanWrapperProperty(V1),
            new BeanWithProperties().withBooleanWrapperProperty(V2),
            new BeanWithProperties().withBooleanWrapperProperty(V3),
            new BeanWithProperties().withBooleanWrapperProperty(V4)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\booleanWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3, V4)), LinkedHashSet.class, Boolean.class);
    }

    @Test
    void byteRead_reduction() {
        // Given
        final Byte V1 = (byte) 11;
        final Byte V2 = (byte) 22;
        final Byte V3 = (byte) 33;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withByteWrapperProperty(V1),
            new BeanWithProperties().withByteWrapperProperty(V2),
            new BeanWithProperties().withByteWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\byteWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Byte.class);
    }

    @Test
    void charRead_reduction() {
        // Given
        final Character V1 = 'a';
        final Character V2 = 'b';
        final Character V3 = 'c';
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withCharWrapperProperty(V1),
            new BeanWithProperties().withCharWrapperProperty(V2),
            new BeanWithProperties().withCharWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\charWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Character.class);
    }

    @Test
    void doubleRead_reduction() {
        // Given
        final Double V1 = 100d;
        final Double V2 = 200d;
        final Double V3 = 300d;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withDoubleWrapperProperty(V1),
            new BeanWithProperties().withDoubleWrapperProperty(V2),
            new BeanWithProperties().withDoubleWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\doubleWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Double.class);
    }

    @Test
    void floatRead_reduction() {
        // Given
        final Float V1 = 100f;
        final Float V2 = 200f;
        final Float V3 = 300f;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withFloatWrapperProperty(V1),
            new BeanWithProperties().withFloatWrapperProperty(V2),
            new BeanWithProperties().withFloatWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\floatWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Float.class);
    }

    @Test
    void intRead_reduction() {
        // Given
        final Integer V1 = 111;
        final Integer V2 = 222;
        final Integer V3 = 333;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withIntWrapperProperty(V1),
            new BeanWithProperties().withIntWrapperProperty(V2),
            new BeanWithProperties().withIntWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\intWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Integer.class);
    }

    @Test
    void longRead_reduction() {
        // Given
        final Long V1 = 111L;
        final Long V2 = 222L;
        final Long V3 = 333L;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withLongWrapperProperty(V1),
            new BeanWithProperties().withLongWrapperProperty(V2),
            new BeanWithProperties().withLongWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\longWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Long.class);
    }

    @Test
    void shortRead_reduction() {
        // Given
        final Short V1 = (short) 1;
        final Short V2 = (short) 2;
        final Short V3 = (short) 3;
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withShortWrapperProperty(V1),
            new BeanWithProperties().withShortWrapperProperty(V2),
            new BeanWithProperties().withShortWrapperProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\shortWrapperProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, Short.class);
    }
}
