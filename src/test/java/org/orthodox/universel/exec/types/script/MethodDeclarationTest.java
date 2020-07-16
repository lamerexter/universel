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

package org.orthodox.universel.exec.types.script;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.compiler.CompiledUnit;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.beanplanet.core.lang.TypeUtil.findMethod;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.cst.Modifiers.PUBLIC;
import static org.orthodox.universel.cst.Modifiers.STATIC;

/**
 * Unit tests for method declarations scoped at the enclosing script level.
 */
public class MethodDeclarationTest {
    @Test
    void voidReturn_noArgs() {
//        assertThat(execute("public static void aMethod() { }\n aMethod()"), nullValue());

        CompiledUnit<?> compiled = compile("public static void aMethod() { }");

        Optional<Method> method = findMethod(PUBLIC | STATIC, "aMethod", compiled.getCompiledClasses().get(0), void.class, (Class<?>[]) null);
        assertThat(method.isPresent(), is(true));
    }

    @Test
    void primitiveReturnCompatible_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static int doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, int.class, int.class);
        assertThat(method.isPresent(), is(true));
    }

    @Test
    void primitiveReturnWidened_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static double doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, double.class, int.class);
        assertThat(method.isPresent(), is(true));
    }

    @Test
    void primitiveReturnNarrowed_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static char doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, char.class, int.class);
        assertThat(method.isPresent(), is(true));
    }
}
