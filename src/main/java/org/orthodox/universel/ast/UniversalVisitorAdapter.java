/*
 *  MIT Licence:
 *  
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.collections.MapExpr;
import org.orthodox.universel.ast.collections.SetExpr;
import org.orthodox.universel.ast.literals.*;

import static org.beanplanet.core.util.CollectionUtil.nullSafe;

public class UniversalVisitorAdapter implements UniversalCodeVisitor {
    @Override
    public boolean visitBooleanLiteral(BooleanLiteralExpr node) {
        return true;
    }

    @Override
    public boolean visitDecimalFloatingPointLiteral(DecimalFloatingPointLiteralExpr node) {
        return true;
    }

    @Override
    public boolean visitDecimalIntegerLiteral(DecimalIntegerLiteralExpr node) {
        return true;
    }

    @Override
    public boolean visitImportDeclaration(ImportDecl node) {
        return true;
    }

    @Override
    public boolean visitList(ListExpr node) {
        for (Node child : node.getElements()) {
            child.accept(this);
        }
        return true;
    }

    @Override
    public boolean visitMap(MapExpr node) {
        for (Node child : nullSafe(node.getEntries())) {
            child.accept(this);
        }

        return true;
    }

    @Override
    public boolean visitMethodCall(MethodCall node) {
        for (Node child : node.getParameters()) {
            child.accept(this);
        }

        return true;
    }

    @Override
    public boolean visitName(Name node) {
        return true;
    }

    @Override
    public boolean visitNullLiteral(NullLiteralExpr node) {
        return true;
    }

    @Override
    public boolean visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node) {
        return true;
    }

    @Override
    public boolean visitScript(Script node) {
        if ( node.getImportDeclaration() != null ) {
            node.getImportDeclaration().accept(this);
        }

        for (Node child : node.getBodyElements()) {
            child.accept(this);
        }

        return true;
    }

    @Override
    public boolean visitSet(SetExpr node) {
        for (Node child : node.getElements()) {
            child.accept(this);
        }

        return true;
    }

    @Override
    public boolean visitStringLiteral(StringLiteralExpr node) {
        return true;
    }
}
