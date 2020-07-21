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

package org.orthodox.universel.ast;

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class ParameterisedTypeImpl extends TypeReference implements ParameterisedType {
    private final Type rawtype;
    private final List<Type> typeParameters;

    /**
     * Constructs a new parameterised type from a given raw type and type parameters.
     *
     * @param rawType        the raw type of this parameterised type.
     * @param typeParameters type parameters of this parameterised type.
     */
    public ParameterisedTypeImpl(final Type rawType, final List<Type> typeParameters) {
        this(null, rawType, typeParameters);
    }

    /**
     * Constructs a new parameterised type from a given raw type and type parameters.
     *
     * @param tokenImage     the parser token image.
     * @param rawType        the raw type of this parameterised type.
     * @param typeParameters type parameters of this parameterised type.
     */
    public ParameterisedTypeImpl(TokenImage tokenImage, final Type rawType, final List<Type> typeParameters) {
        super(tokenImage);
        this.rawtype = rawType;
        this.typeParameters = typeParameters;
    }

    /**
     * Constructs a new parameterised type from a given raw type and type parameters.
     *
     * @param tokenImage     the parser token image.
     * @param rawType        the raw type of this parameterised type.
     * @param typeParameters type parameters of this parameterised type.
     */
    public ParameterisedTypeImpl(TokenImage tokenImage, final Type rawType, final Type ... typeParameters) {
        this(tokenImage, rawType, typeParameters == null ? null : asList(typeParameters));
    }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        return rawtype.getName();
    }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    @Override
    public int getDimensions() {
        return getRawType().getDimensions();
    }

    /**
     * Returns a list of {@code Type} objects representing the actual type
     * parameters to this type.
     *
     * @return a list of {@code Type} objects representing the actual type parameters to this type.
     */
    @Override
    public List<Type> getTypeParameters() {
        return typeParameters;
    }

    /**
     * Returns the {@code Type} object representing this type without regard to type arguments. For example, if this
     * type were <code>Collection&lt;T&gt;</code>, then <code>Collection</code> would be returned.
     *
     * @return the {@code Type} object representing the un-paramaterised class or interface of this type.
     */
    @Override
    public Type getRawType() {
        return rawtype;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ParameterisedTypeImpl)) return false;
        if (!super.equals(o)) return false;
        ParameterisedTypeImpl that = (ParameterisedTypeImpl) o;
        return Objects.equals(getTypeParameters(), that.getTypeParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTypeParameters());
    }
}
