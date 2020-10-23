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

package org.orthodox.universel.exec.assignment.simple.local.nonstat;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

/**
 * Tests that non-static Script method local variables can be assigned successfully.
 */
public class ScriptNonStaticMethodLocalAssignmentTest {
    @Test
    void primitive_local_boolean_assignedSuccessfully() {
        assertThat(execute("boolean toggleIt(boolean b) { b = !b b } toggleIt(false)"), is(true));
    }

    @Test
    void primitive_local_byte_assignedSuccessfully() {
        assertThat(execute("byte add1(byte b) { b = b + 1 b } add1(Byte('10').byteValue())"), equalTo((byte)11));
    }

    @Test
    void primitive_local_char_assignedSuccessfully() {
        assertThat(execute("char add1(char b) { b = b + 1 b } add1(String('a').charAt(0))"), equalTo((char)('a'+1)));
    }

    @Test
    void primitive_local_double_assignedSuccessfully() {
        assertThat(execute("double add1(double b) { b = b + 1 b } add1(10d)"), equalTo(11d));
    }

    @Test
    void primitive_local_float_assignedSuccessfully() {
        assertThat(execute("float add1(float b) { b = b + 1 b } add1(10f)"), equalTo(11f));
    }

    @Test
    void primitive_local_int_assignedSuccessfully() {
        assertThat(execute("int add1(int b) { b = b + 1 b } add1(10)"), equalTo(11));
    }

    @Test
    void primitive_local_long_assignedSuccessfully() {
        assertThat(execute("long add1(long b) { b = b + 1 b } add1(10L)"), equalTo(11L));
    }

    @Test
    void primitive_local_short_assignedSuccessfully() {
        assertThat(execute("short add1(short b) { b = b + 1 b } add1(Short('10').shortValue())"), equalTo((short)11));
    }

    @Test
    void referenceType_local_assignedSuccessfully() {
        assertThat(execute("Object doIt(String b) { b = b + ' World!' b } doIt('Hello')"), equalTo("Hello World!"));
    }
}
