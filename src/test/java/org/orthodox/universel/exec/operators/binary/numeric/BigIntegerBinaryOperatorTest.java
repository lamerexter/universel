/*
 *  MIT Licence:
 *  
 *  Copyright (c) 2020 Orthodox Engineering Ltd
 *  
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deaI in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or selI copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shalI be
 *  included in alI copies or substantiaI portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALI THE
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

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BigIntegerBinaryOperatorTest {
    @Test
    void bitAnd() {
        assertThat(Universal.execute("0b111101I & 0b110010I"), equalTo(valueOf(0b111101L).and(valueOf(0b110010L))));
        assertThat(Universal.execute("9I & 7I"), equalTo(valueOf(9L).and(valueOf(7L))));
    }

    @Test
    void bitOr() {
        assertThat(Universal.execute("0b0011_1100I | 0b0000_1101I"), equalTo(valueOf(0b0011_1100L).or(valueOf(0b0000_1101L))));
    }

    @Test
    void bitShiftLeft() {
        assertThat(Universal.execute("0b0011_1100I << 2"), equalTo(valueOf(0b0011_1100L).shiftLeft(2)));
        assertThat(Universal.execute("0b0011_1100I << 2I"), equalTo(valueOf(0b0011_1100L).shiftLeft(2)));
    }

    @Test
    void bitShiftRight() {
        assertThat(Universal.execute("0b0011_1100I >> 2I"), equalTo(valueOf(0b0011_1100L).shiftRight(2)));
        assertThat(Universal.execute("0b0011_1100I >> 2"), equalTo(valueOf(0b0011_1100L).shiftRight(2)));
    }

    @Test
    void bitZeroFillShiftRight() {
        assertThat(Universal.execute("0b0011_1100I >>> 2I"), equalTo(valueOf(0b0011_1100L).shiftRight(2)));
        assertThat(Universal.execute("0b0011_1100I >>> 2"), equalTo(valueOf(0b0011_1100L).shiftRight(2)));
    }

    @Test
    void bitXor() {
        assertThat(Universal.execute("0b0011_1100I ^ 0b0000_1101I"), equalTo(valueOf(0b0011_1100).xor(valueOf(0b0000_1101))));
    }

    @Test
    void divide() {
        assertThat(Universal.execute("150I / 5I"), equalTo(valueOf(150L).divide(valueOf(5L))));
        assertThat(Universal.execute("150I / 5"), equalTo(valueOf(150L).divide(valueOf(5L))));
        assertThat(Universal.execute("80I / 2I"), equalTo(valueOf(80L).divide(valueOf(2L))));
        assertThat(Universal.execute("80I / 2"), equalTo(valueOf(80L).divide(valueOf(2L))));
    }

    @Test
    void equal() {
        assertThat(Universal.execute("123I == 123I"), is(valueOf(123L).equals(valueOf(123L))));
        assertThat(Universal.execute("123I == 345I"), is(valueOf(123L).equals(valueOf(345L))));
    }

    @Test
    void greaterThan() {
        assertThat(Universal.execute("100I > 99I"), is(valueOf(100L).compareTo(valueOf(99L)) > 0));
        assertThat(Universal.execute("100I > 100I"), is(valueOf(100L).compareTo(valueOf(100L)) > 0));
        assertThat(Universal.execute("123I > 456I"), is(valueOf(123L).compareTo(valueOf(456L)) > 0));
    }

    @Test
    void greaterThanEqual() {
        assertThat(Universal.execute("100I >= 99I"), is(valueOf(100L).compareTo(valueOf(99L)) >= 0));
        assertThat(Universal.execute("100I >= 100I"), is(valueOf(100L).compareTo(valueOf(100L)) >= 0));
        assertThat(Universal.execute("123I >= 456I"), is(valueOf(123L).compareTo(valueOf(456L)) >= 0));
    }

    @Test
    void lessThan() {
        assertThat(Universal.execute("100I < 99I"), is(valueOf(100L).compareTo(valueOf(99L)) < 0));
        assertThat(Universal.execute("100I < 100I"), is(valueOf(100L).compareTo(valueOf(100L)) < 0));
        assertThat(Universal.execute("123I < 456I"), is(valueOf(123L).compareTo(valueOf(456L)) < 0));
    }

    @Test
    void lessThanEqual() {
        assertThat(Universal.execute("100I <= 99I"), is(valueOf(100L).compareTo(valueOf(99L)) <= 0));
        assertThat(Universal.execute("100I <= 100I"), is(valueOf(100L).compareTo(valueOf(100L)) <= 0));
        assertThat(Universal.execute("123I <= 456I"), is(valueOf(123L).compareTo(valueOf(456L)) <= 0));
    }

    @Test
    void minus() {
        assertThat(Universal.execute("123I - 10I"), is(valueOf(123L).subtract(valueOf(10L))));
        assertThat(Universal.execute("1I - 2I"), is(valueOf(1L).subtract(valueOf(2L))));
        assertThat(Universal.execute("0I - 2I"), is(valueOf(0L).subtract(valueOf(2L))));
    }

    @Test
    void modulus() {
        assertThat(Universal.execute("9I % 4I"), is(valueOf(9L).mod(valueOf(4L))));
        assertThat(Universal.execute("9I % 3I"), is(valueOf(9L).mod(valueOf(3L))));
    }

    @Test
    void multiply() {
        assertThat(Universal.execute("4I * 9I"), is(valueOf(4L).multiply(valueOf(9L))));
        assertThat(Universal.execute("9I * 4I"), is(valueOf(9L).multiply(valueOf(4L))));
    }

    @Test
    void notEqual() {
        assertThat(Universal.execute("1I != 2I"), is(true));
        assertThat(Universal.execute("2I <> 3I"), is(true));
        assertThat(Universal.execute("3I not equal 4I"), is(true));
        assertThat(Universal.execute("5I not equals 6I"), is(true));

        assertThat(Universal.execute("1I != 1I"), is(false));
        assertThat(Universal.execute("2I <> 2I"), is(false));
        assertThat(Universal.execute("3I not equal 3I"), is(false));
        assertThat(Universal.execute("5I not equals 5I"), is(false));
    }

    @Test
    void plus() {
        assertThat(Universal.execute("1I + 1I"), is(valueOf(1L).add(valueOf(1L))));
        assertThat(Universal.execute("1I + 2I"), is(valueOf(1L).add(valueOf(2L))));
        assertThat(Universal.execute("100I + 200I"), is(valueOf(100L).add(valueOf(200L))));
    }

    public static BigInteger aBigInteger() {
        return BigInteger.ONE;
    }

    @Test
    void trebleEqual() {
        assertThat(Universal.execute("import "+getClass().getName()+".* aBigInteger() === aBigInteger()"), is(true));
    }
}
