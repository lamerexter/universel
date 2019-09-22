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

package org.orthodox.universel.parse.collections;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.collections.SetExpr;
import org.orthodox.universel.ast.literals.*;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class SetParseTest {
    @Test
    public void emptySet() {
        // When
        SetExpr listExpr = Universal.parse(SetExpr.class, "{\n  }");

        // Then
        assertThat(listExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(listExpr.getTokenImage().getEndColumn(), equalTo(3));
    }

    @Test
    public void singletonSet() {
        // When
        SetExpr listExpr = Universal.parse(SetExpr.class, "{true}");

        // Then
        assertThat(listExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndColumn(), equalTo(6));

        assertThat(listExpr.getElements().size(), equalTo(1));
        assertThat(listExpr.getElements().get(0), instanceOf(BooleanLiteralExpr.class));
    }

    @Test
    public void multiElementSet() {
        // When
        SetExpr listExpr = Universal.parse(SetExpr.class, "{1, \"Hello World\", 2, true, false, 2.5f, true, 3.5d, 'Hello World'}");

        // Then
        assertThat(listExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndColumn(), equalTo(67));

        assertThat(listExpr.getElements().size(), equalTo(9));
        assertThat(listExpr.getElements().stream().map(Object::getClass).collect(Collectors.toList()), equalTo(asList(
                DecimalIntegerLiteralExpr.class,
                InterpolatedStringLiteralExpr.class,
                DecimalIntegerLiteralExpr.class,
                BooleanLiteralExpr.class,
                BooleanLiteralExpr.class,
                DecimalFloatingPointLiteralExpr.class,
                BooleanLiteralExpr.class,
                DecimalFloatingPointLiteralExpr.class,
                StringLiteralExpr.class
        )));
    }

    @Test
    public void setOfSet() {
        // When
        SetExpr listExpr = Universal.parse(SetExpr.class, "{{true}}");

        // Then
        assertThat(listExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(listExpr.getTokenImage().getEndColumn(), equalTo(8));

        assertThat(listExpr.getElements().size(), equalTo(1));
        assertThat(listExpr.getElements().get(0), instanceOf(SetExpr.class));

    }
}