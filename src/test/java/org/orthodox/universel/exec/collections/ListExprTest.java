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

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class ListExprTest {
    @Test
    public void emptyList() {
        assertThat(execute("[]"), equalTo(Collections.emptyList()));
    }

    @Test
    public void singletonList() {
        assertThat(execute("[true]"), equalTo(asList(true)));
    }

    @Test
    public void multiElementList() {
        // Then
        assertThat(execute("[1, 2, true, false, 2.5f, 3.5d, 'Hello World']"),
                   equalTo(asList(1, 2, true, false, 2.5f, 3.5d, "Hello World")));
    }

    public static class Person {
        public String getName() {
            return "Joe Bloggs";
        }
    }

    @Test
    public void stringInterpolation() {
        // Then
        assertThat(execute("[\"Hello there ${name}!\"]", new Person()), equalTo(asList("Hello there Joe Bloggs!")));
    }

}
