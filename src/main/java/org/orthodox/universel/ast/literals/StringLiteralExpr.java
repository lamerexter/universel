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

/**
 * A string literal on the Abstract Syntax Tree.
 */
public class StringLiteralExpr extends Expression {
    /** The delimiter of this string. */
    private String delimeter = "";

    /**
     * Constructs a new string literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public StringLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * Constructs a new string literal node from the given parser token image and delimeter.
     *
     * @param tokenImage the parser token image.
     * @param delimiter the delimiter of this string
     */
    public StringLiteralExpr(TokenImage tokenImage, String delimiter) {
        super(tokenImage);
        this.delimeter = delimiter;
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitStringLiteral(this);
    }

    /**
     * Gets the delimiter of this string, for example single or triple single/double quotes (i.e. <code>'simple string'</code>,
     * <code>'''multi-line string'''</code>, <code>"simple string with ${ 'interpolation' }"</code> or
     * <code>"""multi-line string with ${ 'interpolation}"""</code>).
     * @return
     */
    public String getDelimeter() {
        return delimeter;
    }

    public String getUndelimitedTokenImage() {
        return getTokenImage() == null ? null : getTokenImage().getImage().substring(getDelimeter().length(), getTokenImage().getImage().length()-getDelimeter().length());
    }

    @Override
    public Type getType() {
        return new ResolvedTypeReferenceOld(getTokenImage(), String.class);
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return String.class;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof StringLiteralExpr) ) return false;
        return super.equals(o);
    }
}
