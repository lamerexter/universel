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

package org.orthodox.universel.exec.allocation.arrays.singledimension;

import org.junit.jupiter.api.Test;

import static java.lang.reflect.Array.getLength;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.orthodox.universel.Universal.execute;

public class PrimitiveWrapperSingleDimensionArrayCreationTest {
    @Test
    void Boolean_arrayCreation() {
        assertThat(execute("new Boolean[1]"), instanceOf(Boolean[].class));
        assertThat(getLength(execute("new Boolean[1]")), equalTo(1));
    }

    @Test
    void Byte_arrayCreation() {
        assertThat(execute("new Byte[2]"), instanceOf(Byte[].class));
        assertThat(getLength(execute("new Byte[2]")), equalTo(2));
    }

    @Test
    void Character_arrayCreation() {
        assertThat(execute("new Character[3]"), instanceOf(Character[].class));
        assertThat(getLength(execute("new Character[3]")), equalTo(3));
    }

    @Test
    void Double_arrayCreation() {
        assertThat(execute("new Double[4]"), instanceOf(Double[].class));
        assertThat(getLength(execute("new Double[4]")), equalTo(4));
    }

    @Test
    void Float_arrayCreation() {
        assertThat(execute("new Float[5]"), instanceOf(Float[].class));
        assertThat(getLength(execute("new Float[5]")), equalTo(5));
    }

    @Test
    void Integer_arrayCreation() {
        assertThat(execute("new Integer[6]"), instanceOf(Integer[].class));
        assertThat(getLength(execute("new Integer[6]")), equalTo(6));
    }

    @Test
    void Long_arrayCreation() {
        assertThat(execute("new Long[7]"), instanceOf(Long[].class));
        assertThat(getLength(execute("new Long[7]")), equalTo(7));
    }

    @Test
    void Short_arrayCreation() {
        assertThat(execute("new Short[8]"), instanceOf(Short[].class));
        assertThat(getLength(execute("new Short[8]")), equalTo(8));
    }
}
