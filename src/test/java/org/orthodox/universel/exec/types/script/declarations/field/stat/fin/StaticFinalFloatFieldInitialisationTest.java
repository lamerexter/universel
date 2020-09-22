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
import org.orthodox.universel.exec.types.script.declarations.field.stat.AbstractStaticFieldInitialisationTest;

/**
 * Unit tests for simple static final float field assignment scoped at the enclosing script level.
 */
public class StaticFinalFloatFieldInitialisationTest extends AbstractStaticFieldInitialisationTest {
    @Test
    void primitiveFloatField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "0f", 0f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "123f", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "-1f", -1f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "Float('" + Float.MAX_VALUE + "').floatValue()", Float.MAX_VALUE);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "Float('" + Float.MIN_VALUE + "').floatValue()", Float.MIN_VALUE);
    }

    @Test
    void primitiveFloatField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "Float('123')", 123f);
    }

    @Test
    void primitiveFloatField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "123d", 123f);
    }

    @Test
    void primitiveFloatField_withWiderPrimitiveWrapperTypeValue_isAssignedNarrowedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "Double('123')", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "123D", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "123I", 123f);
    }

    @Test
    void primitiveFloatField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "100f - 10f", 100f - 10f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("float", float.class, "floatField", "5f * -1f", -5f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("import java.lang.Float.*", "Float", Float.class, "floatField", "MAX_VALUE", Float.MAX_VALUE);
        assertSingleAllAccessStaticFinalFieldIsAssigned("import java.lang.Float.*", "Float", Float.class, "floatField", "-9f + MAX_VALUE - 1f", -9f + Float.MAX_VALUE - 1f);
    }

    @Test
    void primitiveFloatWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(0f)", 0f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(123f)", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(-1f)", -1f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(" + Float.MAX_VALUE + ")", Float.MAX_VALUE);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(" + Float.MIN_VALUE + ")", Float.MIN_VALUE);
    }

    @Test
    void primitiveFloatWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float('123').floatValue()", 123f);
    }

    @Test
    void primitiveFloatWrapperField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Double(123d)", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "123I", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "123D", 123f);
    }

    @Test
    void primitiveFloatWrapperField_withWiderPrimitiveTypeValue_isAssignedNarrowedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "123", 123f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "123d", 123f);
    }

    @Test
    void primitiveFloatWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(100f) - Float(10f)", 100f - 10f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "Float(5f) * Float(-1f)", -5f);
        assertSingleAllAccessStaticFinalFieldIsAssigned("import java.lang.Float.*", "Float", Float.class, "floatWrapperField", "Float(MAX_VALUE)", Float.MAX_VALUE);
        assertSingleAllAccessStaticFinalFieldIsAssigned("import java.lang.Float.*", "Float", Float.class, "floatWrapperField", "Float(-9f) + MAX_VALUE - Float(1f)", -9f + Float.MAX_VALUE - 1f);
    }

    @Test
    void primitiveFloatWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessStaticFinalFieldIsAssigned("Float", Float.class, "floatWrapperField", "null", null);
    }
}
