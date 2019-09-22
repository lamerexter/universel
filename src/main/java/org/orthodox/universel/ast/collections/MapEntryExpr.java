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

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;

/**
 * An map entry expression on the Abstract Syntax Tree, consisting of a key and a value expression.</code>
 */
public class MapEntryExpr extends Expression {
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

}
