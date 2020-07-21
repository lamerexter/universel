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

package org.orthodox.universel.parse;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.MethodCall;
import org.orthodox.universel.ast.literals.BooleanLiteralExpr;
import org.orthodox.universel.ast.literals.DecimalIntegerLiteralExpr;
import org.orthodox.universel.ast.literals.StringLiteralExpr;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class MethodCallTest {
    @Test
    public void noArgs() {
        // When
        String input = "\naMethod()\r\n";
        MethodCall methodCall = Universal.parse(MethodCall.class, input);

        // Then
        assertThat(methodCall.getName().getName(), equalTo("aMethod"));
        assertThat(methodCall.getParameters(), equalTo(Collections.emptyList()));
    }

    @Test
    public void withArgs() {
        // When
        String input = "f(1, true, 'hello world')";
        MethodCall methodCall = Universal.parse(MethodCall.class, input);

        // Then
        assertThat(methodCall.getName().getName(), equalTo("f"));
        assertThat(methodCall.getParameters().size(), equalTo(3));
        assertThat(methodCall.getParameters().get(0), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(methodCall.getParameters().get(1), instanceOf(BooleanLiteralExpr.class));
        assertThat(methodCall.getParameters().get(2), instanceOf(StringLiteralExpr.class));
    }
}
