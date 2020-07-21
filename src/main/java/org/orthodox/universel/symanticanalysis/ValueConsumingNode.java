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

package org.orthodox.universel.symanticanalysis;

import org.orthodox.universel.ast.CompositeNode;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;

public class ValueConsumingNode extends Node implements CompositeNode {
    private Node delegate;

    public ValueConsumingNode(Node delegate) {
        super(delegate.getTokenImage(), delegate.getTypeDescriptor());
        this.delegate = delegate;
    }

    public Node getDelegate() {
        return delegate;
    }

    @Override
    public List<Node> getChildNodes() {
        return singletonList(getDelegate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ValueConsumingNode))
            return false;
        if (!super.equals(o))
            return false;
        ValueConsumingNode nodes = (ValueConsumingNode) o;
        return Objects.equals(getDelegate(), nodes.getDelegate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDelegate());
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitValueConsumingNode(this);
    }
}
