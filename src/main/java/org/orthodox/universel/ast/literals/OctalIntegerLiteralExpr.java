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

import static org.beanplanet.core.util.StringUtil.lTrim;
import static org.beanplanet.core.util.StringUtil.rTrim;
import static org.beanplanet.core.util.StringUtil.replaceAll;
import static org.orthodox.universel.ast.literals.BinaryIntegerLiteralExpr.toIntValue;
import static org.orthodox.universel.ast.literals.BinaryIntegerLiteralExpr.toLongValue;

/**
 * An octal literal on the Abstract Syntax Tree.
 */
public class OctalIntegerLiteralExpr extends IntegerLiteral {
    private static final String octalDigitToBinary[] = new String[128];

    {
        octalDigitToBinary['0'] = "000";
        octalDigitToBinary['1'] = "001";
        octalDigitToBinary['2'] = "010";
        octalDigitToBinary['3'] = "011";
        octalDigitToBinary['4'] = "100";
        octalDigitToBinary['5'] = "101";
        octalDigitToBinary['6'] = "110";
        octalDigitToBinary['7'] = "111";
    }

    /**
     * Consructs a new octal literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public OctalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    @Override
    public int asIntValue() {
        final String signedBinaryLiteral = toBinary(replaceAll(getTokenImage().getImage().substring(1), "_", ""));
        return toIntValue(signedBinaryLiteral);
    }

    @Override
    public long asLongValue() {
        final String signedBinaryLiteral = toBinary(replaceAll(rTrim(getTokenImage().getImage().substring(1), "l", false), "_", ""));
        return toLongValue(signedBinaryLiteral);
    }

    @Override
    public BigInteger asBigInteger() {
        return new BigInteger(replaceAll(rTrim(getTokenImage().getImage().substring(1), "I", false), "_", ""), 8);
    }

    /**
     * Converts the octal literal to a binary literal by consuming one nibble at a time to comprise the binary
     * equivalent.
     *
     * @param octalString the string containing a valid hexadecimal literal.
     * @return a string containing the equivalent binary literal.
     */
    private String toBinary(String octalString) {
        StringBuilder s = new StringBuilder(octalString.length()*3);

        for (int n=0; n < octalString.length(); n++) {
            s.append(octalDigitToBinary[octalString.charAt(n)]);
        }

        String trimmedBinary = lTrim(s.toString(), "0");
        return trimmedBinary.isEmpty() ? "0" : trimmedBinary;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof OctalIntegerLiteralExpr) ) return false;
        return super.equals(o);
    }
}
