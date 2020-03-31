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
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;
import org.orthodox.universel.symanticanalysis.conversion.BinaryExpressionOperatorMethodCall;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.beanplanet.core.util.CollectionUtil.nullSafe;

public class UniversalVisitorAdapter implements UniversalCodeVisitor {
    @Override
    public Node visitAnnotation(Annotation node) {
        return node;
    }

    @Override
    public Node visitBetweenExpression(BetweenExpression node) {
        return node;
    }

    @Override
    public Node visitBoxConversion(BoxConversion node) {
        return node.getSource().accept(this);
    }

    @Override
    public Node visitBinaryExpression(BinaryExpression node) {
        node.getLhsExpression().accept(this);
        node.getRhsExpression().accept(this);
        return node;
    }

    @Override
    public Node visitBinaryExpression(BinaryExpressionOperatorMethodCall node) {
        node.getParameters().forEach(p -> p.accept(this));
        return node;
    }

    @Override
    public Node visitNumericLiteralExpression(NumericLiteral node) {
        return (Node)node;
    }

    @Override
    public Node visitInExpression(InExpression node) {
        return node;
    }

    @Override
    public Node visitInstanceofExpression(InstanceofExpression node) {
        node.getLhsExpression().accept(this);
        node.getRhsExpression().accept(this);
        return node;
    }

    @Override
    public Node visitBooleanLiteral(BooleanLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitImportDeclaration(ImportDecl node) {
        return node;
    }

    @Override
    public Node visitList(ListExpr node) {
        for (Node child : node.getElements()) {
            child.accept(this);
        }
        return node;
    }

    @Override
    public Node visitMap(MapExpr node) {
        for (Node child : nullSafe(node.getEntries())) {
            child.accept(this);
        }

        return node;
    }

    @Override
    public Node visitMethodCall(MethodCall node) {
        for (Node child : node.getParameters()) {
            child.accept(this);
        }

        return node;
    }

    @Override
    public Node visitModifiers(Modifiers node) {
        return node;
    }

    @Override
    public Node visitName(QualifiedIdentifier node) {
        return node;
    }

    @Override
    public Node visitName(Name node) {
        return node;
    }

    @Override
    public Node visitNullLiteral(NullLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitNullTestExpression(NullTestExpression node) {
        return node;
    }

//    @Override
//    public Node visitRangeExpression(RangeExpression node) {
//        return node;
//    }

    @Override
    public Node visitReferenceType(ReferenceType node) {
        return node;
    }

    @Override
    public Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node) {
        node.getParts().stream().forEach(c -> c.accept(this));
        return node;
    }

    @Override
    public Node visitScript(Script node) {
        ImportDecl transformedImport = null;
        if ( node.getImportDeclaration() != null ) {
            transformedImport = (ImportDecl)node.getImportDeclaration().accept(this);
        }

        List<Node> transformedBodyElements = new ArrayList<>(node.getBodyElements().size());
        for (Node child : node.getBodyElements()) {
            transformedBodyElements.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getImportDeclaration(), transformedImport)
                                          && Objects.equals(node.getBodyElements(), transformedBodyElements);
        return noTransformationChanges ? node : new Script(transformedImport, transformedBodyElements);
    }

    @Override
    public Node visitSet(SetExpr node) {
        for (Node child : node.getElements()) {
            child.accept(this);
        }

        return node;
    }

    @Override
    public Node visitStringLiteral(StringLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitTernaryExpression(TernaryExpression node) {
        return node;
    }

    @Override
    public Node visitTypeReference(TypeReference node) {
        return node;
    }

    @Override
    public Node visitTypeConversion(TypeConversion node) {
        Node transformedSource = null;
        if ( node.getSource() != null ) {
            transformedSource = node.getSource().accept(this);
        }

        return Objects.equals(node.getSource(), transformedSource) ? node : new TypeConversion(transformedSource, node.getTargetType());
    }

    @Override
    public Node visitUnaryExpression(UnaryExpression node) {
        node.getExpression().accept(this);
        return node;
    }
}
