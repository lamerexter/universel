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

package org.orthodox.universel.symanticanalysis.name;

import javafx.util.Builder;
import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.cst.CompositeNode;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.cst.TokenImage.range;

public class InternalNodeSequence extends Node implements CompositeNode {
    private List<Node> nodes;
    private boolean atomic;

    public InternalNodeSequence(final List<Node> nodes) {
        this(nodes, false);
    }

    public InternalNodeSequence(final List<Node> nodes, boolean atomic) {
        super(range(nodes));
        this.nodes = nodes;
        this.atomic = atomic;
    }

    public static InternalNodeSequenceBuilder builder() {
        return new InternalNodeSequenceBuilder();
    }

    public boolean isEmpty() {
        return nodes == null || nodes.isEmpty();
    }

    public boolean isAtomic() {
        return atomic;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public List<Node> getChildNodes() {
        return getNodes();
    }

    @Override
    public Class<?> getTypeDescriptor() {
        if ( super.getTypeDescriptor() != null ) return super.typeDescriptor;

        return nodes.isEmpty() ? null : nodes.get(nodes.size()-1).getTypeDescriptor();
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitInternalNodeSequence(this);
    }

    public static class InternalNodeSequenceBuilder implements Builder<InternalNodeSequence> {
        private ListBuilder<Node> nodeListBuilder = ListBuilder.builder();
        private boolean atomic = false;

        public final InternalNodeSequenceBuilder add(Node ... elements) {
            nodeListBuilder.add(elements);
            return this;
        }

        public final InternalNodeSequenceBuilder addNotNull(Node ... elements) {
            nodeListBuilder.addNotNull(elements);
            return this;
        }

        public final InternalNodeSequenceBuilder atomic(boolean atomic) {
            this.atomic = atomic;
            return this;
        }

        @Override
        public InternalNodeSequence build() {
            return new InternalNodeSequence(nodeListBuilder.build(), atomic);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof InternalNodeSequence))
            return false;
        if (!super.equals(o))
            return false;
        InternalNodeSequence nodes1 = (InternalNodeSequence) o;
        return Objects.equals(getNodes(), nodes1.getNodes())
                && Objects.equals(isAtomic(), nodes1.isAtomic());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNodes(), isAtomic());
    }
}
