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

public class IntegerBinaryOperatorTest {
    @Test
    void bitAnd() {
        assertThat(Universal.execute("0b111101 & 0b110010"), equalTo(0b111101 & 0b110010));
        assertThat(Universal.execute("9 & 7"), equalTo(9 & 7));
    }

    @Test
    void bitOr() {
        assertThat(Universal.execute("0b0011_1100 | 0b0000_1101"), equalTo(0b0011_1100 | 0b0000_1101));
    }

    @Test
    void bitShiftLeft() {
        assertThat(Universal.execute("0b0011_1100 << 2"), equalTo(0b0011_1100 << 2));
    }

    @Test
    void bitShiftRight() {
        assertThat(Universal.execute("0b0011_1100 >> 2"), equalTo(0b0011_1100 >> 2));
    }

    @Test
    void bitZeroFillShiftRight() {
        assertThat(Universal.execute("0b0011_1100 >>> 2"), equalTo(0b0011_1100 >>> 2));
    }

    @Test
    void bitXor() {
        assertThat(Universal.execute("0b0011_1100 ^ 0b0000_1101"), equalTo(0b0011_1100 ^ 0b0000_1101));
    }

    @Test
    void divide() {
        assertThat(Universal.execute("150 / 5"), equalTo(150 / 5));
        assertThat(Universal.execute("80 / 2"), equalTo(80 / 2));
    }

    @Test
    void equal() {
        assertThat(Universal.execute("123 == 123"), is(123 == 123));
        assertThat(Universal.execute("123 == 345"), is(123 == 345));
    }

    @Test
    void greaterThan() {
        assertThat(Universal.execute("100 > 99"), is(100 > 99));
        assertThat(Universal.execute("100 > 100"), is(100 > 100));
        assertThat(Universal.execute("123 > 456"), is(123 > 456));
    }

    @Test
    void greaterThanEqual() {
        assertThat(Universal.execute("100 >= 99"), is(100 >= 99));
        assertThat(Universal.execute("100 >= 100"), is(100 >= 100));
        assertThat(Universal.execute("123 >= 456"), is(123 >= 456));
    }

    @Test
    void lessThan() {
        assertThat(Universal.execute("100 < 99"), is(100 < 99));
        assertThat(Universal.execute("100 < 100"), is(100 < 100));
        assertThat(Universal.execute("123 < 456"), is(123 < 456));
    }

    @Test
    void lessThanEqual() {
        assertThat(Universal.execute("100 <= 99"), is(100 <= 99));
        assertThat(Universal.execute("100 <= 100"), is(100 <= 100));
        assertThat(Universal.execute("123 <= 456"), is(123 <= 456));
    }

    @Test
    void minus() {
        assertThat(Universal.execute("123 - 10"), is(123 - 10));
        assertThat(Universal.execute("1 - 2"), is(1 - 2));
        assertThat(Universal.execute("0 - 2"), is(0 - 2));
    }

    @Test
    void modulus() {
        assertThat(Universal.execute("9 % 4"), is(9 % 4));
        assertThat(Universal.execute("9 % 3"), is(9 % 3));
    }

    @Test
    void multiply() {
        assertThat(Universal.execute("4 * 9"), is(9 * 4));
        assertThat(Universal.execute("9 * 4"), is(4 * 9));
    }

    @Test
    void notEqual() {
        assertThat(Universal.execute("1 != 2"), is(true));
        assertThat(Universal.execute("2 <> 3"), is(true));
        assertThat(Universal.execute("3 not equal 4"), is(true));
        assertThat(Universal.execute("5 not equals 6"), is(true));

        assertThat(Universal.execute("1 != 1"), is(false));
        assertThat(Universal.execute("2 <> 2"), is(false));
        assertThat(Universal.execute("3 not equal 3"), is(false));
        assertThat(Universal.execute("5 not equals 5"), is(false));
    }

    @Test
    void plus() {
        assertThat(Universal.execute("1 + 1"), is(1 + 1));
        assertThat(Universal.execute("1 + 2"), is(1 + 2));
        assertThat(Universal.execute("100 + 200"), is(100 + 200));
    }

    @Test
    void trebleEqual() {
        assertThat(Universal.execute("1 === 1"), is(true));
        assertThat(Universal.execute("2 === 3"), is(false));
    }
}
