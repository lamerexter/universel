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

package org.orthodox.universel.cst.type.declaration;

import org.orthodox.universel.cst.type.reference.TypeReference;

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
        super(typeDeclaration.getTokenImage(), typeDeclaration.getFullyQualifiedName());
        this.typeDeclaration = typeDeclaration;
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
}
