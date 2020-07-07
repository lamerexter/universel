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

public class PrimitiveSingleDimensionArrayCreationTest {
    @Test
    void boolean_arrayCreation() {
//        CompiledUnit<?> compiled = compile("new boolean[99]");
//        compiled.getCompiledClassResources().stream().forEach(nv -> IoUtil.transferAndClose(nv.getValue(), new FileResource("d:\\scratch\\generated.class")));
//
//        BytecodeOutput.printClass(compiled.getCompiledClassResources().get(0).getValue().readFullyAsBytes());

        assertThat(execute("new boolean[1]"), instanceOf(boolean[].class));
        assertThat(getLength(execute("new boolean[1]")), equalTo(1));
    }

    @Test
    void byte_arrayCreation() {
        assertThat(execute("new byte[2]"), instanceOf(byte[].class));
        assertThat(getLength(execute("new byte[2]")), equalTo(2));
    }

    @Test
    void char_arrayCreation() {
        assertThat(execute("new char[3]"), instanceOf(char[].class));
        assertThat(getLength(execute("new char[3]")), equalTo(3));
    }

    @Test
    void double_arrayCreation() {
        assertThat(execute("new double[4]"), instanceOf(double[].class));
        assertThat(getLength(execute("new double[4]")), equalTo(4));
    }

    @Test
    void float_arrayCreation() {
        assertThat(execute("new float[5]"), instanceOf(float[].class));
        assertThat(getLength(execute("new float[5]")), equalTo(5));
    }

    @Test
    void int_arrayCreation() {
        assertThat(execute("new int[6]"), instanceOf(int[].class));
        assertThat(getLength(execute("new int[6]")), equalTo(6));
    }

    @Test
    void long_arrayCreation() {
        assertThat(execute("new long[7]"), instanceOf(long[].class));
        assertThat(getLength(execute("new long[7]")), equalTo(7));
    }

    @Test
    void short_arrayCreation() {
        assertThat(execute("new short[8]"), instanceOf(short[].class));
        assertThat(getLength(execute("new short[8]")), equalTo(8));
    }
}
