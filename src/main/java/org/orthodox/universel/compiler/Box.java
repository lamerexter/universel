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

package org.orthodox.universel.compiler;

import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Type;
import org.orthodox.universel.ast.UniversalCodeVisitor;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.ResolvedTypeReference;

import java.util.Objects;

import static org.beanplanet.core.lang.TypeUtil.ensureNonPrimitiveType;

public class Box extends JvmInstructionNode {
    public Box(Node source) {
        super(source);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Box))
            return false;
        if (!super.equals(o))
            return false;
        Box nodes = (Box) o;
        return Objects.equals(getSource(), nodes.getSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSource());
    }

    @Override
    public Type getType() {
        return getSource() == null ? super.getType() : new ResolvedTypeReferenceOld(ensureNonPrimitiveType(getSource().getTypeDescriptor()));
    }

    @Override
    public void emit(final BytecodeHelper bch) {
        bch.box(getSource().getTypeDescriptor());
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitBoxExpression(this);
    }

}
