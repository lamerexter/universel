package org.orthodox.universel.ast.literals;

import static org.orthodox.universel.ast.literals.NumericLiteral.NumericPrecision.*;

public interface IntegerLiteral extends NumericLiteral {
    /**
     * Gets the precision required of the integer literal representation.
     *
     * @return the integer representation precision.
     * @see NumericPrecision
     */
    default public NumericPrecision getPrecision() {
        String image = getTokenImage().getImage();
        if ( image.endsWith("l") ) return LONG;
        else if ( image.endsWith("I") ) return ARBITRARY;

        return STANDARD;
    }
}
