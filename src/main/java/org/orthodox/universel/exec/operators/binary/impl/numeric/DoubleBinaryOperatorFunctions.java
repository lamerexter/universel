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
import org.orthodox.universel.exec.operators.range.DoubleRange;

import static org.orthodox.universel.ast.Operator.*;

@BinaryOperator
public class DoubleBinaryOperatorFunctions {

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(double lhs, double rhs) {
        return lhs != rhs;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static double divide(double lhs, double rhs) {
        return lhs / rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(double lhs, double rhs) {
        return lhs == rhs;
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(double lhs, double rhs) {
        return lhs > rhs;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(double lhs, double rhs) {
        return lhs >= rhs;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(double lhs, double rhs) {
        return lhs < rhs;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(double lhs, double rhs) {
        return lhs <= rhs;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(double lhs, double rhs) {
        return lhs != 0 || rhs != 0;
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(double lhs, double rhs) {
        return lhs != 00 && rhs != 0;
    }

    @BinaryOperator(MINUS)
    public static double minus(double lhs, double rhs) {
        return lhs - rhs;
    }

    @BinaryOperator(PERCENT)
    public static double modulus(double lhs, double rhs) {
        return lhs % rhs;
    }

    @BinaryOperator(PLUS)
    public static double plus(double lhs, double rhs) {
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
    public static DoubleRange doubleRange(double lhs, double rhs, Operator operator) {
        return new DoubleRange(operator, lhs, rhs);
    }

    @BinaryOperator(STAR)
    public static double multiply(double lhs, double rhs) {
        return lhs * rhs;
    }
}
