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

import org.beanplanet.core.models.path.DelimitedNamePath;
import org.beanplanet.core.models.path.NamePath;

import static org.beanplanet.core.lang.TypeUtil.determineArrayDimensions;

/**
 * Represents an existing POJO class type.
 */
public class JavaType implements Type {
    private Class<?> javaClass;

    public JavaType(Class<?> javaClass) {
        this.javaClass = javaClass;
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }

    @Override
    public Type getSuperclass() {
        return javaClass.getSuperclass() == null ? new JavaType(javaClass.getSuperclass()) : null;
    }

    @Override
    public NamePath getFullyQualifiedName() {
        return new DelimitedNamePath(javaClass.getName(), ".");
    }

    @Override
    public int getDimensions() {
        return javaClass.isArray() ? determineArrayDimensions(javaClass) : 0;
    }

    @Override
    public Type getComponentType() {
        return new JavaType(javaClass.getComponentType());
    }

    @Override
    public boolean isPrimitiveType() {
        return javaClass.isPrimitive();
    }

    @Override
    public boolean isVoidType() {
        return javaClass == Void.class;
    }

    @Override
    public boolean isInterface() {
        return javaClass.isInterface();
    }

    @Override
    public boolean isReferenceType() {
        return !(isPrimitiveType() || isVoidType());
    }
}
