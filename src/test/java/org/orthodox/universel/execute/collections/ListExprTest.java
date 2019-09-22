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

package org.orthodox.universel.execute.collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.literals.BooleanLiteralExpr;
import org.orthodox.universel.ast.literals.DecimalFloatingPointLiteralExpr;
import org.orthodox.universel.ast.literals.DecimalIntegerLiteralExpr;
import org.orthodox.universel.ast.literals.StringLiteralExpr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListExprTest {
    @Test
    public void emptyList() {
        assertThat(Universal.execute("[]"), equalTo(Collections.emptyList()));
    }

    @Test
    public void singletonList() {
        assertThat(Universal.execute("[true]"), equalTo(asList(true)));
    }

    @Test
    public void multiElementList() throws Exception{
        // Then
        assertThat(Universal.execute("[1, 2, true, false, 2.5f, 3.5d, 'Hello World']"),
                   equalTo(asList(1, 2, true, false, 2.5f, 3.5d, "Hello World")));
    }

    @Test
    public void stringInterpolation() throws Exception{
        // Given
        Map<String, Object> binding = new HashMap<>();
        binding.put("person", "Joe Bloggs");

        // Then
        assertThat(Universal.execute("[\"Hello there ${person}!\"]", binding), equalTo(asList("Hello there Joe Bloggs!")));
    }

}
