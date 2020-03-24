/*
 *  MIT Licence:
 *
 *  Copyright (c) 2020 Orthodox Engineering Ltd
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *
 */

package org.orthodox.universel.exec.literals;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static org.beanplanet.core.util.StringUtil.replaceAll;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.orthodox.universel.Universal.execute;

/**
 * Refer to the <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html">JLS</a>
 */
public class BinaryIntegerLiteralsTest {
    @Test
    public void positiveIntegerLiterals() {
        assertThat(execute("0b0"), equalTo(parseInt("0", 2)));
        assertThat(execute("0B1"), equalTo(parseInt("1", 2)));
        assertThat(execute("0b11110000"), equalTo(parseInt("11110000", 2)));
        assertThat(execute("0b1111__0000"), equalTo(parseInt(replaceAll("1111__0000", "_", ""), 2)));

        // Largest positive int literal is 2147483647 (2^31-1)
        assertThat(execute("0b0111_1111_1111_1111_1111_1111_1111_1111"), equalTo(parseInt(replaceAll("0111_1111_1111_1111_1111_1111_1111_1111", "_", ""), 2)));
        assertThat(execute("0b0111_1111_1111_1111_1111_1111_1111_1111"), equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void positiveLongLiterals() {
        assertThat(execute("0b0L"), equalTo(parseLong("0", 2)));
        assertThat(execute("0B1l"), equalTo(parseLong("1", 2)));
        assertThat(execute("0b11110000L"), equalTo(parseLong("11110000", 2)));
        assertThat(execute("0b1111__0000L"), equalTo(parseLong(replaceAll("1111__0000", "_", ""), 2)));

        // Largest positive int literal is 9223372036854775807L (2^63-1)

        assertThat(execute("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"), equalTo(parseLong(replaceAll("0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111", "_", ""), 2)));
        assertThat(execute("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"), equalTo(Long.MAX_VALUE));
    }

    @Test
    public void positiveBigIntegerLiterals() {
        assertThat(execute("0b0I"), equalTo(new BigInteger("0", 2)));
        assertThat(execute("0B1I"), equalTo(new BigInteger("1", 2)));
        assertThat(execute("0b11110000I"), equalTo(new BigInteger("11110000", 2)));
        assertThat(execute("0b1111__0000I"), equalTo(new BigInteger(replaceAll("1111__0000", "_", ""), 2)));
        assertThat(execute("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111I"), equalTo(new BigInteger(replaceAll("0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111", "_", ""), 2)));
    }

    @Test()
    public void negativeIntegerLiterals()  {
        assertThat(execute("0b1111_1111_1111_1111_1111_1111_1111_1111"), equalTo(-1));
        assertThat(execute("0b1111_1111_1111_1111_1111_1111_1111_1110"), equalTo(-2));

        // Largest negative int literal is -2147483648 (-2^31)
        assertThat(execute("0b1000_0000_0000_0000_0000_0000_0000_0000"), equalTo(parseInt(replaceAll("-1000_0000_0000_0000_0000_0000_0000_0000", "_", ""), 2)));
        assertThat(execute("0b1000_0000_0000_0000_0000_0000_0000_0000"), equalTo(Integer.MIN_VALUE));
    }

    @Test()
    public void negativeLongLiterals()  {
        assertThat(execute("0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"), equalTo(-1L));
        assertThat(execute("0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1110L"), equalTo(-2L));

        // Largest negative long literal is -9223372036854775808L (-2^63)
        assertThat(execute("0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000L"), equalTo(parseLong(replaceAll("-1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000", "_", ""), 2)));
        assertThat(execute("0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000L"), equalTo(Long.MIN_VALUE));
    }
}
