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
import org.orthodox.universel.exec.operators.range.LongRange;
import org.orthodox.universel.exec.operators.range.Range;

import static org.orthodox.universel.cst.Operator.*;

@BinaryOperator
public class LongBinaryOperatorFunctions {

    @BinaryOperator(AMPERSAND)
    public static long bitAnd(long lhs, long rhs) {
        return lhs & rhs;
    }

    @BinaryOperator(CARAT)
    public static long power(long lhs, long rhs) {
        return lhs ^ rhs;
    }

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(long lhs, long rhs) {
        return lhs != rhs;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static long divide(long lhs, long rhs) {
        return lhs / rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(long lhs, long rhs) {
        return lhs == rhs;
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(long lhs, long rhs) {
        return lhs > rhs;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(long lhs, long rhs) {
        return lhs >= rhs;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(long lhs, long rhs) {
        return lhs < rhs;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(long lhs, long rhs) {
        return lhs <= rhs;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(long lhs, long rhs) {
        return lhs != 0 || rhs != 0;
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(long lhs, long rhs) {
        return lhs != 00 && rhs != 0;
    }

    @BinaryOperator(MINUS)
    public static long minus(long lhs, long rhs) {
        return lhs - rhs;
    }

    @BinaryOperator(PERCENT)
    public static long modulus(long lhs, long rhs) {
        return lhs % rhs;
    }

    @BinaryOperator(PIPE)
    public static long bitOr(long lhs, long rhs) {
        return lhs | rhs;
    }

    @BinaryOperator(PLUS)
    public static long plus(long lhs, long rhs) {
        return lhs + rhs;
    }

    @BinaryOperator({
            RANGE_INCLUSIVE,
            RANGE_LEFT_LT_RIGHT_INCLUSIVE, RANGE_LEFT_LT_RIGHT_LT, RANGE_LEFT_LT_RIGHT_LTE, RANGE_LEFT_LT_RIGHT_GT, RANGE_LEFT_LT_RIGHT_GTE,
            RANGE_LEFT_LTE_RIGHT_GT, RANGE_LEFT_LTE_RIGHT_GTE, RANGE_LEFT_LTE_RIGHT_INCLUSIVE, RANGE_LEFT_LTE_RIGHT_LT, RANGE_LEFT_LTE_RIGHT_LTE,
            RANGE_LEFT_GT_RIGHT_GT, RANGE_LEFT_GT_RIGHT_GTE, RANGE_LEFT_GT_RIGHT_INCLUSIVE, RANGE_LEFT_GT_RIGHT_LT, RANGE_LEFT_GT_RIGHT_LTE,
            RANGE_LEFT_GTE_RIGHT_GT, RANGE_LEFT_GTE_RIGHT_GTE, RANGE_LEFT_GTE_RIGHT_INCLUSIVE, RANGE_LEFT_GTE_RIGHT_LT, RANGE_LEFT_GTE_RIGHT_LTE,
            RANGE_LEFT_INCLUSIVE_RIGHT_GT, RANGE_LEFT_INCLUSIVE_RIGHT_GTE, RANGE_LEFT_INCLUSIVE_RIGHT_LT, RANGE_LEFT_INCLUSIVE_RIGHT_LTE
    })
    public static Range<Long> longRange(long lhs, long rhs, Operator operator) {
        return new LongRange(operator, lhs, rhs);
    }

    @BinaryOperator(SHIFT_LEFT)
    public static long shiftLeft(long lhs, long rhs) {
        return lhs << rhs;
    }

    @BinaryOperator(SHIFT_RIGHT)
    public static long shiftRight(long lhs, long rhs) {
        return lhs >> rhs;
    }

    @BinaryOperator(TREBLE_SHIFT_RIGHT)
    public static long zeroFillShiftRight(long lhs, long rhs) {
        return lhs >>> rhs;
    }

    @BinaryOperator(STAR)
    public static long multiply(long lhs, long rhs) {
        return lhs * rhs;
    }
}
