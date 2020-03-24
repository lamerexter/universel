
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


import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.ElvisExpression;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.TypeReference;

/**
 * A use of the <a href="">visitor pattern</a> for working with AST nodes. There is a <code>visit&lt;AstNode&gt;(&lt;AstNode&gt)</code>
 * for each AST node defined by the Universel grammar.
 *
 * @author Gary Watson
 */
public interface AstVisitor {
    Node visitAnnotation(Annotation node);

    Node visitBetweenExpression(BetweenExpression node);

    Node visitBinaryExpression(BinaryExpression node);

    Node visitElvisExpression(ElvisExpression node);

    Node visitInExpression(InExpression node);
    Node visitInstanceofExpression(InstanceofExpression node);

    Node visitBooleanLiteral(BooleanLiteralExpr node);

    Node visitDecimalFloatingPointLiteral(DecimalFloatingPointLiteralExpr node);

    Node visitDecimalIntegerLiteral(DecimalIntegerLiteralExpr node);

    Node visitImportDeclaration(ImportDecl node);

    Node visitList(ListExpr node);

    Node visitMap(MapExpr node);

    Node visitMethodCall(MethodCall node);

    Node visitModifiers(Modifiers node);

    Node visitName(QualifiedIdentifier node);

    Node visitName(Name node);

    Node visitNullLiteral(NullLiteralExpr node);

    Node visitNullTestExpression(NullTestExpression node);

    Node visitNumericLiteralExpression(NumericLiteral node);

    Node visitRangeExpression(RangeExpression node);

    Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node);

    Node visitScript(Script node);

    Node visitSet(SetExpr node);

    Node visitStringLiteral(StringLiteralExpr node);

    Node visitTernaryExpression(TernaryExpression node);

    Node visitTypeReference(TypeReference node);
    Node visitUnaryExpression(UnaryExpression node);
}
