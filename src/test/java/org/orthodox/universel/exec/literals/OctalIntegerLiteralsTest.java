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
public class OctalIntegerLiteralsTest {
    @Test
    public void positiveIntegerLiterals() {
        assertThat(execute("00"), equalTo(parseInt("0", 8)));
        assertThat(execute("01"), equalTo(parseInt("1", 8)));
        assertThat(execute("070"), equalTo(parseInt("70", 8)));
        assertThat(execute("07__0"), equalTo(parseInt(replaceAll("7_0", "_", ""), 8)));

        // Largest positive int literal is 2147483647 (2^31-1)
        assertThat(execute("0177_7777_7777"), equalTo(parseInt(replaceAll("0177_7777_7777", "_", ""), 8)));
        assertThat(execute("0177_7777_7777"), equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void positiveLongLiterals() {
        assertThat(execute("00L"), equalTo(parseLong("0", 8)));
        assertThat(execute("01l"), equalTo(parseLong("1", 8)));
        assertThat(execute("070L"), equalTo(parseLong("70", 8)));
        assertThat(execute("07__0L"), equalTo(parseLong(replaceAll("7_0", "_", ""), 8)));

        // Largest positive int literal is 2147483647 (2^31-1)
        assertThat(execute("07_7777_7777_7777_7777_7777L"), equalTo(parseLong(replaceAll("07_7777_7777_7777_7777_7777", "_", ""), 8)));
        assertThat(execute("07_7777_7777_7777_7777_7777L"), equalTo(Long.MAX_VALUE));
    }

    @Test
    public void positiveBigIntegerLiterals() {
        assertThat(execute("00I"), equalTo(new BigInteger("0", 8)));
        assertThat(execute("01I"), equalTo(new BigInteger("1", 8)));
        assertThat(execute("070I"), equalTo(new BigInteger("70", 8)));
        assertThat(execute("07__0I"), equalTo(new BigInteger(replaceAll("7__0", "_", ""), 8)));

        // At and beyond the largest positive long literal is 9223372036854775807L (2^63-1)

        assertThat(execute("07_7777_7777_7777_7777_7777I"), equalTo(new BigInteger(replaceAll("07_7777_7777_7777_7777_7777", "_", ""), 8)));
        assertThat(execute("07_7777_7777_7777_7777_7777I"), equalTo(new BigInteger(Long.toString(Long.MAX_VALUE))));
    }

    @Test()
    public void negativeIntegerLiterals()  {
        assertThat(execute("0377_7777_7777"), equalTo(-1));
        assertThat(execute("0377_7777_7770"), equalTo(-8));

        // Largest negative int literal is -2147483648 (-2^31)
        assertThat(execute("0200_0000_0000"), equalTo(parseInt(replaceAll("-0200_0000_0000", "_", ""), 8)));
        assertThat(execute("0200_0000_0000"), equalTo(Integer.MIN_VALUE));
    }

    @Test()
    public void negativeLongLiterals()  {
        assertThat(execute("017_7777_7777_7777_7777_7777L"), equalTo(-1L));
        assertThat(execute("017_7777_7777_7777_7777_7770L"), equalTo(-8L));

        // Largest negative int literal is -2147483648 (-2^31)
        assertThat(execute("010_0000_0000_0000_0000_0000L"), equalTo(parseLong(replaceAll("-010_0000_0000_0000_0000_0000", "_", ""), 8)));
        assertThat(execute("010_0000_0000_0000_0000_0000L"), equalTo(Long.MIN_VALUE));
    }
}
