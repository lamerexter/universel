package org.orthodox.universel.exec.literals;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.lang.Long.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class DecimalBigIntegerLiteralsTest {
    @Test
    public void bigInteger0Literal() {
        assertThat(execute("0I"), equalTo(new BigInteger("0")));
    }

    @Test
    public void bigInteger1Literal() {
        assertThat(execute("1I"), equalTo(new BigInteger("1")));
    }

    @Test
    public void bigInteger10Literal() {
        assertThat(execute("10I"), equalTo(new BigInteger("10")));
    }

    @Test
    public void bigIntegerLongMaxLiteral() {
        assertThat(execute(Long.toString(MAX_VALUE)+"I"), equalTo(new BigInteger(Long.toString(MAX_VALUE))));
    }


    @Test
    public void negativeBigInteger0Literal() {
        assertThat(execute("-0I"), equalTo(new BigInteger("-0")));
    }

    @Test
    public void negativeBigInteger1Literal() {
        assertThat(execute("-1I"), equalTo(new BigInteger("-1")));
    }

    @Test
    public void negativeBigInteger10Literal() {
        assertThat(execute("-10I"), equalTo(new BigInteger("-10")));
    }

    @Test
    public void negativeBigIntegerLongMaxLiteral() {
        assertThat(execute("-"+Long.toString(MAX_VALUE)+"I"), equalTo(new BigInteger("-"+Long.toString(MAX_VALUE))));
    }

}
