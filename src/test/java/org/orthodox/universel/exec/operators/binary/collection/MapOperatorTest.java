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

package org.orthodox.universel.exec.operators.binary.collection;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class MapOperatorTest {
    @Test
    void contains_SingleEntry() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'a': 1 }"), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'b': 2 }"), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'c': 3 }"), is(true));
    }

    @Test
    void contains_doesNotContainSingleEntry() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'x': 1 }"), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'y': 2 }"), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'z': 3 }"), is(false));
    }

    @Test
    void contains_containsCollectionOfKeys() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'a' }"), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'a', 'b' }"), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'a', 'b', 'c' }"), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'c', 'a', 'b' }"), is(true));
    }

    @Test
    void contains_containsEmptySetOfKeys() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains {}"), is(true));
    }

    @Test
    void contains_doesNotContainCollectionOfKeys() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'x' }"), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'x', 'y' }"), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'x', 'y', 'z' }"), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains { 'z', 'x', 'y' }"), is(false));
    }

    @Test
    void contains_containsSingleKey() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains 'a' "), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains 'b' "), is(true));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains 'c' "), is(true));
    }

    @Test
    void contains_doesNotContainSingleKey() {
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains 'x' "), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains 'y' "), is(false));
        assertThat(execute("{ 'a': 1, 'b': 2, 'c': 3} contains 'z' "), is(false));
    }

    @Test
    void in_SingleEntry() {
        assertThat(execute("{ 'a': 1 } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("{ 'b': 2 } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("{ 'c': 3 } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
    }

    @Test
    void in_doesNotContainSingleEntry() {
        assertThat(execute("{ 'x': 1 } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("{ 'y': 2 } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("{ 'z': 3 } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
    }

    @Test
    void in_containsCollectionOfKeys() {
        assertThat(execute("{ 'a' } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("{ 'a', 'b' } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("{ 'a', 'b', 'c' } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("{ 'c', 'a', 'b' } in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
    }

    @Test
    void in_containsEmptySetOfKeys() {
        assertThat(execute("{} in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
    }

    @Test
    void in_doesNotContainCollectionOfKeys() {
        assertThat(execute("{ 'x' } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("{ 'x', 'y' } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("{ 'x', 'y', 'z' } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("{ 'z', 'x', 'y' } in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
    }

    @Test
    void in_containsSingleKey() {
        assertThat(execute("'a' in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("'b' in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
        assertThat(execute("'c' in { 'a': 1, 'b': 2, 'c': 3}"), is(true));
    }

    @Test
    void in_doesNotContainSingleKey() {
        assertThat(execute("'x' in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("'y' in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
        assertThat(execute("'z' in { 'a': 1, 'b': 2, 'c': 3}"), is(false));
    }

}
