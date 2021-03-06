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

import org.orthodox.universel.exec.operators.binary.BinaryOperator;

import static org.orthodox.universel.ast.Operator.*;

@BinaryOperator
public class BooleanOperatorFunctions {

    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(boolean lhs, boolean rhs) {
        return lhs != rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(boolean lhs, boolean rhs) {
        return lhs == rhs;
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(boolean lhs, boolean rhs) {
        return lhs && !lhs;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(boolean lhs, boolean rhs) {
        return lhs || (lhs == rhs);
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(boolean lhs, boolean rhs) {
        return !lhs && rhs;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(boolean lhs, boolean rhs) {
        return !lhs || (lhs == rhs);
    }

    @BinaryOperator(LOGICAL_OR)
    public static boolean logicalOr(boolean lhs, boolean rhs) {
        return lhs || rhs;
    }

    @BinaryOperator(LOGICAL_AND)
    public static boolean logicalAnd(boolean lhs, boolean rhs) {
        return lhs && rhs;
    }
}
