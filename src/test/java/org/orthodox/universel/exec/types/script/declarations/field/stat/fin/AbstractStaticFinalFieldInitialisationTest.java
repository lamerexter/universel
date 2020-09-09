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

import org.junit.jupiter.api.Test;
import org.orthodox.universel.compiler.CompiledUnit;

import java.lang.reflect.Field;
import java.util.Optional;

import static java.lang.String.format;
import static org.beanplanet.core.lang.TypeUtil.findField;
import static org.beanplanet.core.lang.TypeUtil.findFields;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.ast.Modifiers.*;

public abstract class AbstractStaticFinalFieldInitialisationTest {
    protected void assertSingleAllAccessStaticFieldIsAssigned(final String importDec, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSinglePublicStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
        assertSingleProtectedStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
        assertSinglePackageStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
        assertSinglePrivateStaticFieldIsAssigned(importDec, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    protected void assertSingleAllAccessStaticFieldIsAssigned(final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned(null, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSinglePublicStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, "public", PUBLIC, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSingleProtectedStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, "protected", PROTECTED, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSinglePackageStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, null, 0, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSinglePrivateStaticFieldIsAssigned(final String importDecl, final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        assertSingleStaticFieldIsAssigned(importDecl, "private", PRIVATE, fieldDeclType, fieldType, fieldName, assignmentExpression, expectedValue);
    }

    private void assertSingleStaticFieldIsAssigned(final String importDecl, final String access, final int modifiers,  final String fieldDeclType, final Class<?> fieldType, final String fieldName, final String assignmentExpression, Object expectedValue) throws Exception {
        CompiledUnit<?> compiled = compile(format("%s %s static %s %s = %s", (importDecl == null ? "" : importDecl), (access == null ? "" : access), fieldDeclType, fieldName, assignmentExpression));

        assertThat("There are compilation errors", compiled.hasErrors(), is(false));
        Class<?> type = compiled.getCompiledClasses().get(0);
        Optional<Field> field = findField(type, modifiers | STATIC, fieldName, fieldType);
        assertThat(findFields(type).size(), equalTo(1));
        assertThat(field.isPresent(), is(true));

        field.get().setAccessible(true);
        assertThat(field.get().get(null), equalTo(expectedValue));
    }

}
