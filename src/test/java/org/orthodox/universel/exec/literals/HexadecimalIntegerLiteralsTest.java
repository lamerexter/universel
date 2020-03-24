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
public class HexadecimalIntegerLiteralsTest {
    @Test
    public void positiveIntegerLiterals() {
        assertThat(execute("0x0"), equalTo(parseInt("0", 16)));
        assertThat(execute("0X1"), equalTo(parseInt("1", 16)));
        assertThat(execute("0xf0"), equalTo(parseInt("f0", 16)));
        assertThat(execute("0xf__0"), equalTo(parseInt(replaceAll("f_0", "_", ""), 16)));

        // Largest positive int literal is 2147483647 (2^31-1)
        assertThat(execute("0x7fff_ffff"), equalTo(parseInt(replaceAll("7fff_ffff", "_", ""), 16)));
        assertThat(execute("0x7fff_ffff"), equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void positiveLongLiterals() {
        assertThat(execute("0x0L"), equalTo(parseLong("0", 16)));
        assertThat(execute("0X1l"), equalTo(parseLong("1", 16)));
        assertThat(execute("0xf0L"), equalTo(parseLong("f0", 16)));
        assertThat(execute("0xf__0L"), equalTo(parseLong(replaceAll("f__0", "_", ""), 16)));

        // Largest positive long literal is 9223372036854775807L (2^63-1)

        assertThat(execute("0x7fff_ffff_ffff_ffffL"), equalTo(parseLong(replaceAll("7fff_ffff_ffff_ffff", "_", ""), 16)));
        assertThat(execute("0x7fff_ffff_ffff_ffffL"), equalTo(Long.MAX_VALUE));
    }

    @Test
    public void positiveBigIntegerLiterals() {
        assertThat(execute("0x0I"), equalTo(new BigInteger("0", 16)));
        assertThat(execute("0X1I"), equalTo(new BigInteger("1", 16)));
        assertThat(execute("0xf0I"), equalTo(new BigInteger("f0", 16)));
        assertThat(execute("0xf__0I"), equalTo(new BigInteger(replaceAll("f__0", "_", ""), 16)));

        // At and beyond the largest positive long literal is 9223372036854775807L (2^63-1)

        assertThat(execute("0x7fff_ffff_ffff_ffffI"), equalTo(new BigInteger(replaceAll("7fff_ffff_ffff_ffff", "_", ""), 16)));
        assertThat(execute("0x7fff_ffff_ffff_ffffI"), equalTo(new BigInteger(Long.toString(Long.MAX_VALUE))));
    }

    @Test()
    public void negativeIntegerLiterals()  {
        assertThat(execute("0xFFFF_FFFf"), equalTo(-1));
        assertThat(execute("0Xffff_fff0"), equalTo(-16));

        // Largest negative int literal is -2147483648 (-2^31)
        assertThat(execute("0x8000_0000"), equalTo(parseInt(replaceAll("-8000_0000", "_", ""), 16)));
        assertThat(execute("0X8000_0000"), equalTo(Integer.MIN_VALUE));
    }

    @Test()
    public void negativeLongLiterals()  {
        assertThat(execute("0xFFFF_FFFF_FFFF_FFFFL"), equalTo(-1L));
        assertThat(execute("0xFFFF_FFFF_FFFF_FFF0L"), equalTo(-16L));

        // Largest negative int literal is -2147483648 (-2^31)
        assertThat(execute("0x8000_0000_0000_0000L"), equalTo(parseLong(replaceAll("-8000_0000_0000_0000", "_", ""), 16)));
        assertThat(execute("0X8000_0000_0000_0000L"), equalTo(Long.MIN_VALUE));
    }
}
