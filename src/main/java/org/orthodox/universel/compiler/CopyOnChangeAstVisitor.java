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

package org.orthodox.universel.compiler;

import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalysisContext;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * This special visitor traverses the AST with a given delegate. When the delegate creates a change in one or more
 * of a node's children, this visitor recreates the node with the changed children.
 * </p>
 * <p>
 * This facilitates isolation of changes by copying changed items, which then become the exclusive property of the caller.
 * Where there are no changes made to a node's children (i.e. the node itself), this visitor simply returns the original
 * node.
 * </p>
 * <p>
 * This visitor effectively creates a faily of visitors whose changes yield a separately owned copy - moving away from
 * the original AST. Specific changers like AST transformations are enabled by this behaviour.
 * </p>
 */
public class CopyOnChangeAstVisitor extends UniversalVisitorAdapter implements SemanticAnalyser {
    private final UniversalCodeVisitor delegate;

    public CopyOnChangeAstVisitor(UniversalCodeVisitor delegate) {
        this.delegate = delegate;
    }

    @Override
    public Node visitScript(Script node) {
        ImportDecl transformedImport = null;
        if ( node.getImportDeclaration() != null ) {
            transformedImport = (ImportDecl)node.getImportDeclaration().accept(this);
        }

        NodeSequence.NodeSequenceBuilder<Node> transformedBodyBuilder = NodeSequence.builder();
        for (Node bodyElement : node.getBody()) {
            transformedBodyBuilder.add(callDelegateForChange(bodyElement));
        }
        transformedBodyBuilder.resultType(node.getBody().getResultType());

        boolean noTransformationChanges = Objects.equals(node.getImportDeclaration(), transformedImport)
                                          && Objects.equals(node.getBody(), transformedBodyBuilder);
        return noTransformationChanges ? node : new Script(node.getType(), node.getPackageDeclaration(), transformedImport, transformedBodyBuilder.build());
    }

    @Override
    public Node visitBinaryExpression(BinaryExpression node) {
        Node newLhs = callDelegateForChange(node.getLhsExpression());
        Node newRhs = callDelegateForChange(node.getRhsExpression());

        return Objects.equals(node.getLhsExpression(), newLhs)
                && Objects.equals(node.getRhsExpression(), newRhs) ?
               node : new BinaryExpression(node.getOperator(), newLhs, newRhs);
    }

    private Node callDelegateForChange(Node node) {
        return node.accept(delegate);
    }

    @Override
    public Node performAnalysis(SemanticAnalysisContext context, Node from) {
        return from.accept(this);
    }
}
