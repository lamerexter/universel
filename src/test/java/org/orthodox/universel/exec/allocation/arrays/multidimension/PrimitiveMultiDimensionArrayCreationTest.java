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

package org.orthodox.universel.exec.allocation.arrays.multidimension;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static java.util.Arrays.stream;
import static org.beanplanet.core.lang.TypeUtil.forName;
import static org.beanplanet.core.util.ArrayUtil.determineArraySize;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class PrimitiveMultiDimensionArrayCreationTest {
    @Test
    void boolean_arrayCreation() {
        assertAndCheckArrayCreation(boolean.class, 1, 11, 111);
    }

    @Test
    void byte_arrayCreation() {
        assertAndCheckArrayCreation(byte.class, 2, 22, 22);
    }

    @Test
    void char_arrayCreation() {
        assertAndCheckArrayCreation(char.class, 3, 3, 3);
    }

    @Test
    void double_arrayCreation() {
        assertAndCheckArrayCreation(double.class, 4, 44, 444);
    }

    @Test
    void float_arrayCreation() {
        assertAndCheckArrayCreation(float.class, 5, 55, 555);
    }

    @Test
    void int_arrayCreation() {
        assertAndCheckArrayCreation(int.class, 6, 66, 666);
    }

    @Test
    void long_arrayCreation() {
        assertAndCheckArrayCreation(long.class, 7, 77, 777);
    }

    @Test
    void short_arrayCreation() {
        assertAndCheckArrayCreation(short.class, 8, 88, 888);
    }

    private void assertAndCheckArrayCreation(final Class<?> baseComponentType,
                                             final int ... dimensionSizes) {
        // Given
        final StringBuilder s = new StringBuilder("new ").append(baseComponentType.getSimpleName());
        stream(dimensionSizes).forEach(d -> s.append('[').append(d).append(']'));
        final String arrayCreationScript = s.toString();

        // When
        Object array = execute(arrayCreationScript);

        // Then
        assertThat(array, instanceOf(forName(baseComponentType, dimensionSizes.length)));
        IntStream.range(0, dimensionSizes.length).forEach(dim -> assertThat(determineArraySize(array, dim+1), equalTo(dimensionSizes[dim])));
    }
}
