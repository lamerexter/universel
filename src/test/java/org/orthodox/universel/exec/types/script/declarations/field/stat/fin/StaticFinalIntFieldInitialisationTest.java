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

/**
 * Unit tests for simple static final int field assignment scoped at the enclosing script level.
 */
public class StaticFinalIntFieldInitialisationTest extends AbstractStaticFinalFieldInitialisationTest {
    @Test
    void primitiveIntField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "0", 0);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "123", 123);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "-1", -1);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "Integer('"+Integer.MAX_VALUE+"').intValue()", Integer.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "Integer('"+Integer.MIN_VALUE+"').intValue()", Integer.MIN_VALUE);
    }

    @Test
    void primitiveIntField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "Integer(123)", 123);
    }

    @Test
    void primitiveIntField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "123I", 123);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "123D", 123);
    }

    @Test
    void primitiveIntField_withWiderPrimitiveWrapperTypeValue_isAssignedNarrowedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "Integer('123')", 123);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "123D", 123);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "123I", 123);
    }

    @Test
    void primitiveIntField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "100 - 10", 100 - 10);
        assertSingleAllAccessStaticFieldIsAssigned("int", int.class, "intField", "5 * -1", -5);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Integer.*", "Integer", Integer.class, "intField", "MAX_VALUE", Integer.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Integer.*", "Integer", Integer.class, "intField", "-9 + MAX_VALUE - 1", -9 + Integer.MAX_VALUE - 1);
    }

    @Test
    void primitiveIntWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer(0)", 0);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer(123)", 123);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer(-1)", -1);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer('"+Integer.MAX_VALUE+"')", Integer.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer('"+Integer.MIN_VALUE+"')", Integer.MIN_VALUE);
    }

    @Test
    void primitiveIntWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer('123').intValue()", 123);
    }

    @Test
    void primitiveIntWrapperField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer(123)", 123);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "123I", 123);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "123D", 123);
    }

    @Test
    void primitiveIntWrapperField_withWiderPrimitiveTypeValue_isAssignedNarrowedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "123d", 123);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "123I", 123);
    }

    @Test
    void primitiveIntWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer(100) - Integer(10)", 100 - 10);
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "Integer(5) * Integer(-1)", -5);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Integer.*", "Integer", Integer.class, "intWrapperField", "Integer(MAX_VALUE)", Integer.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Integer.*", "Integer", Integer.class, "intWrapperField", "Integer(-9) + MAX_VALUE - Integer(1)", -9 + Integer.MAX_VALUE - 1);
    }

    @Test
    void primitiveIntWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Integer", Integer.class, "intWrapperField", "null", null);
    }
}
