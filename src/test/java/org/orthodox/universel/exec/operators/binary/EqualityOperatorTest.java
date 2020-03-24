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

package org.orthodox.universel.exec.operators.binary;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class EqualityOperatorTest {
    @Test
    public void equalsOperator_areEqual() {
        assertThat(execute("'Hello World!' == 'Hello World!'"), is(true));
        assertThat(execute("import "+getClass().getName()+".aString aString() == aString()"), is(true));
        assertThat(execute("import "+getClass().getName()+".* aString() == sameStringDifferentIdentity()"), is(true));

        assertThat(execute("true == true"), is(true));
        assertThat(execute("false == false"), is(true));

        assertThat(execute("1f == 1f"), is(true));
        assertThat(execute("1d == 1d"), is(true));
        assertThat(execute("1  == 1"), is(true));
        assertThat(execute("1L == 1L"), is(true));
        assertThat(execute("1I == 1I"), is(true));
        assertThat(execute("1D == 1D"), is(true));
    }

    @Test
    public void equalsOperator_areNotEqual() {
        assertThat(execute("'Hello World!' == 'Not the same!'"), is(false));
        assertThat(execute("import "+getClass().getName()+".aString aString() == aString()"), is(true));
        assertThat(execute("import "+getClass().getName()+".* aString() == sameStringDifferentIdentity()"), is(true));

        assertThat(execute("false == true"), is(false));
        assertThat(execute("true == false"), is(false));

        assertThat(execute("1f == 2f"), is(false));
        assertThat(execute("1d == 2d"), is(false));
        assertThat(execute("1  == 2"), is(false));
        assertThat(execute("1L == 2L"), is(false));
        assertThat(execute("1I == 2I"), is(false));
        assertThat(execute("1D == 2D"), is(false ));
    }

    @Test
    public void trebleEqualsOperator_areEqual() {
        assertThat(execute("import "+getClass().getName()+".aString aString() === aString()"), is(true));

        assertThat(execute("true === true"), is(true));
        assertThat(execute("false === false"), is(true));

        assertThat(execute("1f === 1f"), is(true));
        assertThat(execute("1d === 1d"), is(true));
        assertThat(execute("1  === 1"), is(true));
        assertThat(execute("1L === 1L"), is(true));
    }

    @Test
    public void trebleEqualsOperator_areNotEqual() {
        assertThat(execute("import "+getClass().getName()+".* aString() === sameStringDifferentIdentity()"), is(false));

        assertThat(execute("false === true"), is(false));
        assertThat(execute("true === false"), is(false));

        assertThat(execute("1f === 2f"), is(false));
        assertThat(execute("1d === 2d"), is(false));
        assertThat(execute("1  === 2"), is(false));
        assertThat(execute("1L === 2L"), is(false));
    }

    public static String aString() {
        return "Hello World!";
    }

    public static String sameStringDifferentIdentity() {
        return hello()+' '+world();
    }

    public static String hello() {
        return "Hello";
    }
    public static String world() {
        return "World!";
    }

    public static String aDifferentString() {
        return "Not the same value!";
    }
}
