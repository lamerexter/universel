package org.orthodox.universel.cst.literals;

import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.math.BigDecimal;

import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.ARBITRARY;
import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.LONG;
import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.STANDARD;

/**
 * A decimal floating point literal on the Abstract Syntax Tree.
 */
public class DecimalFloatingPointLiteralExpr extends Expression implements NumericLiteral {
    /**
     * Consructs a new decimal floating point literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalFloatingPointLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public Class<?> getTypeDescriptor() {
        return getLiteralValueClass();
    }

    public Class<?> getLiteralValueClass() {
        if (getTokenImage() == null) return double.class;

        String image = getTokenImage().getImage();
        if (image.endsWith("f") || image.endsWith("F")) return float.class;
        else if (image.endsWith("D")) return BigDecimal.class;
        else return double.class;
    }

    public float asFloat() {
        return Float.parseFloat(StringUtil.rTrim(getTokenImage().getImage().trim(), "f", false));
    }

    public double asDouble() {
        return Double.parseDouble(StringUtil.rTrim(getTokenImage().getImage().trim(), "d"));
    }

    public String asBigDecimalString() {
        return StringUtil.rTrim(getTokenImage().getImage().trim(), "D");
    }

    /**
     * Gets the precision required of the floating point literal representation.
     *
     * @return the floating point representation precision.
     * @see NumericPrecision
     */
    public NumericPrecision getPrecision() {
        String image = getTokenImage().getImage();
        if ( image.endsWith("f") ||  image.endsWith("F") ) return STANDARD;
        else if ( image.endsWith("d") ) return LONG;
        else if ( image.endsWith("D") ) return ARBITRARY;

        return LONG;
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitDecimalFloatingPointLiteral(this);
    }
}
