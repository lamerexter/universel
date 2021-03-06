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

package org.orthodox.universel.exec.operators.binary.impl.datetime;

import org.orthodox.universel.exec.operators.binary.BinaryOperator;

import java.time.chrono.ChronoZonedDateTime;

import static org.orthodox.universel.ast.Operator.*;

@BinaryOperator
public class ChronoZonedDateTimeBinaryOperatorFunctions {
    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(ChronoZonedDateTime lhs, ChronoZonedDateTime rhs) {
        return lhs != rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(ChronoZonedDateTime lhs, ChronoZonedDateTime rhs) {
        return lhs.equals(rhs);
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(ChronoZonedDateTime lhs, ChronoZonedDateTime rhs) {
        return lhs.compareTo(rhs) > 0;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(ChronoZonedDateTime lhs, ChronoZonedDateTime rhs) {
        return lhs.compareTo(rhs) >= 0;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(ChronoZonedDateTime lhs, ChronoZonedDateTime rhs) {
        return lhs.compareTo(rhs) < 0;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(ChronoZonedDateTime lhs, ChronoZonedDateTime rhs) {
        return lhs.compareTo(rhs) <= 0;
    }
}
