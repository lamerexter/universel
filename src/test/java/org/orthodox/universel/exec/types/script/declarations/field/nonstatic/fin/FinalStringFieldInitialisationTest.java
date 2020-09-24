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
 * Unit tests for simple static final string field assignment scoped at the enclosing script level.
 */
public class FinalStringFieldInitialisationTest extends AbstractFieldInitialisationTest {
    @Test
    void stringField_initialisedWithLiteral() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("String", String.class, "stringField", "''", "");
        assertSingleAllAccessFinalFieldIsAssigned("String", String.class, "stringField", "'Hello World'", "Hello World");
        assertSingleAllAccessFinalFieldIsAssigned("String", String.class, "stringField", "\"Hello World!\"", "Hello World!");
    }

    @Test
    void stringField_initialisedWithInterpolatedString() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("String", String.class, "stringField", "\"Look Ma, I can see the ${ 'stars' }\"", "Look Ma, I can see the stars");
    }

    @Test
    void stringField_initialisedWithExpression() throws Exception {
        assertSingleAllAccessFinalFieldIsAssigned("String", String.class, "stringField", "\"Look Ma, 1 plus 1 does equal \" + '2'", "Look Ma, 1 plus 1 does equal 2");
    }
}
