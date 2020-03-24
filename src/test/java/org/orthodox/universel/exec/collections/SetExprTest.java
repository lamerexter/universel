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

package org.orthodox.universel.exec.collections;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SetExprTest {
    @Test
    public void emptySet() {
        assertThat(Universal.execute("{}"), equalTo(Collections.emptySet()));
    }

    @Test
    public void singletonSet() {
        assertThat(Universal.execute("{true}"), equalTo(new LinkedHashSet<>(asList(true))));
    }

    @Test
    public void multiElementSet() throws Exception{
        // Then
        assertThat(Universal.execute("{1, \"Hello World\", 2, true, false, 2.5f, true, 3.5d, 'Hello World'}"),
                   equalTo(new LinkedHashSet<>(asList(1, "Hello World", 2, true, false, 2.5f, 3.5d))));
    }

    @Test
    public void stringInterpolation() throws Exception{
        // Given
        Map<String, Object> binding = new HashMap<>();
        binding.put("person", "Joe Bloggs");

        // Then
        assertThat(Universal.execute("{\"Hello there ${person}!\"}", binding), equalTo(new LinkedHashSet<>(asList("Hello there Joe Bloggs!"))));
    }

}
