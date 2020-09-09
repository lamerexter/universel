package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Byte.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalShortUnaryMinusTest {
    @Test
    public void primitive_operand() {
        assertThat(execute("-Short('0').shortValue()"), equalTo((short)-new Short("0").shortValue()));
        assertThat(execute("-Short('1').shortValue()"), equalTo((short)-new Short("1").shortValue()));
        assertThat(execute("-shortValue()", valueOf("10")), equalTo((short)-new Short("10").shortValue()));

        assertThat(execute("-shortValue()", MAX_VALUE), equalTo((short)-MAX_VALUE));
        assertThat(execute("- -shortValue()", MAX_VALUE), equalTo((short)- -MAX_VALUE));
        assertThat(execute("-shortValue()", MIN_VALUE), equalTo((short)-MIN_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Short('0')"), equalTo((short)-0));
        assertThat(execute("-Short('1')"), equalTo((short)-1));
        assertThat(execute("-Short('10')"), equalTo((short)-10));

        assertThat(execute("-Short('" + MAX_VALUE + "')"), equalTo((short)-MAX_VALUE));
        assertThat(execute("- -Short('" + MAX_VALUE + "')"), equalTo((short)MAX_VALUE));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-(Short('100').shortValue() * Short('0').shortValue())"), equalTo((short)(-(new Short("100").shortValue()*new Short("0").shortValue()))));
        assertThat(execute("-(Short('100').shortValue() * Short('1').shortValue())"), equalTo((short)(-(new Short("100").shortValue()*new Short("1").shortValue()))));
        assertThat(execute("-(Short('100').shortValue() - Short('10').shortValue())"), equalTo((short)(-(new Short("100").shortValue()-new Short("10").shortValue()))));
        assertThat(execute("-(Short('100').shortValue() + Short('10').shortValue())"), equalTo((short)(-(new Short("100").shortValue()+new Short("10").shortValue()))));
        assertThat(execute("-(Short('100').shortValue() / Short('10').shortValue())"), equalTo((short)(-(new Short("100").shortValue()/new Short("10").shortValue()))));

        assertThat(execute("-(Short('100').shortValue() + Short('-10').shortValue())"), equalTo((short)(-(new Short("100").shortValue()+new Short("-10").shortValue()))));
        assertThat(execute("-(Short('100').shortValue() / Short('-10').shortValue())"), equalTo((short)(-(new Short("100").shortValue()/new Short("-10").shortValue()))));
        assertThat(execute("-(Short('100').shortValue() / Short('20').shortValue())"), equalTo((short)(-(new Short("100").shortValue()/new Short("20").shortValue()))));

        assertThat(execute("-Short('2').shortValue() * (-Short('2').shortValue() - -Short('1').shortValue()) * Short('10').shortValue()"),
                   equalTo((short)(-new Short("2").shortValue() * (-new Short("2").shortValue() - -new Short("1").shortValue()) * new Short("10").shortValue())));
    }
}
