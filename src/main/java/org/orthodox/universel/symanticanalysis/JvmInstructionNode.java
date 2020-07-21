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

import org.orthodox.universel.compiler.BytecodeHelper;
import org.orthodox.universel.ast.CompositeNode;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class JvmInstructionNode extends Node implements CompositeNode {
    private final Node source;

    public JvmInstructionNode(Node source) {
        super(source.getTokenImage());
        this.source = source;
    }

    public JvmInstructionNode(TokenImage tokenImage) {
        super(tokenImage);
        source = null;
    }

    public Node getSource() {
        return source;
    }

    @Override
    public List<Node> getChildNodes() {
        return source == null ? Collections.emptyList() : Collections.singletonList(source);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof JvmInstructionNode)) return false;
        if (!super.equals(o)) return false;
        JvmInstructionNode that = (JvmInstructionNode) o;
        return Objects.equals(getSource(), that.getSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSource());
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitJvmInstruction(this);
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return source == null ? super.getTypeDescriptor() : source.getTypeDescriptor();
    }

    public abstract void emit(BytecodeHelper bch);
}
