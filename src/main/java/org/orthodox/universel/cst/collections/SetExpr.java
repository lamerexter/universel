/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

package org.orthodox.universel.cst.collections;

import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * An set expression on the Abstract Syntax Tree, of the form <code>{}1, 2, 3, 3, 4}</code>
 */
public class SetExpr extends Expression implements CompositeNode {
    /** The element expressions of this set expression. */
    private List<Node> elements;

    /**
     * Consructs set expression from the given parser token image.
     *
     * @param tokenImage the parser token image.
     * @param elements the element expressions of this set expression, which may be empty.
     */
    public SetExpr(TokenImage tokenImage, List<Node> elements) {
        super(tokenImage);
        this.elements = elements;
    }

    @Override
    public Type getType() {
        return new ResolvedTypeReferenceOld(getTokenImage(), Set.class); // This could be a ParameterisedType now, of the common supertype of elements?
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return Set.class;
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitSet(this);
    }

    /**
     * Gets the element expressions of this set expression.
     *
     * @return the element expressions of this set expression, which may be empty.
     */
    public List<Node> getElements() {
        return elements == null ? Collections.emptyList() : elements;
    }

    @Override
    public List<Node> getChildNodes() {
        return elements;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SetExpr)) return false;
        if (!super.equals(o)) return false;
        SetExpr nodes = (SetExpr) o;
        return Objects.equals(getElements(), nodes.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElements());
    }
}
