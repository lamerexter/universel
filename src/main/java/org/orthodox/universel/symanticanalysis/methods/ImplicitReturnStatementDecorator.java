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

package org.orthodox.universel.symanticanalysis.methods;

import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.ast.ReturnStatement;
import org.orthodox.universel.compiler.TransformationUtil;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.literals.NullLiteralExpr;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;

import java.util.Objects;

import static org.orthodox.universel.compiler.Messages.MethodDeclaration.IMPLICIT_RETURN_TYPE_MISMATCH;
import static org.orthodox.universel.compiler.Messages.MethodDeclaration.MISSING_RETURN;

/**
 * Looks at script and method bodies, adding return statements where necessary.
 */
public class ImplicitReturnStatementDecorator extends AbstractSemanticAnalyser {
    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
        NodeSequence<Node> transformedBody = node.getBody();

        //--------------------------------------------------------------------------------------------------------------
        // Empty body method
        //--------------------------------------------------------------------------------------------------------------
        if ( node.getBody().isEmpty() ) {
            if ( node.getReturnType().isPrimitiveType() && !node.getReturnType().isVoidType() ) {
                // It is an error to not have a return value when the method returns a primitive, non-nullable, type.
                getContext().getMessages().addError(MISSING_RETURN.withRelatedObject(node));
                return node;
            }

            transformedBody = NodeSequence.builder()
                                          .tokenImage(node.getTokenImage())
                                          .addAll(node.getBody())
                                          .add(new ReturnStatement(node.getTokenImage(), node.getReturnType().isVoidType() ? null : new NullLiteralExpr(node.getTokenImage(), node.getReturnType().getTypeDescriptor())))
                                          .build();
        }
        //--------------------------------------------------------------------------------------------------------------
        // Method with one or more body statements, without a last return statement.
        //--------------------------------------------------------------------------------------------------------------
        else if ( !(node.getBody().lastNode() instanceof ReturnStatement) && node.getReturnType() != null && node.getBody().getTypeDescriptor() != null ) {
            transformedBody = NodeSequence.builder()
                                          .tokenImage(node.getTokenImage())
                                          .addAll(node.getBody().getNodes().subList(0, node.getBody().size()-1))
                                          .add(new ReturnStatement(node.getTokenImage(), autoboxIfNeeded(node, node.getBody().lastNode())))
                                          .build();
        }

        return Objects.equals(transformedBody, node.getBody()) ? node : new MethodDeclaration(node.getModifiers(),
                                                                                              node.getTypeParameters(),
                                                                                              node.getReturnType(),
                                                                                              node.getName(),
                                                                                              node.getParameters(),
                                                                                              transformedBody
                                                                                              );
    }

    private Node autoboxIfNeeded(final MethodDeclaration node, final Node sourceNode) {
        Class<?> returnType = node.getReturnType().getTypeDescriptor();
        Class<?> sourceType = sourceNode.getTypeDescriptor();

        if ( returnType.isAssignableFrom(sourceType) ) return sourceNode; // Already compatible return type

        Node autoboxedSourceNode = TransformationUtil.autoBoxOrPromoteIfNecessary(sourceNode, returnType);
        if ( !Objects.equals(sourceNode, autoboxedSourceNode) ) return autoboxedSourceNode;

        getContext().getMessages().addError(IMPLICIT_RETURN_TYPE_MISMATCH.withParameters(sourceNode.getTypeDescriptor(), returnType));

        return sourceNode;
    }
}
