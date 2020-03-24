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

package org.orthodox.universel.exec.operators.binary.impl.numeric;

import org.orthodox.universel.exec.operators.binary.BinaryOperator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.orthodox.universel.cst.Operator.*;

@BinaryOperator
public class BigDecimalBinaryOperatorFunctions {

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) != 0;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static BigDecimal divide(BigDecimal lhs, BigDecimal rhs) {
        return lhs.divide(rhs, 10, RoundingMode.HALF_DOWN);
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) > 0;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) >= 0;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) < 0;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(BigDecimal lhs, BigDecimal rhs) {
        return lhs.compareTo(rhs) <= 0;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(BigDecimal lhs, BigDecimal rhs) {
        return (lhs != null && !lhs.equals(BigDecimal.ZERO)) || (rhs != null && !rhs.equals(BigDecimal.ZERO));
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(BigDecimal lhs, BigDecimal rhs) {
        return (lhs != null && !lhs.equals(BigDecimal.ZERO)) && (rhs != null && !rhs.equals(BigDecimal.ZERO));
    }

    @BinaryOperator(MINUS)
    public static BigDecimal minus(BigDecimal lhs, BigDecimal rhs) {
        return lhs.subtract(rhs);
    }

    @BinaryOperator(PERCENT)
    public static BigDecimal modulus(BigDecimal lhs, BigDecimal rhs) {
        return new BigDecimal(lhs.toBigInteger().mod(rhs.toBigInteger()));
    }

    @BinaryOperator(PLUS)
    public static BigDecimal plus(BigDecimal lhs, BigDecimal rhs) {
        return lhs.add(rhs);
    }

    @BinaryOperator(STAR)
    public static BigDecimal multiply(BigDecimal lhs, BigDecimal rhs) {
        return lhs.multiply(rhs);
    }
}
