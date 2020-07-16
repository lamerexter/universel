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

package org.orthodox.universel.cst;

import java.util.List;

/**
 * WildcardType represents a Java wildcard type expression, such as
 * {@code ?}, {@code ? extends Number}, or {@code ? super Integer}.
 *
 * @see java.lang.reflect.WildcardType
 */
public interface WildcardType {
    /**
     * Returns a list of {@code Type} objects representing the  upper
     * bound(s) of this type variable.  If no upper bound is
     * explicitly declared, the upper bound is {@code Object}.
     *
     * @return a list of Types representing the upper bound(s) of this type variable, which may be empty.
     */
    List<Type> getUpperBounds();

    /**
     * Returns a list of {@code Type} objects representing the
     * lower bound(s) of this type variable.  If no lower bound is
     * explicitly declared, the lower bound is the type of {@code null}.
     * In this case, a zero length list is returned.
     *
     * @return list of Types representing the lower bound(s) of this type variable, which may be empty.
     */
    List<Type> getLowerBounds();
}
