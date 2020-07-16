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

import org.beanplanet.core.collections.ListBuilder;
import org.beanplanet.core.models.Builder;
import org.orthodox.universel.cst.CompositeNode;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Type;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.List;
import java.util.Objects;

import static org.beanplanet.core.lang.TypeUtil.loadClassOrNull;
import static org.orthodox.universel.cst.TokenImage.range;

public class InternalNodeSequence extends Node implements CompositeNode {
    private List<Node> nodes;
    private Type resultType;

    public InternalNodeSequence(final List<Node> nodes) {
        this(null, nodes);
    }

    public InternalNodeSequence(final Type resultType, final List<Node> nodes) {
        super(range(nodes));
        this.nodes = nodes;
        this.resultType = resultType;
    }

    public static InternalNodeSequenceBuilder builder() {
        return new InternalNodeSequenceBuilder();
    }

    public boolean isEmpty() {
        return nodes == null || nodes.isEmpty();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public List<Node> getChildNodes() {
        return getNodes();
    }

    @Override
    public Type getType() {
        if ( super.getType() != null ) return super.getType();

        if ( resultType != null ) return resultType;

        return isEmpty() ? null : nodes.get(nodes.size()-1).getType();
    }

    @Override
    public Class<?> getTypeDescriptor() {
        if ( super.getTypeDescriptor() != null ) return super.getTypeDescriptor();

        if ( resultType != null && loadClassOrNull(resultType.getName().join(".")) != null ) {
            return loadClassOrNull( resultType.getName().join(".") );
        }

        return isEmpty() ? null : nodes.get(nodes.size()-1).getTypeDescriptor();
    }

    public Type getResultType() {
        return resultType;
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitInternalNodeSequence(this);
    }

    public static class InternalNodeSequenceBuilder implements Builder<InternalNodeSequence> {
        private ListBuilder<Node> nodeListBuilder = ListBuilder.builder();
        private Type type;

        public final InternalNodeSequenceBuilder add(Node ... elements) {
            nodeListBuilder.add(elements);
            return this;
        }

        public final InternalNodeSequenceBuilder addNotNull(Node ... elements) {
            nodeListBuilder.addNotNull(elements);
            return this;
        }

        public final InternalNodeSequenceBuilder resultType(Type type) {
            this.type = type;
            return this;
        }

        @Override
        public InternalNodeSequence build() {
            return new InternalNodeSequence(type, nodeListBuilder.build());
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
        InternalNodeSequence that = (InternalNodeSequence) o;
        return Objects.equals(this.getType(), that.getNodes())
               && Objects.equals(this.getNodes(), that.getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType(), getNodes());
    }
}
