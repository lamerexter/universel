package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Integer.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalIntegerUnaryMinusTest {
    @Test
    public void literal_primitive_operand() {
        assertThat(execute("-0"), equalTo(-0));
        assertThat(execute("-1"), equalTo(-1));
        assertThat(execute("-10"), equalTo(-10));

        assertThat(execute("-"+MAX_VALUE), equalTo(-MAX_VALUE));
        assertThat(execute("- -"+MAX_VALUE), equalTo(MAX_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Integer(0)"), equalTo(-0));
        assertThat(execute("-Integer(1)"), equalTo(-1));
        assertThat(execute("-Integer(10)"), equalTo(-10));

        assertThat(execute("-Integer("+MAX_VALUE+")"), equalTo(-MAX_VALUE));
        assertThat(execute("- -Integer("+MAX_VALUE+")"), equalTo(MAX_VALUE));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-(100*0)"), equalTo(-(100*0)));
        assertThat(execute("-(100*1)"), equalTo(-(100*1)));
        assertThat(execute("-(100-10)"), equalTo(-(100-10)));
        assertThat(execute("-(100+10)"), equalTo(-(100+10)));
        assertThat(execute("-(100/10)"), equalTo(-(100/10)));

        assertThat(execute("-(-100 + -10)"), equalTo(-(-100 + -10)));
        assertThat(execute("-(100/-10)"), equalTo(-(100/-10)));
        assertThat(execute("-(-100/20)"), equalTo(-(-100/20)));

        assertThat(execute("-2 * (-2 - -1) * 10"), equalTo(-2 * (-2 - -1) * 10));
    }
}
