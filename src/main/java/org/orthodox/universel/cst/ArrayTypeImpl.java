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

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Objects;

import static org.beanplanet.core.lang.TypeUtil.forName;

public class ArrayTypeImpl extends TypeReference implements ArrayType {
    private final Type componentType;
    private final int dimensions;

    public ArrayTypeImpl(final Type componentType) {
        this(componentType, 1);
    }

    public ArrayTypeImpl(final Type componentType, int dimensions) {
        super(null);
        this.componentType = componentType;
        this.dimensions = dimensions;
    }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        return getComponentType().getName();
    }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    @Override
    public int getDimensions() {
        return getComponentType().getDimensions()+dimensions;
    }

    /**
     * If this type is an array type, returns the component type of the array.
     *
     * @return the component type of this array type, or null if this type is not an array type.
     */
    @Override
    public TypeReference getComponentType() {
        return (TypeReference)componentType;
    }

    @Override
    public Class<?> getTypeDescriptor() {
        Class<?> componentClass = getComponentType().getTypeDescriptor();
        return componentClass == null ? null : forName(componentClass, dimensions);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayTypeImpl)) return false;
        if (!super.equals(o)) return false;
        ArrayTypeImpl arrayType = (ArrayTypeImpl) o;
        return getDimensions() == arrayType.getDimensions() &&
               Objects.equals(getComponentType(), arrayType.getComponentType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getComponentType(), getDimensions());
    }
}
