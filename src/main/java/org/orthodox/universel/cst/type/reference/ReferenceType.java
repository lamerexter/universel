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

package org.orthodox.universel.cst.type.reference;

import org.orthodox.universel.cst.CompositeNode;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * An AST representation of a reference type. This will consist of an existing component type type (either an Object type or
 * a primitive type), plus a number of array dimensions. Reference types with primitive component type must have a non-zero
 * number of dimensions.
 */
public final class ReferenceType extends TypeReference implements CompositeNode {
    private final TypeReference referredType;

    /**
     * Constructs a new type AST type reference instance.
     *
     * @param tokenImage   the image representing this cst type.
     * @param referredType the referring type: either an extension of <code>Object</code> or a primitive type.
     * @param dimensions   the number of dimensions, if this is is an array type.
     */
    public ReferenceType(TokenImage tokenImage, TypeReference referredType, int dimensions) {
        super(tokenImage, referredType.getName(), dimensions);
        this.referredType = referredType;
    }

    public TypeReference getReferredType() {
        return referredType;
    }

    /**
     * Whether this type reference is a reference type.
     *
     * @return true if the type referred to is a reference type, false otherwise.
     */
    public boolean isReferenceType() {
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceType)) return false;
        if (!super.equals(o)) return false;
        ReferenceType that = (ReferenceType) o;
        return Objects.equals(getReferredType(), that.getReferredType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getReferredType());
    }

    @Override
    public List<Node> getChildNodes() {
        return referredType == null ? emptyList() : singletonList(referredType);
    }

    /**
     * Whether the type referred to is a sequence type (consists of a number of ordered elements).
     *
     * @return true if the referred component type is a sequence or this reference type has dimensions (is an array).
     */
    public boolean isSequence() {
        return referredType.isSequence() || getDimensions() > 0;
    }
}