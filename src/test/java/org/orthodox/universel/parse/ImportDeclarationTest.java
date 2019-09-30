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
import org.orthodox.universel.ast.ImportDecl;
import org.orthodox.universel.ast.Name;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ImportDeclarationTest {
    @Test
    public void singleName() {
        // When
        String input = "\n  import\r\n a";
        ImportDecl importDecl = Universal.parse(ImportDecl.class, input);

        assertThat(importDecl.getImports().size(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getStartLine(), equalTo(2));
        assertThat(importDecl.getImports().get(0).getTokenImage().getStartColumn(), equalTo(3));
        assertThat(importDecl.getImports().get(0).getTokenImage().getEndLine(), equalTo(3));
        assertThat(importDecl.getImports().get(0).getTokenImage().getEndColumn(), equalTo(2));

        assertThat(importDecl.getImports().get(0).isOnDemand(), is(false));
        assertThat(importDecl.getImports().get(0).getElements().stream().map(Name::getName).collect(Collectors.toList()),
                   equalTo(asList("a")));
    }

    @Test
    public void multipleNames() {
        // When
        String input = "import a.b.cLongerName";
        ImportDecl importDecl = Universal.parse(ImportDecl.class, input);

        assertThat(importDecl.getImports().size(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getStartLine(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getStartColumn(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getEndLine(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getEndColumn(), equalTo(22));

        assertThat(importDecl.getImports().get(0).isOnDemand(), is(false));
        assertThat(importDecl.getImports().get(0).getElements().stream().map(Name::getName).collect(Collectors.toList()),
                   equalTo(asList("a", "b", "cLongerName")));
    }

    @Test
    public void staticOnDemand() {
        // When
        String input = "import a.b.*";
        ImportDecl importDecl = Universal.parse(ImportDecl.class, input);

        assertThat(importDecl.getImports().size(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getStartLine(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getStartColumn(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getEndLine(), equalTo(1));
        assertThat(importDecl.getImports().get(0).getTokenImage().getEndColumn(), equalTo(12));

        assertThat(importDecl.getImports().get(0).isOnDemand(), is(true));
        assertThat(importDecl.getImports().get(0).getElements().stream().map(Name::getName).collect(Collectors.toList()),
                   equalTo(asList("a", "b")));
    }
}
