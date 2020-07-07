/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

package org.orthodox.universel.cst;

import org.beanplanet.core.models.path.NamePath;

/**
 * Represents a type: such as a class, interface, enum type, primitive type or array type. The type may be an existing type (such as a compiled class) or
 * a type currently under compilation.
 */
public interface Type {
    /**
     * Returns the superclass of this type.
     *
     * @return the superclass of this type. If this type represents an interface, primitive type, Object or void then null is returned. If this
     * type represents an array type then Object is returned.
     */
    Type getSuperclass();

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    NamePath getFullyQualifiedName();

    /**
     * Gets the simple name of the type, consisting of a single name element which does not include the package name prefix. The simple name
     * of the fully qualified counterpart type <code>java.lang.String</code> is <code>String</code>.
     *
     * @return the simple type name.
     */
    default String getSimpleName() { return getFullyQualifiedName().getLastElement(); }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    int getDimensions();

    /**
     * Whether this type reference is to an array type. In this default implementation, the type is an array if there
     * are one or more dimensions.
     *
     * @return true if the type referred to is an array, false otherwise.
     */
    default boolean isArray() {
        return getDimensions() > 0;
    }

    /**
     * If this type is an array type, returns the component type of the array.
     *
     * @return the component type of this array type, or null if this type is not an array type.
     */
    Type getComponentType();

    /**
     * Whether this type reference is to a primitive type.
     *
     * @return true if the type referred to is a primitive type, false otherwise.
     */
    boolean isPrimitiveType();

    /**
     * Whether this type reference is the void type.
     *
     * @return true if the type referred to is the void type, false otherwise.
     */
    boolean isVoidType();

    /**
     * Whether the type referred to is an interface type.
     *
     * @return true if the type referred to is an interface, false otherwise.
     */
    boolean isInterface();

    /**
     * Whether this type reference is a reference type.
     *
     * @return true if the type referred to is a reference type, false otherwise.
     */
    boolean isReferenceType();
}
