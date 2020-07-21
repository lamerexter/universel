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

package org.orthodox.universel.ast.type;

import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;

import java.util.Objects;

/**
 * An Abstract Syntax Tree node for loading a specific type (class, enum, interface etc).
 */
public class LoadTypeExpression extends Expression {
    /** The type to be loaded. */
    private final Type loadType;
    public LoadTypeExpression(final TokenImage tokenImage, final Type loadType) {
        super(tokenImage, new ParameterisedTypeImpl(tokenImage, new ResolvedTypeReferenceOld(tokenImage, Class.class), loadType));
        this.loadType = loadType;
    }

    /**
     * Gets the type to be loaded.
     *
     * @return the type to be loaded.
     */
    public Type getLoadType() {
        return loadType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof LoadTypeExpression)) return false;
        if (!super.equals(o)) return false;
        LoadTypeExpression that = (LoadTypeExpression) o;
        return Objects.equals(getLoadType(), that.getLoadType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLoadType());
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitLoadType(this);
    }

}
