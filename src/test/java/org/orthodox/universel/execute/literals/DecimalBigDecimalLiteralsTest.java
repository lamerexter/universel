package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import java.math.BigDecimal;

import static java.lang.Double.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecimalBigDecimalLiteralsTest {
    @Test
    public void bigDecimal0Literal() {
        assertThat(Universal.execute("0D"), equalTo(new BigDecimal("0")));
    }

    @Test
    public void bigDecimal1Literal() {
        assertThat(Universal.execute("1D"), equalTo(new BigDecimal("1")));
    }

    @Test
    public void bigDecimal2Literal() {
        assertThat(Universal.execute("2D"), equalTo(new BigDecimal("2")));
    }

    @Test
    public void bigDecimal3Literal() {
        assertThat(Universal.execute("3D"), equalTo(new BigDecimal("3")));
    }

    @Test
    public void bigDecimal4Literal() {
        assertThat(Universal.execute("4D"), equalTo(new BigDecimal("4")));
    }

    @Test
    public void bigDecimal5Literal() {
        assertThat(Universal.execute("5D"), equalTo(new BigDecimal("5")));
    }

    @Test
    public void bigDecimal10Literal() {
        assertThat(Universal.execute("10D"), equalTo(new BigDecimal("10")));
    }

    @Test
    public void bigDecimalDoubleMaxLiteral() {
        assertThat(Universal.execute(Double.toString(MAX_VALUE)+"D"), equalTo(new BigDecimal(Double.toString(MAX_VALUE))));
    }

    @Test
    public void bigDecimalLargerThanDoubleMaxLiteral() {
        assertThat(Universal.execute("3.1415926535e400D"), equalTo(new BigDecimal("3.1415926535e400")));
    }

    @Test
    public void negativeBigDecimal0Literal() {
        assertThat(Universal.execute("-0D"), equalTo(new BigDecimal("-0")));
    }

    @Test
    public void negativeBigDecimal1Literal() {
        assertThat(Universal.execute("-1D"), equalTo(new BigDecimal("-1")));
    }

    @Test
    public void negativeBigDecimal2Literal() {
        assertThat(Universal.execute("-2D"), equalTo(new BigDecimal("-2")));
    }

    @Test
    public void negativeBigDecimal3Literal() {
        assertThat(Universal.execute("-3D"), equalTo(new BigDecimal("-3")));
    }

    @Test
    public void negativeBigDecimal4Literal() {
        assertThat(Universal.execute("-4D"), equalTo(new BigDecimal("-4")));
    }

    @Test
    public void negativeBigDecimal5Literal() {
        assertThat(Universal.execute("-5D"), equalTo(new BigDecimal("-5")));
    }

    @Test
    public void negativeBigDecimal10Literal() {
        assertThat(Universal.execute("-10D"), equalTo(new BigDecimal("-10")));
    }

    @Test
    public void negativeBigDecimalDoubleMaxLiteral() {
        assertThat(Universal.execute("-"+Double.toString(MAX_VALUE)+"D"), equalTo(new BigDecimal("-"+Double.toString(MAX_VALUE))));
    }

    @Test
    public void negativeBigDecimalLargerThanDoubleMaxLiteral() {
        assertThat(Universal.execute("-3.1415926535e400D"), equalTo(new BigDecimal("-3.1415926535e400")));
    }
}
