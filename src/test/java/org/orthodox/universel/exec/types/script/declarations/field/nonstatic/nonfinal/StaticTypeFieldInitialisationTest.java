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
import org.orthodox.universel.exec.types.script.declarations.field.stat.AbstractStaticFieldInitialisationTest;

/**
 * Unit tests for simple static final type/class field assignment scoped at the enclosing script level.
 */
public class StaticTypeFieldInitialisationTest extends AbstractStaticFieldInitialisationTest {
    @Test
    void typeField_initialisedWithPrimitiveType() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "byte", byte.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "boolean", boolean.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "char", char.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "double", double.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "float", float.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "int", int.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "long", long.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "short", short.class);
    }

    @Test
    void typeField_initialisedWithPrimitiveWrapperType() throws Exception {
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Byte", Byte.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Boolean", Boolean.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Character", Character.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Double", Double.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Float", Float.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Integer", Integer.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Long", Long.class);
        assertSingleAllAccessStaticFieldIsAssigned("Class", Class.class, "typeField", "Short", Short.class);
    }
}
