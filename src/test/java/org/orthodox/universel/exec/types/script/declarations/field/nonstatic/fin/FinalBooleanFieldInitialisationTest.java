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

import org.junit.jupiter.api.Test;
import org.orthodox.universel.exec.types.script.declarations.field.nonstatic.AbstractFieldInitialisationTest;

/**
 * Unit tests for simple static final boolean field assignment scoped at the enclosing script level.
 */
public class FinalBooleanFieldInitialisationTest extends AbstractFieldInitialisationTest {
    @Test
    void primitiveBooleanField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("boolean", boolean.class, "booleanField", "true", true);
        assertSingleAllAccessFinalFieldIsAssigned("boolean", boolean.class, "booleanField", "false", false);
    }

    @Test
    void primitiveBooleanField_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("boolean", boolean.class, "booleanField", "Boolean(true)", true);
        assertSingleAllAccessFinalFieldIsAssigned("boolean", boolean.class, "booleanField", "Boolean(false)", false);
    }

    @Test
    void primitiveBooleanField_withExpressionValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("boolean", boolean.class, "booleanField", "1 == 1", true);
        assertSingleAllAccessFinalFieldIsAssigned("boolean", boolean.class, "booleanField", "1 == 2", false);

        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Boolean.*", "boolean", boolean.class, "booleanField", "TRUE", true);
        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Boolean.*", "boolean", boolean.class, "booleanField", "FALSE", false);
    }

    @Test
    void primitiveWrapperBooleanField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "Boolean(true)", true);
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "Boolean(false)", false);
    }

    @Test
    void primitiveWrapperBooleanField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "true", true);
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "false", false);
    }

    @Test
    void primitiveWrapperBooleanField_withExpressionValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "1 == 1", true);
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "1 == 2", false);

        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Boolean.*", "Boolean", Boolean.class, "booleanWrapperField", "TRUE", true);
        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Boolean.*", "Boolean", Boolean.class, "booleanWrapperField", "FALSE", false);
    }

    @Test
    void primitiveWrapperBooleanField_withNullValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Boolean", Boolean.class, "booleanWrapperField", "null", null);
    }
}
