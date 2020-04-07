
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
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;
import org.orthodox.universel.symanticanalysis.conversion.BinaryExpressionOperatorMethodCall;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;

/**
 * A use of the <a href="">visitor pattern</a> for working with AST nodes. There is a <code>visit&lt;AstNode&gt;(&lt;AstNode&gt)</code>
 * for each AST node defined by the Universel grammar.
 *
 * @author Gary Watson
 */
public interface UniversalCodeVisitor {
    Node visitAnnotation(Annotation node);

    //   Node visitAnnotationDeclaration(AnnotationDeclaration node);
//   Node visitArrayCreationExpression(ArrayCreationExpression node);
//   Node visitAssertStatement(AssertStatement node);
//   Node visitAssignmentExpression(AssignmentExpression node);
    Node visitBetweenExpression(BetweenExpression node);
    Node visitBoxConversion(BoxConversion node);
    Node visitNumericLiteralExpression(NumericLiteral node);

    Node visitBinaryExpression(BinaryExpression node);
    Node visitBinaryExpression(BinaryExpressionOperatorMethodCall node);

    //   Node visitAxisStepExpression(AxisStepExpression node);
    //   Node visitBlockStatement(BlockStatement node);
//   Node visitCaseExpression(CaseExpression node);
//   Node visitClassDeclaration(ClassDeclaration node);
//   Node visitClassExpression(ClassExpression node);
//   Node visitComment(Comment node);
//   Node visitCompoundBlock(CompoundBlock node);
//   Node visitConstructorDeclaration(ConstructorDeclaration node);
//   Node visitDoStatement(DoStatement node);

    //   Node visitEnumDeclaration(EnumDeclaration node);
//   Node visitEmptyStatement(EmptyStatement node);
//   Node visitFieldDeclaration(FieldDeclaration node);
//   Node visitFieldExpression(FieldExpression node);
//   Node visitFilterExpression(FilterExpression node);
//   Node visitForEachStatement(ForEachStatement node);
//   Node visitForStatement(ForStatement node);
//   Node visitFunction(Function node);

    Node visitInExpression(InExpression node);
//   Node visitIfStatement(IfStatement node);
//   Node visitImportDeclaration(ImportDeclaration node);
//   Node visitInterfaceDeclaration(InterfaceDeclaration node);
   Node visitInstanceofExpression(InstanceofExpression node);
//   Node visitJUELStringExpression(JUELStringExpression node);
//   Node visitListExpression(ListExpression node);
    Node visitBooleanLiteral(BooleanLiteralExpr node);

    Node visitImportDeclaration(ImportDecl node);

    Node visitList(ListExpr node);

    Node visitMap(MapExpr node);

    Node visitMethodCall(MethodCall node);

    Node visitModifiers(Modifiers node);

    Node visitName(QualifiedIdentifier node);

    Node visitName(Name node);

    Node visitNullLiteral(NullLiteralExpr node);

    //   Node visitMapExpression(MapExpression node);
//   Node visitMethodDeclaration(MethodDeclaration node);

    Node visitNullTestExpression(NullTestExpression node);
    Node visitNavigationStream(NavigationStream node);

//   Node visitObjectCreationExpression(ObjectCreationExpression node);
//   Node visitPackageDeclaration(PackageDeclaration node);
//   Node visitPathExpression(PathExpression node);
//    Node visitRangeExpression(RangeExpression node);

    Node visitReferenceType(ReferenceType node);

    //   Node visitReturnStatement(ReturnStatement node);
    Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node);

    Node visitScript(Script node);

    Node visitSet(SetExpr node);

    Node visitStringLiteral(StringLiteralExpr node);

    //   Node visitSetExpression(SetExpression node);
    Node visitTernaryExpression(TernaryExpression node);

    //   Node visitThrowStatement(ThrowStatement node);
    Node visitTypeReference(TypeReference node);

    Node visitTypeConversion(TypeConversion node);
//   Node visitTryStatement(TryStatement node);
    Node visitUnaryExpression(UnaryExpression node);
//   boolean visitVariableDeclaration(VariableDeclaration node);
//   boolean visitVariableDeclarationExpression(VariableDeclarationExpression node);
//   boolean visitWhileLoop(WhileStatement node);
}
