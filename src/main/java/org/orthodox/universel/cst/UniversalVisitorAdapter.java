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

package org.orthodox.universel.cst;

import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.ElvisExpression;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;

import static org.beanplanet.core.util.CollectionUtil.nullSafe;

public class UniversalVisitorAdapter implements UniversalCodeVisitor {
    @Override
    public boolean visitAnnotation(Annotation node) {
        return false;
    }

    @Override
    public boolean visitBetweenExpression(BetweenExpression node) {
        return false;
    }

    @Override
    public boolean visitBinaryExpression(BinaryExpression node) {
        return false;
    }

    @Override
    public boolean visitNumericLiteralExpression(NumericLiteral node) {
        return false;
    }

    @Override
    public boolean visitElvisExpression(ElvisExpression node) {
        return false;
    }

    @Override
    public boolean visitInExpression(InExpression node) {
        return false;
    }

    @Override
    public boolean visitInstanceofExpression(InstanceofExpression node) {
        node.getLhsExpression().accept(this);
        node.getRhsExpression().accept(this);
        return false;
    }

    @Override
    public boolean visitBooleanLiteral(BooleanLiteralExpr node) {
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
    public boolean visitModifiers(Modifiers node) {
        return false;
    }

    @Override
    public boolean visitName(QualifiedIdentifier node) {
        return false;
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
    public boolean visitNullTestExpression(NullTestExpression node) {
        return false;
    }

    @Override
    public boolean visitRangeExpression(RangeExpression node) {
        return false;
    }

    @Override
    public boolean visitReferenceType(ReferenceType node) {
        return false;
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

    @Override
    public boolean visitTernaryExpression(TernaryExpression node) {
        return false;
    }

    @Override
    public boolean visitTypeReference(TypeReference node) {
        return false;
    }

    @Override
    public boolean visitUnaryExpression(UnaryExpression node) {
        return node.getExpression() == null || node.getExpression().accept(this);
    }
}
