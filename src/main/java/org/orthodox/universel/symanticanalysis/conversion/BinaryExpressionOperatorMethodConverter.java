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

package org.orthodox.universel.symanticanalysis.conversion;

import org.orthodox.universel.ast.*;
import org.orthodox.universel.exec.operators.binary.BinaryOperatorRegistry;
import org.orthodox.universel.exec.operators.binary.ConcurrentBinaryOperatorRegistry;
import org.orthodox.universel.exec.operators.binary.PackageScanBinaryOperatorLoader;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalysisContext;

import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.beanplanet.core.lang.TypeUtil.*;
import static org.orthodox.universel.compiler.TransformationUtil.autoBoxIfNecessary;

/**
 * <p>
 * Performs a depth-first, post-order traversal of the AST for binary expression nodes, looking for binary operator
 * methods to replace them.
 * </p>
 */
public class BinaryExpressionOperatorMethodConverter extends UniversalVisitorAdapter implements SemanticAnalyser {
    private static BinaryOperatorRegistry binaryOperatorRegistry;

    private SemanticAnalysisContext context;

    private synchronized void checkLoaded() {
        if (binaryOperatorRegistry != null)
            return;

        PackageScanBinaryOperatorLoader loader = new PackageScanBinaryOperatorLoader();
        BinaryOperatorRegistry newBinaryOperatorRegistry = new ConcurrentBinaryOperatorRegistry();
        loader.load(newBinaryOperatorRegistry);
        binaryOperatorRegistry = newBinaryOperatorRegistry;
    }

    @Override
    public Node performAnalysis(SemanticAnalysisContext context, Node from) {
        this.context = context;
        checkLoaded();
        return from.accept(this);
    }

    @Override
    public Node visitBinaryExpression(final BinaryExpression node) {
        final BinaryExpression transformedBinaryExpr = (BinaryExpression)super.visitBinaryExpression(node);

        Node lhs = transformedBinaryExpr.getLhsExpression().accept(this);
        Node rhs = transformedBinaryExpr.getRhsExpression().accept(this);

        Class<?> lhsType = lhs.getTypeDescriptor();
        Class<?> rhsType = rhs.getTypeDescriptor();

        if (lhsType == null || rhsType == null) return transformedBinaryExpr;

        // Separately implemented, for now...
        if (Operator.ELVIS == transformedBinaryExpr.getOperator() || Operator.INSTANCE_OF == transformedBinaryExpr.getOperator()) {
            if (isPrimitiveType(lhsType)) {
                lhs = new BoxConversion(lhs);
                lhsType = lhs.getTypeDescriptor();
            }
            if (isPrimitiveType(rhsType)) {
                rhs = new BoxConversion(rhs);
                rhsType = rhs.getTypeDescriptor();
            }
        } else if (Operator.INSTANCE_OF == transformedBinaryExpr.getOperator()) {
            rhsType = Class.class;
        }

        Optional<Method> binaryOperatorMethod = binaryOperatorRegistry.lookup(transformedBinaryExpr.getOperator(), lhsType, rhsType);
        if ( binaryOperatorMethod.isPresent()) {
            return new BinaryExpressionOperatorMethodCall(transformedBinaryExpr.getTokenImage(), transformedBinaryExpr.getOperator(), binaryOperatorMethod.get(), asList(lhs, rhs));
        }

        if ( Operator.ELVIS != transformedBinaryExpr.getOperator() && Operator.INSTANCE_OF != transformedBinaryExpr.getOperator()
             && isPrimitiveTypeOrWrapperClass(lhsType) && isPrimitiveTypeOrWrapperClass(rhsType) ) {
            binaryOperatorMethod = binaryOperatorRegistry.lookup(transformedBinaryExpr.getOperator(), primitiveTypeFor(lhsType), primitiveTypeFor(rhsType));
            if ( binaryOperatorMethod.isPresent()) {
                return new BinaryExpressionOperatorMethodCall(transformedBinaryExpr.getTokenImage(), transformedBinaryExpr.getOperator(), binaryOperatorMethod.get(), asList(autoBoxIfNecessary(lhs, primitiveTypeFor(lhsType)), autoBoxIfNecessary(rhs, primitiveTypeFor(rhsType))));
            }
            binaryOperatorMethod = binaryOperatorRegistry.lookup(transformedBinaryExpr.getOperator(), getPrimitiveWrapperType(lhsType), getPrimitiveWrapperType(rhsType));
            if ( binaryOperatorMethod.isPresent()) {
                return new BinaryExpressionOperatorMethodCall(transformedBinaryExpr.getTokenImage(), transformedBinaryExpr.getOperator(), binaryOperatorMethod.get(), asList(autoBoxIfNecessary(lhs, getPrimitiveWrapperType(lhsType)), autoBoxIfNecessary(rhs, getPrimitiveWrapperType(rhsType))));
            }
        }
        return transformedBinaryExpr;
    }

    @Override
    public Node visitInstanceofExpression(final InstanceofExpression node) {
        final InstanceofExpression transformedInstanceOfExpr = (InstanceofExpression)super.visitInstanceofExpression(node);

        Node lhs = transformedInstanceOfExpr.getLhsExpression().accept(this);
        Node rhs = transformedInstanceOfExpr.getRhsExpression().accept(this);

        Class<?> lhsType = lhs.getTypeDescriptor();
        Class<?> rhsType = rhs.getTypeDescriptor();

        if (lhsType == null || rhsType == null) return transformedInstanceOfExpr;

        if (isPrimitiveType(lhsType)) {
            lhs = new BoxConversion(lhs);
        }

        Optional<Method> binaryOperatorMethod = binaryOperatorRegistry.lookup(transformedInstanceOfExpr.getOperator(), Object.class, Class.class);
        return binaryOperatorMethod.isPresent() ? new BinaryExpressionOperatorMethodCall(transformedInstanceOfExpr.getTokenImage(), transformedInstanceOfExpr.getOperator(), binaryOperatorMethod.get(), asList(lhs, rhs)) : transformedInstanceOfExpr;
    }
}
