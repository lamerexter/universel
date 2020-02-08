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
package org.orthodox.universel.cst.conditionals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.Objects;

public class ElvisExpression extends Expression {
    protected Expression testExpression;

    protected Expression expression;

    public ElvisExpression(Expression testExpression, Expression expression) {
        super(TokenImage.range(testExpression, expression));
        this.testExpression = testExpression;
        this.expression = expression;
    }

    public Expression getTestExpression() {
        return testExpression;
    }

    public Expression getExpression() {
        return expression;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if ( !(other instanceof ElvisExpression) ) return false;

        ElvisExpression that = (ElvisExpression)other;
        return Objects.equals(this.testExpression, that.testExpression)
                && Objects.equals(this.expression, that.expression);
    }

    public int hashCode() {
        return Objects.hash(testExpression, expression);
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitElvisExpression(this);
    }
}
