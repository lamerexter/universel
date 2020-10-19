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

import java.util.Objects;

/**
 * A JVM specific instruction to store a value into a local variable at the specified offset (starting with paramaters (0 .. p),
 * through to local declarations (p+1 ... n).
 */
public class StoreLocal extends Node {
    /** The value expression to be stored. */
    private final Node value;
    /** Whether the local operation is applied to a static method. */
    private final boolean staticMethod;
    /** The zero-based index of the local variable. */
    private final int localIndex;

    public StoreLocal(final TokenImage tokenImage, final Type type, final Node value, final boolean staticMethod, final int localIndex) {
        super(tokenImage, type);
        this.value = value;
        this.staticMethod = staticMethod;
        this.localIndex = localIndex;
    }

    /**
     * Gets the value expression to be stored.
     *
     * @return the value expression to be stored.
     */
    public Node getValue() {
        return value;
    }

    /**
     * Gets whether the local operation is applied to a static method.
     *
     * @return true is the local variable operation is on a static method, false otherwise.
     */
    public boolean isStaticMethod() {
        return staticMethod;
    }

    /**
     * Gets the zero-based index of the local variable.
     *
     * @return the zero-based index of the local variable.
     */
    public int getLocalIndex() {
        return localIndex;
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitStoreLocal(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreLocal)) return false;
        if (!super.equals(o)) return false;
        StoreLocal that = (StoreLocal) o;
        return isStaticMethod() == that.isStaticMethod() &&
               getLocalIndex() == that.getLocalIndex() &&
               Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue(), isStaticMethod(), getLocalIndex());
    }
}
