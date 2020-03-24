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

package org.orthodox.universel.parse.operators.binary;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.conditionals.ElvisExpression;
import org.orthodox.universel.cst.literals.StringLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.orthodox.universel.Universal.parse;

public class ElvisOperatorTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = " 'Hello world' ?: \n'That was not null!' ";
        ElvisExpression elvis = parse(ElvisExpression.class, input);
        assertThat(elvis.getTokenImage().getStartLine(), equalTo(1));
        assertThat(elvis.getTokenImage().getStartColumn(), equalTo(2));
        assertThat(elvis.getTokenImage().getEndLine(), equalTo(2));
        assertThat(elvis.getTokenImage().getEndColumn(), equalTo(20));

        assertThat(elvis.getLhsExpression(), instanceOf(StringLiteralExpr.class));
        assertThat(elvis.getRhsExpression(), instanceOf(StringLiteralExpr.class));

        Node lhs = elvis.getLhsExpression();
        assertThat(lhs.getTokenImage().getStartLine(), equalTo(elvis.getTokenImage().getStartLine()));
        assertThat(lhs.getTokenImage().getStartColumn(), equalTo(elvis.getTokenImage().getStartColumn()));
        assertThat(lhs.getTokenImage().getEndLine(), equalTo(1));
        assertThat(lhs.getTokenImage().getEndColumn(), equalTo(14));

        Node rhs = elvis.getRhsExpression();
        assertThat(rhs.getTokenImage().getStartLine(), equalTo(2));
        assertThat(rhs.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(rhs.getTokenImage().getEndLine(), equalTo(elvis.getTokenImage().getEndLine()));
        assertThat(rhs.getTokenImage().getEndColumn(), equalTo(elvis.getTokenImage().getEndColumn()));
    }
}
