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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.array;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class MultiStepPrimitiveArrayPropertyNavigationTest {
    @Test
    void booleanRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withBooleanProperty(true),
            new BeanWithProperties().withBooleanProperty(false),
            new BeanWithProperties().withBooleanProperty(true),
            new BeanWithProperties().withBooleanProperty(true)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\booleanProperty\\[[]]", binding), equalTo(new boolean[] {true, false, true, true}));
    }

    @Test
    void byteRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withByteProperty((byte)11),
            new BeanWithProperties().withByteProperty((byte)22),
            new BeanWithProperties().withByteProperty((byte)33)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\byteProperty\\[[]]", binding), equalTo(new byte[] {11, 22, 33}));
    }

    @Test
    void charRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withCharProperty('a'),
            new BeanWithProperties().withCharProperty('b'),
            new BeanWithProperties().withCharProperty('c')
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\charProperty\\[[]]", binding), equalTo(new char[] {'a', 'b', 'c'}));
    }

    @Test
    void doubleRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withDoubleProperty(100d),
            new BeanWithProperties().withDoubleProperty(200d),
            new BeanWithProperties().withDoubleProperty(300d)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\doubleProperty\\[[]]", binding), equalTo(new double[] {100d, 200d, 300d}));
    }

    @Test
    void floatRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withFloatProperty(100f),
            new BeanWithProperties().withFloatProperty(200f),
            new BeanWithProperties().withFloatProperty(300f)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\floatProperty\\[[]]", binding), equalTo(new float[] {100f, 200f, 300f}));
    }

    @Test
    void intRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withIntProperty(111),
            new BeanWithProperties().withIntProperty(222),
            new BeanWithProperties().withIntProperty(333)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\intProperty\\[[]]", binding), equalTo(new int[] {111, 222, 333}));
    }

    @Test
    void longRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withLongProperty(1111L),
            new BeanWithProperties().withLongProperty(2222L),
            new BeanWithProperties().withLongProperty(3333L)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\longProperty\\[[]]", binding), equalTo(new long[] {1111L, 2222L, 3333L}));
    }

    @Test
    void shortRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withShortProperty((short)1),
            new BeanWithProperties().withShortProperty((short)2),
            new BeanWithProperties().withShortProperty((short)3)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\shortProperty\\[[]]", binding), equalTo(new short[] {1, 2, 3}));
    }
}
