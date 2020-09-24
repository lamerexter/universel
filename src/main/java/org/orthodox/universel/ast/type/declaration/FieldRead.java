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

package org.orthodox.universel.ast.type.declaration;

import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.Type;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.util.Objects;

/**
 * A read of a type's field on the Abstract Syntax Tree (AST).
 */
public class FieldRead extends Node {
    /** Whether the field read is static. */
    private final boolean isStatic;
    /** The Type in which the field is declared. */
    private final Type declaringType;
    /** The name of the field read. */
    private final String fieldName;

    public FieldRead(TokenImage tokenImage,
                     boolean isStatic,
                     Type declaringType,
                     Type fieldType,
                     String fieldName) {
        super(tokenImage, fieldType);
        this.isStatic = isStatic;
        this.declaringType = declaringType;
        this.fieldName = fieldName;
    }

    /**
     * Whether the field read is static
     *
     * @return true if the field read is a static field, false if it is non-static.
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * Gets the Type in which the field is declared.
     *
     * @return the Type in which the field is declared.
     */
    public Type getDeclaringType() {
        return declaringType;
    }

    /**
     * Gets the type of the field read.
     *
     * @return the type of the field.
     */
    public Type getFieldType() {
        return getType();
    }

    /**
     * Gets the name of the field read.
     *
     * @return the name of the field read.
     */
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldRead)) return false;
        if (!super.equals(o)) return false;
        FieldRead that = (FieldRead) o;
        return Objects.equals(isStatic(), that.isStatic()) &&
               Objects.equals(getDeclaringType(), that.getDeclaringType()) &&
               Objects.equals(getFieldName(), that.getFieldName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isStatic(), getDeclaringType(), getFieldName());
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitFieldAccess(this);
    }
}
