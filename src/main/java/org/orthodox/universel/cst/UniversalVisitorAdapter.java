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

import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.NodeSequence.NodeSequenceBuilder;
import org.orthodox.universel.ast.allocation.ArrayCreationExpression;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.ast.conditionals.IfStatement;
import org.orthodox.universel.ast.conditionals.IfStatement.ElseIf;
import org.orthodox.universel.ast.functional.FunctionalInterfaceObject;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.ast.navigation.NodeTest;
import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapEntryExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.type.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.declaration.ClassDeclaration;
import org.orthodox.universel.cst.type.declaration.InterfaceDeclaration;
import org.orthodox.universel.cst.type.reference.TypeReference;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.ValueConsumingNode;
import org.orthodox.universel.symanticanalysis.conversion.BinaryExpressionOperatorMethodCall;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.beanplanet.core.util.CollectionUtil.nullSafe;

public class UniversalVisitorAdapter implements UniversalCodeVisitor {
    @Override
    public Node visitAnnotation(final Annotation node) {
        return node;
    }

    @Override
    public ArrayCreationExpression visitArrayCreationExpression(final ArrayCreationExpression node) {
        TypeReference transformedType = node.getComponentType().accept(this);
        List<Node> transformedDimensionExpressions = node.getDimensionExpressions()
                                                         .stream()
                                                         .map(n -> n.accept(this))
                                                         .collect(Collectors.toList());

        boolean noTransformationChanges = Objects.equals(transformedType, node.getComponentType())
                                          && Objects.equals(transformedDimensionExpressions, node.getDimensionExpressions());
        return noTransformationChanges ? node : new ArrayCreationExpression(node.getTokenImage(),
                                                                            transformedType,
                                                                            transformedDimensionExpressions,
                                                                            node.getInitialiserExpression());
    }

    @Override
    public Node visitBetweenExpression(final BetweenExpression node) {
        return node;
    }

    @Override
    public Node visitBinaryExpression(final BinaryExpression node) {
        Node transformedLhs = node.getLhsExpression().accept(this);
        Node transformedRhs = node.getRhsExpression().accept(this);

        boolean noTransformationChanges = Objects.equals(transformedLhs, node.getLhsExpression()) &&
                                          Objects.equals(transformedRhs, node.getRhsExpression());
        return noTransformationChanges ? node : new BinaryExpression(node.getOperator(), transformedLhs, transformedRhs);
    }

    @Override
    public Node visitBoxConversion(final BoxConversion node) {
        Node transformedSource = node.getSource().accept(this);
        return Objects.equals(transformedSource, node.getSource()) ? node : new BoxConversion(transformedSource);
    }

    @Override
    public Node visitBinaryExpression(final BinaryExpressionOperatorMethodCall node) {
        node.getParameters().forEach(p -> p.accept(this));
        return node;
    }

    @Override
    public Node visitBooleanLiteral(final BooleanLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitClassDeclaration(ClassDeclaration node) {
        Modifiers transformedModifiers = node.getModifiers().accept(this);

        Name transformedName = node.getName().accept(this);

        PackageDeclaration transformedPackageDeclaration = node.getPackageDeclaration() == null ? null : node.getPackageDeclaration().accept(this);

        NodeSequenceBuilder<Node> transformedMembers = NodeSequence.builder();
        for (Node member : node.getMembers()) {
            transformedMembers.add(member.accept(this));
        }

        List<TypeReference> transformedExtendsList = new ArrayList<>(node.getExtendsList().size());
        for (TypeReference cit : node.getExtendsList()) {
            transformedExtendsList.add(cit.accept(this));
        }

        List<TypeReference> transformedImplementsList = new ArrayList<>(node.getImplementsList().size());
        for (TypeReference cit : node.getImplementsList()) {
            transformedImplementsList.add(cit.accept(this));
        }

        ClassDeclaration transformedClassDeclaration = new ClassDeclaration(transformedModifiers,
                                                                            transformedPackageDeclaration,
                                                                            transformedName,
                                                                            node.getTypeParameters(),
                                                                            transformedMembers.build(),
                                                                            transformedExtendsList,
                                                                            transformedImplementsList);
        return Objects.equals(node, transformedClassDeclaration) ? node : transformedClassDeclaration;
    }

    @Override
    public Node visitFunctionalInterfaceObject(final FunctionalInterfaceObject node) {
        MethodDeclaration transformedMethodPrototype = node.getTargetMethodPrototype() == null ? null : node.getTargetMethodPrototype().accept(this);

        return Objects.equals(node.getTargetMethodPrototype(), transformedMethodPrototype) ? node : new FunctionalInterfaceObject(node.getTokenImage(),
                                                                                                                                  node.getSourceFunctionType(),
                                                                                                                                  node.getSourceFunctionReturnType(),
                                                                                                                                  node.getSourceFunctionParameters(),
                                                                                                                                  node.getSourceFunctionName(),
                                                                                                                                  transformedMethodPrototype);
    }

    @Override
    public Node visitInternalNodeSequence(InternalNodeSequence node) {
        List<Node> transformedNodes = new ArrayList<>(node.getNodes().size());
        for (Node child : node.getNodes()) {
            transformedNodes.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getNodes(), transformedNodes);
        return noTransformationChanges ? node : new InternalNodeSequence(transformedNodes);
    }

    @Override
    public Node visitNumericLiteralExpression(final NumericLiteral node) {
        return (Node)node;
    }

    @Override
    public Node visitImportDeclaration(final ImportDecl node) {
        return node;
    }

    @Override
    public Node visitInterfaceDeclaration(final InterfaceDeclaration node) {
        Modifiers transformedModifiers = node.getModifiers().accept(this);

        Name transformedName = node.getName().accept(this);

        PackageDeclaration transformedPackageDeclaration = node.getPackageDeclaration() == null ? null : node.getPackageDeclaration().accept(this);

        NodeSequenceBuilder<Node> transformedMembers = NodeSequence.builder();
        for (Node member : node.getMembers()) {
            transformedMembers.add(member.accept(this));
        }

        List<TypeReference> transformedExtendsList = new ArrayList<>(node.getExtendsList().size());
        for (TypeReference cit : node.getExtendsList()) {
            transformedExtendsList.add(cit.accept(this));
        }

        InterfaceDeclaration transformedInterfaceDeclaration = new InterfaceDeclaration(transformedModifiers,
                                                                            transformedPackageDeclaration,
                                                                            transformedName,
                                                                            node.getTypeParameters(),
                                                                            transformedMembers.build(),
                                                                            transformedExtendsList);
        return Objects.equals(node, transformedInterfaceDeclaration) ? node : transformedInterfaceDeclaration;
    }

    @Override
    public Node visitInExpression(final InExpression node) {
        return node;
    }

    @Override
    public Node visitInstanceofExpression(final InstanceofExpression node) {
        node.getLhsExpression().accept(this);
        node.getRhsExpression().accept(this);
        return node;
    }

    @Override
    public IfStatement visitIfStatement(final IfStatement node) {
        Node transformedTestExpression = node.getTestExpression().accept(this);

        Node transformedThen = node.getThenExpression().accept(this);

        List<ElseIf> transformedIfElseExpressions = null;
        if ( node.getElseIfExpressions() != null ) {
            transformedIfElseExpressions = new ArrayList<>(node.getElseIfExpressions().size());
            for (ElseIf elseIf : node.getElseIfExpressions()) {
                Node elseIfTransformedTestExpression = elseIf.getTestExpression().accept(this);
                Node elseIfTransformedBody = elseIf.getBody().accept(this);

                transformedIfElseExpressions.add(
                    Objects.equals(elseIfTransformedTestExpression, elseIf.getTestExpression())
                    && Objects.equals(elseIfTransformedBody, elseIf.getBody()) ?
                    elseIf : new ElseIf(elseIf.getTokenImage(), elseIfTransformedTestExpression, elseIfTransformedBody)
                );
            }
        }

        Node transformedElse = node.getElseExpression() == null ? null : node.getElseExpression().accept(this);

        return Objects.equals(transformedTestExpression, node.getTestExpression())
               && Objects.equals(transformedThen, node.getThenExpression())
               && Objects.equals(transformedIfElseExpressions, node.getElseIfExpressions())
               && Objects.equals(transformedElse, node.getElseExpression())
               ? node : new IfStatement(node.getTokenImage(), transformedTestExpression, transformedThen, transformedIfElseExpressions, transformedElse);
    }

    @Override
    public Node visitInterpolatedStringLiteral(final InterpolatedStringLiteralExpr node) {
        List<Node> transformedParts = node.getParts() == null ? null : new ArrayList<>(node.getParts().size());
        if ( transformedParts != null ) {
            for (Node part : node.getParts()) {
                transformedParts.add(part.accept(this));
            }
        }

        boolean noTransformationChanges = Objects.equals(node.getParts(), transformedParts);
        return noTransformationChanges ? node : new InterpolatedStringLiteralExpr(node.getTokenImage(), transformedParts, node.getDelimeter());
    }

    @Override
    public Node visitList(final ListExpr node) {
        List<Node> transformedElements = new ArrayList<>(node.getElements().size());
        for (Node child : node.getElements()) {
            transformedElements.add(child.accept(this));
        }
        boolean noTransformationChanges = Objects.equals(node.getElements(), transformedElements);
        return noTransformationChanges ? node : new ListExpr(node.getTokenImage(), transformedElements);
    }

    @Override
    public Node visitLoadLocal(final LoadLocal node) {
        return node;
    }

    public MapEntryExpr visitMapEntry(MapEntryExpr node) {
        Node transformedKey = node.getKeyExpression() == null ? null : node.getKeyExpression().accept(this);
        Node transformedVal = node.getValueExpression() == null ? null : node.getValueExpression().accept(this);

        boolean noTransformationChanges = Objects.equals(node.getKeyExpression(), transformedKey) &&
                                          Objects.equals(node.getValueExpression(), transformedVal);
        return noTransformationChanges ? node : new MapEntryExpr(node.getTokenImage(), transformedKey, transformedVal);
    }

    @Override
    public Node visitMap(final MapExpr node) {
        List<MapEntryExpr> transformedEntries = new ArrayList<>(node.getEntries().size());
        for (MapEntryExpr child : node.getEntries()) {
            transformedEntries.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getEntries(), transformedEntries);
        return noTransformationChanges ? node : new MapExpr(node.getTokenImage(), transformedEntries);
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
        if (node.getModifiers() != null) {
            node.getModifiers().accept(this);
        }

        TypeReference transformedReturnType = node.getReturnType().accept(this);

        List<Parameter> transformedParameterList = new ArrayList<>(node.getParameters().size());
        for (Node parameter : nullSafe(node.getParameters().getNodes())) {
            transformedParameterList.add((Parameter)parameter.accept(this));
        }
        NodeSequence<Parameter> transformedParameters = new NodeSequence<>(node.getParameters().getTokenImage(), transformedParameterList);

        List<Node> transformedBodyNodes = new ArrayList<>(node.getBody().size());
        for (Node bodyNode : nullSafe(node.getBody().getNodes())) {
            transformedBodyNodes.add(bodyNode.accept(this));
        }
        NodeSequence<Node> transformedBody = new NodeSequence<>(node.getBody().getTokenImage(), transformedBodyNodes);

        boolean noTransformationChanges = Objects.equals(node.getReturnType(), transformedReturnType) &&
                                          Objects.equals(node.getParameters(), transformedParameters) &&
                                          Objects.equals(node.getBody(), transformedBody);
        return noTransformationChanges ? node : new MethodDeclaration(node.getModifiers(), node.getTypeParameters(), transformedReturnType, node.getName(), transformedParameters, transformedBody);
    }

    @Override
    public Node visitMethodCall(final MethodCall node) {
        List<Node> transformedParameters = node.getParameters() == null ? null : new ArrayList<>(node.getParameters().size());
        if ( transformedParameters != null ) {
            for (Node child : node.getParameters()) {
                transformedParameters.add(child.accept(this));
            }
        }

        boolean noTransformationChanges = Objects.equals(node.getParameters(), transformedParameters);
        return noTransformationChanges ? node : new MethodCall(node.getTokenImage(), node.getName(), transformedParameters);
    }

    @Override
    public Node visitMethodCall(final InstanceMethodCall node) {
        List<Node> transformedParameters = new ArrayList<>(node.getParameters().size());
        for (Node child : node.getParameters()) {
            transformedParameters.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getParameters(), transformedParameters);
        return noTransformationChanges ? node : new InstanceMethodCall(node.getTypeDescriptor(), node.getTokenImage(), node.getDeclaringClass(), node.getName(), transformedParameters);
    }

    @Override
    public Node visitMethodCall(final StaticMethodCall node) {
        List<Node> transformedParameters = new ArrayList<>(node.getParameters().size());
        for (Node child : node.getParameters()) {
            transformedParameters.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getParameters(), transformedParameters);
        return noTransformationChanges ? node : new StaticMethodCall(node.getTypeDescriptor(), node.getTokenImage(), node.getDeclaringClass(), node.getName(), transformedParameters);
    }

    @Override
    public Modifiers visitModifiers(final Modifiers node) {
        return node;
    }

    @Override
    public Node visitName(final QualifiedIdentifier node) {
        return node;
    }

    @Override
    public Name visitName(final Name node) {
        return node;
    }

    @Override
    public Node visitNameTest(final NameTest node) {
        return node;
    }

    @Override
    public Node visitNodeSequence(final NodeSequence<? extends Node> node) {
        List<Node> transformedNodes = new ArrayList<>(node.getNodes().size());
        for (Node child : node.getNodes()) {
            transformedNodes.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getNodes(), transformedNodes);
        return noTransformationChanges ? node : new NodeSequence<>(transformedNodes);
    }

    @Override
    public Node visitNullLiteral(final NullLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitNullTestExpression(final NullTestExpression node) {
        return node;
    }

    @Override
    public <T extends NodeTest> NavigationStep<?> visitNavigationStep(NavigationStep<T> node) {
        Node transformedNodeTest = (Node)node.getNodeTest();
        if (node.getNodeTest() != null) {
            transformedNodeTest = node.getNodeTest().accept(this);
        }

        boolean noTransformationChanges = Objects.equals(node.getNodeTest(), transformedNodeTest);
        return noTransformationChanges ? node : new NavigationStep<>(node.getTokenImage(), node.getAxis(), (NodeTest)transformedNodeTest, node.getPredicates());
    }

    @Override
    public Node visitNavigationStream(final NavigationStream node) {
        List<Node> transformedSteps = new ArrayList<>(node.getSteps().size());
        for (Node child : node.getSteps()) {
            transformedSteps.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getSteps(), transformedSteps);
        return noTransformationChanges ? node : new NavigationStream(transformedSteps);
    }

    @Override
    public Node visitObjectCreationExpression(ObjectCreationExpression node) {
        TypeReference transformedType = node.getType() == null ? null : node.getType().accept(this);

        List<Node> transformedParameters = node.getParameters() == null ? null : new ArrayList<>(node.getParameters().size());
        if ( transformedParameters != null ) {
            for (Node parameter : node.getParameters()) {
                transformedParameters.add(parameter.accept(this));
            }
        }

        boolean noTransformationChanges = Objects.equals(node.getType(), transformedType) &&
                                          Objects.equals(node.getParameters(), transformedParameters);
        return noTransformationChanges ? node : new ObjectCreationExpression(node.getTokenImage(), transformedType, transformedParameters);
    }

    @Override
    public PackageDeclaration visitPackageDeclaration(final PackageDeclaration node) {
        return node;
    }

    @Override
    public Node visitReturnStatement(final ReturnStatement node) {
        if ( node.getExpression() == null) return node;

        Node transformedExpression = node.getExpression().accept(this);

        return Objects.equals(transformedExpression, node.getExpression()) ? node : new ReturnStatement(node.getTokenImage(), transformedExpression);
    }

    @Override
    public Node visitScript(final Script node) {
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
    public Node visitSet(final SetExpr node) {
        List<Node> transformedElements = new ArrayList<>(node.getElements().size());
        for (Node child : node.getElements()) {
            transformedElements.add(child.accept(this));
        }

        boolean noTransformationChanges = Objects.equals(node.getElements(), transformedElements);
        return noTransformationChanges ? node : new SetExpr(node.getTokenImage(), transformedElements);
    }

    @Override
    public Node visitStringLiteral(final StringLiteralExpr node) {
        return node;
    }

    @Override
    public Node visitTernaryExpression(final TernaryExpression node) {
        Node transformedTest = null;
        Node transformedLhs = null;
        Node transformedRhs = null;

        if (node.getTestExpression() != null) transformedTest = node.getTestExpression().accept(this);
        if (node.getLhsExpression() != null) transformedLhs = node.getLhsExpression().accept(this);
        if (node.getRhsExpression() != null) transformedRhs = node.getRhsExpression().accept(this);

        return Objects.equals(node.getTestExpression(), transformedTest)
                && Objects.equals(node.getLhsExpression(), transformedLhs)
                && Objects.equals(node.getLhsExpression(), transformedLhs) ? node : new TernaryExpression(transformedTest, transformedLhs, transformedRhs);
    }

    @Override
    public TypeReference visitTypeReference(final TypeReference node) {
        return node;
    }

    @Override
    public Node visitTypeConversion(final TypeConversion node) {
        Node transformedSource = null;
        if ( node.getSource() != null ) {
            transformedSource = node.getSource().accept(this);
        }

        return Objects.equals(node.getSource(), transformedSource) ? node : new TypeConversion(transformedSource, node.getTargetType());
    }

    @Override
    public Node visitUnaryExpression(final UnaryExpression node) {
        node.getExpression().accept(this);
        return node;
    }

    @Override
    public Node visitValueConsumingNode(ValueConsumingNode node) {
        Node transformedNode = null;
        if ( node.getDelegate() != null ) {
            transformedNode = node.getDelegate().accept(this);
        }
        return Objects.equals(node.getDelegate(), transformedNode) ? node : new ValueConsumingNode(transformedNode);
    }

    @Override
    public Node visitJvmInstruction(JvmInstructionNode jvmInstructionNode) {
        return jvmInstructionNode;
    }

    protected NodeSequence<Node> transformNodeSequence(final NodeSequence<Node> nodeSequence) {
        if ( nodeSequence == null ) return null;

        final NodeSequenceBuilder<Node> transformedNodes = NodeSequence.builder();
        nodeSequence.getNodes().forEach(n -> transformedNodes.add(n.accept(this)));

        return transformedNodes.build();
    }
}
