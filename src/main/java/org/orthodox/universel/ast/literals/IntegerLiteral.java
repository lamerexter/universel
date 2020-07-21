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

import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;

import java.math.BigInteger;

import static org.orthodox.universel.ast.literals.NumericLiteral.NumericPrecision.*;

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

    @Override
    public Type getType() {
        return new ResolvedTypeReferenceOld(getTokenImage(), getLiteralValueClass());
    }

    @Override
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
