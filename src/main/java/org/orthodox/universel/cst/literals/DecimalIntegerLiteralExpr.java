package org.orthodox.universel.cst.literals;

import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.math.BigInteger;

/**
 * A decimal integer literal on the Abstract Syntax Tree.
 */
public class DecimalIntegerLiteralExpr extends Expression implements IntegerLiteral {
    /**
     * Consructs a new decimal integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public Class<?> getLiteralValueClass() {
        if (getTokenImage() == null) return int.class;

        String image = getTokenImage().getImage();
        if (image.endsWith("l") || image.endsWith("L")) return long.class;
        else if (image.endsWith("I")) return BigInteger.class;
        else return int.class;
    }

    public int asIntValue() {
        return Integer.parseInt(getTokenImage().getImage().trim());
    }

    public long asLongValue() {
        return Long.parseLong(StringUtil.rTrim(getTokenImage().getImage().trim(), "l", false));
    }

    public String asBigIntegerString() {
        return StringUtil.rTrim(getTokenImage().getImage().trim(), "I", false);
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitDecimalIntegerLiteral(this);
    }

    public Class<?> getTypeDescriptor() {
        return getLiteralValueClass();
    }
}
