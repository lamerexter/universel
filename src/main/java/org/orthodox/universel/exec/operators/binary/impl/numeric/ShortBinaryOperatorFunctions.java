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

import org.orthodox.universel.ast.Operator;
import org.orthodox.universel.exec.operators.binary.BinaryOperator;
import org.orthodox.universel.exec.operators.range.Range;
import org.orthodox.universel.exec.operators.range.ShortRange;

import static org.orthodox.universel.ast.Operator.*;

@BinaryOperator
public class ShortBinaryOperatorFunctions {

    @BinaryOperator(AMPERSAND)
    public static int bitAnd(short lhs, short rhs) {
        return lhs & rhs;
    }

    @BinaryOperator(CARAT)
    public static int power(short lhs, short rhs) {
        return lhs ^ rhs;
    }

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(short lhs, short rhs) {
        return lhs != rhs;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static int divide(short lhs, short rhs) {
        return lhs / rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(short lhs, short rhs) {
        return lhs == rhs;
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(short lhs, short rhs) {
        return lhs > rhs;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(short lhs, short rhs) {
        return lhs >= rhs;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(short lhs, short rhs) {
        return lhs < rhs;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(short lhs, short rhs) {
        return lhs <= rhs;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(short lhs, short rhs) {
        return lhs != 0 || rhs != 0;
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(short lhs, short rhs) {
        return lhs != 00 && rhs != 0;
    }

    @BinaryOperator(MINUS)
    public static int minus(short lhs, short rhs) {
        return lhs - rhs;
    }

    @BinaryOperator(PERCENT)
    public static int modulus(short lhs, short rhs) {
        return lhs % rhs;
    }

    @BinaryOperator(PIPE)
    public static int bitOr(short lhs, short rhs) {
        return lhs | rhs;
    }

    @BinaryOperator(PLUS)
    public static int plus(short lhs, short rhs) {
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
    public static Range<Short> shortRange(short lhs, short rhs, Operator operator) {
        return new ShortRange(operator, lhs, rhs);
    }

    @BinaryOperator(SHIFT_LEFT)
    public static int shiftLeft(short lhs, short rhs) {
        return lhs << rhs;
    }

    @BinaryOperator(SHIFT_RIGHT)
    public static int shiftRight(short lhs, short rhs) {
        return lhs >> rhs;
    }

    @BinaryOperator(TREBLE_SHIFT_RIGHT)
    public static int zeroFillShiftRight(short lhs, short rhs) {
        return lhs >>> rhs;
    }

    @BinaryOperator(STAR)
    public static int multiply(short lhs, short rhs) {
        return lhs * rhs;
    }
}
