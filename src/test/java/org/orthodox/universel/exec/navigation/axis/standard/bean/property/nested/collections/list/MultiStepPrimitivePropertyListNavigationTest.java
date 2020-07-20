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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.collections.list;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.exec.TypedValue;

import java.util.List;

import static java.util.Arrays.asList;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedType;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiStepPrimitivePropertyListNavigationTest {
    @Test
    void booleanRead_reduction() {
        // Given
        final boolean V1 = true;
        final boolean V2 = false;
        final boolean V3 = true;
        final boolean V4 = true;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withBooleanProperty(V1),
            new BeanWithProperties().withBooleanProperty(V2),
            new BeanWithProperties().withBooleanProperty(V3),
            new BeanWithProperties().withBooleanProperty(V4)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\booleanProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3, V4), List.class, Boolean.class);
    }

    @Test
    void byteRead_reduction() {
        // Given
        final byte V1 = (byte) 11;
        final byte V2 = (byte) 22;
        final byte V3 = (byte) 33;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withByteProperty(V1),
            new BeanWithProperties().withByteProperty(V2),
            new BeanWithProperties().withByteProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\byteProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Byte.class);
    }

    @Test
    void charRead_reduction() {
        // Given
        final char V1 = 'a';
        final char V2 = 'b';
        final char V3 = 'c';
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withCharProperty(V1),
            new BeanWithProperties().withCharProperty(V2),
            new BeanWithProperties().withCharProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\charProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Character.class);
    }

    @Test
    void doubleRead_reduction() {
        // Given
        final double V1 = 100d;
        final double V2 = 200d;
        final double V3 = 300d;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withDoubleProperty(V1),
            new BeanWithProperties().withDoubleProperty(V2),
            new BeanWithProperties().withDoubleProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\doubleProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Double.class);
    }

    @Test
    void floatRead_reduction() {
        // Given
        final float V1 = 100f;
        final float V2 = 200f;
        final float V3 = 300f;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withFloatProperty(V1),
            new BeanWithProperties().withFloatProperty(V2),
            new BeanWithProperties().withFloatProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\floatProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Float.class);
    }

    @Test
    void intRead_reduction() {
        // Given
        final int V1 = 111;
        final int V2 = 222;
        final int V3 = 333;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withIntProperty(V1),
            new BeanWithProperties().withIntProperty(V2),
            new BeanWithProperties().withIntProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\intProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Integer.class);
    }

    @Test
    void longRead_reduction() {
        // Given
        final long V1 = 111;
        final long V2 = 222;
        final long V3 = 333;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withLongProperty(V1),
            new BeanWithProperties().withLongProperty(V2),
            new BeanWithProperties().withLongProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\longProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Long.class);
    }

    @Test
    void shortRead_reduction() {
        // Given
        final short V1 = (short) 1;
        final short V2 = (short) 2;
        final short V3 = (short) 3;
        final List<BeanWithProperties> list = asList(
            new BeanWithProperties().withShortProperty(V1),
            new BeanWithProperties().withShortProperty(V2),
            new BeanWithProperties().withShortProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(list);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\shortProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, Short.class);
    }
}
