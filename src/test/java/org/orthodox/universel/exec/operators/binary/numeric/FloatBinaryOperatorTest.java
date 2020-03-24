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

package org.orthodox.universel.exec.operators.binary.numeric;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FloatBinaryOperatorTest {
    @Test
    void divide() {
        assertThat(Universal.execute("150f / 5f"), equalTo(150f / 5f));
        assertThat(Universal.execute("80f / 2f"), equalTo(80f / 2f));
    }

    @Test
    void equal() {
        assertThat(Universal.execute("123f == 123f"), is(123f == 123f));
        assertThat(Universal.execute("123f == 345f"), is(123f == 345f));
    }

    @Test
    void greaterThan() {
        assertThat(Universal.execute("100f > 99f"), is(100f > 99f));
        assertThat(Universal.execute("100f > 100f"), is(100f > 100f));
        assertThat(Universal.execute("123f > 456f"), is(123f > 456f));
    }

    @Test
    void greaterThanEqual() {
        assertThat(Universal.execute("100f >= 99f"), is(100f >= 99f));
        assertThat(Universal.execute("100f >= 100f"), is(100f >= 100f));
        assertThat(Universal.execute("123f >= 456f"), is(123f >= 456f));
    }

    @Test
    void lessThan() {
        assertThat(Universal.execute("100f < 99f"), is(100f < 99f));
        assertThat(Universal.execute("100f < 100f"), is(100f < 100f));
        assertThat(Universal.execute("123f < 456f"), is(123f < 456f));
    }

    @Test
    void lessThanEqual() {
        assertThat(Universal.execute("100f <= 99f"), is(100f <= 99f));
        assertThat(Universal.execute("100f <= 100f"), is(100f <= 100f));
        assertThat(Universal.execute("123f <= 456f"), is(123f <= 456f));
    }

    @Test
    void minus() {
        assertThat(Universal.execute("123f - 10f"), is(123f - 10f));
        assertThat(Universal.execute("1f - 2f"), is(1f - 2f));
        assertThat(Universal.execute("0f - 2f"), is(0f - 2f));
    }

    @Test
    void modulus() {
        assertThat(Universal.execute("9f % 4f"), is(9f % 4f));
        assertThat(Universal.execute("9f % 3f"), is(9f % 3f));
    }

    @Test
    void multiply() {
        assertThat(Universal.execute("4f * 9f"), is(9f * 4f));
        assertThat(Universal.execute("9f * 4f"), is(4f * 9f));
    }

    @Test
    void notEqual() {
        assertThat(Universal.execute("1f != 2f"), is(true));
        assertThat(Universal.execute("2f <> 3f"), is(true));
        assertThat(Universal.execute("3f not equal 4f"), is(true));
        assertThat(Universal.execute("5f not equals 6f"), is(true));

        assertThat(Universal.execute("1f != 1f"), is(false));
        assertThat(Universal.execute("2f <> 2f"), is(false));
        assertThat(Universal.execute("3f not equal 3f"), is(false));
        assertThat(Universal.execute("5f not equals 5f"), is(false));
    }

    @Test
    void plus() {
        assertThat(Universal.execute("1f + 1f"), is(1f + 1f));
        assertThat(Universal.execute("1f + 2f"), is(1f + 2f));
        assertThat(Universal.execute("100f + 200f"), is(100f + 200f));
    }

    @Test
    void trebleEqual() {
        assertThat(Universal.execute("1f === 1f"), is(true));
        assertThat(Universal.execute("2f === 3f"), is(false));
    }
}
