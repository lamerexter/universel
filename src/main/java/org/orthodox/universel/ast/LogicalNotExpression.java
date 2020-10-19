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

/**
 * A logical not expression on the Abstract Syntax Tree (AST).
 */
public class LogicalNotExpression extends Expression implements CompositeNode {
    /** The operand of the logical not operation. */
    protected Node operand;

    public LogicalNotExpression(final TokenImage tokenImage, final Node operand) {
        super(tokenImage, operand.getType());
        this.operand = operand;
    }

    /**
     * Gets the operand of the logical not operation.
     *
     * @return the operand of the logical not operation.
     */
    public Node getOperand() {
        return operand;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitLogicalNot(this);
    }

    @Override
    public List<Node> getChildNodes() {
        return operand == null ? Collections.emptyList() : Collections.singletonList(operand);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof LogicalNotExpression)) return false;
        if (!super.equals(o)) return false;
        LogicalNotExpression nodes = (LogicalNotExpression) o;
        return Objects.equals(getOperand(), nodes.getOperand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOperand());
    }
}
