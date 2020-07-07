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

public class MultiStepPrimitiveWrapperArrayPropertyNavigationTest {
    @Test
    void booleanRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withBooleanWrapperProperty(true),
            new BeanWithProperties().withBooleanWrapperProperty(false),
            new BeanWithProperties().withBooleanWrapperProperty(true),
            new BeanWithProperties().withBooleanWrapperProperty(true)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\booleanWrapperProperty\\[[]]", binding), equalTo(new Boolean[] {true, false, true, true}));
    }

    @Test
    void byteRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withByteWrapperProperty((byte)11),
            new BeanWithProperties().withByteWrapperProperty((byte)22),
            new BeanWithProperties().withByteWrapperProperty((byte)33)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\byteWrapperProperty\\[[]]", binding), equalTo(new Byte[] {11, 22, 33}));
    }

    @Test
    void charRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withCharWrapperProperty('a'),
            new BeanWithProperties().withCharWrapperProperty('b'),
            new BeanWithProperties().withCharWrapperProperty('c')
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\charWrapperProperty\\[[]]", binding), equalTo(new Character[] {'a', 'b', 'c'}));
    }

    @Test
    void doubleRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withDoubleWrapperProperty(100d),
            new BeanWithProperties().withDoubleWrapperProperty(200d),
            new BeanWithProperties().withDoubleWrapperProperty(300d)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\doubleWrapperProperty\\[[]]", binding), equalTo(new Double[] {100d, 200d, 300d}));
    }

    @Test
    void floatRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withFloatWrapperProperty(100f),
            new BeanWithProperties().withFloatWrapperProperty(200f),
            new BeanWithProperties().withFloatWrapperProperty(300f)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\floatWrapperProperty\\[[]]", binding), equalTo(new Float[] {100f, 200f, 300f}));
    }

    @Test
    void intRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withIntWrapperProperty(111),
            new BeanWithProperties().withIntWrapperProperty(222),
            new BeanWithProperties().withIntWrapperProperty(333)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\intWrapperProperty\\[[]]", binding), equalTo(new Integer[] {111, 222, 333}));
    }

    @Test
    void longRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withLongWrapperProperty(1111L),
            new BeanWithProperties().withLongWrapperProperty(2222L),
            new BeanWithProperties().withLongWrapperProperty(3333L)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\longWrapperProperty\\[[]]", binding), equalTo(new Long[] {1111L, 2222L, 3333L}));
    }

    @Test
    void shortRead_reduction() {
        // Given
        final BeanWithProperties[] array = {
            new BeanWithProperties().withShortWrapperProperty((short)1),
            new BeanWithProperties().withShortWrapperProperty((short)2),
            new BeanWithProperties().withShortWrapperProperty((short)3)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(array);

        // Then
        assertThat(execute("referenceArrayProperty\\shortWrapperProperty\\[[]]", binding), equalTo(new Short[] {1, 2, 3}));
    }
}
