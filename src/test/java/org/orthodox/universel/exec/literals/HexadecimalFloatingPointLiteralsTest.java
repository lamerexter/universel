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

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.orthodox.universel.Universal.execute;

public class HexadecimalFloatingPointLiteralsTest {
    @Test
    public void positiveUntypedLiterals() {
        assertThat(execute("0x50.1p3"), equalTo(640.5d));
        assertThat(execute("0x12.2P2"), equalTo(72.5d));
    }

    @Test
    public void positiveFloatLiterals() {
        assertThat(execute("0x50.1p3f"), equalTo(640.5f));
        assertThat(execute("0x12.2P2f"), equalTo(72.5f));
    }

    @Test
    public void positiveDoubleLiterals() {
        assertThat(execute("0x50.1p3d"), equalTo(640.5d));
        assertThat(execute("0x12.2P2d"), equalTo(72.5d));
    }

    @Test
    public void positiveBigLiterals() {
        assertThat(execute("0x50.1p3D"), equalTo(new BigDecimal(640.5d)));
        assertThat(execute("0x12.2P2D"), equalTo(new BigDecimal(72.5d)));
    }
}
