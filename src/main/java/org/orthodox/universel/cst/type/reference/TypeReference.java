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

import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.Type;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.Objects;

/**
 * Represents a referred to type, such as java.lang.String or com.acme.MyClass. The referenced type may be an existing
 * type (such as a compiled class) or a type currently being compiled.
 *
 * @author Gary Watson
 */
public class TypeReference extends Node implements Type {
    /** The name of the type referred to, including any package name prefix which may have been used. */
    private final NamePath name;

    /** The arity of dimensions for an array type reference. */
    private final int dimensions;

    /**
     * Constructs a new type AST type reference instance.
     *
     * @param tokenImage the image representing this cst type.
     * @param typeDescriptor the name of the referenced type, which may be fully qualified.
     * @param name the name of the referenced type, which may be fully qualified.
     */
    public TypeReference(TokenImage tokenImage, Class<?> typeDescriptor, NamePath name) {
        this(tokenImage, typeDescriptor, name, 0);
    }

    /**
     * Constructs a new type AST type reference instance.
     *
     * @param tokenImage the image representing this cst type.
     * @param name the name of the referenced type, which may be fully qualified.
     * @param dimensions the number of dimensions, if this is is an array type.
     */
    public TypeReference(TokenImage tokenImage, NamePath name, int dimensions) {
        this(tokenImage, null, name, dimensions);
    }

    /**
     * Constructs a new type AST type reference instance. TODO: The type hierachy for this type is not quite right. This
     * constructor is only used by {@link ResolvedTypeReference} which passes in the resolved type...
     *
     * @param tokenImage the image representing this cst type.
     * @param typeDescriptor the name of the referenced type, which may be fully qualified.
     * @param name the name of the referenced type, which may be fully qualified.
     * @param dimensions the number of dimensions, if this is is an array type.
     */
    public TypeReference(TokenImage tokenImage, Class<?> typeDescriptor, NamePath name, int dimensions) {
        super(tokenImage, typeDescriptor);
        this.name = name;
        this.dimensions = dimensions;
    }

    /**
     * Constructs a new type AST node instance.
     *
     * @param tokenImage the image representing this cst type.
     * @param name the name of the referenced type, which may be fully qualified.
     */
    public TypeReference(TokenImage tokenImage, NamePath name) {
        this(tokenImage, name, 0);
    }

    @Override
    public TypeReference getType() {
        return this;
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return TypeUtil.loadClassOrNull(getName().join("."));
    }

    /**
     * If this type is an array type, returns the component type of the array.
     *
     * @return the component type of this array type, or null if this type is not an array type.
     */
    public TypeReference getComponentType() {
        Class<?> clazz = getTypeDescriptor();
        return clazz != null && clazz.isArray() ? new ResolvedTypeReference(getTokenImage(), clazz.getComponentType()) : null;
    }

    /**
     * Gets the name of the type referred to, including any package name prefix which may have been used.
     *
     * @return the name of the type reference.
     */
    public NamePath getName() {
        return name;
    }

    @Override
    public Type getSuperclass() {
        return null;
    }

    @Override
    public NamePath getFullyQualifiedName() {
        return getName();
    }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    public int getDimensions() {
        return dimensions;
    }

    /**
     * Whether this type reference is to a primitive type.
     *
     * @return true if the type referred to is a primitive type, false otherwise.
     */
    public boolean isPrimitiveType() {
        return getTypeDescriptor() != null && getTypeDescriptor().isPrimitive();
    }

    /**
     * Whether this type reference is the void type.
     *
     * @return true if the type referred to is the void type, false otherwise.
     */
    public boolean isVoidType() {
        return getTypeDescriptor() == void.class;
    }

    /**
     * Whether the type referred to is an interface type.
     *
     * @return true if the type referred to is an interface, false otherwise.
     */
    public boolean isInterface() {
        return getTypeDescriptor() != null && getTypeDescriptor().isInterface();
    }

    /**
     * Whether this type reference is a reference type.
     *
     * @return true if the type referred to is a reference type, false otherwise.
     */
    public boolean isReferenceType() {
        return getTypeDescriptor() != null && Object.class.isAssignableFrom(getTypeDescriptor());
    }

    public TypeReference accept(UniversalCodeVisitor visitor) {
        return visitor.visitTypeReference(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeReference)) return false;
        if (!super.equals(o)) return false;
        TypeReference that = (TypeReference) o;
        return getDimensions() == that.getDimensions()
               && Objects.equals(getName(), that.getName())
               && Objects.equals(getTypeDescriptor(), that.getTypeDescriptor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getDimensions());
    }

    /**
     * Whether the type referred to is a sequence type (consists of a number of ordered elements). This includes arrays,
     * list, sets and the class of all other iterable and streamable types.
     *
     * @return true if the type referred to is an array or {@link Iterable}.
     */
    public boolean isSequence() {
        final Class<?> clazz = getTypeDescriptor();
        return isArray() || (clazz != null && Iterable.class.isAssignableFrom(clazz));
    }
}
