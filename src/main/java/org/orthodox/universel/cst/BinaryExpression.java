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
package org.orthodox.universel.cst;

import java.util.Objects;

public class BinaryExpression extends Expression {
    protected Operator operator;

    protected Expression lhsExpression;

    protected Expression rhsExpression;

    public BinaryExpression(Operator operator, Expression lhsExpr, Expression rhsExpr) {
        super(TokenImage.range(lhsExpr, rhsExpr));
        setLhsExpression(lhsExpr);
        setRhsExpression(rhsExpr);
        setOperator(operator);
    }

    public Expression getLhsExpression() {
        return lhsExpression;
    }

    public void setLhsExpression(Expression lhsExpression) {
        this.lhsExpression = lhsExpression;
    }

    public Expression getRhsExpression() {
        return rhsExpression;
    }

    public void setRhsExpression(Expression rhsExpression) {
        this.rhsExpression = rhsExpression;
    }

    public Operator getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BinaryExpression))
            return false;
        BinaryExpression that = (BinaryExpression) o;
        return operator == that.operator
               && Objects.equals(lhsExpression, that.lhsExpression)
               && Objects.equals(rhsExpression, that.rhsExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, lhsExpression, rhsExpression);
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        visitor.visitBinaryExpression(this);
        return true;
    }
}
