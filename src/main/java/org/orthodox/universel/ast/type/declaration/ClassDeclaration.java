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

import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.List;

public class ClassDeclaration extends TypeDeclaration implements CompositeNode {
    /**
     * Instantiates a new type declaration
     *
     * @param modifiers the modifiers associated with this type declaration.
     * @param packageDeclaration the package in which this type is declared.
     * @param name the simple name of the type declared.
     * @param typeParameters the type parameters associated with the declared type.
     * @param members the members declared by the type.
     */
    public ClassDeclaration(Modifiers modifiers,
                            PackageDeclaration packageDeclaration,
                            Name name,
                            NodeSequence<TypeParameter> typeParameters,
                            NodeSequence<Node> members,
                            List<TypeReference> extendsList,
                            List<TypeReference> implementsList) {
        super(modifiers, packageDeclaration, name, typeParameters, members, extendsList, implementsList);
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitClassDeclaration(this);
    }
}
