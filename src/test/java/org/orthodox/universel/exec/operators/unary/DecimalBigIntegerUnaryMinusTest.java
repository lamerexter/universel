package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalBigIntegerUnaryMinusTest {
    @Test
    public void literal_operand() {
        assertThat(execute("-0I"), equalTo(BigInteger.valueOf(0).negate()));
        assertThat(execute("-1I"), equalTo(BigInteger.ONE.negate()));
        assertThat(execute("-10I"), equalTo(BigInteger.TEN.negate()));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-100I * 0I"), equalTo(new BigInteger("-100").multiply(new BigInteger("0"))));
        assertThat(execute("-100I * 1I"), equalTo(new BigInteger("-100").multiply(new BigInteger("1"))));
        assertThat(execute("-100I - 10I"), equalTo(new BigInteger("-100").subtract(new BigInteger("10"))));
        assertThat(execute("-100I + 10I"), equalTo(new BigInteger("-100").add(new BigInteger("10"))));
        assertThat(execute("-100I / 10I"), equalTo(new BigInteger("-100").divide(new BigInteger("10"))));

        assertThat(execute("-100I + -10I"), equalTo(new BigInteger("-100").add(new BigInteger("-10"))));
        assertThat(execute("-100I / -10I"), equalTo(new BigInteger("-100").divide(new BigInteger("-10"))));
        assertThat(execute("-100I / 20I"), equalTo(new BigInteger("-100").divide(new BigInteger("20"))));

        assertThat(execute("-2I * (-2I - -1I) * 10I"), equalTo(new BigInteger("-2").multiply(new BigInteger("-2").subtract(new BigInteger("-1"))).multiply(new BigInteger("10"))));
    }
}
