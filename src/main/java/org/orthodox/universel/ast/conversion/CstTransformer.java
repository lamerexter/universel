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

package org.orthodox.universel.ast.conversion;

import org.orthodox.universel.ast.AstVisitor;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.ElvisExpression;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.TypeReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     Converts the Concrete Syntax Tree (CST) to the Abstract Syntax Tree (AST).
 * </p>
 * <p>
 *     Delegates to a number of registered transformers, iterating over the CST until no further changes are
 *     made to the tree. That is, tends towards an AST through iterative application of a number of specific trabsformers.
 * </p>
 */
public class CstTransformer implements AstVisitor {
    private static final List<AstVisitor> cstTransformers = Arrays.asList(
//        new UnaryMinusLiteralTransformer()
    );

    @Override
    public Node visitAnnotation(Annotation node) {
        return node;
    }

    @Override
    public Node visitBetweenExpression(BetweenExpression node) {
        return node;
    }

    @Override
    public Node visitBinaryExpression(BinaryExpression node) {
        return node;
    }

    @Override
    public Node visitElvisExpression(ElvisExpression node) {
        return node;
    }

    @Override
    public Node visitInExpression(InExpression node) {
        return node;
    }

    @Override
    public Node visitInstanceofExpression(InstanceofExpression node) {
        return node;
    }

    @Override
    public Node visitBooleanLiteral(BooleanLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitDecimalFloatingPointLiteral(DecimalFloatingPointLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitDecimalIntegerLiteral(DecimalIntegerLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitImportDeclaration(ImportDecl node) {
        return node;
    }

    @Override
    public Node visitList(ListExpr node) {
        return node;
    }

    @Override
    public Node visitMap(MapExpr node) {
        return node;
    }

    @Override
    public Node visitMethodCall(MethodCall node) {
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

    @Override
    public Node visitRangeExpression(RangeExpression node) {
        return node;
    }

    @Override
    public Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitScript(Script node) {
        List<Node> newBodyElements = new ArrayList<>(node.getBodyElements());
        for (int n=0; n < newBodyElements.size(); n++) {
            Node bodyElement = transform(newBodyElements.get(n));
            newBodyElements.set(n, bodyElement);
        }
        return new Script(node.getImportDeclaration(), newBodyElements);
    }

    private Node transform(Node node) {
        Node newNode = node;
        for (AstVisitor transformer : cstTransformers) {
            newNode = node.accept(transformer);
        }

        return newNode;
    }

    @Override
    public Node visitSet(SetExpr node) {
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
    public Node visitUnaryExpression(UnaryExpression node) {
        return transform(node);
    }
}
