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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.beanplanet.core.util.ArrayUtil.asListOfNotNull;

/**
 * An map entry expression on the Abstract Syntax Tree, consisting of a key and a value expression.</code>
 */
public class MapEntryExpr extends Expression implements CompositeNode {
    /** The key expression of this map entry expression. */
    private Node keyExpression;
    /** The value expression of this map entry expression. */
    private Node valueExpression;

    /**
     * Consructs map entry expression from the given parser token image.
     *
     * @param keyExpression the key expression of this map entry expression.
     * @param valueExpression the value expression of this map entry expression.
     */
    public MapEntryExpr(TokenImage tokenImage, Node keyExpression, Node valueExpression) {
        super(tokenImage);
        this.keyExpression = keyExpression;
        this.valueExpression = valueExpression;
    }

    /**
     * Gets the key expression of this map entry expression.
     *
     * @return the key expression of this map entry expression.
     */
    public Node getKeyExpression() {
        return keyExpression;
    }

    /**
     * Gets the value expression of this map entry expression.
     *
     * @return the value expression of this map entry expression.
     */
    public Node getValueExpression() {
        return valueExpression;
    }

    @Override
    public Type getType() {
        return new ResolvedTypeReferenceOld(getTokenImage(), Map.Entry.class); // This could be a Parameterised type now, of the common supertype of elements?
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return Map.Entry.class;
    }

    @Override
    public List<Node> getChildNodes() {
        return asListOfNotNull(keyExpression, valueExpression);
    }

    @Override
    public MapEntryExpr accept(UniversalCodeVisitor visitor) {
        return visitor.visitMapEntry(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MapEntryExpr)) return false;
        if (!super.equals(o)) return false;
        MapEntryExpr nodes = (MapEntryExpr) o;
        return Objects.equals(getKeyExpression(), nodes.getKeyExpression()) &&
               Objects.equals(getValueExpression(), nodes.getValueExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKeyExpression(), getValueExpression());
    }
}
