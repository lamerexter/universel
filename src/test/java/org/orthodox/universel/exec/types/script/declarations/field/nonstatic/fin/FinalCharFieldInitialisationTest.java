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
 * Unit tests for simple final char field assignment scoped at the enclosing script level.
 */
public class FinalCharFieldInitialisationTest extends AbstractFieldInitialisationTest {
    @Test
    void  primitiveCharField_withPrimitiveValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("char", char.class, "charField", "String('a').charAt(0)", 'a');
    }

    @Test
    void primitiveCharField_withPrimitiveWrapperValue_isAssignedUnboxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("char", char.class, "charField", "Character(String('a').charAt(0))", 'a');
    }

    @Test
    void primitiveCharField_withExpressionValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("char", char.class, "charField", "String('Z').charAt(0) - String('A').charAt(0)", (char)('Z' - 'A'));
    }

    @Test
    void primitiveCharWrapperField_withPrimitiveWrapperValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Character", Character.class, "charWrapperField", "Character(String('a').charAt(0))", 'a');
    }

    @Test
    void primitiveCharWrapperField_withPrimitiveValue_isAssignedBoxedValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Character", Character.class, "charWrapperField", "String('a').charAt(0)", 'a');
    }

    @Test
    void primitiveCharWrapperField_withExpressionValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Character", Character.class, "charWrapperField", "Character(String('Z').charAt(0)) - Character(String('A').charAt(0))", (char)('Z' - 'A'));
    }

    @Test
    void primitiveCharWrapperField_withNullValue() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("Character", Character.class, "charWrapperField", "null", null);
    }
}
