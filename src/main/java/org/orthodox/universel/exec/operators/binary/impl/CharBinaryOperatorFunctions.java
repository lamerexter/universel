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

package org.orthodox.universel.exec.operators.binary.impl;

import org.orthodox.universel.ast.Operator;
import org.orthodox.universel.exec.operators.binary.BinaryOperator;
import org.orthodox.universel.exec.operators.range.ByteRange;
import org.orthodox.universel.exec.operators.range.CharacterRange;

import static org.orthodox.universel.ast.Operator.*;

@BinaryOperator
public class CharBinaryOperatorFunctions {

    @BinaryOperator(AMPERSAND)
    public static char bitAnd(char lhs, char rhs) {
        return (char)(lhs & rhs);
    }

    @BinaryOperator(CARAT)
    public static char power(char lhs, char rhs) {
        return (char)(lhs ^ rhs);
    }

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(char lhs, char rhs) {
        return lhs != rhs;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static char divide(char lhs, char rhs) {
        return (char)(lhs / rhs);
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(char lhs, char rhs) {
        return lhs == rhs;
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(char lhs, char rhs) {
        return lhs > rhs;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(char lhs, char rhs) {
        return lhs >= rhs;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(char lhs, char rhs) {
        return lhs < rhs;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(char lhs, char rhs) {
        return lhs <= rhs;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(char lhs, char rhs) {
        return lhs != 0 || rhs != 0;
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(char lhs, char rhs) {
        return lhs != 00 && rhs != 0;
    }

    @BinaryOperator(MINUS)
    public static char minus(char lhs, char rhs) {
        return (char)(lhs - rhs);
    }

    @BinaryOperator(PERCENT)
    public static char modulus(char lhs, char rhs) {
        return (char)(lhs % rhs);
    }

    @BinaryOperator(PIPE)
    public static char bitOr(char lhs, char rhs) {
        return (char)(lhs | rhs);
    }

    @BinaryOperator(PLUS)
    public static char plus(char lhs, char rhs) {
        return (char)(lhs + rhs);
    }

    @BinaryOperator({
            RANGE_INCLUSIVE,
            RANGE_LEFT_LT_RIGHT_INCLUSIVE, RANGE_LEFT_LT_RIGHT_LT, RANGE_LEFT_LT_RIGHT_LTE, RANGE_LEFT_LT_RIGHT_GT, RANGE_LEFT_LT_RIGHT_GTE,
            RANGE_LEFT_LTE_RIGHT_GT, RANGE_LEFT_LTE_RIGHT_GTE, RANGE_LEFT_LTE_RIGHT_INCLUSIVE, RANGE_LEFT_LTE_RIGHT_LT, RANGE_LEFT_LTE_RIGHT_LTE,
            RANGE_LEFT_GT_RIGHT_GT, RANGE_LEFT_GT_RIGHT_GTE, RANGE_LEFT_GT_RIGHT_INCLUSIVE, RANGE_LEFT_GT_RIGHT_LT, RANGE_LEFT_GT_RIGHT_LTE,
            RANGE_LEFT_GTE_RIGHT_GT, RANGE_LEFT_GTE_RIGHT_GTE, RANGE_LEFT_GTE_RIGHT_INCLUSIVE, RANGE_LEFT_GTE_RIGHT_LT, RANGE_LEFT_GTE_RIGHT_LTE,
            RANGE_LEFT_INCLUSIVE_RIGHT_GT, RANGE_LEFT_INCLUSIVE_RIGHT_GTE, RANGE_LEFT_INCLUSIVE_RIGHT_LT, RANGE_LEFT_INCLUSIVE_RIGHT_LTE
    })

    public static CharacterRange charRange(char lhs, char rhs, Operator operator) {
        return new CharacterRange(operator, lhs, rhs);
    }

    @BinaryOperator(SHIFT_LEFT)
    public static char shiftLeft(char lhs, char rhs) {
        return (char)(lhs << rhs);
    }

    @BinaryOperator(SHIFT_RIGHT)
    public static char shiftRight(char lhs, char rhs) {
        return (char)(lhs >> rhs);
    }

    @BinaryOperator(TREBLE_SHIFT_RIGHT)
    public static char zeroFillShiftRight(char lhs, char rhs) {
        return (char)(lhs >>> rhs);
    }

    @BinaryOperator(STAR)
    public static char multiply(char lhs, char rhs) {
        return (char)(lhs * rhs);
    }
}
