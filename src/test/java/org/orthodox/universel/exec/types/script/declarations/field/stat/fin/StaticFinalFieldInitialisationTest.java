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

package org.orthodox.universel.exec.types.script.declarations.field.stat.fin;

import org.beanplanet.messages.domain.Message;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.compiler.CompiledUnit;

import java.lang.reflect.Field;
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
 * Unit tests for simple static field assignment scoped at the enclosing script level.
 */
public class StaticFinalFieldInitialisationTest {
    @Test
    void primitive_byteField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", "0", (byte)0);
        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", "123", (byte)123);
        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", Byte.toString(Byte.MAX_VALUE), Byte.MAX_VALUE);

        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", "-1", (byte)-1);
        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", Byte.toString(Byte.MIN_VALUE), Byte.MIN_VALUE);
    }

    @Test
    void expression_byteField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", "100 - 10", (byte)(100 - 10));
        assertSingleAllAccessStaticFieldIsAssigned("byte", byte.class, "byteField", "5 * -1", (byte)-5);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteField", "MAX_VALUE", Byte.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteField", "-9 + MAX_VALUE - 1", (byte)(-9 + Byte.MAX_VALUE - 1));
    }

    @Test
    void primitive_byteWrapperField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "0", (byte)0);
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "123", (byte)123);
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", Byte.toString(Byte.MAX_VALUE), Byte.MAX_VALUE);

        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "-1", (byte)-1);
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", Byte.toString(Byte.MIN_VALUE), Byte.MIN_VALUE);
    }

    @Test
    void expression_byteWrapperField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('100') - Byte('10')", (byte)(100 - 10));
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('5') * Byte('-1')", (byte)-5);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteWrapperField", "Byte(MAX_VALUE)", Byte.MAX_VALUE);
    }

    @Test
    void null_byteWrapperField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "null", null);
    }

    @Test
    void primitive_booleanField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("boolean", boolean.class, "booleanField", "true", true);
        assertSingleAllAccessStaticFieldIsAssigned("boolean", boolean.class, "booleanField", "false", false);
    }

    @Test
    void primitive_booleanField_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("boolean", boolean.class, "booleanField", "Boolean(true)", true);
        assertSingleAllAccessStaticFieldIsAssigned("boolean", boolean.class, "booleanField", "Boolean(false)", false);
    }

    @Test
    void expression_booleanField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("boolean", boolean.class, "booleanField", "1 == 1", true);
        assertSingleAllAccessStaticFieldIsAssigned("boolean", boolean.class, "booleanField", "1 == 2", false);

        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Boolean.*", "boolean", boolean.class, "booleanField", "TRUE", true);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Boolean.*", "boolean", boolean.class, "booleanField", "FALSE", false);
    }

    @Test
    void primitive_booleanWrapperField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "Boolean(true)", true);
        assertSingleAllAccessStaticFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "Boolean(false)", false);
    }

    @Test
    void primitive_booleanWrapperField_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "true", true);
        assertSingleAllAccessStaticFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "false", false);
    }

    @Test
    void expression_booleanWrapperField() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "1 == 1", true);
        assertSingleAllAccessStaticFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "1 == 2", false);

        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Boolean.*", "Boolean", Boolean.class, "booleanWrapperField", "TRUE", true);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Boolean.*", "Boolean", Boolean.class, "booleanWrapperField", "FALSE", false);
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

    private void assertSingleAllAccessStaticFieldIsAssigned(final String importDec, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSinglePublicStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
        assertSingleProtectedStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
        assertSinglePackageStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
        assertSinglePrivateStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSingleAllAccessStaticFieldIsAssigned(final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned(null, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSinglePublicStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, "public", fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSingleProtectedStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, "protected", fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSinglePackageStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, null, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSinglePrivateStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, "private", fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSingleStaticFieldIsAssigned(final String importDecl, final String access, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        CompiledUnit<?> compiled = compile(format("%s %s static %s %s = %s", (importDecl == null ? "" : importDecl), (access == null ? "" : access), fieldDeclType, fieldName, assignmentExpression));

        Class<?> type = compiled.getCompiledClasses().get(0);
        Optional<Field> field = findField(type, PUBLIC | STATIC, fieldName, fieldType);
        assertThat(findFields(type).size(), equalTo(1));
        assertThat(field.isPresent(), is(true));

        field.get().setAccessible(true);
        assertThat(field.get().get(null), equalTo(expectedValue));
    }

}
