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

package org.orthodox.universel.execute.objectcreation;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.orthodox.universel.Universal.execute;

public class ConstructorCallTest {
    @Test
    void noArgs() {
        assertThat(execute("import " + org.orthodox.universel.execute.objectcreation.TestClass.class.getName() + " TestClass()"), equalTo(new TestClass()));
    }

    @Test
    void singleArg_exactTypeMatch() {
        assertThat(execute("import " + org.orthodox.universel.execute.objectcreation.TestClass.class.getName() + " TestClass(999)"), equalTo(new TestClass(999)));
    }

    @Test
    void singleArg_boxing() {
        assertThat(execute("import " + org.orthodox.universel.execute.objectcreation.TestClass.class.getName() + " TestClass(999.99)"), equalTo(new TestClass(999.99d)));
    }

    @Test
    void singleArg_unboxing() {
        // TODO: So-called redundant cast below is required because he Java compiler is converting the long to a double.
        // Fimd the narrowing/widening or conversion rule in the JLS.
        assertThat(execute("import java.lang.Long import " + org.orthodox.universel.execute.objectcreation.TestClass.class.getName() + " TestClass(Long(1234L))"), equalTo(new TestClass((long)1234L)));
    }

    @Test
    void multipleArgs() {
        assertThat(execute("import " + org.orthodox.universel.execute.objectcreation.TestClass.class.getName() + " TestClass(1234, 5678L, 789d, 'Hello world')"), equalTo(new TestClass(1234, 5678L, 789d, "Hello world")));
    }
}