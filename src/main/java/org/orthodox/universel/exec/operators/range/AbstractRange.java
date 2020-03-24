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

import java.util.*;

/**
 * Implementation of a range of scalar values.
 */
public abstract class AbstractRange<T> extends AbstractList<T> implements Range<T>, Comparator<T> {
    private int step = 1;

    protected T lhs;
    protected T lhsInclusive;
    protected Operator lhsOperator;
    protected T rhs;
    protected T rhsInclusive;
    protected Operator rhsOperator;
    protected boolean forward;
    protected Number size;
    protected boolean lhsLessThanEqualRhs;
    protected boolean inverted;


    public AbstractRange(T lhs, Operator lhsOperator, T rhs, Operator rhsOperator) {
        this.lhs = lhs;
        this.rhs = rhs;

        if (lhsOperator == null)
            lhsOperator = Operator.RANGE_INCLUSIVE;
        if (rhsOperator == null)
            rhsOperator = Operator.RANGE_INCLUSIVE;

        if (lhsOperator == Operator.RANGE_INCLUSIVE && rhsOperator == Operator.RANGE_INCLUSIVE) {
            if (compare(lhs, rhs) <= 0) {
                this.lhsOperator = Operator.GREATER_THAN_EQUAL;
                this.rhsOperator = Operator.LESS_THAN_EQUAL;
            } else {
                this.lhsOperator = Operator.LESS_THAN_EQUAL;
                this.rhsOperator = Operator.GREATER_THAN_EQUAL;
            }
        } else if (lhsOperator == Operator.RANGE_INCLUSIVE) {
            this.rhsOperator = rhsOperator;
            if (rhsOperator == Operator.GREATER_THAN || rhsOperator == Operator.GREATER_THAN_EQUAL) {
                this.lhsOperator = Operator.LESS_THAN_EQUAL;
            } else {
                this.lhsOperator = Operator.GREATER_THAN_EQUAL;
            }
        } else if (rhsOperator == Operator.RANGE_INCLUSIVE) {
            this.lhsOperator = lhsOperator;
            if (lhsOperator == Operator.GREATER_THAN || lhsOperator == Operator.GREATER_THAN_EQUAL) {
                this.rhsOperator = Operator.LESS_THAN_EQUAL;
            } else {
                this.rhsOperator = Operator.GREATER_THAN_EQUAL;
            }
        } else {
            this.lhsOperator = lhsOperator;
            this.rhsOperator = rhsOperator;
        }

        this.lhsInclusive = adjustToInclusiveValue(lhs, this.lhsOperator);
        this.rhsInclusive = adjustToInclusiveValue(rhs, this.rhsOperator);
        lhsLessThanEqualRhs = compare(lhsInclusive, rhsInclusive) <= 0;
        forward = this.lhsOperator == Operator.GREATER_THAN || this.lhsOperator == Operator.GREATER_THAN_EQUAL;
        inverted = lhsLessThanEqualRhs ? (this.lhsOperator == Operator.LESS_THAN || this.lhsOperator == Operator.LESS_THAN_EQUAL) : (this.lhsOperator == Operator.GREATER_THAN || this.lhsOperator == Operator.GREATER_THAN_EQUAL);
        checkValidRange(lhs, this.lhsOperator, rhs, this.rhsOperator);
    }


    protected static Operator lhsOperatorFor(Operator rangeOperator) {
        int rangeStartPos = rangeOperator.getSymbol().indexOf(Operator.RANGE_INCLUSIVE.getSymbol());
        if ( rangeStartPos < 0 ) {
            throw new IllegalArgumentException("Invalid range operator ["+rangeOperator.getSymbol()+"]");
        }

        if (rangeStartPos == 0) return Operator.RANGE_INCLUSIVE;
        final String lhsOperatorSymbol = rangeOperator.getSymbol().substring(0, rangeStartPos);
        return Arrays.stream(Operator.values())
                     .filter(o -> o.getSymbol().equals(lhsOperatorSymbol))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("No operator matches the LHS of range operator ["+rangeOperator.getSymbol()+"]"));

    }

    protected static Operator rhsOperatorFor(Operator rangeOperator) {
        final String rangeSymbol = rangeOperator.getSymbol();
        int rangeStartPos = rangeSymbol.indexOf(Operator.RANGE_INCLUSIVE.getSymbol());
        if ( rangeStartPos < 0 ) {
            throw new IllegalArgumentException("Invalid range operator ["+rangeOperator.getSymbol()+"]");
        }

        if (rangeStartPos == rangeSymbol.length() - Operator.RANGE_INCLUSIVE.getSymbol().length() ) return Operator.RANGE_INCLUSIVE;
        final String rhsOperatorSymbol = rangeOperator.getSymbol().substring(rangeStartPos+Operator.RANGE_INCLUSIVE.getSymbol().length());
        return Arrays.stream(Operator.values())
                     .filter(o -> o.getSymbol().equals(rhsOperatorSymbol))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("No operator matches the RHS of range operator ["+rangeOperator.getSymbol()+"]"));

    }


    protected void checkValidRange(T lhs, Operator lhsOperator, T rhs, Operator rhsOperator) throws InvalidRangeException {
        boolean invalid = (((lhsOperator == Operator.LESS_THAN || lhsOperator == Operator.LESS_THAN_EQUAL)) && ((rhsOperator == Operator.LESS_THAN || rhsOperator == Operator.LESS_THAN_EQUAL)) || ((lhsOperator == Operator.GREATER_THAN || lhsOperator == Operator.GREATER_THAN_EQUAL)) && ((rhsOperator == Operator.GREATER_THAN || rhsOperator == Operator.GREATER_THAN_EQUAL)));
        if (invalid) {
            throw new InvalidRangeException("The range [" + lhs + lhsOperator.getSymbol() + ".." + rhsOperator.getSymbol() + rhs + "] is invalid because its lower and upper bounds imply intersecting range values");
        }
    }

    private T adjustToInclusiveValue(T value, Operator operator) {
        if (operator == Operator.LESS_THAN) {
            // Range bound is greater than the value (i.e. value < R) so increment
            // value to find real bound
            return decrement(value);
        } else if (operator == Operator.GREATER_THAN) {
            // Range bound is less than the value (i.e. value > R) so decrement
            // value to find real bound
            return increment(value);
        } else {
            return value;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The range element index [" + index + "] may not be negative");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("The range element index [" + index + "] exceeds the maximum index of the range " + this);
        }

        if (forward) {
            return increment(getLowerBound(), index * step);
        } else {
            return decrement(getUpperBound(), index * step);
        }
    }

    public T increment(T value) {
        return increment(value, 1);
    }

    public abstract T increment(T value, int amount) ;

    public T decrement(T value) {
        return decrement(value, 1);
    }

    public abstract T decrement(T value, int amount);

    @Override
    public final int size() {
        return getRangeSize().intValue();
    }

    /**
     * Returns the size of the range, which may be arbitrarily large.
     *
     * @return the size of the range, guaranteed to be >= 0.
     */
    public final Number getRangeSize() {
        if (size == null) {
            size = recalculateSize();
        }

        return size;
    }

    protected Number recalculateSize() {
        int size = 0;
        // In this implementation we will iterate over the range and count
        for (Iterator<T> iter = iterator(); iter.hasNext(); ) {
            iter.next();
            size++;
        }

        return size;
    }

    public T getLeftBound() {
        return lhsInclusive;
    }

    public T getLowerBound() {
        return (lhsLessThanEqualRhs ? lhsInclusive : rhsInclusive);
    }

    public T getRightBound() {
        return rhsInclusive;
    }

    public T getUpperBound() {
        return (lhsLessThanEqualRhs ? rhsInclusive : lhsInclusive);
    }

    public List<T> step(int step) {
        return null; // new ContextualRange<T>(context, from, fromInclusive, to,
        // toInclusive, step);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("The from index [" + fromIndex + "] may not be negative");
        }
        if (toIndex > size()) {
            throw new IndexOutOfBoundsException("The to index [" + toIndex + "] must be less than the size of the range [" + size() + "]");
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("The from index [" + fromIndex + "] must be less than or equal to the to index [" + toIndex + "]");
        }

        return null; // new ContextualRange<T>(context, get(fromIndex), (fromIndex
        // > 0 || fromInclusive), get(toIndex--), (toIndex < size()-1
        // || toInclusive), step);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private boolean first = true;
            private T nextValue = lhsInclusive;

            public boolean hasNext() {
                if (first) {
                    return true;
                }
                // Stop conditions
                if (!first && !inverted) {
                    if (compare(lhsInclusive, rhsInclusive) == 0) {
                        if (compare(peekNext(), rhsInclusive) == 0) {
                            return false;
                        }
                    } else {
                        if (compare(nextValue, rhsInclusive) == 0) {
                            return false;
                        }
                    }
                }

                if (!first && inverted) {
                    if (compare(lhsInclusive, rhsInclusive) == 0) {
                        if (compare(peekNext(), rhsInclusive) == 0) {
                            return false;
                        }
                    } else {
                        if (compare(nextValue, rhsInclusive) == 0) {
                            return false;
                        }
                    }
                }

                boolean valueWithinRange;
                T possibleNext = peekNext();
                if (inverted) {
                    if (lhsLessThanEqualRhs) {
                        // left-low --> right-high (e.g. 1<=..>=2)
                        valueWithinRange = compare(possibleNext, lhsInclusive) <= 0 || compare(possibleNext, rhsInclusive) >= 0;
                    } else {
                        // left-high-->right-low (e.g. 2>=..<=1)
                        valueWithinRange = compare(possibleNext, lhsInclusive) >= 0 || compare(possibleNext, rhsInclusive) <= 0;
                    }
                } else {
                    if (lhsLessThanEqualRhs) {
                        // left-low --> right-high (e.g. 1>=..<=2)
                        valueWithinRange = compare(possibleNext, lhsInclusive) >= 0 && compare(possibleNext, rhsInclusive) <= 0;
                    } else {
                        // left-high-->right-low (e.g. 2<=..>=1)
                        valueWithinRange = compare(possibleNext, lhsInclusive) <= 0 && compare(possibleNext, rhsInclusive) >= 0;
                    }
                }

                return valueWithinRange;
            }

            public T next() {

                if (!hasNext()) {
                    throw new NoSuchElementException("A call to next() was made on an exhausted range iterator: the value [" + nextValue + "] lies outside the range [" + this + "]");
                }

                if (first) {
                    first = false;
                } else {
                    nextValue = peekNext();
                }

                return nextValue;
            }

            private T peekNext() {
                if (forward) {
                    return increment(nextValue, step);
                } else {
                    return decrement(nextValue, step);
                }
            }

            public void remove() {
                // Not implemented yet
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object value) {
        boolean withinRange;
        if (inverted) {
            if (lhsLessThanEqualRhs) {
                // left-low --> right-high (e.g. 1<=..>=2)
                withinRange = compare((T) value, lhsInclusive) <= 0 || compare((T) value, rhsInclusive) >= 0;
            } else {
                // left-high-->right-low (e.g. 2>=..<=1)
                withinRange = compare((T) value, lhsInclusive) >= 0 || compare((T) value, rhsInclusive) <= 0;
            }
        } else {
            if (lhsLessThanEqualRhs) {
                // left-low --> right-high (e.g. 1>=..<=2)
                withinRange = compare((T) value, lhsInclusive) >= 0 && compare((T) value, rhsInclusive) <= 0;
            } else {
                // left-high-->right-low (e.g. 2<=..>=1)
                withinRange = compare((T) value, lhsInclusive) <= 0 && compare((T) value, rhsInclusive) >= 0;
            }
        }

        return withinRange;
    }

//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append(lhs).append(lhsOperator.getSymbol()).append("..").append(rhsOperator.getSymbol()).append(rhs);
//        return s.toString();
//    }

    /**
     * @return the lhsOperator
     */
    public Operator getLhsOperator() {
        return lhsOperator;
    }

    /**
     * @param lhsOperator the lhsOperator to set
     */
    public void setLhsOperator(Operator lhsOperator) {
        this.lhsOperator = lhsOperator;
    }

    /**
     * @return the rhsOperator
     */
    public Operator getRhsOperator() {
        return rhsOperator;
    }

    /**
     * @param rhsOperator the rhsOperator to set
     */
    public void setRhsOperator(Operator rhsOperator) {
        this.rhsOperator = rhsOperator;
    }

    /**
     * @return the lhsLessThanEqualRhs
     */
    public boolean isLhsLessThanEqualRhs() {
        return lhsLessThanEqualRhs;
    }

    /**
     * @param lhsLessThanEqualRhs the lhsLessThanEqualRhs to set
     */
    public void setLhsLessThanEqualRhs(boolean lhsLessThanEqualRhs) {
        this.lhsLessThanEqualRhs = lhsLessThanEqualRhs;
    }

    /**
     * @return the inverted
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * @param inverted the inverted to set
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }
}
