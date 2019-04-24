package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static java.lang.Float.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecimalFloatLiteralsTest {
    @Test
    public void float0Literal() {
        assertThat(Universal.execute("0f"), equalTo(0f));
    }

    @Test
    public void float1Literal() {
        assertThat(Universal.execute("1f"), equalTo(1f));
    }

    @Test
    public void float2Literal() {
        assertThat(Universal.execute("2f"), equalTo(2f));
    }

    @Test
    public void float3Literal() {
        assertThat(Universal.execute("3f"), equalTo(3f));
    }

    @Test
    public void float4Literal() {
        assertThat(Universal.execute("4f"), equalTo(4f));
    }

    @Test
    public void float5Literal() {
        assertThat(Universal.execute("5f"), equalTo(5f));
    }

    @Test
    public void float10Literal() {
        assertThat(Universal.execute("10f"), equalTo(10f));
    }

    @Test
    public void floatMaxLiteral() {
        assertThat(Universal.execute(Float.toString(MAX_VALUE)+"f"), equalTo(MAX_VALUE));
    }
}
