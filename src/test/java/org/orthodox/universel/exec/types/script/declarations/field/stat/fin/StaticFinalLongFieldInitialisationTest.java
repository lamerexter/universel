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
 * Unit tests for simple static final long field assignment scoped at the enclosing script level.
 */
public class StaticFinalLongFieldInitialisationTest extends AbstractStaticFinalFieldInitialisationTest {
    @Test
    void primitiveLongField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "0L", 0L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "123L", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "-1L", -1L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "Long('"+Long.MAX_VALUE+"').longValue()", Long.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "Long('"+Long.MIN_VALUE+"').longValue()", Long.MIN_VALUE);
    }

    @Test
    void primitiveLongField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "Long(123L)", 123L);
    }

    @Test
    void primitiveLongField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "123I", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "123D", 123L);
    }

    @Test
    void primitiveLongField_withWiderPrimitiveWrapperTypeValue_isAssignedNarrowedUnboxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "Long('123')", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "123D", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "123I", 123L);
    }

    @Test
    void primitiveLongField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "100L - 10L", 100L - 10L);
        assertSingleAllAccessStaticFieldIsAssigned("long", long.class, "longField", "5L * -1L", -5L);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Long.*", "Long", Long.class, "longField", "MAX_VALUE", Long.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Long.*", "Long", Long.class, "longField", "-9L + MAX_VALUE - 1", -9L + Long.MAX_VALUE - 1);
    }

    @Test
    void primitiveLongWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long(0L)", 0L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long(123L)", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long(-1L)", -1L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long('"+Long.MAX_VALUE+"')", Long.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long('"+Long.MIN_VALUE+"')", Long.MIN_VALUE);
    }

    @Test
    void primitiveLongWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long('123').longValue()", 123L);
    }

    @Test
    void primitiveLongWrapperField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Double(123.5)", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "123I", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "123D", 123L);
    }

    @Test
    void primitiveLongWrapperField_withWiderPrimitiveTypeValue_isAssignedNarrowedBoxedValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "123I", 123L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "123D", 123L);
    }

    @Test
    void primitiveLongWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long(100L) - Long(10L)", 100L - 10L);
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "Long(5L) * Long(-1L)", -5L);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Long.*", "Long", Long.class, "longWrapperField", "Long(MAX_VALUE)", Long.MAX_VALUE);
        assertSingleAllAccessStaticFieldIsAssigned("import java.lang.Long.*", "Long", Long.class, "longWrapperField", "Long(-9L) + MAX_VALUE - Long(1L)", -9L + Long.MAX_VALUE - 1L);
    }

    @Test
    void primitiveLongWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Long", Long.class, "longWrapperField", "null", null);
    }
}
