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

import org.orthodox.universel.cst.BinaryExpression;
import org.orthodox.universel.cst.ImportDecl;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.UniversalVisitorAdapter;
import org.orthodox.universel.cst.literals.NumericLiteral;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalysisContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Arrays.asList;
import static org.beanplanet.core.lang.conversion.SystemTypeConverter.systemTypeConverter;

/**
 * <p>
 * Widening and Narrowing conversion of binary expression operands, as defined im the
 * <a href="https://docs.oracle.com/javase/specs/jls/se10/html/jls-5.html#jls-5.1.1">JLS</a>, which generally follow the
 * IEEE 754 rounding rules.
 * </p>
 * <p>
 * Performs a depth-first, post-order traversal of the AST so type inference moves up the tree from leaf nodes.
 * </p>
 *
 */
public class WideningNumericConversionAnalyser extends UniversalVisitorAdapter implements SemanticAnalyser {
    private SemanticAnalysisContext context;
    private ImportDecl importDecl;

    private static final Map<Set<Class<?>>, Class<?>> standardPromotionMap = new HashMap<>();

    static {
        standardPromotionMap.put(new HashSet<>(asList(short.class, short.class)), int.class);
        standardPromotionMap.put(new HashSet<>(asList(short.class, int.class)), int.class);
        standardPromotionMap.put(new HashSet<>(asList(short.class, long.class)), long.class);
        standardPromotionMap.put(new HashSet<>(asList(short.class, BigInteger.class)), BigInteger.class);
        standardPromotionMap.put(new HashSet<>(asList(short.class, float.class)), float.class);
        standardPromotionMap.put(new HashSet<>(asList(short.class, double.class)), double.class);
        standardPromotionMap.put(new HashSet<>(asList(short.class, BigDecimal.class)), BigDecimal.class);

        standardPromotionMap.put(new HashSet<>(asList(int.class, int.class)), int.class);
        standardPromotionMap.put(new HashSet<>(asList(int.class, long.class)), long.class);
        standardPromotionMap.put(new HashSet<>(asList(int.class, BigInteger.class)), BigInteger.class);
        standardPromotionMap.put(new HashSet<>(asList(int.class, float.class)), float.class);
        standardPromotionMap.put(new HashSet<>(asList(int.class, double.class)), double.class);
        standardPromotionMap.put(new HashSet<>(asList(int.class, BigDecimal.class)), BigDecimal.class);

        standardPromotionMap.put(new HashSet<>(asList(long.class, long.class)), long.class);
        standardPromotionMap.put(new HashSet<>(asList(long.class, BigInteger.class)), BigInteger.class);
        standardPromotionMap.put(new HashSet<>(asList(long.class, float.class)), float.class);
        standardPromotionMap.put(new HashSet<>(asList(long.class, double.class)), double.class);
        standardPromotionMap.put(new HashSet<>(asList(long.class, BigDecimal.class)), BigDecimal.class);

        standardPromotionMap.put(new HashSet<>(asList(BigInteger.class, BigInteger.class)), BigInteger.class);
        standardPromotionMap.put(new HashSet<>(asList(BigInteger.class, float.class)), BigDecimal.class);
        standardPromotionMap.put(new HashSet<>(asList(BigInteger.class, double.class)), BigDecimal.class);
        standardPromotionMap.put(new HashSet<>(asList(BigInteger.class, BigDecimal.class)), BigDecimal.class);

        standardPromotionMap.put(new HashSet<>(asList(float.class, float.class)), float.class);
        standardPromotionMap.put(new HashSet<>(asList(float.class, double.class)), double.class);
        standardPromotionMap.put(new HashSet<>(asList(float.class, BigDecimal.class)), BigDecimal.class);

        standardPromotionMap.put(new HashSet<>(asList(double.class, double.class)), double.class);
        standardPromotionMap.put(new HashSet<>(asList(double.class, BigDecimal.class)), BigDecimal.class);
    }

    private static final List<Class<?>> widenNumericalTypeOrder = asList(short.class, int.class, long.class, BigInteger.class,
                                                                         float.class, double.class, BigDecimal.class);

    @Override
    public Node performAnalysis(SemanticAnalysisContext context, Node from) {
        this.context = context;
        return from.accept(this);
    }

    @Override
    public Node visitBinaryExpression(BinaryExpression node) {

        BinaryExpression transformedNode = new BinaryExpression(node.getOperator(),
                                                                node.getLhsExpression().accept(this),
                                                                node.getRhsExpression().accept(this));
        if ( isNumericExpressionType(transformedNode.getLhsExpression()) && isNumericExpressionType(transformedNode.getRhsExpression()) ) {
            Class<?> resulting = standardPromotionMap.get(typeOperationKey(transformedNode.getLhsExpression(), transformedNode.getRhsExpression()));
            if ( resulting != null ) {
                transformedNode.setTypeDescriptor(resulting);
            }

            if ( !Objects.equals(transformedNode.getLhsExpression().getTypeDescriptor(), transformedNode.getRhsExpression().getTypeDescriptor()) ) {
                transformedNode = performWideningConversion(transformedNode);
            }
        }

        return Objects.equals(node, transformedNode) ? node : transformedNode;
    }

    private boolean isNumericExpressionType(Node node) {
        return widenNumericalTypeOrder.contains(node.getTypeDescriptor());
    }

    private BinaryExpression performWideningConversion(BinaryExpression node) {
        //--------------------------------------------------------------------------------------------------------------
        // Deal with short-circuit where both operands are numeric literals and can be compile-time substituted.
        // Otherwise we're dealing with one or both operands being dynamic runtime values and we'll need to convert one
        // or other operand at runtime.
        //--------------------------------------------------------------------------------------------------------------
        Node lhs = node.getLhsExpression();
        Node rhs = node.getRhsExpression();
        Class<?> resultingType = standardPromotionMap.get(typeOperationKey(lhs, rhs));
        if ( resultingType == null ) return node; // Promotion mapping not known. Warn?

        if ( lhs instanceof NumericLiteral && rhs instanceof NumericLiteral) {
            if ( !resultingType.equals(lhs.getTypeDescriptor()) ) {
                Number lhsPromoted = (Number) systemTypeConverter().convert(((NumericLiteral)lhs).getValue(), resultingType);
                lhs = new ConvertedNumericLiteral(lhs.getTokenImage(), lhsPromoted, ((NumericLiteral)rhs).getPrecision());
            }

            if ( !resultingType.equals(rhs.getTypeDescriptor()) ) {
                Number rhsPromoted = (Number) systemTypeConverter().convert(((NumericLiteral)rhs).getValue(), resultingType);
                rhs = new ConvertedNumericLiteral(lhs.getTokenImage(), rhsPromoted, ((NumericLiteral)rhs).getPrecision());
            }
        } else {
            if ( !resultingType.equals(lhs.getTypeDescriptor()) ) {
                lhs = new TypeConversion(lhs, resultingType);
            }

            if ( !resultingType.equals(rhs.getTypeDescriptor()) ) {
                rhs = new TypeConversion(rhs, resultingType);
            }
        }

        BinaryExpression transformedNode = new BinaryExpression(node.getOperator(), lhs, rhs);
        transformedNode.setTypeDescriptor(resultingType);
        return transformedNode;
    }

    private HashSet<Class<?>> typeOperationKey(Node lhs, Node rhs) {
        return new HashSet<>(asList(lhs.getTypeDescriptor(), rhs.getTypeDescriptor()));
    }

}
