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

package org.orthodox.universel.parse.types.script;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.ReturnStatement;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.cst.Modifiers;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.literals.DecimalIntegerLiteralExpr;
import org.orthodox.universel.cst.literals.NullLiteralExpr;
import org.orthodox.universel.cst.type.MethodDeclaration;
import org.orthodox.universel.cst.type.reference.PrimitiveType;
import org.orthodox.universel.cst.type.reference.VoidType;
import org.orthodox.universel.BeanWithProperties;

import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.beanplanet.core.lang.TypeUtil.findMethod;
import static org.beanplanet.core.lang.TypeUtil.invokeStaticMethod;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.parse;
import static org.orthodox.universel.compiler.Messages.MethodDeclaration.MISSING_RETURN;
import static org.orthodox.universel.cst.Modifiers.PUBLIC;
import static org.orthodox.universel.cst.Modifiers.STATIC;

/**
 * Unit tests for method declarations scoped at the enclosing script level.
 */
public class MethodDeclarationTest {
    @Test
    void voidReturnType_noArgs() {
        MethodDeclaration methodDeclaration = parse(MethodDeclaration.class, "\r\n  void helloWorld() {\n}");

        assertThat(methodDeclaration.getTokenImage().getStartLine(), equalTo(2));
        assertThat(methodDeclaration.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(methodDeclaration.getTokenImage().getEndLine(), equalTo(3));
        assertThat(methodDeclaration.getTokenImage().getEndColumn(), equalTo(1));

        assertThat(methodDeclaration.getModifiers(), nullValue());
        assertThat(methodDeclaration.getName(), equalTo("helloWorld"));
        assertThat(methodDeclaration.getParameters().isEmpty(), is(true));

        assertThat(methodDeclaration.getReturnType(), instanceOf(VoidType.class));
        assertThat(methodDeclaration.getReturnType().getTypeDescriptor(), equalTo(void.class));
        assertThat(methodDeclaration.getReturnType().getTokenImage(), equalTo(TokenImage.builder()
                                                                                        .startLine(2)
                                                                                        .startColumn(3)
                                                                                        .endLine(2)
                                                                                        .endColumn(6)
                                                                                        .image("void")
                                                                                        .build()));

        assertThat(methodDeclaration.getBody().isEmpty(), is(true));
    }

    @Test
    void modifiers_primitiveReturnType_args() {
        MethodDeclaration methodDeclaration = parse(MethodDeclaration.class, "\r\n  public static int helloWorld(String greeting, boolean loud) {\n 101 \n    }");

        assertThat(methodDeclaration.getTokenImage().getStartLine(), equalTo(2));
        assertThat(methodDeclaration.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(methodDeclaration.getTokenImage().getEndLine(), equalTo(4));
        assertThat(methodDeclaration.getTokenImage().getEndColumn(), equalTo(5));

        assertThat(methodDeclaration.getReturnType(), instanceOf(PrimitiveType.class));
        assertThat(methodDeclaration.getReturnType().getTypeDescriptor(), equalTo(int.class));
        assertThat(methodDeclaration.getModifiers(), equalTo(new Modifiers(TokenImage.builder()
                                                                                     .startLine(2)
                                                                                     .startColumn(3)
                                                                                     .endLine(2)
                                                                                     .endColumn(15)
                                                                                     .build(),
                                                                           Modifiers.PUBLIC | Modifiers.STATIC)));

        assertThat(methodDeclaration.getBody().getNodes().size(), is(1));
        assertThat(methodDeclaration.getBody().getNodes().get(0), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(methodDeclaration.getReturnType().getTokenImage(), equalTo(TokenImage.builder()
                                                                                        .startLine(2)
                                                                                        .startColumn(17)
                                                                                        .endLine(2)
                                                                                        .endColumn(19)
                                                                                        .image("int")
                                                                                        .build()));
    }

    @Test
    void voidReturnType_EmptyBody_implicitReturnIsAdded() {
        CompiledUnit<?> compiled = compile("void methodWithImplicitReturn() {}");

        assertThat(compiled.hasErrors(), is(false));

        MethodDeclaration methodDeclaration = compiled.getAstNode(n -> n instanceof MethodDeclaration && ((MethodDeclaration)n).getName().equals("methodWithImplicitReturn"));
        assertThat(methodDeclaration.getBody().getNodes().get(0), instanceOf(ReturnStatement.class));

        ReturnStatement implicitReturn = (ReturnStatement) methodDeclaration.getBody().getNodes().get(0);
        assertThat(implicitReturn.getExpression(), nullValue());
    }

    @Test
    void primitiveReturnType_emptyBody_errorIsAdded() {
        CompiledUnit<?> compiled = compile("int methodWithPrimitiveReturnType() {}");

        assertThat(compiled.getMessages().hasErrorWithCode(MISSING_RETURN.getCode()), is(true));
    }

    @Test
    void nonPrimitiveReturnType_emptyBody_implicitReturnOfNullIsAdded() {
        CompiledUnit<?> compiled = compile("Boolean methodWithReferenceReturnType() {}");

        assertThat(compiled.hasErrors(), is(false));

        MethodDeclaration methodDeclaration = compiled.getAstNode(n -> n instanceof MethodDeclaration && ((MethodDeclaration)n).getName().equals("methodWithReferenceReturnType"));
        assertThat(methodDeclaration.getBody().getNodes().get(0), instanceOf(ReturnStatement.class));

        ReturnStatement implicitReturn = (ReturnStatement) methodDeclaration.getBody().getNodes().get(0);
        assertThat(implicitReturn.getExpression(), instanceOf(NullLiteralExpr.class));
    }

    @Test
    void primitiveReturnType_withBody_implicitReturnIsAddedToLastStatement() {
        CompiledUnit<?> compiled = compile("int methodWithBody() { 1234 }");

        assertThat(compiled.hasErrors(), is(false));

        MethodDeclaration methodDeclaration = compiled.getAstNode(n -> n instanceof MethodDeclaration && ((MethodDeclaration)n).getName().equals("methodWithBody"));
        assertThat(methodDeclaration.getBody().getNodes().get(0), instanceOf(ReturnStatement.class));

        ReturnStatement implicitReturn = (ReturnStatement) methodDeclaration.getBody().getNodes().get(0);
        assertThat(implicitReturn.getExpression(), instanceOf(DecimalIntegerLiteralExpr.class));
        assertThat(implicitReturn.getTypeDescriptor(), equalTo(implicitReturn.getExpression().getTypeDescriptor()));
    }

    @Test
    void primitiveReturnCompatible_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static int doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, int.class, int.class);
        assertThat(method.isPresent(), is(true));
        assertThat(invokeStaticMethod(method.get(), 100), equalTo(200));
    }

    @Test
    void primitiveReturnWidened_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static double doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, double.class, int.class);
        assertThat(method.isPresent(), is(true));
        assertThat(invokeStaticMethod(method.get(), 100), equalTo(200d));
    }

    @Test
    void primitiveReturnNarrowed_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static char doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, char.class, int.class);
        assertThat(method.isPresent(), is(true));
        assertThat(invokeStaticMethod(method.get(), 100), equalTo(((char) 200)));
    }

    @Test
    void primitiveWrapperReturnBoxed_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("public static Integer doubleIt(int x) { x * 2 }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "doubleIt", compiledClass, Integer.class, int.class);
        System.out.println("****> "+asList(compiledClass.getDeclaredMethods()));
        assertThat(method.isPresent(), is(true));
        assertThat(invokeStaticMethod(method.get(), 100), equalTo(200));
    }

    @Test
    void primitiveWrapperReturnUnboxed_PrimitiveWrapperArg() {
        CompiledUnit<?> compiled = compile("public static long unboxIt(Long x) { x }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "unboxIt", compiledClass, long.class, Long.class);
        assertThat(method.isPresent(), is(true));
        System.out.println("****> "+method);
        assertThat(invokeStaticMethod(method.get(), 100L), equalTo(100L));
    }

    @Test
    void referenceReturnType_PrimitiveArg() {
        CompiledUnit<?> compiled = compile("import " + BeanWithProperties.class.getName() + "\npublic static BeanWithProperties callIt(int intProperty) { BeanWithProperties(intProperty) }");

        Class<?> compiledClass = compiled.getCompiledClasses().get(0);
        Optional<Method> method = findMethod(PUBLIC | STATIC, "callIt", compiledClass, BeanWithProperties.class, int.class);
        assertThat(method.isPresent(), is(true));
        assertThat(invokeStaticMethod(method.get(), 999), equalTo(new BeanWithProperties(999)));
    }
}
