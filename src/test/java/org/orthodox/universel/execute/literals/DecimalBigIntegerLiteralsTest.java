package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import java.math.BigInteger;

import static java.lang.Long.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecimalBigIntegerLiteralsTest {
    @Test
    public void bigInteger0Literal() {
        assertThat(Universal.execute("0I"), equalTo(new BigInteger("0")));
    }

    @Test
    public void bigInteger1Literal() {
        assertThat(Universal.execute("1I"), equalTo(new BigInteger("1")));
    }

    @Test
    public void bigInteger10Literal() {
        assertThat(Universal.execute("10I"), equalTo(new BigInteger("10")));
    }

    @Test
    public void bigIntegerLongMaxLiteral() {
        assertThat(Universal.execute(Long.toString(MAX_VALUE)+"I"), equalTo(new BigInteger(Long.toString(MAX_VALUE))));
    }
}
