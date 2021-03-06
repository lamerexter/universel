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

package org.orthodox.universel.symanticanalysis.navigation;

import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Type;

import java.util.Objects;

public abstract class NavigationStage extends Node {
    private Node node;
    private boolean isSequence;
    private boolean inSequence;

    public NavigationStage(final Node node,
                           final boolean isSequence,
                           final boolean inSequence) {
        this.node = node;
        this.isSequence = isSequence;
        this.inSequence = inSequence;
    }

    public Node getNode() {
        return node;
    }

    public boolean isSequence() {
        return isSequence;
    }

    public void setSequence(final boolean sequence) {
        isSequence = sequence;
    }

    public boolean getInSequence() {
        return inSequence;
    }

    public void setInSequence(final boolean inSequence) {
        this.inSequence = inSequence;
    }

    public Class<?> getTypeDescriptor() {
        return node == null ? null : node.getTypeDescriptor();
    }

    public Type getType() {
        return node == null ? null : node.getType();
    }

    public boolean isFilter() { return false; }
    public boolean isMap() { return false; }
    public boolean isReduce() { return false; }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigationStage)) return false;
        if (!super.equals(o)) return false;
        NavigationStage that = (NavigationStage) o;
        return isSequence() == that.isSequence() &&
               getInSequence() == that.getInSequence() &&
               Objects.equals(getNode(), that.getNode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNode(), isSequence(), getInSequence());
    }
}
