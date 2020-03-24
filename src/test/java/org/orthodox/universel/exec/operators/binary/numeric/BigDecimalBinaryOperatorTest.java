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
import org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.math.BigDecimal.valueOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults.BIG_DECIMAL_PRECISION_DP;
import static org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults.BIG_DECIMAL_ROUNDING_MODE;

public class BigDecimalBinaryOperatorTest {
    @Test
    void divide() {
        assertThat(Universal.execute("150D / 5D"), equalTo(valueOf(150L).divide(valueOf(5L), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(Universal.execute("150D / 5"), equalTo(valueOf(150L).divide(valueOf(5L), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(Universal.execute("80D / 2D"), equalTo(valueOf(80L).divide(valueOf(2L), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(Universal.execute("80D / 2"), equalTo(valueOf(80L).divide(valueOf(2L), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
    }

    @Test
    void equal() {
        assertThat(Universal.execute("123D == 123D"), is(valueOf(123L).equals(valueOf(123L))));
        assertThat(Universal.execute("123D == 345D"), is(valueOf(123L).equals(valueOf(345L))));
    }

    @Test
    void greaterThan() {
        assertThat(Universal.execute("100D > 99D"), is(valueOf(100L).compareTo(valueOf(99L)) > 0));
        assertThat(Universal.execute("100D > 100D"), is(valueOf(100L).compareTo(valueOf(100L)) > 0));
        assertThat(Universal.execute("123D > 456D"), is(valueOf(123L).compareTo(valueOf(456L)) > 0));
    }

    @Test
    void greaterThanEqual() {
        assertThat(Universal.execute("100D >= 99D"), is(valueOf(100L).compareTo(valueOf(99L)) >= 0));
        assertThat(Universal.execute("100D >= 100D"), is(valueOf(100L).compareTo(valueOf(100L)) >= 0));
        assertThat(Universal.execute("123D >= 456D"), is(valueOf(123L).compareTo(valueOf(456L)) >= 0));
    }

    @Test
    void lessThan() {
        assertThat(Universal.execute("100D < 99D"), is(valueOf(100L).compareTo(valueOf(99L)) < 0));
        assertThat(Universal.execute("100D < 100D"), is(valueOf(100L).compareTo(valueOf(100L)) < 0));
        assertThat(Universal.execute("123D < 456D"), is(valueOf(123L).compareTo(valueOf(456L)) < 0));
    }

    @Test
    void lessThanEqual() {
        assertThat(Universal.execute("100D <= 99D"), is(valueOf(100L).compareTo(valueOf(99L)) <= 0));
        assertThat(Universal.execute("100D <= 100D"), is(valueOf(100L).compareTo(valueOf(100L)) <= 0));
        assertThat(Universal.execute("123D <= 456D"), is(valueOf(123L).compareTo(valueOf(456L)) <= 0));
    }

    @Test
    void minus() {
        assertThat(Universal.execute("123D - 10D"), is(valueOf(123L).subtract(valueOf(10L))));
        assertThat(Universal.execute("1D - 2D"), is(valueOf(1L).subtract(valueOf(2L))));
        assertThat(Universal.execute("0D - 2D"), is(valueOf(0L).subtract(valueOf(2L))));
    }

    @Test
    void modulus() {
        assertThat(Universal.execute("9D % 4D"), is(new BigDecimal(valueOf(9L).toBigInteger().mod(valueOf(4L).toBigInteger()))));
        assertThat(Universal.execute("9D % 3D"), is(new BigDecimal(valueOf(9L).toBigInteger().mod(valueOf(3L).toBigInteger()))));
    }

    @Test
    void multiply() {
        assertThat(Universal.execute("4D * 9D"), is(valueOf(4L).multiply(valueOf(9L))));
        assertThat(Universal.execute("9D * 4D"), is(valueOf(9L).multiply(valueOf(4L))));
    }

    @Test
    void notEqual() {
        assertThat(Universal.execute("1D != 2D"), is(true));
        assertThat(Universal.execute("2D <> 3D"), is(true));
        assertThat(Universal.execute("3D not equal 4D"), is(true));
        assertThat(Universal.execute("5D not equals 6D"), is(true));

        assertThat(Universal.execute("1D != 1D"), is(false));
        assertThat(Universal.execute("2D <> 2D"), is(false));
        assertThat(Universal.execute("3D not equal 3D"), is(false));
        assertThat(Universal.execute("5D not equals 5D"), is(false));
    }

    @Test
    void plus() {
        assertThat(Universal.execute("1D + 1D"), is(valueOf(1L).add(valueOf(1L))));
        assertThat(Universal.execute("1D + 2D"), is(valueOf(1L).add(valueOf(2L))));
        assertThat(Universal.execute("100D + 200D"), is(valueOf(100L).add(valueOf(200L))));
    }

    public static BigInteger aBigDecimal() {
        return BigInteger.ONE;
    }

    @Test
    void trebleEqual() {
        assertThat(Universal.execute("import "+getClass().getName()+".* aBigDecimal() === aBigDecimal()"), is(true));
    }
}
