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
import java.util.Date;

import static org.orthodox.universel.ast.Operator.MINUS;
import static org.orthodox.universel.ast.Operator.PLUS;

@BinaryOperator
public class LegacyDateBinaryOperatorFunctions {

    @BinaryOperator(PLUS)
    public static Date add(Date lhs, int rhs) {
        return toDate(toInstant(lhs).plus(Period.ofDays(rhs)));
    }

    @BinaryOperator(PLUS)
    public static Date add(Date lhs, double rhs) {
        return toDate(toInstant(lhs).plus(Duration.ofMillis((long)(3600L * 1000L * 24 * rhs))));
    }

    @BinaryOperator(PLUS)
    public static Date add(Date lhs, Duration rhs) {
        return toDate(toInstant(lhs).plus(rhs));
    }

    @BinaryOperator(PLUS)
    public static Date add(Date lhs, Period rhs) {
        return toDate(toInstant(lhs).plus(rhs));
    }

    @BinaryOperator(MINUS)
    public static Date subtract(Date lhs, int rhs) {
        return toDate(toInstant(lhs).minus(Period.ofDays(rhs)));
    }

    @BinaryOperator(MINUS)
    public static Date subtract(Date lhs, double rhs) {
        return toDate(toInstant(lhs).minus(Duration.ofMillis((long)(3600L * 1000L * 24 * rhs))));
    }

    @BinaryOperator(MINUS)
    public static Date subtract(Date lhs, Duration rhs) {
        return toDate(toInstant(lhs).minus(rhs));
    }

    @BinaryOperator(MINUS)
    public static Date subtract(Date lhs, Period rhs) {
        return toDate(toInstant(lhs).minus(rhs));
    }

    private static Date toDate(Instant instant) {
        return new Date( instant.toEpochMilli());
    }

    private static Instant toInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }

}
