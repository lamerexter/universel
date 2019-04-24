package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static java.lang.Double.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DecimalDoubleLiteralsTest {
    @Test
    public void double0Literal() {
        assertThat(Universal.execute("0d"), equalTo(0d));
    }

    @Test
    public void double1Literal() {
        assertThat(Universal.execute("1d"), equalTo(1d));
    }

    @Test
    public void double2Literal() {
        assertThat(Universal.execute("2d"), equalTo(2d));
    }

    @Test
    public void double3Literal() {
        assertThat(Universal.execute("3d"), equalTo(3d));
    }

    @Test
    public void double4Literal() {
        assertThat(Universal.execute("4d"), equalTo(4d));
    }

    @Test
    public void double5Literal() {
        assertThat(Universal.execute("5d"), equalTo(5d));
    }

    @Test
    public void double10Literal() {
        assertThat(Universal.execute("10d"), equalTo(10d));
    }

    @Test
    public void doubleMaxLiteral() {
        assertThat(Universal.execute(Double.toString(MAX_VALUE)+"d"), equalTo(MAX_VALUE));
    }
}
