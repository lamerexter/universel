
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


import org.orthodox.universel.ast.allocation.ArrayCreationExpression;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.ast.annotation.Annotation;
import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.collections.MapEntryExpr;
import org.orthodox.universel.ast.collections.MapExpr;
import org.orthodox.universel.ast.collections.SetExpr;
import org.orthodox.universel.ast.conditionals.IfStatement;
import org.orthodox.universel.ast.conditionals.TernaryExpression;
import org.orthodox.universel.ast.functional.FunctionalInterfaceObject;
import org.orthodox.universel.ast.literals.*;
import org.orthodox.universel.ast.methods.LambdaFunction;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.ast.navigation.*;
import org.orthodox.universel.ast.type.LoadTypeExpression;
import org.orthodox.universel.ast.type.Parameter;
import org.orthodox.universel.ast.type.StaticFieldGetExpression;
import org.orthodox.universel.ast.type.declaration.ClassDeclaration;
import org.orthodox.universel.ast.type.declaration.InterfaceDeclaration;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.compiler.Box;
import org.orthodox.universel.compiler.Unbox;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.ValueConsumingNode;
import org.orthodox.universel.symanticanalysis.conversion.BinaryExpressionOperatorMethodCall;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

/**
 * A use of the <a href="">visitor pattern</a> for working with AST nodes. There is a <code>visit&lt;AstNode&gt;(&lt;AstNode&gt)</code>
 * for each AST node defined by the Universel grammar.
 *
 * @author Gary Watson
 */
public interface UniversalCodeVisitor {
    Node visitAnnotation(Annotation node);

    //   Node visitAnnotationDeclaration(AnnotationDeclaration node);
    Node visitArrayCreationExpression(ArrayCreationExpression node);

    //   Node visitAssertStatement(AssertStatement node);
//   Node visitAssignmentExpression(AssignmentExpression node);
    Node visitBetweenExpression(BetweenExpression node);

    Node visitBoxExpression(Box node);

    Node visitBooleanLiteral(BooleanLiteralExpr node);

    Node visitBoxConversion(BoxConversion node);

    Node visitClassDeclaration(ClassDeclaration node);

    Node visitNumericLiteralExpression(NumericLiteral node);

    Node visitBinaryExpression(BinaryExpression node);

    Node visitBinaryExpression(BinaryExpressionOperatorMethodCall node);

    //   Node visitAxisStepExpression(AxisStepExpression node);
    //   Node visitBlockStatement(BlockStatement node);
//   Node visitCaseExpression(CaseExpression node);
//   Node visitClassExpression(ClassExpression node);
//   Node visitComment(Comment node);
//   Node visitCompoundBlock(CompoundBlock node);
//   Node visitConstructorDeclaration(ConstructorDeclaration node);
//   Node visitDoStatement(DoStatement node);

    //   Node visitEnumDeclaration(EnumDeclaration node);
//   Node visitEmptyStatement(EmptyStatement node);
    Node visitFieldDeclaration(FieldDeclaration node);
//   Node visitFieldExpression(FieldExpression node);
//   Node visitFilterExpression(FilterExpression node);
//   Node visitForEachStatement(ForEachStatement node);
//   Node visitForStatement(ForStatement node);
//   Node visitFunction(Function node);

    Node visitFunctionalInterfaceObject(FunctionalInterfaceObject node);

    Node visitInExpression(InExpression node);

    Node visitIfStatement(IfStatement node);

    Node visitInternalNodeSequence(InternalNodeSequence node);

    //   Node visitImportDeclaration(ImportDeclaration node);
//   Node visitInterfaceDeclaration(InterfaceDeclaration node);
    Node visitInstanceofExpression(InstanceofExpression node);

    //   Node visitJUELStringExpression(JUELStringExpression node);
//   Node visitListExpression(ListExpression node);
    Node visitImportDeclaration(ImportDecl node);

    Node visitInterfaceDeclaration(InterfaceDeclaration node);

    Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node);

    Node visitLambdaFunction(LambdaFunction node);

    Node visitList(ListExpr node);

    Node visitLoadLocal(LoadLocal node);

    Node visitLoadType(LoadTypeExpression node);

    MapEntryExpr visitMapEntry(MapEntryExpr node);

    Node visitMap(MapExpr node);

    MethodDeclaration visitMethodDeclaration(MethodDeclaration node);

    Node visitMethodCall(MethodCall node);

    Node visitMethodCall(StaticMethodCall node);

    Node visitMethodCall(InstanceMethodCall node);

    Modifiers visitModifiers(Modifiers node);

    Node visitName(QualifiedIdentifier node);

    Name visitName(Name node);

    Node visitNameTest(NameTest nameTest);

//    <T extends NodeTest> Node visitNavigationStep(NavigationAxisAndTestStep<T> node);

    <T extends NodeTest> NavigationAxisAndNodeTest<T> visitNavigationAxisAndNodeTest(NavigationAxisAndNodeTest<T> node);

    Node visitNavigationExpression(NavigationExpression node);

    Node visitNavigationStream(NavigationStream node);

    NavigationFilterStep visitNavigationFilterStep(NavigationFilterStep node);

    Node visitNodeSequence(NodeSequence<? extends Node> node);

    Node visitNullLiteral(NullLiteralExpr node);

    //   Node visitMapExpression(MapExpression node);
//   Node visitMethodDeclaration(MethodDeclaration node);

    Node visitNullTestExpression(NullTestExpression node);

    Node visitObjectCreationExpression(ObjectCreationExpression node);

    PackageDeclaration visitPackageDeclaration(PackageDeclaration node);
//   Node visitPathExpression(PathExpression node);
//    Node visitRangeExpression(RangeExpression node);

    Parameter visitParameter(Parameter node);

    Node visitReturnStatement(ReturnStatement node);

    Node visitScript(Script node);

    Node visitSet(SetExpr node);

    Node visitStringLiteral(StringLiteralExpr node);

    Node visitStaticFieldGet(StaticFieldGetExpression node);

    //   Node visitSetExpression(SetExpression node);
    Node visitTernaryExpression(TernaryExpression node);

    //   Node visitThrowStatement(ThrowStatement node);
    TypeReference visitTypeReference(TypeReference node);

    Node visitTypeConversion(TypeConversion node);

    //   Node visitTryStatement(TryStatement node);
    Node visitUnaryExpression(UnaryExpression node);

    Node visitUnboxExpression(Unbox node);

    Node visitValueConsumingNode(ValueConsumingNode node);

    Node visitJvmInstruction(JvmInstructionNode jvmInstructionNode);

//   boolean visitVariableDeclaration(VariableDeclaration node);
//   boolean visitVariableDeclarationExpression(VariableDeclarationExpression node);
//   boolean visitWhileLoop(WhileStatement node);
}
