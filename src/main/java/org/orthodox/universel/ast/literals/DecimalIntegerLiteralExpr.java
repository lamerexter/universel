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

import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.ast.TokenImage;

import java.math.BigInteger;

/**
 * A decimal integer literal on the Abstract Syntax Tree.
 */
public class DecimalIntegerLiteralExpr extends IntegerLiteral {
    /**
     * Consructs a new decimal integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public int asIntValue() {
        return Integer.parseInt(getTokenImage().getImage().trim());
    }

    public long asLongValue() {
        return Long.parseLong(StringUtil.rTrim(getTokenImage().getImage().trim(), "l", false));
    }

    public BigInteger asBigInteger() {
        return new BigInteger(StringUtil.rTrim(getTokenImage().getImage().trim(), "I", false));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof DecimalIntegerLiteralExpr) ) return false;
        return super.equals(o);
    }
}
