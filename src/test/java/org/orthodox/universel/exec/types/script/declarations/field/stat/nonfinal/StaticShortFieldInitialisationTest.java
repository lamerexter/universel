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

import org.junit.jupiter.api.Test;
import org.orthodox.universel.exec.types.script.declarations.field.stat.AbstractStaticFieldInitialisationTest;

/**
 * Unit tests for simple static final short field assignment scoped at the enclosing script level.
 */
public class StaticShortFieldInitialisationTest extends AbstractStaticFieldInitialisationTest {
    @Test
    void primitiveShortField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('0').shortValue()", (short)0);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('123').shortValue()", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('-1').shortValue()", (short)-1);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('" + Short.MAX_VALUE + "').shortValue()", Short.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('" + Short.MIN_VALUE + "').shortValue()", Short.MIN_VALUE);
    }

    @Test
    void primitiveShortField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('123')", (short)123);
    }

    @Test
    void primitiveShortField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "123I", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "123D", (short)123);
    }

    @Test
    void primitiveShortField_withWiderPrimitiveWrapperTypeValue_isAssignedNarrowedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('123')", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "123D", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "123I", (short)123);
    }

    @Test
    void primitiveShortField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('100').shortValue() - Short('10')", (short)(100 - 10));
        assertSingleAllAccessStaticFieldIsAssigned("short", short.class, "shortField", "Short('5') * Short('-1').shortValue()", (short)-5);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Short.*", "Short", Short.class, "shortField", "MAX_VALUE", Short.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Short.*", "Short", Short.class, "shortField", "Short('-9') + MAX_VALUE - Short('1')", (short)(-9 + Short.MAX_VALUE - 1));
    }

    @Test
    void primitiveShortWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('0')", (short)0);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('123')", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('-1')", (short)-1);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('" + Short.MAX_VALUE + "')", Short.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('" + Short.MIN_VALUE + "')", Short.MIN_VALUE);
    }

    @Test
    void primitiveShortWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('123').shortValue()", (short)123);
    }

    @Test
    void primitiveShortWrapperField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Double(123.5)", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "123I", (short)123);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "123D", (short)123);
    }

    @Test
    void primitiveShortWrapperField_withWiderPrimitiveTypeValue_isAssignedNarrowedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "123I", (short)123L);
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "123D", (short)123L);
    }

    @Test
    void primitiveShortWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('100') - Short('10')", (short)(100 - 10));
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "Short('5') * Short('-1')", (short)-5);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Short.*", "Short", Short.class, "shortWrapperField", "Short(MAX_VALUE)", Short.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Short.*", "Short", Short.class, "shortWrapperField", "Short('-9') + MAX_VALUE - Short('1')", (short)(-9 + Short.MAX_VALUE - 1));
    }

    @Test
    void primitiveShortWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Short", Short.class, "shortWrapperField", "null", null);
    }
}
