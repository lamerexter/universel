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

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.beanplanet.core.util.StringUtil.rTrim;

/**
 * A hexadecimal floating point literal on the Abstract Syntax Tree.
 */
public class HexadecimalFloatingPointLiteralExpr extends FloatingPointLiteral {
    private final static BigInteger TWO = BigInteger.valueOf(2);
    private final static BigDecimal MINUS_ONE = new BigDecimal(-1);

    /**
     * Consructs a new decimal floating point literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public HexadecimalFloatingPointLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public float asFloat() {
        return Float.valueOf(rTrim(getTokenImage().getImage().trim(), "f", false));
    }

    public double asDouble() {
        return Double.valueOf(rTrim(getTokenImage().getImage().trim(), "d"));
    }

    public BigDecimal asBigDecimal() {
        return toBigDecimal(rTrim(getTokenImage().getImage().trim(), "D"));
    }

    public static BigDecimal toBigDecimal(String hex) {
        // handle leading sign
        BigDecimal sign = null;
        if (hex.startsWith("-")) {
            hex = hex.substring(1);
            sign = MINUS_ONE;
        } else if (hex.startsWith("+")) {
            hex = hex.substring(1);
        }

        // constant must start with 0x or 0X
        if (!(hex.startsWith("0x") || hex.startsWith("0X"))) {
            throw new IllegalArgumentException(
                    "not a hexadecimal floating point constant");
        }
        hex = hex.substring(2);

        // ... and end in 'p' or 'P' and an exponent
        int p = hex.indexOf("p");
        if (p < 0) p = hex.indexOf("P");
        if (p < 0) {
            throw new IllegalArgumentException(
                    "not a hexadecimal floating point constant");
        }
        String mantissa = hex.substring(0, p);
        String exponent = hex.substring(p+1);

        // find the hexadecimal point, if any
        int hexadecimalPoint = mantissa.indexOf(".");
        int hexadecimalPlaces = 0;
        if (hexadecimalPoint >= 0) {
            hexadecimalPlaces = mantissa.length() - 1 - hexadecimalPoint;
            mantissa = mantissa.substring(0, hexadecimalPoint) +
                       mantissa.substring(hexadecimalPoint + 1);
        }

        // reduce the exponent by 4 for every hexadecimal place
        int binaryExponent = Integer.valueOf(exponent) - (hexadecimalPlaces * 4);
        boolean positive = true;
        if (binaryExponent < 0) {
            binaryExponent = -binaryExponent;
            positive = false;
        }

        BigDecimal base = new BigDecimal(new BigInteger(mantissa, 16));
        BigDecimal factor = new BigDecimal(TWO.pow(binaryExponent));
        BigDecimal value = positive? base.multiply(factor) : base.divide(factor);
        if (sign != null) value = value.multiply(sign);

        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof HexadecimalFloatingPointLiteralExpr) ) return false;
        return super.equals(o);
    }
}
