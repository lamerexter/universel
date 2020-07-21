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

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

import static org.orthodox.universel.ast.Operator.*;

@BinaryOperator
public class InstantBinaryOperatorFunctions {
    @BinaryOperator({EXCLAMATION_EQUAL, LESS_GREATER_THAN})
    public static boolean notEqual(Instant lhs, Instant rhs) {
        return lhs != rhs;
    }

    @BinaryOperator({EQUAL, TREBLE_EQUAL})
    public static boolean equal(Instant lhs, Instant rhs) {
        return lhs.equals(rhs);
    }

    @BinaryOperator(GREATER_THAN)
    public static boolean greaterThan(Instant lhs, Instant rhs) {
        return lhs.compareTo(rhs) > 0;
    }

    @BinaryOperator(GREATER_THAN_EQUAL)
    public static boolean greaterThanEqual(Instant lhs, Instant rhs) {
        return lhs.compareTo(rhs) >= 0;
    }

    @BinaryOperator(LESS_THAN)
    public static boolean lessThan(Instant lhs, Instant rhs) {
        return lhs.compareTo(rhs) < 0;
    }

    @BinaryOperator(LESS_THAN_EQUAL)
    public static boolean lessThanEqual(Instant lhs, Instant rhs) {
        return lhs.compareTo(rhs) <= 0;
    }

    @BinaryOperator(MINUS)
    public static Instant subtract(Instant lhs, int rhs) {
        return lhs.minus(Period.ofDays(rhs));
    }

    @BinaryOperator(MINUS)
    public static Instant subtract(Instant lhs, double rhs) {
        return lhs.minus(Duration.ofMillis((long)(3600L * 1000L * 24 * rhs)));
    }

    @BinaryOperator(MINUS)
    public static Instant subtract(Instant lhs, Duration rhs) {
        return lhs.minus(rhs);
    }

    @BinaryOperator(MINUS)
    public static Instant subtract(Instant lhs, Period rhs) {
        return lhs.minus(rhs);
    }

    @BinaryOperator(PLUS)
    public static Instant add(Instant lhs, int rhs) {
        return lhs.plus(Period.ofDays(rhs));
    }

    @BinaryOperator(PLUS)
    public static Instant add(Instant lhs, double rhs) {
        return lhs.plus(Duration.ofMillis((long)(3600L * 1000L * 24 * rhs)));
    }

    @BinaryOperator(PLUS)
    public static Instant add(Instant lhs, Duration rhs) {
        return lhs.plus(rhs);
    }

    @BinaryOperator(PLUS)
    public static Instant add(Instant lhs, Period rhs) {
        return lhs.plus(rhs);
    }
}
