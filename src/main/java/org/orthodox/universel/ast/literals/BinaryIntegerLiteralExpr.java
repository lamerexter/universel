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

package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.TokenImage;

import java.math.BigInteger;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static org.beanplanet.core.util.StringUtil.rTrim;
import static org.beanplanet.core.util.StringUtil.replaceAll;

/**
 * A binary integer literal on the Abstract Syntax Tree.
 */
public class BinaryIntegerLiteralExpr extends IntegerLiteral {

    public static final String INTEGER_MIN_2S_COMP = "10000000000000000000000000000000";
    public static final int INTEGER_BIT_LEN = 32;
    public static final int LONG_BIT_LEN = 64;

    /**
     * Consructs a new binary integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public BinaryIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public int asIntValue() {
        final String signedBinaryLiteral = replaceAll(getTokenImage().getImage().substring(2), "_", "");
        return toIntValue(signedBinaryLiteral);
    }

    public static int toIntValue(String signedBinaryLiteral) {
        if ( signedBinaryLiteral.length() == INTEGER_BIT_LEN && signedBinaryLiteral.startsWith("1") ) {
            int result = 0;
            for (int n = 0; n < INTEGER_BIT_LEN; n++) {
                result <<= 1;
                result |= signedBinaryLiteral.charAt(n) == '1' ? 1 : 0;
            }
            return result;
        } else {
            return parseInt(signedBinaryLiteral, 2);
        }
    }

    public long asLongValue() {
        final String signedBinaryLiteral = replaceAll(rTrim(getTokenImage().getImage().substring(2), "l", false), "_", "");
        return toLongValue(signedBinaryLiteral);
    }

    public static long toLongValue(String signedBinaryLiteral) {
        if ( signedBinaryLiteral.length() == LONG_BIT_LEN && signedBinaryLiteral.startsWith("1") ) {
            long result = 0L;
            for (int n = 0; n < LONG_BIT_LEN; n++) {
                result <<= 1;
                result |= signedBinaryLiteral.charAt(n) == '1' ? 1 : 0;
            }
            return result;
        } else {
            return parseLong(signedBinaryLiteral, 2);
        }
    }

    public BigInteger asBigInteger() {
        return new BigInteger(replaceAll(rTrim(getTokenImage().getImage().substring(2), "I", false), "_", ""), 2);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof BinaryIntegerLiteralExpr) ) return false;
        return super.equals(o);
    }
}
