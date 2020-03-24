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

public class LongBinaryOperatorTest {
    @Test
    void bitAnd() {
        assertThat(Universal.execute("0b111101L & 0b110010L"), equalTo(0b111101L & 0b110010L));
        assertThat(Universal.execute("9L & 7L"), equalTo(9L & 7L));
    }

    @Test
    void bitOr() {
        assertThat(Universal.execute("0b0011_1100L | 0b0000_1101L"), equalTo(0b0011_1100L | 0b0000_1101L));
    }

    @Test
    void bitShiftLeft() {
        assertThat(Universal.execute("0b0011_1100L << 2L"), equalTo(0b0011_1100L << 2L));
    }

    @Test
    void bitShiftRight() {
        assertThat(Universal.execute("0b0011_1100L >> 2L"), equalTo(0b0011_1100L >> 2L));
    }

    @Test
    void bitZeroFillShiftRight() {
        assertThat(Universal.execute("0b0011_1100L >>> 2L"), equalTo(0b0011_1100L >>> 2L));
    }

    @Test
    void bitXor() {
        assertThat(Universal.execute("0b0011_1100L ^ 0b0000_1101L"), equalTo(0b0011_1100L ^ 0b0000_1101L));
    }

    @Test
    void divide() {
        assertThat(Universal.execute("150L / 5L"), equalTo(150L / 5L));
        assertThat(Universal.execute("80L / 2L"), equalTo(80L / 2L));
    }

    @Test
    void equal() {
        assertThat(Universal.execute("123L == 123L"), is(123L == 123L));
        assertThat(Universal.execute("123L == 345L"), is(123L == 345L));
    }

    @Test
    void greaterThan() {
        assertThat(Universal.execute("100L > 99L"), is(100L > 99L));
        assertThat(Universal.execute("100L > 100L"), is(100L > 100L));
        assertThat(Universal.execute("123L > 456L"), is(123L > 456L));
    }

    @Test
    void greaterThanEqual() {
        assertThat(Universal.execute("100L >= 99L"), is(100L >= 99L));
        assertThat(Universal.execute("100L >= 100L"), is(100L >= 100L));
        assertThat(Universal.execute("123L >= 456L"), is(123L >= 456L));
    }

    @Test
    void lessThan() {
        assertThat(Universal.execute("100L < 99L"), is(100L < 99L));
        assertThat(Universal.execute("100L < 100L"), is(100L < 100L));
        assertThat(Universal.execute("123L < 456L"), is(123L < 456L));
    }

    @Test
    void lessThanEqual() {
        assertThat(Universal.execute("100L <= 99L"), is(100L <= 99L));
        assertThat(Universal.execute("100L <= 100L"), is(100L <= 100L));
        assertThat(Universal.execute("123L <= 456L"), is(123L <= 456L));
    }

    @Test
    void minus() {
        assertThat(Universal.execute("123L - 10L"), is(123L - 10L));
        assertThat(Universal.execute("1L - 2L"), is(1L - 2L));
        assertThat(Universal.execute("0L - 2L"), is(0L - 2L));
    }

    @Test
    void modulus() {
        assertThat(Universal.execute("9L % 4L"), is(9L % 4L));
        assertThat(Universal.execute("9L % 3L"), is(9L % 3L));
    }

    @Test
    void multiply() {
        assertThat(Universal.execute("4L * 9L"), is(9L * 4L));
        assertThat(Universal.execute("9L * 4L"), is(4L * 9L));
    }

    @Test
    void notEqual() {
        assertThat(Universal.execute("1L != 2L"), is(true));
        assertThat(Universal.execute("2L <> 3L"), is(true));
        assertThat(Universal.execute("3L not equal 4L"), is(true));
        assertThat(Universal.execute("5L not equals 6L"), is(true));

        assertThat(Universal.execute("1L != 1L"), is(false));
        assertThat(Universal.execute("2L <> 2L"), is(false));
        assertThat(Universal.execute("3L not equal 3L"), is(false));
        assertThat(Universal.execute("5L not equals 5L"), is(false));
    }

    @Test
    void plus() {
        assertThat(Universal.execute("1L + 1L"), is(1L + 1L));
        assertThat(Universal.execute("1L + 2L"), is(1L + 2L));
        assertThat(Universal.execute("100L + 200L"), is(100L + 200L));
    }

    @Test
    void trebleEqual() {
        assertThat(Universal.execute("1L === 1L"), is(true));
        assertThat(Universal.execute("2L === 3L"), is(false));
    }
}
