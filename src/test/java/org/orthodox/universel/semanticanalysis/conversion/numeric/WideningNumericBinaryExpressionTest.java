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

package org.orthodox.universel.semanticanalysis.conversion.numeric;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults.BIG_DECIMAL_PRECISION_DP;
import static org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults.BIG_DECIMAL_ROUNDING_MODE;

/**
 * Tests widening promotion across the numeric types spectrum in binary expressions.
 */
public class WideningNumericBinaryExpressionTest {
    @Test
    void sameTypes_singleBinaryAddExpr_areNotPromoted() {
        assertThat(execute("1  + 1"), equalTo(2));
        assertThat(execute("1L + 1L"), equalTo(2L));
        assertThat(execute("1I + 1I"), equalTo(BigInteger.valueOf(2)));
        assertThat(execute("1f + 1f"), equalTo(2f));
        assertThat(execute("1d + 1d"), equalTo(2d));
        assertThat(execute("1D + 1D"), equalTo(BigDecimal.valueOf(2, 0)));
    }

    @Test
    void differentTypes_singleBinaryAddExpr_arePromoted() {
        assertThat(execute("1  + 1L"), equalTo(2L));
        assertThat(execute("1  + 1I"), equalTo(BigInteger.valueOf(2)));
        assertThat(execute("1  + 1f"), equalTo(1 + 1f));
        assertThat(execute("1  + 1d"), equalTo(1 + 1d));
        assertThat(execute("1  + 1D"), equalTo(BigDecimal.valueOf(2, 0)));

        assertThat(execute("1L + 1"), equalTo(2L));
        assertThat(execute("1L + 1I"), equalTo(BigInteger.valueOf(2)));
        assertThat(execute("1L + 1f"), equalTo(1L + 1F));
        assertThat(execute("1L + 1d"), equalTo(2d));
        assertThat(execute("1L + 1D"), equalTo(BigDecimal.valueOf(2, 0)));

        assertThat(execute("1  + 1f"), equalTo(1 + 1f));
        assertThat(execute("1f + 1L"), equalTo(1f + 1L));
        assertThat(execute("1f + 1I"), equalTo(new BigDecimal("2.0")));
        assertThat(execute("1f + 1d"), equalTo(2d));
        assertThat(execute("1f + 1D"), equalTo(new BigDecimal("2.0")));
    }

    @Test
    void differentTypes_singleBinaryDivExpr_arePromoted() {
        assertThat(execute("20d  / 6"), equalTo(20d / 6));
        assertThat(execute("20d  / 6L"), equalTo(20d / 6L));
        assertThat(execute("20d  / 6I"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20d  / 6f"), equalTo(20d / 6f));
        assertThat(execute("20d  / 6d"), equalTo(20d / 6d));
        assertThat(execute("20d  / 6D"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("20D  / 6"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20D  / 6L"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20D  / 6I"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20D  / 6f"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20D  / 6d"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20D  / 6D"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("20f  / 6"), equalTo(20f / 6));
        assertThat(execute("20f  / 6L"), equalTo(20f / 6L));
        assertThat(execute("20f  / 6I"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20f  / 6f"), equalTo(20f / 6f));
        assertThat(execute("20f  / 6d"), equalTo(20f / 6d));
        assertThat(execute("20f  / 6D"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("20  / 6L"), equalTo(20 / 6L));
        assertThat(execute("20  / 6I"), equalTo(BigInteger.valueOf(3)));
        assertThat(execute("20  / 6f"), equalTo(20 / 6f));
        assertThat(execute("20  / 6d"), equalTo(20 / 6d));
        assertThat(execute("20  / 6D"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("20L  / 6"), equalTo(20L / 6));
        assertThat(execute("20L  / 6L"), equalTo(20L / 6L));
        assertThat(execute("20L  / 6I"), equalTo(BigInteger.valueOf(3)));
        assertThat(execute("20L  / 6f"), equalTo(20L / 6f));
        assertThat(execute("20L  / 6d"), equalTo(20L / 6d));
        assertThat(execute("20L  / 6D"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("20I  / 6"), equalTo(BigInteger.valueOf(3)));
        assertThat(execute("20I  / 6L"), equalTo(BigInteger.valueOf(3)));
        assertThat(execute("20I  / 6I"), equalTo(BigInteger.valueOf(3)));
        assertThat(execute("20I  / 6f"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20I  / 6d"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("20I  / 6D"), equalTo(BigDecimal.valueOf(20).divide(BigDecimal.valueOf(6), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
    }
}
