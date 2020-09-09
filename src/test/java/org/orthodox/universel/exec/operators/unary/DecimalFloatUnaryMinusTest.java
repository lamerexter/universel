package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Float.MAX_VALUE;
import static java.lang.Float.MIN_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalFloatUnaryMinusTest {
    @Test
    public void literal_primitive_operand() {
        assertThat(execute("-0f"), equalTo(-0f));
        assertThat(execute("-1f"), equalTo(-1f));
        assertThat(execute("-floatValue()", 10f), equalTo(-10f));

        assertThat(execute("-floatValue()", MAX_VALUE), equalTo(-MAX_VALUE));
        assertThat(execute("- -floatValue()", MAX_VALUE), equalTo(- -MAX_VALUE));
        assertThat(execute("-floatValue()", MIN_VALUE), equalTo(-MIN_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Float('0')"), equalTo(-0f));
        assertThat(execute("-Float('1')"), equalTo(-1f));
        assertThat(execute("-Float('10')"), equalTo(-10f));

        assertThat(execute("-Float('" + MAX_VALUE + "')"), equalTo(-MAX_VALUE));
        assertThat(execute("- -Float('" + MAX_VALUE + "')"), equalTo(MAX_VALUE));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-100f * 0f"), equalTo(-100f * 0f));
        assertThat(execute("-100f * 1f"), equalTo(-100f * 1f));
        assertThat(execute("-100f - 10f"), equalTo(-100f - 10f));
        assertThat(execute("-100f + 10f"), equalTo(-100f + 10f));
        assertThat(execute("-100f / 10f"), equalTo(-100f / 10f));

        assertThat(execute("-100f + -10f"), equalTo(-100f + -10f));
        assertThat(execute("-100f / -10f"), equalTo(-100f / -10f));
        assertThat(execute("-100f / 20f"), equalTo(-100f / 20f));

        assertThat(execute("-2f * (-2f - -1f) * 10f"), equalTo(-2f * (-2f - -1f) * 10f));
    }
}
