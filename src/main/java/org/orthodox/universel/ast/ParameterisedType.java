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

package org.orthodox.universel.ast;

import java.util.List;

/**
 * ParameterisedType represents a type parameterised with actual types or type variables such as
 * <code>Collection&lt;String&gt;</code> or <code>Collection&lt;T&gt;</code>.
 */
public interface ParameterisedType extends Type {
    /**
     * Returns a list of {@code Type} objects representing the actual type
     * parameters to this type.
     *
     * @return a list of {@code Type} objects representing the actual type parameters to this type.
     */
    List<Type> getTypeParameters();

    /**
     * Returns the {@code Type} object representing this type without regard to type arguments. For example, if this
     * type were <code>Collection&lt;T&gt;</code>, then <code>Collection</code> would be returned.
     *
     * @return the {@code Type} object representing the un-paramaterised class or interface of this type.
     */
    Type getRawType();
}

