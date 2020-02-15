package org.orthodox.universel.cst.literals;

import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.cst.TokenImage;

import java.math.BigDecimal;

/**
 * A decimal floating point literal on the Abstract Syntax Tree.
 */
public class DecimalFloatingPointLiteralExpr extends FloatingPointLiteral {
    /**
     * Consructs a new decimal floating point literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalFloatingPointLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public float asFloat() {
        return Float.parseFloat(StringUtil.rTrim(getTokenImage().getImage().trim(), "f", false));
    }

    public double asDouble() {
        return Double.parseDouble(StringUtil.rTrim(getTokenImage().getImage().trim(), "d"));
    }

    public BigDecimal asBigDecimal() {
        return new BigDecimal(StringUtil.rTrim(getTokenImage().getImage().trim(), "D"));
    }
}
