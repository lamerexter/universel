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

import javax.lang.model.type.NullType;

/**
 * A null literal (<i>null</i>) on the Abstract Syntax Tree.
 */
public class NullLiteralExpr extends Expression {
    /**
     * Constructs a new null literal node with no associated token image.
     */
    public NullLiteralExpr() {
        this(null, NullType.class);
    }

    /**
     * Constructs a new null literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public NullLiteralExpr(TokenImage tokenImage) {
        super(tokenImage, NullType.class);
    }

    /**
     * Constructs a new null literal node from the given parser token image and known type information.
     *
     * @param tokenImage the parser token image.
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     */
    public NullLiteralExpr(TokenImage tokenImage, Class<?> typeDescriptor) {
        super(tokenImage, typeDescriptor);
    }

    /**
     * Constructs a new null literal node from the given parser token image and known type information.
     *
     * @param tokenImage the parser token image.
     * @param type the type of the null expression, or null if unknown at this time.
     */
    public NullLiteralExpr(TokenImage tokenImage, Type type) {
        super(tokenImage, type);
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNullLiteral(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof NullLiteralExpr) ) return false;
        return super.equals(o);
    }
}
