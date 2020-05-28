package org.orthodox.universel.cst.literals;

import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

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
