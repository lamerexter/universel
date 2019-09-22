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
import org.orthodox.universel.ast.collections.MapEntryExpr;
import org.orthodox.universel.ast.collections.MapExpr;
import org.orthodox.universel.ast.literals.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class MapParseTest {
    @Test
    public void emptyMap() {
        // When
        MapExpr mapExpr = Universal.parse(MapExpr.class, "{:}");

        // Then
        assertThat(mapExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndColumn(), equalTo(3));
    }

    @Test
    public void singletonMap() {
        // When
        MapExpr mapExpr = Universal.parse(MapExpr.class, "{ 'theKey' : true}");

        // Then
        assertThat(mapExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndColumn(), equalTo(18));

        assertThat(mapExpr.getEntries().size(), equalTo(1));
        assertThat(mapExpr.getEntries().get(0).getKeyExpression(), instanceOf(StringLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(0).getValueExpression(), instanceOf(BooleanLiteralExpr.class));
    }

    @Test
    public void multiElementMap() {
        // When
        MapExpr mapExpr = Universal.parse(MapExpr.class, "{1 : 'one', \"Hello World\" : 2, 3 : 33I, true : 4, false : 5, 2.5f : 6, 7 : 7, 3.5D : 4.5d, 'Hello World' : 'Hello World!'}");

        // Then
        assertThat(mapExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndColumn(), equalTo(122));

        assertThat(mapExpr.getEntries().size(), equalTo(9));

        assertThat(mapExpr.getEntries().get(0).getKeyExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(0).getKeyExpression().getTokenImage().getImage(), equalTo("1"));
        assertThat(mapExpr.getEntries().get(0).getValueExpression(), instanceOf(StringLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(0).getValueExpression().getTokenImage().getImage(), equalTo("'one'"));

        assertThat(mapExpr.getEntries().get(1).getKeyExpression(), instanceOf(InterpolatedStringLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(1).getKeyExpression().getTokenImage().getImage(), equalTo("\"Hello World\""));
        assertThat(mapExpr.getEntries().get(1).getValueExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(1).getValueExpression().getTokenImage().getImage(), equalTo("2"));

        assertThat(mapExpr.getEntries().get(2).getKeyExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(2).getKeyExpression().getTokenImage().getImage(), equalTo("3"));
        assertThat(mapExpr.getEntries().get(2).getValueExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(2).getValueExpression().getTokenImage().getImage(), equalTo("33I"));

        assertThat(mapExpr.getEntries().get(3).getKeyExpression(), instanceOf(BooleanLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(3).getKeyExpression().getTokenImage().getImage(), equalTo("true"));
        assertThat(mapExpr.getEntries().get(3).getValueExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(3).getValueExpression().getTokenImage().getImage(), equalTo("4"));

        assertThat(mapExpr.getEntries().get(4).getKeyExpression(), instanceOf(BooleanLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(4).getKeyExpression().getTokenImage().getImage(), equalTo("false"));
        assertThat(mapExpr.getEntries().get(4).getValueExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(4).getValueExpression().getTokenImage().getImage(), equalTo("5"));

        assertThat(mapExpr.getEntries().get(5).getKeyExpression(), instanceOf(DecimalFloatingPointLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(5).getKeyExpression().getTokenImage().getImage(), equalTo("2.5f"));
        assertThat(mapExpr.getEntries().get(5).getValueExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(5).getValueExpression().getTokenImage().getImage(), equalTo("6"));

        assertThat(mapExpr.getEntries().get(6).getKeyExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(6).getKeyExpression().getTokenImage().getImage(), equalTo("7"));
        assertThat(mapExpr.getEntries().get(6).getValueExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(6).getValueExpression().getTokenImage().getImage(), equalTo("7"));

        assertThat(mapExpr.getEntries().get(7).getKeyExpression(), instanceOf(DecimalFloatingPointLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(7).getKeyExpression().getTokenImage().getImage(), equalTo("3.5D"));
        assertThat(mapExpr.getEntries().get(7).getValueExpression(), instanceOf(DecimalFloatingPointLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(7).getValueExpression().getTokenImage().getImage(), equalTo("4.5d"));

        assertThat(mapExpr.getEntries().get(8).getKeyExpression(), instanceOf(StringLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(8).getKeyExpression().getTokenImage().getImage(), equalTo("'Hello World'"));
        assertThat(mapExpr.getEntries().get(8).getValueExpression(), instanceOf(StringLiteralExpr.class));
        assertThat(mapExpr.getEntries().get(8).getValueExpression().getTokenImage().getImage(), equalTo("'Hello World!'"));
    }

    @Test
    public void mapOfMap() {
        // When
        MapExpr mapExpr = Universal.parse(MapExpr.class, "{1 : {2 : 'inner'}}");

        // Then
        assertThat(mapExpr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(mapExpr.getTokenImage().getEndColumn(), equalTo(19));

        assertThat(mapExpr.getEntries().size(), equalTo(1));
        assertThat(mapExpr.getEntries().get(0), instanceOf(MapEntryExpr.class));
    }
}