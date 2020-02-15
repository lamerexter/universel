package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;

import java.math.BigDecimal;

import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.*;

/**
 * A decimal floating point literal on the Abstract Syntax Tree.
 */
public class HexadecimalFloatingPointLiteralExpr extends Expression {
    /**
     * Consructs a new decimal floating point literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public HexadecimalFloatingPointLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * Gets the precision required of the floating point literal representation.
     *
     * @return the floating point representation precision.
     * @see NumericLiteral.NumericPrecision
     */
    public NumericLiteral.NumericPrecision getPrecision() {
        String image = getTokenImage().getImage();
        if ( image.endsWith("f") ) return STANDARD;
        else if ( image.endsWith("d") ) return LONG;
        else if ( image.endsWith("D") ) return ARBITRARY;

        return LONG;
    }

    public Class<? extends Number> getLiteralValueClass() {
        switch (getPrecision()) {
            case STANDARD: return float.class;
            case ARBITRARY: return BigDecimal.class;
            default: return double.class;
        }
    }

    public Class<?> getTypeDescriptor() {
        return getLiteralValueClass();
    }

}
