
/*
 *  MIT Licence:
 *
 *  Copyright (c) 2018 Orthodox Engineering Ltd
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
import org.orthodox.universel.cst.types.TypeReference;

/**
 * A use of the <a href="">visitor pattern</a> for working with AST nodes. There is a <code>visit&lt;AstNode&gt;(&lt;AstNode&gt)</code>
 * for each AST node defined by the Universel grammar.
 *
 * @author Gary Watson
 */
public interface UniversalCodeVisitor {
    boolean visitAnnotation(Annotation node);

    //   boolean visitAnnotationDeclaration(AnnotationDeclaration node);
//   boolean visitArrayCreationExpression(ArrayCreationExpression node);
//   boolean visitAssertStatement(AssertStatement node);
//   boolean visitAssignmentExpression(AssignmentExpression node);
    boolean visitBetweenExpression(BetweenExpression node);
    boolean visitNumericLiteralExpression(NumericLiteral node);

    boolean visitBinaryExpression(BinaryExpression node);

    //   boolean visitAxisStepExpression(AxisStepExpression node);
    //   boolean visitBlockStatement(BlockStatement node);
//   boolean visitCaseExpression(CaseExpression node);
//   boolean visitClassDeclaration(ClassDeclaration node);
//   boolean visitClassExpression(ClassExpression node);
//   boolean visitComment(Comment node);
//   boolean visitCompoundBlock(CompoundBlock node);
//   boolean visitConstructorDeclaration(ConstructorDeclaration node);
//   boolean visitDoStatement(DoStatement node);
    boolean visitElvisExpression(ElvisExpression node);

    //   boolean visitEnumDeclaration(EnumDeclaration node);
//   boolean visitEmptyStatement(EmptyStatement node);
//   boolean visitFieldDeclaration(FieldDeclaration node);
//   boolean visitFieldExpression(FieldExpression node);
//   boolean visitFilterExpression(FilterExpression node);
//   boolean visitForEachStatement(ForEachStatement node);
//   boolean visitForStatement(ForStatement node);
//   boolean visitFunction(Function node);

    boolean visitInExpression(InExpression node);
//   boolean visitIfStatement(IfStatement node);
//   boolean visitImportDeclaration(ImportDeclaration node);
//   boolean visitInterfaceDeclaration(InterfaceDeclaration node);
   boolean visitInstanceofExpression(InstanceofExpression node);
//   boolean visitJUELStringExpression(JUELStringExpression node);
//   boolean visitListExpression(ListExpression node);
    boolean visitBooleanLiteral(BooleanLiteralExpr node);

    boolean visitImportDeclaration(ImportDecl node);

    boolean visitList(ListExpr node);

    boolean visitMap(MapExpr node);

    boolean visitMethodCall(MethodCall node);

    boolean visitModifiers(Modifiers node);

    boolean visitName(QualifiedIdentifier node);

    boolean visitName(Name node);

    boolean visitNullLiteral(NullLiteralExpr node);

    //   boolean visitMapExpression(MapExpression node);
//   boolean visitMethodDeclaration(MethodDeclaration node);

    boolean visitNullTestExpression(NullTestExpression node);
//   boolean visitObjectCreationExpression(ObjectCreationExpression node);
//   boolean visitPackageDeclaration(PackageDeclaration node);
//   boolean visitPathExpression(PathExpression node);
    boolean visitRangeExpression(RangeExpression node);

    //   boolean visitReturnStatement(ReturnStatement node);
    boolean visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node);

    boolean visitScript(Script node);

    boolean visitSet(SetExpr node);

    boolean visitStringLiteral(StringLiteralExpr node);

    //   boolean visitSetExpression(SetExpression node);
    boolean visitTernaryExpression(TernaryExpression node);

    //   boolean visitThrowStatement(ThrowStatement node);
    boolean visitTypeReference(TypeReference node);
//   boolean visitTryStatement(TryStatement node);
    boolean visitUnaryExpression(UnaryExpression node);
//   boolean visitVariableDeclaration(VariableDeclaration node);
//   boolean visitVariableDeclarationExpression(VariableDeclarationExpression node);
//   boolean visitWhileLoop(WhileStatement node);
}
