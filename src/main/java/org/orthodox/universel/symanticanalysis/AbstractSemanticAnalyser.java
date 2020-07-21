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

import org.orthodox.universel.compiler.ImportScope;
import org.orthodox.universel.compiler.MethodScope;
import org.orthodox.universel.compiler.TypeDeclarationScope;
import org.orthodox.universel.ast.ImportDecl;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Script;
import org.orthodox.universel.ast.UniversalVisitorAdapter;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.ast.type.declaration.ClassDeclaration;

public class AbstractSemanticAnalyser extends UniversalVisitorAdapter implements SemanticAnalyser {
    private SemanticAnalysisContext context;
    private ImportDecl importDecl;

    protected SemanticAnalysisContext getContext() {
        return context;
    }

    @Override
    public Node performAnalysis(SemanticAnalysisContext context, Node from) {
        this.context = context;
        return from.accept(this);
    }

    @Override
    public Node visitClassDeclaration(ClassDeclaration node) {
        getContext().pushScope(new TypeDeclarationScope(node));

        try {
            return super.visitClassDeclaration(node);
        } finally {
            getContext().popScope();
        }
    }

    @Override
    public Node visitImportDeclaration(final ImportDecl node) {
        this.importDecl = node;

        // Imports are scope to end-of-file so remain on the scopes stack
        getContext().pushScope(new ImportScope(node, getContext().getMessages()));

        return super.visitImportDeclaration(node);
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
        getContext().pushScope(new MethodScope(node));

        try {
            return super.visitMethodDeclaration(node);
        } finally {
            getContext().popScope();
        }
    }

    @Override
    public Node visitScript(final Script node) {
        getContext().pushScope(new ImportScope(getContext().getDefaultImports(), getContext().getMessages()));

        try {
            return super.visitScript(node);
        } finally {
            getContext().popScope();
        }
    }

}
