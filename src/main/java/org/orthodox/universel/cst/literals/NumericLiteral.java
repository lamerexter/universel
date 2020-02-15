package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.TokenImage;

public interface NumericLiteral {
    enum NumericPrecision {
        STANDARD, LONG, ARBITRARY
    }

    /**
     * Gets the parser token image associated with this literal.
     *
     * @return the parsed token image of this literal.
     */
    TokenImage getTokenImage();

    /**
     * Gets the precision required of the numeric literal representation.
     *
     * @return the numeric representation precision.
     * @see NumericPrecision
     */
    NumericPrecision getPrecision();

    Class<?> getTypeDescriptor();

    Number getValue();
}
