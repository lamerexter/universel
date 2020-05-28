package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.math.BigInteger;

import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.*;

public abstract class IntegerLiteral extends Expression implements NumericLiteral {

    public IntegerLiteral(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * Gets the precision required of the integer literal representation.
     *
     * @return the integer representation precision.
     * @see NumericPrecision
     */
    public NumericPrecision getPrecision() {
        if (getTokenImage() == null) return STANDARD;

        String image = getTokenImage().getImage();
        if ( image.endsWith("l") || image.endsWith("L") ) return LONG;
        else if ( image.endsWith("I") ) return ARBITRARY;

        return STANDARD;
    }

    public Class<?> getLiteralValueClass() {
        switch (getPrecision()) {
            case LONG : return long.class;
            case ARBITRARY : return BigInteger.class;
            default: return int.class;
        }
    }

    public Class<?> getTypeDescriptor() {
        return getLiteralValueClass();
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNumericLiteralExpression(this);
    }

    public abstract int asIntValue();

    public abstract long asLongValue();

    public abstract BigInteger asBigInteger();

    public Number getValue() {
        switch (getPrecision()) {
            case LONG : return asLongValue();
            case ARBITRARY : return asBigInteger();
            default: return asIntValue();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if ( this == o ) return true;
        if ( o == null || o.getClass() != getClass() ) return false;

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
