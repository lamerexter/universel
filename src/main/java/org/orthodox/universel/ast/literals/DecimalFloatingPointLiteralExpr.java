package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

import static org.orthodox.universel.ast.literals.NumericLiteral.NumericPrecision.ARBITRARY;
import static org.orthodox.universel.ast.literals.NumericLiteral.NumericPrecision.LONG;
import static org.orthodox.universel.ast.literals.NumericLiteral.NumericPrecision.STANDARD;

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

    /**
     * Gets the precision required of the floating point literal representation.
     *
     * @return the floating point representation precision.
     * @see NumericPrecision
     */
    public NumericPrecision getPrecision() {
        String image = getTokenImage().getImage();
        if ( image.endsWith("f") ) return STANDARD;
        else if ( image.endsWith("d") ) return LONG;
        else if ( image.endsWith("D") ) return ARBITRARY;

        return LONG;
    }
}
