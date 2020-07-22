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

import java.util.Objects;

public class StaticFieldGetExpression extends Expression {
    private final Type declaringType;
    private final String fieldName;

    public StaticFieldGetExpression(TokenImage tokenImage,
                                    Type declaringType,
                                    Type fieldType,
                                    String fieldName
    ) {
        super(tokenImage, fieldType);
        this.declaringType = declaringType;
        this.fieldName = fieldName;
    }

    public Type getDeclaringType() {
        return declaringType;
    }

    public Type getFieldType() {
        return getType();
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof StaticFieldGetExpression)) return false;
        if (!super.equals(o)) return false;
        StaticFieldGetExpression that = (StaticFieldGetExpression) o;
        return Objects.equals(declaringType, that.declaringType) &&
               Objects.equals(getFieldName(), that.getFieldName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), declaringType, getFieldName());
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitStaticFieldGet(this);
    }
}
