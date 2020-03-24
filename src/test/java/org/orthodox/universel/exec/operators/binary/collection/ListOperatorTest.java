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

public class ListOperatorTest {
    @Test
    void contains_list_contains_collection() {
        assertThat(execute("[1,2,3] contains [1]"), is(true));
        assertThat(execute("[1,2,3] contains [2]"), is(true));
        assertThat(execute("[1,2,3] contains [3]"), is(true));

        assertThat(execute("[1,2,3] contains [1, 2]"), is(true));
        assertThat(execute("[1,2,3] contains [1, 3]"), is(true));
        assertThat(execute("[1,2,3] contains [2, 3]"), is(true));

        assertThat(execute("[1,2,3] contains {1}"), is(true));
        assertThat(execute("[1,2,3] contains {2}"), is(true));
        assertThat(execute("[1,2,3] contains {3}"), is(true));

        assertThat(execute("[1,2,3] contains {1, 2}"), is(true));
        assertThat(execute("[1,2,3] contains {1, 3}"), is(true));
        assertThat(execute("[1,2,3] contains {2, 3}"), is(true));

        assertThat(execute("[1,2,3,4,5] contains 1..5"), is(true));

        assertThat(execute("['Hello','World','!'] contains ['World']"), is(true));
    }

    @Test
    void contains_list_doesNotContain_collection() {
        assertThat(execute("[1,2,3] contains [7]"), is(false));
        assertThat(execute("[1,2,3] contains [8]"), is(false));
        assertThat(execute("[1,2,3] contains [9]"), is(false));

        assertThat(execute("[1,2,3] contains [7, 8]"), is(false));
        assertThat(execute("[1,2,3] contains [7, 9]"), is(false));
        assertThat(execute("[1,2,3] contains [8, 9]"), is(false));

        assertThat(execute("[1,2,3] contains {7}"), is(false));
        assertThat(execute("[1,2,3] contains {8}"), is(false));
        assertThat(execute("[1,2,3] contains {9}"), is(false));

        assertThat(execute("[1,2,3] contains {7, 8}"), is(false));
        assertThat(execute("[1,2,3] contains {7, 9}"), is(false));
        assertThat(execute("[1,2,3] contains {8, 9}"), is(false));

        assertThat(execute("[1,2,3,4,5] contains 0..100"), is(false));

        assertThat(execute("['Hello','World','!'] contains ['nope']"), is(false));
    }

    @Test
    void contains_list_contains_singleItem() {
        assertThat(execute("[1,2,3] contains 1"), is(true));
        assertThat(execute("[1,2,3] contains 2"), is(true));
        assertThat(execute("[1,2,3] contains 3"), is(true));

        assertThat(execute("['Hello','World','!'] contains 'World'"), is(true));
    }

    @Test
    void contains_list_doesNotContain_singleItem() {
        assertThat(execute("[1,2,3] contains 7"), is(false));
        assertThat(execute("[1,2,3] contains 8"), is(false));
        assertThat(execute("[1,2,3] contains 9"), is(false));

        assertThat(execute("['Hello','World','!'] contains 'nope'"), is(false));
    }

    @Test
    void in_collection_in_list() {
        assertThat(execute("[1] in [1,2,3]"), is(true));
        assertThat(execute("[2] in [1,2,3]"), is(true));
        assertThat(execute("[3] in [1,2,3]"), is(true));

        assertThat(execute("[1, 2] in [1,2,3]"), is(true));
        assertThat(execute("[1, 3] in [1,2,3]"), is(true));
        assertThat(execute("[2, 3] in [1,2,3]"), is(true));

        assertThat(execute("{1} in {1,2,3}"), is(true));
        assertThat(execute("{2} in {1,2,3}"), is(true));
        assertThat(execute("{3} in {1,2,3}"), is(true));

        assertThat(execute("{1, 2} in {1,2,3}"), is(true));
        assertThat(execute("{1, 2} in {1,2,3}"), is(true));
        assertThat(execute("{1, 2} in {1,2,3}"), is(true));

        assertThat(execute("{1, 2, 50, 100} in 0..100"), is(true));
        assertThat(execute("[1, 2, 50, 100] in 0..100"), is(true));

        assertThat(execute("['World'] in {'Hello','World','!'}"), is(true));
        assertThat(execute("{'World'} in {'Hello','World','!'}"), is(true));
    }

    @Test
    void in_collection_notIn_list() {
        assertThat(execute("[7] in [1,2,3]"), is(false));
        assertThat(execute("[8] in [1,2,3]"), is(false));
        assertThat(execute("[9] in [1,2,3]"), is(false));

        assertThat(execute("[7, 8] in [1,2,3]"), is(false));
        assertThat(execute("[7, 9] in [1,2,3]"), is(false));
        assertThat(execute("[8, 9] in [1,2,3]"), is(false));

        assertThat(execute("{7} in [1,2,3]"), is(false));
        assertThat(execute("{8} in [1,2,3]"), is(false));
        assertThat(execute("{9} in [1,2,3]"), is(false));

        assertThat(execute("{7, 8} in [1,2,3]"), is(false));
        assertThat(execute("{7, 9} in [1,2,3]"), is(false));
        assertThat(execute("{8, 9} in [1,2,3]"), is(false));

        assertThat(execute("[1, 2, 50, 100] in 0..99"), is(false));

        assertThat(execute("['nope'] in ['Hello','World','!']"), is(false));
    }

    @Test
    void in_singleElement_in_list() {
        assertThat(execute("1 in [1,2,3]"), is(true));
        assertThat(execute("2 in [1,2,3]"), is(true));
        assertThat(execute("3 in [1,2,3]"), is(true));

        assertThat(execute("'World' in ['Hello','World','!']"), is(true));
    }

    @Test
    void in_list_doesNotContain_singleElement() {
        assertThat(execute("7 in [1,2,3]"), is(false));
        assertThat(execute("8 in [1,2,3]"), is(false));
        assertThat(execute("9 in [1,2,3]"), is(false));

        assertThat(execute("'nope' in {'Hello','World','!'}"), is(false));
    }
}
