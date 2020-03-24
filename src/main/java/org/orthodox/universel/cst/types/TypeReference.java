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

package org.orthodox.universel.cst.types;

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

/**
 * Represents a referred to type, such as java.lang.String or com.acme.MyClass. The referenced type may be an existing
 * type (such as a compiled class) or a type currently being compiled.
 *
 * @author Gary Watson
 */
public abstract class TypeReference extends Expression {
    private NamePath name;
    protected Class<?> typeDescriptor;

    /**
     * Constructs a new type AST node instance.
     *
     * @param tokenImage the image representing this cst type.
     * @param name the name of the referenced type, which may be fully qualified.
     * @param dimensions the number of dimensions, if this is is an array type.
     */
    public TypeReference(TokenImage tokenImage, NamePath name, int dimensions) {
        super(tokenImage);
        this.name = name;
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

    /**
     * Gets the name of the type referred to, including any package name prefix which may have been used.
     *
     * @return the name of the type reference.
     */
    public NamePath getName() {
        return name;
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return typeDescriptor;
    }

    public void setTypeDescriptor(Class<?> typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitTypeReference(this);
    }
}
