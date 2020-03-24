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

import org.orthodox.universel.cst.Operator;

/**
 * A more efficient implementation of a byte range.
 */
public class ByteRange extends AbstractRange<Byte> {
    public ByteRange(Operator rangeOperator, Byte lhs, Byte rhs) {
        this(lhs, lhsOperatorFor(rangeOperator), rhs, rhsOperatorFor(rangeOperator));
    }

    public ByteRange(Byte lhs, Operator lhsOperator, Byte rhs, Operator rhsOperator) {
        super(lhs, lhsOperator, rhs, rhsOperator);
    }

    @Override
    public int compare(Byte lhs, Byte rhs) {
        return lhs == null || rhs == null ? 0 : lhs.compareTo(rhs);
    }

    @Override
    public Byte increment(Byte value, int amount) {
        return value == null ? null : (byte)(value + 1);
    }

    @Override
    public Byte decrement(Byte value, int amount) {
        return value == null ? null : (byte)(value - 1);
    }

    @Override
    public Number recalculateSize() {
        if (forward) {
            if (lhsLessThanEqualRhs) {
                return getUpperBound().longValue() - getLowerBound().longValue() + 1;
            } else {
                return (long) Byte.MAX_VALUE - lhsInclusive + Math.abs((long) Byte.MIN_VALUE - rhsInclusive) + 2;
            }
        } else {
            if (lhsLessThanEqualRhs) {
                return (long) Byte.MAX_VALUE - rhsInclusive + Math.abs((long) Byte.MIN_VALUE - lhsInclusive) + 2;
            } else {
                return getUpperBound().longValue() - getLowerBound().longValue() + 1;
            }
        }
    }
}
