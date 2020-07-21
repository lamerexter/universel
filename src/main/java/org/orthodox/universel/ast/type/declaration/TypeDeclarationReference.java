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

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.Objects;

/**
 * Represents a type reference to a {@link TypeDeclaration}.
 */
public class TypeDeclarationReference extends TypeReference {
    private final TypeDeclaration typeDeclaration;

    /**
     * Constructs a new type AST resolved type reference instance.
     *
     * @param typeDeclaration the type referred to by the type reference.
     */
    public TypeDeclarationReference(TypeDeclaration typeDeclaration) {
        super(typeDeclaration.getTokenImage());
        this.typeDeclaration = typeDeclaration;
    }

    /**
     * Gets the type declaration associated with this type referece.
     *
     * @return the type declaration associsted with this type reference.
     */
    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        return getTypeDeclaration().getFullyQualifiedName();
    }

    /**
     * Whether the type referred to is an interface type.
     *
     * @return true if the type referred to is an interface, false otherwise.
     */
    public boolean isInterface() {
        return typeDeclaration instanceof InterfaceDeclaration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeDeclarationReference)) return false;
        if (!super.equals(o)) return false;
        TypeDeclarationReference that = (TypeDeclarationReference) o;
        return Objects.equals(typeDeclaration, that.typeDeclaration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeDeclaration);
    }

    /**
     * Whether the type referred to is a sequence type (consists of a number of ordered elements).
     *
     * @return true if the referred component type is a sequence or this reference type has dimensions (is an array).
     */
    public boolean isSequence() {
        return getTypeDeclaration().isSequance();
    }
}
