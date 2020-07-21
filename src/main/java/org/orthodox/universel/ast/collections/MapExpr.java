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

package org.orthodox.universel.ast.collections;

import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An map entry expression on the Abstract Syntax Tree, of the form <code>[1, 2, 3, 4]</code>
 */
public class MapExpr extends Expression implements CompositeNode {
    /** The element expressions of this map entry expression. */
    private List<MapEntryExpr> entries;

    /**
     * Consructs map entry expression from the given parser token image.
     *
     * @param tokenImage the parser token image.
     * @param entries the key/value expressions of this map entry expression, which may be empty.
     */
    public MapExpr(TokenImage tokenImage, List<MapEntryExpr> entries) {
        super(tokenImage);
        this.entries = entries == null ? Collections.emptyList() : entries;
    }

    /**
     * Gets the entry expressions of this map entry expression.
     *
     * @return the key/value expressions of this map entry expression, which may be empty.
     */
    public List<MapEntryExpr> getEntries() {
        return entries == null ? Collections.emptyList() : entries;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitMap(this);
    }

    @Override
    public Type getType() {
        return new ResolvedTypeReferenceOld(getTokenImage(), Map.class); // This could be a Parameterised type now, of the common supertype of elements?
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return Map.class;
    }

    @Override
    public List<Node> getChildNodes() {
        return (List)entries;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MapExpr)) return false;
        if (!super.equals(o)) return false;
        MapExpr nodes = (MapExpr) o;
        return Objects.equals(getEntries(), nodes.getEntries());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEntries());
    }
}
