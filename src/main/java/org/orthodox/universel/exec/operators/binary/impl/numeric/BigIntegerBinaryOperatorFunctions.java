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

import org.orthodox.universel.cst.Operator;
import org.orthodox.universel.exec.operators.binary.BinaryOperator;
import org.orthodox.universel.exec.operators.range.BigIntegerRange;
import org.orthodox.universel.exec.operators.range.Range;

import java.math.BigInteger;

import static org.orthodox.universel.cst.Operator.*;

@BinaryOperator
public class BigIntegerBinaryOperatorFunctions {

    @BinaryOperator(AMPERSAND)
    public static BigInteger bitAnd(BigInteger lhs, BigInteger rhs) {
        return lhs.and(rhs);
    }

    @BinaryOperator(CARAT)
    public static BigInteger power(BigInteger lhs, BigInteger rhs) {
        return lhs.xor(rhs);
    }

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) != 0;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static BigInteger divide(BigInteger lhs, BigInteger rhs) {
        return lhs.divide(rhs);
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) > 0;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) >= 0;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) < 0;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(BigInteger lhs, BigInteger rhs) {
        return lhs.compareTo(rhs) <= 0;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(BigInteger lhs, BigInteger rhs) {
        return !lhs.equals(BigInteger.ZERO) || !rhs.equals(BigInteger.ZERO);
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(BigInteger lhs, BigInteger rhs) {
        return !lhs.equals(BigInteger.ZERO) && !rhs.equals(BigInteger.ZERO);
    }

    @BinaryOperator(MINUS)
    public static BigInteger minus(BigInteger lhs, BigInteger rhs) {
        return lhs.subtract(rhs);
    }

    @BinaryOperator(PERCENT)
    public static BigInteger modulus(BigInteger lhs, BigInteger rhs) {
        return lhs.mod(rhs);
    }

    @BinaryOperator(PIPE)
    public static BigInteger bitOr(BigInteger lhs, BigInteger rhs) {
        return lhs.or(rhs);
    }

    @BinaryOperator(PLUS)
    public static BigInteger plus(BigInteger lhs, BigInteger rhs) {
        return lhs.add(rhs);
    }

    @BinaryOperator({
            RANGE_INCLUSIVE,
            RANGE_LEFT_LT_RIGHT_INCLUSIVE, RANGE_LEFT_LT_RIGHT_LT, RANGE_LEFT_LT_RIGHT_LTE, RANGE_LEFT_LT_RIGHT_GT, RANGE_LEFT_LT_RIGHT_GTE,
            RANGE_LEFT_LTE_RIGHT_GT, RANGE_LEFT_LTE_RIGHT_GTE, RANGE_LEFT_LTE_RIGHT_INCLUSIVE, RANGE_LEFT_LTE_RIGHT_LT, RANGE_LEFT_LTE_RIGHT_LTE,
            RANGE_LEFT_GT_RIGHT_GT, RANGE_LEFT_GT_RIGHT_GTE, RANGE_LEFT_GT_RIGHT_INCLUSIVE, RANGE_LEFT_GT_RIGHT_LT, RANGE_LEFT_GT_RIGHT_LTE,
            RANGE_LEFT_GTE_RIGHT_GT, RANGE_LEFT_GTE_RIGHT_GTE, RANGE_LEFT_GTE_RIGHT_INCLUSIVE, RANGE_LEFT_GTE_RIGHT_LT, RANGE_LEFT_GTE_RIGHT_LTE,
            RANGE_LEFT_INCLUSIVE_RIGHT_GT, RANGE_LEFT_INCLUSIVE_RIGHT_GTE, RANGE_LEFT_INCLUSIVE_RIGHT_LT, RANGE_LEFT_INCLUSIVE_RIGHT_LTE
    })
    public static Range<BigInteger> bigInteger(BigInteger lhs, BigInteger rhs, Operator operator) {
        return new BigIntegerRange(operator, lhs, rhs);
    }

    @BinaryOperator(SHIFT_LEFT)
    public static BigInteger shiftLeft(BigInteger lhs, BigInteger rhs) {
        return lhs.shiftLeft(rhs.intValue());
    }

    @BinaryOperator(SHIFT_RIGHT)
    public static BigInteger shiftRight(BigInteger lhs, BigInteger rhs) {
        return lhs.shiftRight(rhs.intValue());
    }

    @BinaryOperator(TREBLE_SHIFT_RIGHT)
    public static BigInteger zeroFillShiftRight(BigInteger lhs, BigInteger rhs) {
        return lhs.shiftRight(rhs.intValue());
    }

    @BinaryOperator(STAR)
    public static BigInteger multiply(BigInteger lhs, BigInteger rhs) {
        return lhs.multiply(rhs);
    }
}
