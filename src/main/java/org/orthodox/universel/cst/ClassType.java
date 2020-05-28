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

package org.orthodox.universel.cst;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 * Represents an existing class type.
 *
 * @author Gary Watson
 */
public class ClassType implements Type {
    private Class<?> wrappedClass;

    public ClassType() {
    }

    public ClassType(Class<?> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }

    /**
     * Returns the superclass of this type.
     *
     * @return the superclass of this type. If this type represents an interface, primitive type, Object or void then null is returned. If this
     * type represents an array type then Object is returned.
     */
    public Type getSuperclass() {
        return new ClassType(wrappedClass.getSuperclass());
    }

    /**
     * Returns the fully qualified name of the type, such as <code>a.b.c.D</code>.
     *
     * @return the fully qualified type name, including package prefix, delimited by dot (.).
     */
    public String getFullyQualifiedName() {
        return wrappedClass.getName();
    }

    /**
     * Returns the fields this type declares.
     *
     * @return a list of the fields declared by this type, guaranteed to be non-null.
     */
    public List<Field> getDeclaredFields() {
        return stream(wrappedClass.getDeclaredFields()).map(ClassField::new).collect(Collectors.toList());
    }

    /**
     * Attempts to resolve the actual type this type represents.
     *
     * @return the actual type this type represents, which may be null if not yet available.
     */
    public Class<?> resolveType() {
        return wrappedClass;
    }

    /**
     * Returns whether this type represents an array.
     *
     * @return true if this type is an array type, false otherwise.
     */
    public boolean isArray() {
        return wrappedClass.isArray();
    }

    /**
     * Returns the component type of this array type. Note that the component type may itself be an array in the
     * event this array type represents a multi-dimensional array.
     *
     * @return the component type of this array type or null if this type does not represent an array type.
     */
    public Type getComponentType() {
        Class<?> componentType = wrappedClass.getComponentType();
        return componentType == null ? null : new ClassType(componentType);
    }
}
