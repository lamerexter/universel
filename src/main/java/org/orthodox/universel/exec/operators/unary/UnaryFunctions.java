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

package org.orthodox.universel.exec.operators.unary;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Unary operators for all numeric datatypes.
 */
public class UnaryFunctions {
    /**
     * Performs a unary minus of a byte.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static byte unaryMinus(byte number) {
        return (byte)-number;
    }

    /**
     * Performs a unary minus of a character.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static char unaryMinus(char number) {
        return (char)-number;
    }

    /**
     * Performs a unary minus of a double.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static double unaryMinus(double number) {
        return -number;
    }

    /**
     * Performs a unary minus of a float.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static float unaryMinus(float number) {
        return -number;
    }

    /**
     * Performs a unary minus of an integer.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static int unaryMinus(int number) {
        return -number;
    }

    /**
     * Performs a unary minus of a long.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static long unaryMinus(long number) {
        return -number;
    }

    /**
     * Performs a unary minus of a {@link BigDecimal}.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static BigDecimal unaryMinus(BigDecimal number) {
        return number.negate();
    }

    /**
     * Performs a unary minus of a {@link BigInteger}.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static BigInteger unaryMinus(BigInteger number) {
        return number.negate();
    }

    /**
     * Performs a unary minus of a short.
     *
     * @param number the number to be unary negated.
     * @return the unary minus of the given number.
     */
    public static short unaryMinus(short number) {
        return (short)-number;
    }
}
