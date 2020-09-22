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

package org.orthodox.universel.exec.types.script.declarations.field.stat.nonfinal;

import org.beanplanet.messages.domain.Message;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.compiler.CompiledUnit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static java.lang.String.format;
import static org.beanplanet.core.lang.TypeUtil.findField;
import static org.beanplanet.core.lang.TypeUtil.findFields;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.ast.Modifiers.*;
import static org.orthodox.universel.compiler.Messages.FieldDeclaration.VOID_TYPE;
import static org.orthodox.universel.compiler.Messages.TYPE.TYPE_NOT_FOUND;

/**
 * Unit tests for simple static field declarations scoped at the enclosing script level.
 */
public class StaticFieldDeclarationTest {
    @Test
    void primitive_byteField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("byte", byte.class, "byteField");
    }

    @Test
    void primitiveWrapper_byteField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Byte", Byte.class, "byteWrapperField");
    }

    @Test
    void primitive_booleanField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("boolean", boolean.class, "booleanField");
    }

    @Test
    void primitiveWrapper_booleanField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Boolean", Boolean.class, "booleanWrapperField");
    }

    @Test
    void primitive_charField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("char", char.class, "charField");
    }

    @Test
    void primitiveWrapper_charField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Character", Character.class, "charWrapperField");
    }

    @Test
    void primitive_doubleField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("double", double.class, "doubleField");
    }

    @Test
    void primitiveWrapper_doubleField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Double", Double.class, "doubleWrapperField");
    }

    @Test
    void primitive_floatField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("float", float.class, "floatField");
    }

    @Test
    void primitiveWrapper_floatField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Float", Float.class, "floatWrapperField");
    }

    @Test
    void primitive_intField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("int", int.class, "intField");
    }

    @Test
    void primitiveWrapper_intField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Integer", Integer.class, "intWrapperField");
    }

    @Test
    void primitive_longField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("long", long.class, "longField");
    }

    @Test
    void primitiveWrapper_longField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Long", Long.class, "longWrapperField");
    }

    @Test
    void primitive_shortField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("short", short.class, "shortField");
    }

    @Test
    void primitiveWrapper_shortField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Short", Short.class, "shortWrapperField");
    }

    @Test
    void typeField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("Class", Class.class, "typeField");
        assertSingleAllAccessStaticFieldCanBeGenerated("Class<?>", Class.class, "typeField");
    }

    @Test
    void bigDecimalField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("BigDecimal", BigDecimal.class, "bigDecimalField");
    }

    @Test
    void bigIntegerField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("BigInteger", BigInteger.class, "bigIntegerField");
    }

    @Test
    void referenceTypeField() {
        assertSingleAllAccessStaticFieldCanBeGenerated("import " + BeanWithProperties.class.getName(), BeanWithProperties.class.getSimpleName(), BeanWithProperties.class, "referenceTypeField");
    }

    @Test
    void unresolvedTypeIsReported() {
        assertErrorIsPresent("\npublic static UnknownType unknownTypeField", TYPE_NOT_FOUND.getCode(), 2, 15, 2, 25);
    }

    @Test
    void primitive_voidField_IsReported() {
        assertErrorIsPresent("public static void illegalVoidField", VOID_TYPE.getCode(), 1, 15, 1, 18);
    }

    @Test
    void primitiveWrapper_voidField_IsReported() {
        assertErrorIsPresent("public static Void illegalVoidField", VOID_TYPE.getCode(), 1, 15, 1, 18);
    }

    void assertErrorIsPresent(String script, String code, int startLine, int startColumn, int endLine, int endColumn) {
        CompiledUnit<?> compiled = compile(script);

        assertThat(compiled.hasErrors(), is(true));
        Optional<Message> errorMessage = compiled.getMessages().findErrorWithCode(code);
        assertThat(errorMessage.isPresent(), is(true));
        assertThat(errorMessage.get().getRelatedObject(), instanceOf(Node.class));

        Node nodeInError = errorMessage.get().getRelatedObject();
        assertThat(nodeInError.getTokenImage().getStartLine(), equalTo(startLine));
        assertThat(nodeInError.getTokenImage().getStartColumn(), equalTo(startColumn));
        assertThat(nodeInError.getTokenImage().getEndLine(), equalTo(endLine));
        assertThat(nodeInError.getTokenImage().getEndColumn(), equalTo(endColumn));
    }

    private void assertSingleAllAccessStaticFieldCanBeGenerated(final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        assertSingleAllAccessStaticFieldCanBeGenerated(null, fieldDeclType, fieldType, fieldName);
    }

    private void assertSingleAllAccessStaticFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        assertSinglePublicStaticFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
        assertSingleProtectedStaticFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
        assertSinglePrivateStaticFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
        assertSinglePackageStaticFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
    }

    private void assertSinglePublicStaticFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s public static %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), PUBLIC | STATIC, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }

    private void assertSingleProtectedStaticFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s protected static %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), PROTECTED | STATIC, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }

    private void assertSinglePrivateStaticFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s private static %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), PRIVATE | STATIC, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }

    private void assertSinglePackageStaticFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s static %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), STATIC, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }
}
