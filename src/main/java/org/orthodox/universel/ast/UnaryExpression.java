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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UnaryExpression extends Expression implements CompositeNode {
    protected Operator operator;

    protected Node operand;

    public UnaryExpression(final TokenImage tokenImage, final Operator operator, final Node operand) {
        super(tokenImage);
        this.operator = operator;
        this.operand = operand;
    }

    /**
     * @return the expression
     */
    public Node getOperand() {
        return operand;
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }

    public Node accept(AstVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }

    @Override
    public Type getType() {
        return operand.getType();
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return operand.getTypeDescriptor();
    }

    @Override
    public List<Node> getChildNodes() {
        return Collections.singletonList(getOperand());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UnaryExpression)) return false;
        if (!super.equals(o)) return false;
        UnaryExpression nodes = (UnaryExpression) o;
        return getOperator() == nodes.getOperator() &&
               Objects.equals(getOperand(), nodes.getOperand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOperator(), getOperand());
    }
}
