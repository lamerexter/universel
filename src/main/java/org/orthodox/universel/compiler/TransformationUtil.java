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

import org.beanplanet.core.lang.TypeUtil;
import org.orthodox.universel.ast.MethodCall;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Type;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;

import javax.lang.model.type.NullType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.beanplanet.core.lang.TypeUtil.*;
import static org.beanplanet.core.util.CollectionUtil.nullSafe;

/**
 * Static utility for transformation functions.
 */
public class TransformationUtil {
    private static final Map<Set<Class<?>>, Class<?>> standardWideningMap = new HashMap<>();

    static {
        standardWideningMap.put(new HashSet<>(asList(byte.class, byte.class)), byte.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, char.class)), char.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, short.class)), int.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, int.class)), int.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, long.class)), long.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, BigInteger.class)), BigInteger.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, float.class)), float.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(byte.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(char.class, char.class)), char.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, short.class)), int.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, int.class)), int.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, long.class)), long.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, BigInteger.class)), BigInteger.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, float.class)), float.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(char.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(short.class, short.class)), short.class);
        standardWideningMap.put(new HashSet<>(asList(short.class, int.class)), int.class);
        standardWideningMap.put(new HashSet<>(asList(short.class, long.class)), long.class);
        standardWideningMap.put(new HashSet<>(asList(short.class, BigInteger.class)), BigInteger.class);
        standardWideningMap.put(new HashSet<>(asList(short.class, float.class)), float.class);
        standardWideningMap.put(new HashSet<>(asList(short.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(short.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(int.class, int.class)), int.class);
        standardWideningMap.put(new HashSet<>(asList(int.class, long.class)), long.class);
        standardWideningMap.put(new HashSet<>(asList(int.class, BigInteger.class)), BigInteger.class);
        standardWideningMap.put(new HashSet<>(asList(int.class, float.class)), float.class);
        standardWideningMap.put(new HashSet<>(asList(int.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(int.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(long.class, long.class)), long.class);
        standardWideningMap.put(new HashSet<>(asList(long.class, BigInteger.class)), BigInteger.class);
        standardWideningMap.put(new HashSet<>(asList(long.class, float.class)), float.class);
        standardWideningMap.put(new HashSet<>(asList(long.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(long.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(BigInteger.class, BigInteger.class)), BigInteger.class);
        standardWideningMap.put(new HashSet<>(asList(BigInteger.class, float.class)), BigDecimal.class);
        standardWideningMap.put(new HashSet<>(asList(BigInteger.class, double.class)), BigDecimal.class);
        standardWideningMap.put(new HashSet<>(asList(BigInteger.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(float.class, float.class)), float.class);
        standardWideningMap.put(new HashSet<>(asList(float.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(float.class, BigDecimal.class)), BigDecimal.class);

        standardWideningMap.put(new HashSet<>(asList(double.class, double.class)), double.class);
        standardWideningMap.put(new HashSet<>(asList(double.class, BigDecimal.class)), BigDecimal.class);
    }

    private static final List<Class<?>> widenNumericalTypeOrder = asList(byte.class, char.class, short.class, int.class, long.class, BigInteger.class,
                                                                         float.class, double.class, BigDecimal.class);

    private static final Set<Class<?>> STANDARD_NUMERIC_TYPES = new HashSet<>(widenNumericalTypeOrder);

    public static boolean isNumericExpressionType(Node node) {
        return STANDARD_NUMERIC_TYPES.contains(primitiveTypeFor(node.getTypeDescriptor()));
    }

    public static List<Node> autoBoxOrPromoteIfNecessary(List<Node> sourceParameters, List<Class<?>> targetArgumentTypes) {
        return IntStream.range(0, targetArgumentTypes.size())
                        .mapToObj(i -> autoBoxOrPromoteIfNecessary(sourceParameters.get(i), targetArgumentTypes.get(i)))
                        .collect(Collectors.toList());

    }

    public static Node autoBoxIfNecessary(Node node, Class<?> targetType) {
        final Class<?> nodeType = node.getTypeDescriptor();

        if ( boxCompatible(nodeType, targetType) ) {
            return box(node);
        }

        if ( unboxCompatible(nodeType, targetType) ) {
            return unbox(node);
        }

        return node;
    }

    public static Node promoteIfNecessary(Node node, Class<?> targetType) {
        final Class<?> nodeType = node.getTypeDescriptor();

        if ( numericWideningTypeConversionApplies(nodeType, targetType) ) {
            return wideningConversion(node, targetType);
        }

        if ( numericNarrowingTypeConversionApplies(nodeType, targetType) ) {
            return narrowingConversion(node, targetType);
        }

        return node;
    }

    public static Node autoBoxOrPromoteIfNecessary(Node node, Class<?> targetType) {
        Class<?> nodeType = node.getTypeDescriptor();

        if ( boxCompatible(nodeType, targetType) ) {
            return box(node);
        }

        if ( unboxCompatible(nodeType, targetType) ) {
            return unbox(node);
        }

        if ( numericWideningTypeConversionApplies(nodeType, targetType) ) {
            return wideningConversion(node, targetType);
        }

        if ( numericNarrowingTypeConversionApplies(nodeType, targetType) ) {
            return narrowingConversion(node, targetType);
        }

        return node;
    }

    public static Optional<Class<?>> determineWidestNumericOperandType(Class<?> operand1, Class<?> operand2) {
        return Optional.ofNullable(standardWideningMap.get(numericTypeOperationKey(operand1, operand2)));
    }

    private static boolean numericWideningTypeConversionApplies(final Class<?> nodeType, final Class<?> targetType) {
        if (targetType.isAssignableFrom(nodeType) ) return false;

        return widenNumericalTypeOrder.indexOf(primitiveTypeFor(targetType)) > widenNumericalTypeOrder.indexOf(primitiveTypeFor(nodeType));
    }

    private static boolean numericNarrowingTypeConversionApplies(final Class<?> nodeType, final Class<?> targetType) {
        if (targetType.isAssignableFrom(nodeType) ) return false;

        return widenNumericalTypeOrder.indexOf(primitiveTypeFor(targetType)) < widenNumericalTypeOrder.indexOf(primitiveTypeFor(nodeType));
    }

    private static Node wideningConversion(final Node node, final Class<?> targetType) {
        return new TypeConversion(node, targetType);
    }

    private static Node narrowingConversion(final Node node, final Class<?> targetType) {
        return new TypeConversion(node, targetType);
    }

    private static Set<Class<?>> numericTypeOperationKey(Class<?> lhsType, Class<?> rhsType) {
        return new HashSet<>(asList(primitiveTypeFor(lhsType), primitiveTypeFor(rhsType)));
    }

    private static Node box(final Node node) {
        return new Box(node);
    }

    private static Node unbox(final Node node) {
        return new Unbox(node);
    }

    public static List<Class<?>> extractTypes(List<Node> nodes) {
        return nullSafe(nodes).stream().map(Node::getTypeDescriptor).collect(Collectors.toList());
    }

    public static boolean callableTypesMatch(MethodCall methodCall, MethodDeclaration methodDeclaration) {
        return callableTypesMatch(methodCall.getParameters(), methodDeclaration.getParameters().getNodes());
    }

    public static boolean callableTypesMatch(List<? extends Node> sourceNodes, List<? extends Node> targetNodes) {
        if ( sourceNodes.size() != targetNodes.size() ) return false;

        for (int n=0; n < sourceNodes.size(); n++) {
            Class<?> sourceType = sourceNodes.get(n).getTypeDescriptor();
            Class<?> targetType = targetNodes.get(n).getTypeDescriptor();

            if ( !callCompatible(sourceType, targetType) ) return false;
        }

        return true;
    }

    private static boolean callCompatible(final Class<?> sourceType, final Class<?> targetType) {
        return isAssignmentCompatible(sourceType, targetType);
    }

    public static boolean autoboxCompatible(Class<?> sourceType, Class<?> targetType) {
        return boxCompatible(sourceType, targetType) || unboxCompatible(sourceType, targetType);
    }

    public static boolean promotionCompatible(Class<?> sourceType, Class<?> targetType) {
        return numericWideningTypeConversionApplies(sourceType, targetType) || numericNarrowingTypeConversionApplies(sourceType, targetType);
    }

    private static boolean boxCompatible(Class<?> sourceType, Class<?> targetType) {
        return isPrimitiveType(sourceType) && (Object.class == targetType || isPrimitiveTypeWrapperClass(targetType));
    }

    private static boolean unboxCompatible(Class<?> sourceType, Class<?> targetType) {
        return isPrimitiveTypeWrapperClass(sourceType) && isPrimitiveType(targetType);
    }

    public static boolean isAssignableFrom(final Type targetType, final Type sourceType) {
        return targetType != null
               && sourceType != null
               && targetType.getTypeClass() != null
               && sourceType.getTypeClass() != null
               && targetType.getTypeClass().isAssignableFrom(sourceType.getTypeClass());
    }

    public static boolean isAssignmentCompatible(final Type targetType, final Type sourceType) {
        return targetType != null
               && sourceType != null
               && targetType.getTypeClass() != null
               && sourceType.getTypeClass() != null
               && (targetType.getTypeClass().isAssignableFrom(sourceType.getTypeClass())
                   || autoboxCompatible(sourceType.getTypeClass(), targetType.getTypeClass())
                   ||((NullType.class == sourceType.getTypeClass()) && !TypeUtil.isPrimitiveType(targetType.getTypeClass())));
    }

    public static boolean isAssignmentCompatible(final Class<?> sourceType, final Class<?> targetType) {
        return targetType.isAssignableFrom(sourceType) ||
               autoboxCompatible(sourceType, targetType) ||
               ((NullType.class == sourceType) && !TypeUtil.isPrimitiveType(targetType));
    }
}
