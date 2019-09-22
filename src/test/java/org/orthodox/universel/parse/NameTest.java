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
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.Name;
import org.orthodox.universel.ast.Script;
import org.orthodox.universel.ast.literals.NullLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class NameTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n\r\n  theVariable123Abc";
        Script script = Universal.parse(input);

        assertThat(script.getChildNodes().size(), equalTo(1));
        assertThat(script.getChildNodes().get(0), instanceOf(Name.class));

        Name name = (Name) script.getChildNodes().get(0);
        assertThat(name.getTokenImage().getStartLine(), equalTo(3));
        assertThat(name.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(name.getTokenImage().getEndLine(), equalTo(3));
        assertThat(name.getTokenImage().getEndColumn(), equalTo(19));
        assertThat(name.getTokenImage().getImage(), equalTo(input.trim()));
        assertThat(name.getName(), equalTo(input.trim()));
    }
}
