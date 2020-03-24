/*
 *  MIT Licence:
 *  
 *  Copyright (c) 2020 Orthodox Engineering Ltd
 *  
 *  Permission is hereby granted, free od charge, to any person
 *  obtaining a copy od this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies od the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions od the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY Od ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES Od MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT Od OR IN
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

public class DoubleBinaryOperatorTest {
    @Test
    void divide() {
        assertThat(Universal.execute("150d / 5d"), equalTo(150d / 5d));
        assertThat(Universal.execute("80d / 2d"), equalTo(80d / 2d));
    }

    @Test
    void equal() {
        assertThat(Universal.execute("123d == 123d"), is(123d == 123d));
        assertThat(Universal.execute("123d == 345d"), is(123d == 345d));
    }

    @Test
    void greaterThan() {
        assertThat(Universal.execute("100d > 99d"), is(100d > 99d));
        assertThat(Universal.execute("100d > 100d"), is(100d > 100d));
        assertThat(Universal.execute("123d > 456d"), is(123d > 456d));
    }

    @Test
    void greaterThanEqual() {
        assertThat(Universal.execute("100d >= 99d"), is(100d >= 99d));
        assertThat(Universal.execute("100d >= 100d"), is(100d >= 100d));
        assertThat(Universal.execute("123d >= 456d"), is(123d >= 456d));
    }

    @Test
    void lessThan() {
        assertThat(Universal.execute("100d < 99d"), is(100d < 99d));
        assertThat(Universal.execute("100d < 100d"), is(100d < 100d));
        assertThat(Universal.execute("123d < 456d"), is(123d < 456d));
    }

    @Test
    void lessThanEqual() {
        assertThat(Universal.execute("100d <= 99d"), is(100d <= 99d));
        assertThat(Universal.execute("100d <= 100d"), is(100d <= 100d));
        assertThat(Universal.execute("123d <= 456d"), is(123d <= 456d));
    }

    @Test
    void minus() {
        assertThat(Universal.execute("123d - 10d"), is(123d - 10d));
        assertThat(Universal.execute("1d - 2d"), is(1d - 2d));
        assertThat(Universal.execute("0d - 2d"), is(0d - 2d));
    }

    @Test
    void modulus() {
        assertThat(Universal.execute("9d % 4d"), is(9d % 4d));
        assertThat(Universal.execute("9d % 3d"), is(9d % 3d));
    }

    @Test
    void multiply() {
        assertThat(Universal.execute("4d * 9d"), is(9d * 4d));
        assertThat(Universal.execute("9d * 4d"), is(4d * 9d));
    }

    @Test
    void notEqual() {
        assertThat(Universal.execute("1d != 2d"), is(true));
        assertThat(Universal.execute("2d <> 3d"), is(true));
        assertThat(Universal.execute("3d not equal 4d"), is(true));
        assertThat(Universal.execute("5d not equals 6d"), is(true));

        assertThat(Universal.execute("1d != 1d"), is(false));
        assertThat(Universal.execute("2d <> 2d"), is(false));
        assertThat(Universal.execute("3d not equal 3d"), is(false));
        assertThat(Universal.execute("5d not equals 5d"), is(false));
    }

    @Test
    void plus() {
        assertThat(Universal.execute("1d + 1d"), is(1d + 1d));
        assertThat(Universal.execute("1d + 2d"), is(1d + 2d));
        assertThat(Universal.execute("100d + 200d"), is(100d + 200d));
    }

    @Test
    void trebleEqual() {
        assertThat(Universal.execute("1d === 1d"), is(true));
        assertThat(Universal.execute("2d === 3d"), is(false));
    }
}
