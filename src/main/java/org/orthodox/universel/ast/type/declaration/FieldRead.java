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

import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.Type;
import org.orthodox.universel.ast.UniversalCodeVisitor;

/**
 * A read of a declared type's field on the Abstract Syntax Tree (AST).
 */
public class FieldRead extends AbstractFieldOperation {
    public FieldRead(TokenImage tokenImage,
                     boolean isStatic,
                     Type declaringType,
                     Type fieldType,
                     String fieldName) {
        super(tokenImage, isStatic, declaringType, fieldType, fieldName);
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitFieldAccess(this);
    }
}
