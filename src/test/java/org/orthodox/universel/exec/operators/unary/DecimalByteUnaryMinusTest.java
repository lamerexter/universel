package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Byte.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalByteUnaryMinusTest {
    @Test
    public void literal_primitive_operand() {
        assertThat(execute("-Byte('0').byteValue()"), equalTo((byte)-new Byte("0").byteValue()));
        assertThat(execute("-Byte('1').byteValue()"), equalTo((byte)-new Byte("1").byteValue()));
        assertThat(execute("-byteValue()", valueOf("10")), equalTo((byte)-new Byte("10").byteValue()));

        assertThat(execute("-byteValue()", MAX_VALUE), equalTo((byte)-MAX_VALUE));
        assertThat(execute("- -byteValue()", MAX_VALUE), equalTo((byte)- -MAX_VALUE));
        assertThat(execute("-byteValue()", MIN_VALUE), equalTo((byte)-MIN_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Byte('0')"), equalTo((byte)-0));
        assertThat(execute("-Byte('1')"), equalTo((byte)-1));
        assertThat(execute("-Byte('10')"), equalTo((byte)-10));

//        assertThat(execute("-Byte.MAX_VALUE"), equalTo((byte)-MAX_VALUE));
        assertThat(execute("-Byte('" + MAX_VALUE + "')"), equalTo((byte)-MAX_VALUE));
        assertThat(execute("- -Byte('" + MAX_VALUE + "')"), equalTo((byte)MAX_VALUE));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-(Byte('100').byteValue() * Byte('0').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()*new Byte("0").byteValue()))));
        assertThat(execute("-(Byte('100').byteValue() * Byte('1').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()*new Byte("1").byteValue()))));
        assertThat(execute("-(Byte('100').byteValue() - Byte('10').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()-new Byte("10").byteValue()))));
        assertThat(execute("-(Byte('100').byteValue() + Byte('10').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()+new Byte("10").byteValue()))));
        assertThat(execute("-(Byte('100').byteValue() / Byte('10').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()/new Byte("10").byteValue()))));

        assertThat(execute("-(Byte('100').byteValue() + Byte('-10').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()+new Byte("-10").byteValue()))));
        assertThat(execute("-(Byte('100').byteValue() / Byte('-10').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()/new Byte("-10").byteValue()))));
        assertThat(execute("-(Byte('100').byteValue() / Byte('20').byteValue())"), equalTo((byte)(-(new Byte("100").byteValue()/new Byte("20").byteValue()))));

        assertThat(execute("-Byte('2').byteValue() * (-Byte('2').byteValue() - -Byte('1').byteValue()) * Byte('10').byteValue()"),
                   equalTo((byte)(-new Byte("2").byteValue() * (-new Byte("2").byteValue() - -new Byte("1").byteValue()) * new Byte("10").byteValue())));
    }
}
