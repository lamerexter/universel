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
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.InstanceofExpression;
import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.literals.DecimalIntegerLiteralExpr;
import org.orthodox.universel.ast.literals.StringLiteralExpr;
import org.orthodox.universel.ast.type.reference.ClassOrInterfaceType;
import org.orthodox.universel.ast.type.reference.PrimitiveType;
import org.orthodox.universel.ast.type.reference.ReferenceType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.orthodox.universel.Universal.parse;

public class InstanceOfParseTest {
    @Test
    public void parsePosition() {
        // When
        String input = " 'Hello world' instanceof String";
        InstanceofExpression ioe = parse(InstanceofExpression.class, input);
        assertThat(ioe.getTokenImage().getStartLine(), equalTo(1));
        assertThat(ioe.getTokenImage().getStartColumn(), equalTo(2));
        assertThat(ioe.getTokenImage().getEndLine(), equalTo(1));
        assertThat(ioe.getTokenImage().getEndColumn(), equalTo(32));

        assertThat(ioe.getLhsExpression(), instanceOf(StringLiteralExpr.class));
        assertThat(ioe.getRhsExpression(), instanceOf(ReferenceType.class));

        ReferenceType referenceType = (ReferenceType) ioe.getRhsExpression();
        assertThat(referenceType.getTokenImage().getStartLine(), equalTo(1));
        assertThat(referenceType.getTokenImage().getStartColumn(), equalTo(27));
        assertThat(referenceType.getTokenImage().getEndLine(), equalTo(1));
        assertThat(referenceType.getTokenImage().getEndColumn(), equalTo(32));
        assertThat(referenceType.getReferredType(), instanceOf(ClassOrInterfaceType.class));
    }

    @Test
    public void arrayClassOrInterfaceReferenceType() {
        // When
        InstanceofExpression ioe = Universal.parse(InstanceofExpression.class, "[1,2,3] instanceof Integer[][][]");

        assertThat(ioe.getTokenImage().getStartLine(), equalTo(1));
        assertThat(ioe.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(ioe.getTokenImage().getEndLine(), equalTo(1));
        assertThat(ioe.getTokenImage().getEndColumn(), equalTo(32));

        assertThat(ioe.getLhsExpression(), instanceOf(ListExpr.class));
        assertThat(ioe.getRhsExpression(), instanceOf(ReferenceType.class));

        ReferenceType referenceType = (ReferenceType) ioe.getRhsExpression();
        assertThat(referenceType.getTokenImage().getStartLine(), equalTo(1));
        assertThat(referenceType.getTokenImage().getStartColumn(), equalTo(20));
        assertThat(referenceType.getTokenImage().getEndLine(), equalTo(1));
        assertThat(referenceType.getTokenImage().getEndColumn(), equalTo(32));
        assertThat(referenceType.getReferredType(), instanceOf(ClassOrInterfaceType.class));
    }

    @Test
    public void arrayPrimitiveReferenceType() {
        // When
        InstanceofExpression ioe = Universal.parse(InstanceofExpression.class, "133 instanceof boolean[][]");

        assertThat(ioe.getTokenImage().getStartLine(), equalTo(1));
        assertThat(ioe.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(ioe.getTokenImage().getEndLine(), equalTo(1));
        assertThat(ioe.getTokenImage().getEndColumn(), equalTo(26));

        assertThat(ioe.getLhsExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(ioe.getRhsExpression(), instanceOf(ReferenceType.class));

        ReferenceType referenceType = (ReferenceType) ioe.getRhsExpression();
        assertThat(referenceType.getTokenImage().getStartLine(), equalTo(1));
        assertThat(referenceType.getTokenImage().getStartColumn(), equalTo(16));
        assertThat(referenceType.getTokenImage().getEndLine(), equalTo(1));
        assertThat(referenceType.getTokenImage().getEndColumn(), equalTo(26));
        assertThat(referenceType.getReferredType(), instanceOf(PrimitiveType.class));
    }
}
