package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults.BIG_DECIMAL_PRECISION_DP;
import static org.orthodox.universel.exec.operators.binary.impl.numeric.NumericOperatorDefaults.BIG_DECIMAL_ROUNDING_MODE;

public class DecimalBigDecimalUnaryMinusTest {
    @Test
    public void literal_operand() {
        assertThat(execute("-0D"), equalTo(BigDecimal.valueOf(0).negate()));
        assertThat(execute("-1.5D"), equalTo(BigDecimal.valueOf(1.5).negate()));
        assertThat(execute("-10.2D"), equalTo(BigDecimal.valueOf(10.2).negate()));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-100D * 0D"), equalTo(new BigDecimal("-100").multiply(new BigDecimal("0"))));
        assertThat(execute("-100D * 1D"), equalTo(new BigDecimal("-100").multiply(new BigDecimal("1"))));
        assertThat(execute("-100D - 10D"), equalTo(new BigDecimal("-100").subtract(new BigDecimal("10"))));
        assertThat(execute("-100D + 10D"), equalTo(new BigDecimal("-100").add(new BigDecimal("10"))));
        assertThat(execute("-100D / 10D"), equalTo(new BigDecimal("-100").divide(new BigDecimal("10"), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("-100D + -10D"), equalTo(new BigDecimal("-100").add(new BigDecimal("-10"))));
        assertThat(execute("-100D / -10D"), equalTo(new BigDecimal("-100").divide(new BigDecimal("-10"), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));
        assertThat(execute("-100D / 20D"), equalTo(new BigDecimal("-100").divide(new BigDecimal("20"), BIG_DECIMAL_PRECISION_DP, BIG_DECIMAL_ROUNDING_MODE)));

        assertThat(execute("-2D * (-2D - -1D) * 10D"), equalTo(new BigDecimal("-2").multiply(new BigDecimal("-2").subtract(new BigDecimal("-1"))).multiply(new BigDecimal("10"))));
    }
}
