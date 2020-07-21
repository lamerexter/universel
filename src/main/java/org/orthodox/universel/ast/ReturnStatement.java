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

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * The classic return statement on the Abstract Syntax Tree (AST).
 */
public class ReturnStatement extends Statement implements CompositeNode {
    private Node expression;

    /**
     * Constructs an return statement from the given parser token image and expression.
     *
     * @param tokenImage the parser token image backing this expression.
     */
    public ReturnStatement(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * Constructs an return statement from the given parser token image and expression.
     *
     * @param tokenImage the parser token image backing this expression.
     * @param expression the expression value returned by this statement.
     */
    public ReturnStatement(TokenImage tokenImage, Node expression) {
        super(tokenImage);
        this.expression = expression;
    }

    /**
     * Constructs an return statement from the givenexpression.
     *
     * @param expression the expression value returned by this statement.
     */
    public ReturnStatement(Node expression) {
        this(expression.getTokenImage(), expression);
    }

    public Node getExpression() {
        return expression;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnStatement)) return false;
        if (!super.equals(o)) return false;
        ReturnStatement nodes = (ReturnStatement) o;
        return Objects.equals(getExpression(), nodes.getExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpression());
    }

    @Override
    public List<Node> getChildNodes() {
        return expression == null ? emptyList() : singletonList(expression);
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitReturnStatement(this);
    }

    /**
     * Returns the static type of the node. A return expression type is simply the type of its operand.
     *
     * @return the node's type, or null if the type cannot be determined at this time.
     */
    @Override
    public Type getType() {
        return expression == null ? null : expression.getType();
    }

    public Class<?> getTypeDescriptor() {
        return expression == null ? void.class : expression.getTypeDescriptor();
    }

}
