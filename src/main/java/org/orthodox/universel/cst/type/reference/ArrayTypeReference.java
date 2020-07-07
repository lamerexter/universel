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

import org.orthodox.universel.cst.TokenImage;

import static org.beanplanet.core.lang.TypeUtil.forName;

/**
 * Represents an array type. Examples include <code>boolean[]</code> and <code>com.acme.MyClass[][]</code>.
 */
public class ArrayTypeReference extends TypeReference {
    private final TypeReference componentType;

    /**
     * Constructs a new type AST resolved type reference instance.
     *
     * @param tokenImage the image representing this cst type.
     * @param componentType the component type of the array referred to, for example <code>con.acme.MyClass[]</code> for
     *                      the array type <code>con.acme.MyClass[][]</code> or <code>boolean</code> for array type
     *                      <code>boolean[]</code>.
     * @param dimensions the number of dimensions of the array, for example <code>2</code> for the array type
     *                   <code>con.acme.MyClass[][]</code> or <code>1</code> for array type <code>boolean[]</code>.
     */
    public ArrayTypeReference(TokenImage tokenImage, TypeReference componentType, int dimensions) {
        super(tokenImage, componentType.getName(), dimensions);
        this.componentType = componentType;
    }

    public TypeReference getComponentType() {
        return componentType;
    }

    @Override
    public TypeReference getType() {
        return this;
    }

    public Class<?> getTypeDescriptor() {
        Class<?> componentClass = getComponentType().getTypeDescriptor();
        return forName(componentClass, getDimensions());
    }

    /**
     * Whether this type reference is a reference type.
     *
     * @return always true as arrays are all reference types in the JVM.
     */
    public boolean isReferenceType() {
        return true;
    }
}
