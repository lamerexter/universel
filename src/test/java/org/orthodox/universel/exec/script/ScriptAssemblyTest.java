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

package org.orthodox.universel.exec.script;

import org.beanplanet.core.lang.TypeUtil;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.Universal;
import org.orthodox.universel.compiler.CompiledUnit;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.compiler.CompilationDefaults.*;

/**
 * Tests the basic composition of an executable universel script.
 */
public class ScriptAssemblyTest {
    @Test
    void emptyScript_producesClassWithStaticExecutionProfile() {
        CompiledUnit<?> compiled = Universal.compile("", BeanWithProperties.class);

        assertThat(compiled.getCompiledClasses().size(), equalTo(1));
        assertThat(compiled.getCompiledClasses().get(0).getDeclaredMethods().length, equalTo(1));
        assertStaticExecutionMethodPresent(compiled);

        assertThat(execute(compiled, null), nullValue());
    }

    @Test
    void scriptWithStatementsOnly_producesClassWithStaticExecutionProfile() {
        CompiledUnit<?> compiled = Universal.compile("1234", BeanWithProperties.class);

        assertThat(compiled.getCompiledClasses().size(), equalTo(1));
        assertThat(compiled.getCompiledClasses().get(0).getDeclaredMethods().length, equalTo(1));
        assertStaticExecutionMethodPresent(compiled);


        assertThat(execute(compiled, null), equalTo(1234));
    }

    @Test
    void scriptWithNoNonStaticMembers_producesClassWithStaticExecutionProfile() {
        CompiledUnit<?> compiled = Universal.compile("public static long echo(long i) { return i } echo(5678L)", BeanWithProperties.class);

        assertThat(compiled.getCompiledClasses().size(), equalTo(1));
        assertThat(compiled.getCompiledClasses().get(0).getDeclaredMethods().length, equalTo(2));
        assertStaticExecutionMethodPresent(compiled);


        assertThat(execute(compiled, null), equalTo(5678L));
    }

    @Test
    void scriptWithNonStaticMembers_producesClassWithNonStaticExecutionProfile() {
        CompiledUnit<?> compiled = Universal.compile("public long echo(long i) { return i } echo(3456L)", BeanWithProperties.class);

        assertThat(compiled.getCompiledClasses().size(), equalTo(1));
        assertThat(compiled.getCompiledClasses().get(0).getDeclaredMethods().length, equalTo(3));
        assertNonStaticExecutionMethodPresent(compiled);


        assertThat(execute(compiled, null), equalTo(3456L));
    }

    private void assertStaticExecutionMethodPresent(final CompiledUnit<?> compiled) {
        Optional<Method> staticExecutionMethod = TypeUtil.findMethod(compiled.getCompiledClasses().get(0),
                                                                     SCRIPT_EXECUTE_METHOD_MODIFIERS.getModifiers(),
                                                                     SCRIPT_MAIN_METHOD_NAME,
                                                                     null,
                                                                     BeanWithProperties.class
                                                                    );
        assertThat(staticExecutionMethod.isPresent(), is(true));
    }

    private void assertNonStaticExecutionMethodPresent(final CompiledUnit<?> compiled) {
        Optional<Method> staticExecutionMethod = TypeUtil.findMethod(compiled.getCompiledClasses().get(0),
                                                                     SCRIPT_MAIN_METHOD_MODIFIERS.getModifiers(),
                                                                     SCRIPT_MAIN_METHOD_NAME,
                                                                     null,
                                                                     BeanWithProperties.class
                                                                    );
        Optional<Method> nonStaticExecutionMethod = TypeUtil.findMethod(compiled.getCompiledClasses().get(0),
                                                                        SCRIPT_EXECUTE_METHOD_MODIFIERS.getModifiers(),
                                                                        SCRIPT_EXECUTE_METHOD_NAME,
                                                                        null,
                                                                        BeanWithProperties.class
                                                                       );
        assertThat(staticExecutionMethod.isPresent(), is(true));
        assertThat(nonStaticExecutionMethod.isPresent(), is(true));
    }
}
