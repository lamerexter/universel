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

package org.orthodox.universel.exec.operators.binary.range;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

class CharacterRangeTest {
    @Test
    void rangeInclusive_contains_rangeInRange() {
        assertThat(execute("'a'..'z' contains 'a'..'z'"), is(true));
        assertThat(execute("'a'..'z' contains 'b'..'f'"), is(true));
        assertThat(execute("'a'..'z' contains 'g'..'g'"), is(true));

        assertThat(execute("'a'..'z' contains 'z'..'a'"), is(true));
        assertThat(execute("'a'..'z' contains 'f'..'b'"), is(true));
    }

    @Test
    void rangeInclusive_contains_singleStringCharacter() {
        assertThat(execute("'a'..'z' contains 'a'"), is(true));

        assertThat(execute("'a'..'z' contains '&'"), is(false));
    }

    @Test
    void rangeInclusive_contains_emptyCharacterSequence() {
        // equivalent to {} in a range ...
        assertThat(execute("'a'..'z' contains ''"), is(true));
    }

    @Test
    void rangeInclusive_contains_multipleStringCharacters() {
        assertThat(execute("'a'..'z' contains 'helloworld'"), is(true));

        assertThat(execute("'a'..'z' contains 'helloworld!'"), is(false));
        assertThat(execute("'a'..'z' contains 'hello world'"), is(false));
        assertThat(execute("'a'..'z' contains 'Xhelloworld'"), is(false));
    }

    @Test
    void rangeInclusive_contains_collectionOfCharacterSequences() {
//        assertThat(execute("'a'..'z' contains null"), is(true));
        assertThat(execute("'a'..'z' contains []"), is(true));
        assertThat(execute("'a'..'z' contains ['a', 's', 'z']"), is(true));
        assertThat(execute("'a'..'z' contains ['abc', 'sfg', 'zhj']"), is(true));

        assertThat(execute("'a'..'z' contains {'a', 's', 'z'}"), is(true));
        assertThat(execute("'a'..'z' contains {'abc', 'sfg', 'zhj'}"), is(true));

        assertThat(execute("'a'..'z' contains ['a', 's', 'X', 'z']"), is(false));
        assertThat(execute("'a'..'z' contains ['abc', 'sfg', 'abX', 'zhj']"), is(false));

        assertThat(execute("'a'..'z' contains {'a', 's', 'X', 'z'}"), is(false));
        assertThat(execute("'a'..'z' contains {'abc', 'sfg', 'abX', 'zhj'}"), is(false));
    }

    @Test
    void rangeInclusive_in_rangeInRange() {
        assertThat(execute("'a'..'z' in 'a'..'z'"), is(true));
        assertThat(execute("'b'..'f' in 'a'..'z'"), is(true));
        assertThat(execute("'g'..'g' in 'a'..'z'"), is(true));

        assertThat(execute("'z'..'a' in 'a'..'z'"), is(true));
        assertThat(execute("'f'..'b' in 'a'..'z'"), is(true));
    }

    @Test
    void rangeInclusive_in_emptyCharacterSequence() {
        // equivalent to {} in a range ...
        assertThat(execute("'' in 'a'..'z'"), is(true));
    }

    @Test
    void rangeInclusive_in_singleStringCharacter() {
        assertThat(execute("'a' in 'a'..'z'"), is(true));

        assertThat(execute("'&' in 'a'..'z'"), is(false));
    }

    @Test
    void rangeInclusive_in_multipleStringCharacters() {
        assertThat(execute("'helloworld' in 'a'..'z'"), is(true));

        assertThat(execute("'helloworld!' in 'a'..'z'"), is(false));
        assertThat(execute("'hello world' in 'a'..'z'"), is(false));
        assertThat(execute("'Xhelloworld' in 'a'..'z'"), is(false));
    }

    @Test
    void rangeInclusive_in_collectionOfCharacterSequences() {
        assertThat(execute("[] in 'a'..'z'"), is(true));
        assertThat(execute("['a', 's', 'z'] in 'a'..'z'"), is(true));
        assertThat(execute("['abc', 'sfg', 'zhj'] in 'a'..'z'"), is(true));

        assertThat(execute("{} in 'a'..'z'"), is(true));
        assertThat(execute("{'a', 's', 'z'} in 'a'..'z'"), is(true));
        assertThat(execute("{'abc', 'sfg', 'zhj'} in 'a'..'z'"), is(true));

        assertThat(execute("null in 'a'..'z'"), is(true));
        assertThat(execute("['a', 's', 'X', 'z'] in 'a'..'z'"), is(false));
        assertThat(execute("['abc', 'sfg', 'abX', 'zhj'] in 'a'..'z'"), is(false));

        assertThat(execute("{'a', 's', 'X', 'z'} in 'a'..'z'"), is(false));
        assertThat(execute("{'abc', 'sfg', 'abX', 'zhj'} in 'a'..'z'"), is(false));
    }
}