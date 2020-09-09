package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalDoubleUnaryMinusTest {
    @Test
    public void literal_primitive_operand() {
        assertThat(execute("-0d"), equalTo(-0d));
        assertThat(execute("-1d"), equalTo(-1d));
        assertThat(execute("-doubleValue()", 10d), equalTo(-10d));

        assertThat(execute("-doubleValue()", MAX_VALUE), equalTo(-MAX_VALUE));
        assertThat(execute("- -doubleValue()", MAX_VALUE), equalTo(- -MAX_VALUE));
        assertThat(execute("-doubleValue()", MIN_VALUE), equalTo(-MIN_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Double('0')"), equalTo(-0d));
        assertThat(execute("-Double('1')"), equalTo(-1d));
        assertThat(execute("-Double('10')"), equalTo(-10d));

        assertThat(execute("-Double('" + MAX_VALUE + "')"), equalTo(-MAX_VALUE));
        assertThat(execute("- -Double('" + MAX_VALUE + "')"), equalTo(MAX_VALUE));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-100d * 0d"), equalTo(-100d * 0d));
        assertThat(execute("-100d * 1d"), equalTo(-100d * 1d));
        assertThat(execute("-100d - 10d"), equalTo(-100d - 10d));
        assertThat(execute("-100d + 10d"), equalTo(-100d + 10d));
        assertThat(execute("-100d / 10d"), equalTo(-100d / 10d));

        assertThat(execute("-100d + -10d"), equalTo(-100d + -10d));
        assertThat(execute("-100d / -10d"), equalTo(-100d / -10d));
        assertThat(execute("-100d / 20d"), equalTo(-100d / 20d));

        assertThat(execute("-2d * (-2d - -1d) * 10d"), equalTo(-2d * (-2d - -1d) * 10d));
    }
}
