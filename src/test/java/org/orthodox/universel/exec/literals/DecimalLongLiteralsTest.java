package org.orthodox.universel.exec.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static java.lang.Long.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecimalLongLiteralsTest {
    @Test
    public void long0Literal() {
        assertThat(Universal.execute("0l"), equalTo(0L));
    }

    @Test
    public void long1Literal() {
        assertThat(Universal.execute("1L"), equalTo(1L));
    }

    @Test
    public void long10Literal() {
        assertThat(Universal.execute("10L"), equalTo(10L));
    }

    @Test
    public void longMaxLiteral() {
        assertThat(Universal.execute(Long.toString(MAX_VALUE)+"L"), equalTo(MAX_VALUE));
    }
}
