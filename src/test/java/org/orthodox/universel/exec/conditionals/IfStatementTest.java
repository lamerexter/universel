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

package org.orthodox.universel.exec.conditionals;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class IfStatementTest {
    @Test
    void emptyThenBlock_ImplicitlyReturnsNull() {
        assertThat(execute("if ( true ) { }"), nullValue());
    }

    @Test
    void then_returnsLastStatementValue() {
        assertThat(execute("if ( true ) { true }"), equalTo(true));
        assertThat(execute("if ( true ) true"), equalTo(true));

        assertThat(execute("if ( true ) { null }"), nullValue());
        assertThat(execute("if ( true ) null"), nullValue());

        assertThat(execute("if ( true ) { 123 }"), equalTo(123));
        assertThat(execute("if ( true ) 123"), equalTo(123));

        assertThat(execute("if ( true ) { 999 456 }"), equalTo(456));
    }

    @Test
    public void iftest_explicitlyConvertedToBoolean() {
        assertThat(execute("if ( 'true' ) 'thenExecuted' else 'elseExecuted'"), equalTo("thenExecuted"));
        assertThat(execute("if ( 'false' ) 'thenExecuted' else 'elseExecuted'"), equalTo("elseExecuted"));
    }

    @Test
    public void elseIf_emptyBlock_ImplicitlyReturnsNull() {
        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) { }"), nullValue());
    }

    @Test
    public void elseIf_emptyBlock_returnsLastStatementValue() {
        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) { true }"), equalTo(true));
        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) true"), equalTo(true));

        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) { null }"), nullValue());
        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) null"), nullValue());

        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) { 123 }"), equalTo(123));
        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) 123"), equalTo(123));

        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( true ) { 999 456 }"), equalTo(456));
    }

    @Test
    public void secondElseIf_executedWhenPrecedingTestsFalse() {
        assertThat(execute("if ( false ) { 'thenExecuted' } else if ( false ) { 'firstElseIf' } else if (true) { 'secondElseIf' }"), equalTo("secondElseIf"));
    }

    @Test
    public void else_emptyBlock_ImplicitlyReturnsNull() {
        assertThat(execute("if ( false ) { 'thenExecuted' } else {}"), nullValue());
    }

    @Test
    public void else_returnsLastStatementValue() {
        assertThat(execute("if ( false ) { 'thenExecuted' } else { true }"), equalTo(true));
        assertThat(execute("if ( false ) { 'thenExecuted' } else true"), equalTo(true));

        assertThat(execute("if ( false ) { 'thenExecuted' } else { null }"), nullValue());
        assertThat(execute("if ( false ) { 'thenExecuted' } else null"), nullValue());

        assertThat(execute("if ( false ) { 'thenExecuted' } else { 123 }"), equalTo(123));
        assertThat(execute("if ( false ) { 'thenExecuted' } else 123"), equalTo(123));

        assertThat(execute("if ( false ) { 'thenExecuted' } else { 999 456 }"), equalTo(456));
    }

    @Test
    public void else_executedWhenPrecedingTestsFalse() {
        assertThat(execute("if ( false ) 'thenExecuted' else if ( false ) 'firstElseIf' else 'elseExecuted'"), equalTo("elseExecuted"));
    }
}
