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

import java.util.List;
import java.util.Objects;

/**
 * The assignment expression on the Abstract Syntax Tree (AST).
 */
public class AssignmentExpression extends Expression implements CompositeNode {
    /** The l-value expression on the left-hand-side of the assignment. */
    private final Node lhsExpression;
    /** The assignment operator. */
    private final Operator operator;
    /** The r-value expression on the right-hand-side of the assignment. */
    private final Node rhsExpression;

    public AssignmentExpression(final Node lhsExpression, final Operator operator, final Node rhsExpression) {
        super(TokenImage.range(lhsExpression, rhsExpression));
        this.lhsExpression = lhsExpression;
        this.operator = operator;
        this.rhsExpression = rhsExpression;
    }

    /**
     * Gets the l-value expression on the left-hand-side of the assignment.
     *
     * @return the expression appearing on the left-hand-side of this assignment expression.
     */
    public Node getLhsExpression() {
        return lhsExpression;
    }

    /**
     * Gets the assignment operator.
     *
     * @return the assignment operator.
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Gets the r-value expression on the right-hand-side of the assignment.
     *
     * @return the expression appearing on the right-hand-side of this assignment expression.
     */
    public Node getRhsExpression() {
        return rhsExpression;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignmentExpression)) return false;
        if (!super.equals(o)) return false;
        AssignmentExpression nodes = (AssignmentExpression) o;
        return Objects.equals(getLhsExpression(), nodes.getLhsExpression()) &&
               getOperator() == nodes.getOperator() &&
               Objects.equals(getRhsExpression(), nodes.getRhsExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLhsExpression(), getOperator(), getRhsExpression());
    }

    /**
     * Gets a list of the children of this node.
     *
     * @return the children of this node, which may be empty but not null.
     */
    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                          .addNotNull(lhsExpression)
                          .addNotNull(rhsExpression)
                          .build();
    }
}
