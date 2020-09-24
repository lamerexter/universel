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
 * Unit tests for simple static final double field assignment scoped at the enclosing script level.
 */
public class FinalDoubleFieldInitialisationTest extends AbstractFieldInitialisationTest {
    @Test
    void primitiveDoubleField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "0d", 0d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "123d", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "-1d", -1d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "Double('" + Double.MAX_VALUE + "').doubleValue()", Double.MAX_VALUE);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "Double('" + Double.MIN_VALUE + "').doubleValue()", Double.MIN_VALUE);
    }

    @Test
    void primitiveDoubleField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "Double(123d)", 123d);
    }

    @Test
    void primitiveDoubleField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "123I", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "123D", 123d);
    }

    @Test
    void primitiveDoubleField_withWiderPrimitiveWrapperTypeValue_isAssignedNarrowedUnboxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "Double('123')", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "123D", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "123I", 123d);
    }

    @Test
    void primitiveDoubleField_withExpressionValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "100d - 10d", 100d - 10d);
        assertSingleAllAccessFinalFieldIsAssigned("double", double.class, "doubleField", "5d * -1d", -5d);
        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Double.*", "Double", Double.class, "doubleField", "MAX_VALUE", Double.MAX_VALUE);
        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Double.*", "Double", Double.class, "doubleField", "-9d + MAX_VALUE - 1d", -9d + Double.MAX_VALUE - 1d);
    }

    @Test
    void primitiveDoubleWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(0d)", 0d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(123d)", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(-1d)", -1d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(" + Double.MAX_VALUE + ")", Double.MAX_VALUE);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(" + Double.MIN_VALUE + ")", Double.MIN_VALUE);
    }

    @Test
    void primitiveDoubleWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double('123').doubleValue()", 123d);
    }

    @Test
    void primitiveDoubleWrapperField_withWiderTypeValue_isAssignedNarrowedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(123d)", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "123I", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "123D", 123d);
    }

    @Test
    void primitiveDoubleWrapperField_withWiderPrimitiveTypeValue_isAssignedNarrowedBoxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "123", 123d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "123d", 123d);
    }

    @Test
    void primitiveDoubleWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(100d) - Double(10d)", 100d - 10d);
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "Double(5d) * Double(-1d)", -5d);
        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Double.*", "Double", Double.class, "doubleWrapperField", "Double(MAX_VALUE)", Double.MAX_VALUE);
        assertSingleAllAccessFinalFieldIsAssigned("import java.lang.Double.*", "Double", Double.class, "doubleWrapperField", "Double(-9d) + MAX_VALUE - Double(1d)", -9d + Double.MAX_VALUE - 1d);
    }

    @Test
    void primitiveDoubleWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Double", Double.class, "doubleWrapperField", "null", null);
    }
}
