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

import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;

/**
 * The superclass of all expressions on the Abstract Syntax Tree.
 */
public class Expression extends Node {
    /**
     * Constructs an expression from the given parser token image.
     *
     * @param tokenImage the parser token image backing this expression.
     */
    public Expression(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * Constructs an expression from the given parser token image and type.
     *
     * @param tokenImage the parser token image backing this node.
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     */
    public Expression(TokenImage tokenImage, Class<?> typeDescriptor) {
        this(tokenImage, new ResolvedTypeReferenceOld(tokenImage, typeDescriptor));
    }

    /**
     * Constructs the expression from the given parser token image and type.
     *
     * @param tokenImage the parser token image backing this node.
     * @param type  the type of the node, or null if unknown at this time.
     */
    public Expression(TokenImage tokenImage, Type type) {
        super(tokenImage, type);
    }
}
