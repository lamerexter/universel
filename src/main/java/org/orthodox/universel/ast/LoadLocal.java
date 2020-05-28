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

import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Objects;

/**
 * A JVM specific instruction to load the local variable at the specified offset (starting with paramaters (0 .. p),
 * through to local declarations (p+1 ... n).
 */
public class LoadLocal extends Node {
    private final int localIndex;

    public LoadLocal(final TokenImage tokenImage, final Class<?> typeDescriptor, final int localIndex) {
        super(tokenImage, typeDescriptor);
        this.localIndex = localIndex;
    }

    public LoadLocal(final TokenImage tokenImage, final TypeReference type, final int localIndex) {
        super(tokenImage, type);
        this.localIndex = localIndex;
    }

    public int getLocalIndex() {
        return localIndex;
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitLoadLocal(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LoadLocal))
            return false;
        if (!super.equals(o))
            return false;
        LoadLocal loadLocal = (LoadLocal) o;
        return localIndex == loadLocal.localIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), localIndex);
    }
}
