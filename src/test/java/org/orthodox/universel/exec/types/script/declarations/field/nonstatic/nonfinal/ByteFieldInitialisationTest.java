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

package org.orthodox.universel.exec.types.script.declarations.field.nonstatic.nonfinal;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.exec.types.script.declarations.field.nonstatic.AbstractFieldInitialisationTest;

/**
 * Unit tests for simple static final byte field assignment scoped at the enclosing script level.
 */
public class ByteFieldInitialisationTest extends AbstractFieldInitialisationTest {
    @Test
    void primitiveByteField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Byte('0').byteValue()", (byte)0);
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Byte('123').byteValue()", (byte)123);
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Byte('-1').byteValue()", (byte)-1);
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Byte('" + Byte.MAX_VALUE + "').byteValue()", Byte.MAX_VALUE);
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Byte('" + Byte.MIN_VALUE + "').byteValue()", Byte.MIN_VALUE);
    }

    @Test
    void primitiveByteField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Byte('123')", (byte)123);
    }

    @Test
    void primitiveByteField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "123", (byte)123);
    }

    @Test
    void primitiveByteField_withWiderPrimitiveWrapperTypeValue_isAssignedNarrowedUnboxedValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "Integer('123')", (byte)123);
    }

    @Test
    void primitiveByteField_withExpressionValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "100 - 10", (byte)(100 - 10));
        assertSingleAllAccessFieldIsAssigned("byte", byte.class, "byteField", "5 * -1", (byte)-5);
        assertSingleAllAccessFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteField", "MAX_VALUE", Byte.MAX_VALUE);
        assertSingleAllAccessFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteField", "-9 + MAX_VALUE - 1", (byte)(-9 + Byte.MAX_VALUE - 1));
    }

    @Test
    void primitiveByteWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('0')", (byte)0);
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('123')", (byte)123);
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('-1')", (byte)-1);
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('" + Byte.MAX_VALUE + "')", Byte.MAX_VALUE);
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('" + Byte.MIN_VALUE + "')", Byte.MIN_VALUE);
    }

    @Test
    void primitiveByteWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('123').byteValue()", (byte)123);
    }

    @Test
    void primitiveByteWrapperField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Integer('123')", (byte)123);
    }

    @Test
    void primitiveByteWrapperField_withWiderPrimitiveTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "123", (byte)123);
    }

    @Test
    void primitiveByteWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('100') - Byte('10')", (byte)(100 - 10));
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "Byte('5') * Byte('-1')", (byte)-5);
        assertSingleAllAccessFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteWrapperField", "Byte(MAX_VALUE)", Byte.MAX_VALUE);
        assertSingleAllAccessFieldIsAssigned("import java.lang.Byte.*", "Byte", Byte.class, "byteField", "Byte('-9') + Byte(MAX_VALUE) - Byte('1')", (byte)(-9 + Byte.MAX_VALUE - 1));
    }

    @Test
    void primitiveByteWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessFieldIsAssigned("Byte", Byte.class, "byteWrapperField", "null", null);
    }
}
