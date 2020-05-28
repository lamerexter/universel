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

package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.math.BigDecimal;

import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.*;

public abstract class FloatingPointLiteral extends Expression implements NumericLiteral {
    /**
     * Consructs a new floating point literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public FloatingPointLiteral(TokenImage tokenImage) {
        super(tokenImage);
    }

    public Class<?> getTypeDescriptor() {
        return getLiteralValueClass();
    }

    public Number getValue() {
        switch (getPrecision()) {
            case STANDARD : return asFloat();
            case ARBITRARY : return asBigDecimal();
            default: return asDouble();
        }
    }

    public Class<?> getLiteralValueClass() {
        switch (getPrecision()) {
            case STANDARD : return float.class;
            case ARBITRARY : return BigDecimal.class;
            default: return double.class;
        }
    }

    public abstract float asFloat();

    public abstract double asDouble();

    public abstract BigDecimal asBigDecimal();

    /**
     * Gets the precision required of the floating point literal representation.
     *
     * @return the floating point representation precision.
     * @see NumericLiteral.NumericPrecision
     */
    public NumericLiteral.NumericPrecision getPrecision() {
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
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNumericLiteralExpression(this);
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
