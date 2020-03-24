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

import static org.orthodox.universel.cst.Operator.*;

@BinaryOperator
public class IntegerBinaryOperatorFunctions {

    @BinaryOperator(AMPERSAND)
    public static int bitAnd(int lhs, int rhs) {
        return lhs & rhs;
    }

    @BinaryOperator(CARAT)
    public static int power(int lhs, int rhs) {
        return lhs ^ rhs;
    }

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(int lhs, int rhs) {
        return lhs != rhs;
    }

    @BinaryOperator(FORWARD_SLASH)
    public static int divide(int lhs, int rhs) {
        return lhs / rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(int lhs, int rhs) {
        return lhs == rhs;
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(int lhs, int rhs) {
        return lhs > rhs;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(int lhs, int rhs) {
        return lhs >= rhs;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(int lhs, int rhs) {
        return lhs < rhs;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(int lhs, int rhs) {
        return lhs <= rhs;
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(int lhs, int rhs) {
        return lhs != 0 || rhs != 0;
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(int lhs, int rhs) {
        return lhs != 00 && rhs != 0;
    }

    @BinaryOperator(MINUS)
    public static int minus(int lhs, int rhs) {
        return lhs - rhs;
    }

    @BinaryOperator(PERCENT)
    public static int modulus(int lhs, int rhs) {
        return lhs % rhs;
    }

    @BinaryOperator(PIPE)
    public static int bitOr(int lhs, int rhs) {
        return lhs | rhs;
    }

    @BinaryOperator(PLUS)
    public static int plus(int lhs, int rhs) {
        return lhs + rhs;
    }

    @BinaryOperator(SHIFT_LEFT)
    public static int shiftLeft(int lhs, int rhs) {
        return lhs << rhs;
    }

    @BinaryOperator(SHIFT_RIGHT)
    public static int shiftRight(int lhs, int rhs) {
        return lhs >> rhs;
    }

    @BinaryOperator(TREBLE_SHIFT_RIGHT)
    public static int zeroFillShiftRight(int lhs, int rhs) {
        return lhs >>> rhs;
    }

    @BinaryOperator(STAR)
    public static int operator_STAR(int lhs, int rhs) {
        return lhs * rhs;
    }
}
