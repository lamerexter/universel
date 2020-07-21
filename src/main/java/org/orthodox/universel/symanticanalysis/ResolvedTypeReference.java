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

package org.orthodox.universel.symanticanalysis;

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.Type;
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.Objects;

public class ResolvedTypeReference extends TypeReference {
    private Type wrappedType;

    /**
     * Constructs a new type AST type reference instance.
     *
     * @param typeReference the referenced type.
     */
    public ResolvedTypeReference(final TypeReference typeReference) {
        this(typeReference.getTokenImage(), typeReference);
    }

    /**
     * Constructs a new type AST type reference instance.
     *
     * @param tokenImage the image representing this cst type.
     * @param type the referenced type.
     */
    public ResolvedTypeReference(final TokenImage tokenImage, final Type type) {
        super(tokenImage);
        this.wrappedType = type;
    }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        return wrappedType.getName();
    }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    @Override
    public int getDimensions() {
        return wrappedType.getDimensions();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ResolvedTypeReference)) return false;
        if (!super.equals(o)) return false;
        ResolvedTypeReference that = (ResolvedTypeReference) o;
        return Objects.equals(wrappedType, that.wrappedType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wrappedType);
    }
}
