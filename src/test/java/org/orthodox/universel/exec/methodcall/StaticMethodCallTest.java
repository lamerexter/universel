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

package org.orthodox.universel.exec.methodcall;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.compiler.CompiledUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.compiler.Messages.MethodCall.METHOD_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.MethodCall.METHOD_NOT_FOUND;
import static org.orthodox.universel.exec.methodcall.TestClass.*;

public class StaticMethodCallTest {
    @Test
    void noArgs_resolveFromExplicitImport() {
        assertThat(execute("import " + TestClass.class.getName() + ".noArgs noArgs()"), equalTo(noArgs()));
    }

    @Test
    void oneArg_resolveFromExplicitImport() {
        assertThat(execute("import " + TestClass.class.getName() + ".* oneIntParam(8)"), equalTo(oneIntParam(8)));
    }

    @Test
    void oneArg_boxed_resolveFromImport() {
        assertThat(execute("import " + TestClass.class.getName() + ".* oneIntegerParam(8)"), equalTo(oneIntegerParam(8)));
    }

    @Test
    void oneArg_overloadedMethod_resolveWhenExactlyOneMatchesParameterTypes() {
        assertThat(execute("import " + TestClass.class.getName() + ".* overloadedMethod(9)"), equalTo(overloadedMethod(9)));
        assertThat(execute("import " + TestClass.class.getName() + ".* overloadedMethod(false)"), equalTo(overloadedMethod(false)));
        assertThat(execute("import " + TestClass.class.getName() + ".* overloadedMethod(true)"), equalTo(overloadedMethod(true)));
    }

    @Test
    void oneArg_methodNotFound() {
        CompiledUnit<?> compiled = compile("import " + TestClass.class.getName() + ".* doesNotExist('xyz')");
        assertThat(compiled.getMessages().hasErrorWithCode( METHOD_NOT_FOUND.getCode()), is(true));
    }

    @Test
    void oneArg_overloadedMethod_ambiguousWhenMoreThanOneMatches() {
        CompiledUnit<?> compiled = compile("import " + TestClass.class.getName() + ".* overloadedMethod(null)");
        assertThat(compiled.getMessages().hasErrorWithCode(METHOD_AMBIGUOUS.getCode()), is(true));
    }

    @Test
    void oneArg_ambiguousWhenImportedOnDemandFromMoreThanOneType() {
        CompiledUnit<?> compiled = compile(
                "import " + TestClass.class.getName() + ".*"+
                "import " + AnotherTestClass.class.getName() + ".*"+
                "oneIntParam(9)"
        );
        assertThat(compiled.getMessages().hasErrorWithCode(METHOD_AMBIGUOUS.getCode()), is(true));
    }

    @Test
    void oneArg_resolvesWhenExplicitlyImportedFromType() {
        CompiledUnit<?> compiled = compile(
                "import " + TestClass.class.getName() + ".*"+
                "import " + AnotherTestClass.class.getName() + ".oneIntParam"+
                " "+
                "oneIntParam(9)"
        );
        assertThat(compiled.getMessages().hasErrors(), is(false));
    }

    @Test
    void oneArg_ambiguousWhenExplicitlyImportedFromSeparateTypes() {
        CompiledUnit<?> compiled = compile(
                "import " + TestClass.class.getName() + ".oneIntParam\n"+
                "import " + AnotherTestClass.class.getName() + ".oneIntParam\n"+
                "oneIntParam(9)"
        );
        assertThat(compiled.getMessages().hasErrorWithCode(METHOD_AMBIGUOUS.getCode()), is(true));
    }

    @Test
    void oneArg_ambiguousWhenImportedOnDemandFromSeparateTypes() {
        CompiledUnit<?> compiled = compile(
                "import " + TestClass.class.getName() + ".*\n"+
                "import " + AnotherTestClass.class.getName() + ".*\n"+
                "oneIntParam(9)"
        );
        assertThat(compiled.getMessages().hasErrorWithCode(METHOD_AMBIGUOUS.getCode()), is(true));
    }
}
