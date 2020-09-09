package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalLongUnaryMinusTest {
    @Test
    public void literal_primitive_operand() {
        assertThat(execute("-0L"), equalTo(-0L));
        assertThat(execute("-1L"), equalTo(-1L));
        assertThat(execute("-longValue()", 10L), equalTo(-10L));

        assertThat(execute("-longValue()", MAX_VALUE), equalTo(-MAX_VALUE));
        assertThat(execute("- -longValue()", MAX_VALUE), equalTo(- -MAX_VALUE));
        assertThat(execute("-longValue()", MIN_VALUE), equalTo(-MIN_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Long('0')"), equalTo(-0L));
        assertThat(execute("-Long('1')"), equalTo(-1L));
        assertThat(execute("-Long('10')"), equalTo(-10L));

        assertThat(execute("-Long('" + MAX_VALUE + "')"), equalTo(-MAX_VALUE));
        assertThat(execute("- -Long('" + MAX_VALUE + "')"), equalTo(MAX_VALUE));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-100L * 0L"), equalTo(-100L * 0L));
        assertThat(execute("-100L * 1L"), equalTo(-100L * 1L));
        assertThat(execute("-100L - 10L"), equalTo(-100L - 10L));
        assertThat(execute("-100L + 10L"), equalTo(-100L + 10L));
        assertThat(execute("-100L / 10L"), equalTo(-100L / 10L));

        assertThat(execute("-100L + -10L"), equalTo(-100L + -10L));
        assertThat(execute("-100L / -10L"), equalTo(-100L / -10L));
        assertThat(execute("-100L / 20L"), equalTo(-100L / 20L));

        assertThat(execute("-2L * (-2L - -1L) * 10L"), equalTo(-2L * (-2L - -1L) * 10L));
    }
}
