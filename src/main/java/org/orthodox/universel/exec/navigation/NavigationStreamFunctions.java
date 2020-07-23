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

package org.orthodox.universel.exec.navigation;

import org.orthodox.universel.exec.operators.range.NumericRange;

import java.util.List;
import java.util.stream.Stream;

/**
 * Navigation stage implementation functions.
 */
public class NavigationStreamFunctions {
    /**
     * Implementation of a numeric range of indices to be included from a given list and returned as a stream.
     *
     * @param list the list whose sub-list, consisting of the elements within the given range of indices, is to be returned.
     * @param range the range indices (inclusive) of elements to be returned.
     * @return a stream of elements from the given list, whose indices are in the given range, inclusively.
     */
    public static <T> Stream<T> indexRangeStream(final List<T> list, final NumericRange<?> range) {
        return list.subList(range.getLowerBound().intValue(), range.getUpperBound().intValue()+1).stream();
    }
}
