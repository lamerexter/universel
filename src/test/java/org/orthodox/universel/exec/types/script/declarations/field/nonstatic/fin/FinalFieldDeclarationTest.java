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

package org.orthodox.universel.exec.types.script.declarations.field.nonstatic.fin;

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
 * Unit tests for simple non-static field declarations scoped at the enclosing script level.
 */
public class FinalFieldDeclarationTest {
    @Test
    void primitive_byteField() {
        assertSingleAllAccessFieldCanBeGenerated("byte", byte.class, "byteField");
    }

    @Test
    void primitiveWrapper_byteField() {
        assertSingleAllAccessFieldCanBeGenerated("Byte", Byte.class, "byteWrapperField");
    }

    @Test
    void primitive_booleanField() {
        assertSingleAllAccessFieldCanBeGenerated("boolean", boolean.class, "booleanField");
    }

    @Test
    void primitiveWrapper_booleanField() {
        assertSingleAllAccessFieldCanBeGenerated("Boolean", Boolean.class, "booleanWrapperField");
    }

    @Test
    void primitive_charField() {
        assertSingleAllAccessFieldCanBeGenerated("char", char.class, "charField");
    }

    @Test
    void primitiveWrapper_charField() {
        assertSingleAllAccessFieldCanBeGenerated("Character", Character.class, "charWrapperField");
    }

    @Test
    void primitive_doubleField() {
        assertSingleAllAccessFieldCanBeGenerated("double", double.class, "doubleField");
    }

    @Test
    void primitiveWrapper_doubleField() {
        assertSingleAllAccessFieldCanBeGenerated("Double", Double.class, "doubleWrapperField");
    }

    @Test
    void primitive_floatField() {
        assertSingleAllAccessFieldCanBeGenerated("float", float.class, "floatField");
    }

    @Test
    void primitiveWrapper_floatField() {
        assertSingleAllAccessFieldCanBeGenerated("Float", Float.class, "floatWrapperField");
    }

    @Test
    void primitive_intField() {
        assertSingleAllAccessFieldCanBeGenerated("int", int.class, "intField");
    }

    @Test
    void primitiveWrapper_intField() {
        assertSingleAllAccessFieldCanBeGenerated("Integer", Integer.class, "intWrapperField");
    }

    @Test
    void primitive_longField() {
        assertSingleAllAccessFieldCanBeGenerated("long", long.class, "longField");
    }

    @Test
    void primitiveWrapper_longField() {
        assertSingleAllAccessFieldCanBeGenerated("Long", Long.class, "longWrapperField");
    }

    @Test
    void primitive_shortField() {
        assertSingleAllAccessFieldCanBeGenerated("short", short.class, "shortField");
    }

    @Test
    void primitiveWrapper_shortField() {
        assertSingleAllAccessFieldCanBeGenerated("Short", Short.class, "shortWrapperField");
    }

    @Test
    void typeField() {
        assertSingleAllAccessFieldCanBeGenerated("Class", Class.class, "typeField");
        assertSingleAllAccessFieldCanBeGenerated("Class<?>", Class.class, "typeField");
    }

    @Test
    void bigDecimalField() {
        assertSingleAllAccessFieldCanBeGenerated("BigDecimal", BigDecimal.class, "bigDecimalField");
    }

    @Test
    void bigIntegerField() {
        assertSingleAllAccessFieldCanBeGenerated("BigInteger", BigInteger.class, "bigIntegerField");
    }

    @Test
    void referenceTypeField() {
        assertSingleAllAccessFieldCanBeGenerated("import " + BeanWithProperties.class.getName(), BeanWithProperties.class.getSimpleName(), BeanWithProperties.class, "referenceTypeField");
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

    private void assertSingleAllAccessFieldCanBeGenerated(final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        assertSingleAllAccessFieldCanBeGenerated(null, fieldDeclType, fieldType, fieldName);
    }

    private void assertSingleAllAccessFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        assertSinglePublicFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
        assertSingleProtectedFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
        assertSinglePrivateFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
        assertSinglePackageFieldCanBeGenerated(importDecl, fieldDeclType, fieldType, fieldName);
    }

    private void assertSinglePublicFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s public final %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), PUBLIC | FINAL, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }

    private void assertSingleProtectedFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s protected final %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), PROTECTED | FINAL, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }

    private void assertSinglePrivateFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s private final %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), PRIVATE | FINAL, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }

    private void assertSinglePackageFieldCanBeGenerated(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName) {
        CompiledUnit<?> compiled = compile(format("%s final %s %s", (importDecl == null ? "" : importDecl), fieldDeclType, fieldName));

        assertThat(findField(compiled.getCompiledClasses().get(0), FINAL, fieldName, fieldType).isPresent(), is(true));
        assertThat(findFields(compiled.getCompiledClasses().get(0)).size(), equalTo(1));
    }
}
