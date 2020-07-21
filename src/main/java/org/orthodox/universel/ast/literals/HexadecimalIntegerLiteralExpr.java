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
 * An hexadecimal integer literal on the Abstract Syntax Tree.
 */
public class HexadecimalIntegerLiteralExpr extends IntegerLiteral {
    private final static String hexDigitToBinaryNibble[] = new String[128];

    {
        hexDigitToBinaryNibble['0'] = "0000";
        hexDigitToBinaryNibble['1'] = "0001";
        hexDigitToBinaryNibble['2'] = "0010";
        hexDigitToBinaryNibble['3'] = "0011";
        hexDigitToBinaryNibble['4'] = "0100";
        hexDigitToBinaryNibble['5'] = "0101";
        hexDigitToBinaryNibble['6'] = "0110";
        hexDigitToBinaryNibble['7'] = "0111";
        hexDigitToBinaryNibble['8'] = "1000";
        hexDigitToBinaryNibble['9'] = "1001";
        hexDigitToBinaryNibble['A'] = "1010";
        hexDigitToBinaryNibble['B'] = "1011";
        hexDigitToBinaryNibble['C'] = "1100";
        hexDigitToBinaryNibble['D'] = "1101";
        hexDigitToBinaryNibble['E'] = "1110";
        hexDigitToBinaryNibble['F'] = "1111";
    }

    /**
     * Consructs a new hexadecimal integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public HexadecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    @Override
    public int asIntValue() {
        final String signedBinaryLiteral = toBinary(replaceAll(getTokenImage().getImage().substring(2), "_", "").toUpperCase());
        return toIntValue(signedBinaryLiteral);
    }

    @Override
    public long asLongValue() {
        final String signedBinaryLiteral = toBinary(replaceAll(rTrim(getTokenImage().getImage().substring(2), "l", false), "_", "").toUpperCase());
        return toLongValue(signedBinaryLiteral);
    }

    @Override
    public BigInteger asBigInteger() {
        return new BigInteger(replaceAll(rTrim(getTokenImage().getImage().substring(2), "I", false), "_", ""), 16);
    }

    /**
     * Converts the hexadecimal literal to a binary literal by consuming one nibble at a time to comprise the binary
     * equivalent.
     *
     * @param hexString the string containing a valid hexadecimal literal.
     * @return a string containing the equivalent binary literal.
     */
    private String toBinary(String hexString) {
        StringBuilder s = new StringBuilder(hexString.length()*4);

        for (int n=0; n < hexString.length(); n++) {
            s.append(hexDigitToBinaryNibble[hexString.charAt(n)]);
        }

        String trimmedBinary = lTrim(s.toString(), "0");
        return trimmedBinary.isEmpty() ? "0" : trimmedBinary;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof HexadecimalIntegerLiteralExpr) ) return false;
        return super.equals(o);
    }
}
