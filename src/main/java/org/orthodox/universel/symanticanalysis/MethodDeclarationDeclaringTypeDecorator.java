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

package org.orthodox.universel.symanticanalysis;

import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.ast.type.declaration.ClassDeclaration;
import org.orthodox.universel.ast.type.declaration.TypeDeclaration;
import org.orthodox.universel.ast.type.declaration.TypeDeclarationReference;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Locates {@link TypeDeclaration}s and updates them, where not already set, with their declaring type information.
 */
public class MethodDeclarationDeclaringTypeDecorator extends AbstractSemanticAnalyser {
    private Deque<TypeDeclaration> enclosingTypeDeclarations = new ArrayDeque<>();

    @Override
    public Node visitClassDeclaration(ClassDeclaration node) {
        enclosingTypeDeclarations.push(node);
        try {
            super.visitClassDeclaration(node);
        } finally {
            enclosingTypeDeclarations.pop();
        }

        return node;
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(MethodDeclaration node) {
        if (node.getDeclaringType() != null || enclosingTypeDeclarations.isEmpty()) return node;

        node.setDeclaringType(new TypeDeclarationReference(enclosingTypeDeclarations.peek()));
        return node;
    }
}