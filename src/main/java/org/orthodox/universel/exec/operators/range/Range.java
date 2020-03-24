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

import java.util.List;

/**
 * Definition of a range of scalar values.
 * 
 * @param <T> the type of items in the range.
 */
public interface Range<T> extends List<T> {
   /**
    * Returns the LHS (Left-Hand-Side) inclusive bound of the range which may or may not be
    * lower than the <em>to</em> RHS inclusive bound of the range. The lower bound of the
    * range can be obtained via a call to <code>{@link }</code>.
    * 
    * @return the range's <em>from</em> value, inclusive.
    */
   T getLeftBound();
   
   /**
    * Returns the RHS (Right-Hand-Side) inclusive bound of the range which may or may not be
    * higher than the <em>from</em> LHS inclusive bound of the range. The lower bound of the
    * range can be obtained via a call to <code>{@link }</code>.
    * 
    * @return the range's <em>to</em> value, inclusive.
    */
   T getRightBound();

   /**
    * Returns the lower inclusive bound of the range according to the scalar value type of the range.
    * 
    * @return the range's lower bounded value, inclusive: the <em>from</em> value if lower than or equal to the
    * <em>to</em> value or the <em>to</em> value if lower than the <em>from</em> value.
    */
   T getLowerBound();

   /**
    * Returns the upper inclusive bound of the range according to the scalar value type of the range.
    * 
    * @return the range's upper bounded value, inclusive: the <em>to</em> value if higher than or equal to the
    * <em>from</em> value or the <em>from</em> value if higher than the <em>to</em> value.
    */
   T getUpperBound();
   
   /**
    * Returns the size of the range, which may be arbitrarily large.
    * 
    * @return the size of the range, guaranteed to be >= 0.
    */
   Number getRangeSize();
}
