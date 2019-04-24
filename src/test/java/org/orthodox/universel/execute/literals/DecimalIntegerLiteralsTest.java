package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import static java.lang.Integer.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecimalIntegerLiteralsTest {
    @Test
    public void integer0Literal() {
        assertThat(Universal.execute("0"), equalTo(0));
    }

    @Test
    public void integer1Literal() {
        assertThat(Universal.execute("1"), equalTo(1));
    }

    @Test
    public void integer2Literal() {
        assertThat(Universal.execute("2"), equalTo(2));
    }

    @Test
    public void integer3Literal() {
        assertThat(Universal.execute("3"), equalTo(3));
    }

    @Test
    public void integer4Literal() {
        assertThat(Universal.execute("4"), equalTo(4));
    }

    @Test
    public void integer5Literal() {
        assertThat(Universal.execute("5"), equalTo(5));
    }

    @Test
    public void integer10Literal() {
        assertThat(Universal.execute("10"), equalTo(10));
    }

    @Test
    public void integerMaxLiteral() {
        assertThat(Universal.execute(Integer.toString(MAX_VALUE)), equalTo(MAX_VALUE));
    }
}
