package org.orthodox.universel.exec.operators.unary;

import org.junit.jupiter.api.Test;

import static java.lang.Character.MAX_VALUE;
import static java.lang.Character.MIN_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalCharUnaryMinusTest {
    @Test
    public void primitive_operand() {
        assertThat(execute("-String('0').charAt(0)"), equalTo((char)-new Character('0').charValue()));
        assertThat(execute("-String('1').charAt(0)"), equalTo((char)-new Character('1').charValue()));
        assertThat(execute("-charValue()", 'a'), equalTo((char)-new Character('a').charValue()));

        assertThat(execute("-charValue()", MAX_VALUE), equalTo((char)-MAX_VALUE));
        assertThat(execute("- -charValue()", MAX_VALUE), equalTo((char)- -MAX_VALUE));
        assertThat(execute("-charValue()", MIN_VALUE), equalTo((char)-MIN_VALUE));
    }

    @Test
    public void primitiveWrapper_operand() {
        assertThat(execute("-Character(String('0').charAt(0))"), equalTo((char)-new Character("0".charAt(0))));
        assertThat(execute("-Character(String('1').charAt(0))"), equalTo((char)-new Character("1".charAt(0))));
        assertThat(execute("-Character(String('a').charAt(0))"), equalTo((char)-new Character("a".charAt(0))));

        assertThat(execute("import java.lang.Character.MAX_VALUE -Character(MAX_VALUE)"), equalTo((char)-new Character(MAX_VALUE)));
        assertThat(execute("import java.lang.Character.MAX_VALUE - -Character(MAX_VALUE)"), equalTo((char)- -new Character(MAX_VALUE)));
    }

    @Test
    public void expression_operand() {
        assertThat(execute("-(String('a').charAt(0) * String('B').charAt(0))"), equalTo((char)-('a' * 'B')));
        assertThat(execute("-(String('a').charAt(0) - String('B').charAt(0))"), equalTo((char)-('a' - 'B')));
        assertThat(execute("-(String('a').charAt(0) + String('B').charAt(0))"), equalTo((char)-('a' + 'B')));
        assertThat(execute("-(String('a').charAt(0) / String('B').charAt(0))"), equalTo((char)-('a' / 'B')));

        assertThat(execute("-String('a').charAt(0) * (-String('b').charAt(0) - -String('c').charAt(0)) * String('D').charAt(0)"),
                   equalTo((char)(-'a' * (-'b' - -'c') * 'D')));
    }
}
