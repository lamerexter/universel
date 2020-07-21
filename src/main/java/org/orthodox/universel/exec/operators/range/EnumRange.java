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

/**
 * A more efficient implementation of a range of integer.
 */
public class EnumRange<E extends Enum<E>>  extends AbstractRange<E> {
    public EnumRange(Operator rangeOperator, E lhs, E rhs) {
        this(lhs, lhsOperatorFor(rangeOperator), rhs, rhsOperatorFor(rangeOperator));
    }

    public EnumRange(E lhs, Operator lhsOperator, E rhs, Operator rhsOperator) {
        super(lhs, lhsOperator, rhs, rhsOperator);
    }

    @Override
    public int compare(E lhs, E rhs) {
        return lhs == null || rhs == null ? 0 : lhs.compareTo(rhs);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E increment(E value, int amount) {
        return value == null ? null : (E)value.getClass().getEnumConstants()[value.ordinal() + amount];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E decrement(E value, int amount) {
        return value == null ? null : (E)value.getClass().getEnumConstants()[value.ordinal() - amount];
    }

    @Override
    public Number recalculateSize() {
        if (forward) {
            if (lhsLessThanEqualRhs) {
                return getUpperBound().ordinal() - getLowerBound().ordinal() + 1;
            } else {
                return getUpperBound().getClass().getEnumConstants()[getUpperBound().getClass().getEnumConstants().length-1].ordinal() - lhsInclusive.ordinal()
                       + Math.abs(getUpperBound().getClass().getEnumConstants()[0].ordinal() - rhsInclusive.ordinal()) + 2;
            }
        } else {
            if (lhsLessThanEqualRhs) {
                return getUpperBound().getClass().getEnumConstants()[getUpperBound().getClass().getEnumConstants().length-1].ordinal() - rhsInclusive.ordinal()
                       + Math.abs(getUpperBound().getClass().getEnumConstants()[0].ordinal() - lhsInclusive.ordinal()) + 2;
            } else {
                return getUpperBound().ordinal() - getLowerBound().ordinal() + 1;
            }
        }
    }
}
