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

/**
 * Abstract Syntax Tree (AST) node representation for the 'in' expression (e.g. <code>x in 1..10</code>).
 *
 * @author Gary Watson
 */
public class InExpression extends BinaryExpression {
    /**
     * Constructs an a complete InExpression.
     *
     * @param testExpression the left-hand-side test operand expression of the InExpression.
     * @param inExpression   the right-hand-side in operand expression of the InExpression.
     * @param not            whether this is a 'not in' expression.
     */
    public InExpression(Expression testExpression, Expression inExpression, boolean not) {
        super(not ? Operator.NOT_IN : Operator.IN, testExpression, inExpression);
    }

    /**
     * Gets the test operand expression of the InExpression.
     *
     * @return the left-hand-side test operand expression of the InExpression.
     * @see #getLhsExpression()
     */
    public Expression getTestExpression() {
        return getLhsExpression();
    }

    /**
     * Sets the test operand expression of the InExpression.
     *
     * @param testExpression the left-hand-side test operand expression to set.
     * @see #setLhsExpression(Expression)
     */
    public void setTestExpression(Expression testExpression) {
        setLhsExpression(testExpression);
    }

    /**
     * Gets the right-hand-side operand expression of the InExpression.
     *
     * @return the right-hand-side operand expression of the InExpression.
     * @see #getRhsExpression()
     */
    public Expression getInExpresion() {
        return getRhsExpression();
    }

    /**
     * Sets the right-hand-side in operand expression of the InExpression.
     *
     * @param inExpression the right-hand-side in operand expression of the InExpression to set.
     * @see #setRhsExpression(Expression)
     */
    public void setExpressionList(Expression inExpression) {
        setRhsExpression(inExpression);
    }

    /**
     * @return the not
     */
    public boolean isNot() {
        return getOperator() == Operator.NOT_IN;
    }

    /**
     * @param not the not to set
     */
    public void setNot(boolean not) {
        setOperator(not ? Operator.NOT_IN : Operator.IN);
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        visitor.visitInExpression(this);
        return true;
    }
}
