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
package org.orthodox.universel.cst;

import org.orthodox.universel.ast.AstVisitor;

import java.util.Collections;
import java.util.List;

public class UnaryExpression extends Expression implements CompositeNode {
    protected Operator operator;

    protected Expression expression;

    public UnaryExpression(TokenImage tokenImage, Operator operator, Expression expression) {
        super(tokenImage);
        setExpression(expression);
        setOperator(operator);
    }

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
        if (expression != null) {
            expression.setParent(this);
        }
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }

    public Node accept(AstVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }

    public Class<?> getTypeDescriptor() {
        return expression.getTypeDescriptor();
    }

    @Override
    public List<Node> getChildNodes() {
        return Collections.singletonList(getExpression());
    }
}
