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

import org.beanplanet.core.collections.ListBuilder;
import org.beanplanet.core.models.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static org.orthodox.universel.ast.TokenImage.range;

public class NodeSequence<T extends Node> extends Node implements CompositeNode {
    private List<T> nodes;

    private static final NodeSequence<?> EMPTY_NODESEQUENCE = new NodeSequence<>(unmodifiableList(emptyList()));

    public NodeSequence(final List<T> nodes) {
        this(range(nodes), nodes);
    }

    public NodeSequence(final TokenImage tokenImage, final List<T> nodes) {
        super(tokenImage);
        this.nodes = nodes;
    }

    public NodeSequence(final TokenImage tokenImage, final Type resultType, final List<T> nodes) {
        super(tokenImage, resultType);
        this.nodes = nodes;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Node> NodeSequence<T> emptyNodeSequence() { return (NodeSequence<T>)EMPTY_NODESEQUENCE; }

    public static <T extends Node> NodeSequenceBuilder<T> builder() {
        return new NodeSequenceBuilder<>();
    }

    public boolean isEmpty() {
        return nodes == null || nodes.isEmpty();
    }

    public int size() {
        return nodes == null ? 0 : nodes.size();
    }

    public List<T> getNodes() {
        return nodes == null ? emptyList() : nodes;
    }

    public T lastNode() {
        return getNodes().get(size()-1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Node> getChildNodes() {
        return (List<Node>)getNodes();
    }

    public Type getResultType() {
        return super.getType();
    }

    public Stream<T> stream() {
        return nodes == null ? Collections.<T>emptyList().stream() : getNodes().stream();
    }

    @Override
    public Type getType() {
        if ( super.getType() != null ) return super.getType();

        return isEmpty() ? null : nodes.get(nodes.size()-1).getType();
    }

    @Override
    public Class<?> getTypeDescriptor() {
        if ( super.getTypeDescriptor() != null ) return super.getTypeDescriptor();

        return isEmpty() ? null : nodes.get(nodes.size()-1).getTypeDescriptor();
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNodeSequence(this);
    }

    public static class NodeSequenceBuilder<T extends Node> implements Builder<NodeSequence<T>> {
        private TokenImage tokenImage;
        private ListBuilder<T> nodeListBuilder = ListBuilder.builder();
        private Type resultType;

        public final NodeSequenceBuilder<T> add(T ... elements) {
            nodeListBuilder.add(elements);
            return this;
        }

        public final NodeSequenceBuilder<T> addNotNull(T ... elements) {
            nodeListBuilder.addNotNull(elements);
            return this;
        }

        public final NodeSequenceBuilder<T> addAll(List<T> elements) {
            nodeListBuilder.addAll(elements);
            return this;
        }

        public final NodeSequenceBuilder<T> addNotNull(List<T> elements) {
            nodeListBuilder.addAllNotNull(elements);
            return this;
        }

        public final NodeSequenceBuilder<T> addAll(NodeSequence<T> nodeSequence) {
            if ( nodeSequence != null ) {
                addNotNull(nodeSequence.getNodes());
            }
            return this;
        }

        public final NodeSequenceBuilder<T> resultType(Type resultType) {
            this.resultType = resultType;
            return this;
        }

        @Override
        public NodeSequence<T> build() {
            return new NodeSequence<T>(tokenImage, resultType, nodeListBuilder.build());
        }

        public final NodeSequenceBuilder<T> tokenImage(final TokenImage tokenImage) {
            this.tokenImage = tokenImage;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NodeSequence))
            return false;
        if (!super.equals(o))
            return false;
        NodeSequence<?> nodes1 = (NodeSequence<?>) o;
        return Objects.equals(getNodes(), nodes1.getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNodes());
    }
}
