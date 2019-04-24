package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BooleanLiteralsTest {
    @Test
    public void trueBooleanLiteral() {
        assertThat(Universal.execute("true"), equalTo(true));
    }

    @Test
    public void falseBooleanLiteral() {
        assertThat(Universal.execute("false"), equalTo(false));
    }
}
