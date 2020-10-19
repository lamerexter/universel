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

package org.orthodox.universel.symanticanalysis.operators.unary;

import org.orthodox.universel.ast.LogicalNotExpression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.StaticMethodCall;
import org.orthodox.universel.ast.UnaryExpression;
import org.orthodox.universel.exec.operators.unary.UnaryFunctions;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static org.beanplanet.core.lang.TypeUtil.findMethod;
import static org.beanplanet.core.lang.TypeUtil.primitiveTypeFor;
import static org.orthodox.universel.compiler.TransformationUtil.autoBoxOrPromoteIfNecessary;

/**
 * Transforms each unary operation into an implementation by finding the applicable registered implementation.
 */
public class UnaryOperatorImplementationAnalyser extends AbstractSemanticAnalyser implements SemanticAnalyser {

    /**
     * At the moment unary operations are hard-coded from corresponding operand types in {@link UnaryFunctions}.
     *
     * @param node the unary operation node whose operand expression is to be unary operated on.
     * @return a transformed implementation of the raw unary operation, or the raw unary operation if no implementation could be found.
     */
    @Override
    public Node visitUnaryExpression(final UnaryExpression node) {
        final Node transformedOperand = node.getOperand().accept(this);

        final Class<?> unaryExprType = transformedOperand.getTypeDescriptor();
        if (unaryExprType == null) return node;

        // TODO: Quick hack to implement logical NOT operations. Refactor into UnaryOperator discovery
        if ( primitiveTypeFor(unaryExprType) == boolean.class ) {
            return new LogicalNotExpression(node.getTokenImage(), node.getOperand());
        }

        final Class<?> unaryExprPrimitiveType = primitiveTypeFor(unaryExprType);
        return findMethod(UnaryFunctions.class, PUBLIC + STATIC, "unaryMinus", unaryExprPrimitiveType, unaryExprPrimitiveType)
            .map(m -> autoBoxOrPromoteIfNecessary(new StaticMethodCall(unaryExprPrimitiveType,
                                                                       transformedOperand.getTokenImage(),
                                                                       UnaryFunctions.class,
                                                                       "unaryMinus",
                                                                       autoBoxOrPromoteIfNecessary(transformedOperand, unaryExprPrimitiveType)),
                                                  unaryExprType)
            )
            .orElse(transformedOperand);
    }
}
