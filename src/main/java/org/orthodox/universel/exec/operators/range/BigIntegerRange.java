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

package org.orthodox.universel.exec.operators.range;

import org.orthodox.universel.ast.Operator;

import java.math.BigInteger;

/**
 * A more efficient implementation of a range of {@link java.math.BigInteger}.
 */
public class BigIntegerRange extends AbstractRange<BigInteger> {
    public BigIntegerRange(Operator rangeOperator, BigInteger lhs, BigInteger rhs) {
        this(lhs, lhsOperatorFor(rangeOperator), rhs, rhsOperatorFor(rangeOperator));
    }

    public BigIntegerRange(BigInteger lhs, Operator lhsOperator, BigInteger rhs, Operator rhsOperator) {
        super(lhs, lhsOperator, rhs, rhsOperator);
    }

    @Override
    public int compare(BigInteger lhs, BigInteger rhs) {
        return lhs == null || rhs == null ? 0 : lhs.compareTo(rhs);
    }

    @Override
    public BigInteger increment(BigInteger value, int amount) {
        return value == null ? null : value.add(BigInteger.ONE);
    }

    @Override
    public BigInteger decrement(BigInteger value, int amount) {
        return value == null ? null : value.subtract(BigInteger.ONE);
    }

    @Override
    public Number recalculateSize() {
        if (forward) {
            if (lhsLessThanEqualRhs) {
                return getUpperBound().subtract(getLowerBound()).add(BigInteger.ONE);
            } else {
                throw new IllegalArgumentException("Range sizes approaching BigInetger maximum size range is not supported");
            }
        } else {
            if (lhsLessThanEqualRhs) {
                throw new IllegalArgumentException("Range sizes approaching BigInetger maximum size range is not supported");
            } else {
                return getUpperBound().subtract(getLowerBound()).add(BigInteger.ONE);
            }
        }
    }
}
