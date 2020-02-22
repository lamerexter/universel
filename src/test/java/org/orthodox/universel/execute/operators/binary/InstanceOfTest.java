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

package org.orthodox.universel.execute.operators.binary;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.util.AType;
import org.orthodox.universel.util.RefTypes;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.compiler.Messages.MethodCall.TYPE_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.MethodCall.TYPE_NOT_FOUND;

public class InstanceOfTest {
    @Test
    void classOrInterfaceType_viaImplicitImports() {
        assertThat(execute("[] instanceof List"), equalTo(true));
        assertThat(execute("{} instanceof Set"), equalTo(true));
        assertThat(execute("{:} instanceof Map"), equalTo(true));

        assertThat(execute("123I instanceof BigInteger"), equalTo(true));
        assertThat(execute("123.456D instanceof BigDecimal"), equalTo(true));
        assertThat(execute("'Hello World!' instanceof String"), equalTo(true));
        assertThat(execute("\"Hello World!\" instanceof String"), equalTo(true));
        assertThat(execute("\"\"\"Hello World!\"\"\" instanceof String"), equalTo(true));

        assertThat(execute("{} instanceof List"), equalTo(false));
        assertThat(execute("{} instanceof List"), equalTo(false));
        assertThat(execute("{} instanceof List"), equalTo(false));
    }

    @Test
    void classOrInterfaceType_viaExplicitImports() {
        assertThat(execute("import " + AType.class.getName() + "  "
                           + "import " + RefTypes.class.getName() + ".aType aType() instanceof AType"), equalTo(true));
    }

    @Test
    void classOrInterfaceType_viaOnDemandImports() {
        assertThat(execute("import " + AType.class.getPackage().getName() + ".*  "
                           + "import " + RefTypes.class.getName() + ".aType aType() instanceof AType"), equalTo(true));
    }


    @Test
    void classOrInterfaceArrayType_viaExplicitImports() {
        assertThat(execute("import " + RefTypes.class.getName()+".integerArray integerArray() instanceof Integer[]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".integerArray integerArray() instanceof Integer[][]"), equalTo(false));
    }

    @Test
    void classOrInterface2ArrayType_viaExplicitImports() {
        assertThat(execute("import " + RefTypes.class.getName()+".integer2Array integer2Array() instanceof Integer[][]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".integerArray integerArray() instanceof Integer[][]"), equalTo(false));
    }

    @Test
    void classOrInterfaceArrayType_viaOnDemandImports() {
        assertThat(execute("import " + RefTypes.class.getName()+".* integerArray() instanceof Integer[]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".* integerArray() instanceof Integer[][]"), equalTo(false));
    }

    @Test
    void primitiveLiteralTypes_shouldBeAutoBoxed() {
        assertThat(execute("true   instanceof Boolean"), equalTo(true));

        assertThat(execute("123f   instanceof Float"), equalTo(true));
        assertThat(execute("123f   instanceof Float"), equalTo(true));

        assertThat(execute("123d   instanceof Double"), equalTo(true));

        assertThat(execute("0b101  instanceof Integer"), equalTo(true));
        assertThat(execute("0101   instanceof Integer"), equalTo(true));
        assertThat(execute("123    instanceof Integer"), equalTo(true));
        assertThat(execute("0x101  instanceof Integer"), equalTo(true));

        assertThat(execute("0b101L instanceof Long"), equalTo(true));
        assertThat(execute("0101L  instanceof Long"), equalTo(true));
        assertThat(execute("123L   instanceof Long"), equalTo(true));
        assertThat(execute("0x101L instanceof Long"), equalTo(true));
    }

    @Test
    void primitiveArrayType_viaExplicitImports() {
        assertThat(execute("import " + RefTypes.class.getName()+".booleanArray booleanArray() instanceof boolean[]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".floatArray floatArray() instanceof float[]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".doubleArray doubleArray() instanceof double[]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".intArray intArray() instanceof int[]"), equalTo(true));
        assertThat(execute("import " + RefTypes.class.getName()+".longArray longArray() instanceof long[]"), equalTo(true));

        assertThat(execute("import " + RefTypes.class.getName()+".booleanArray booleanArray() instanceof int[]"), equalTo(false));
    }

    @Test
    void unresolvedTypeReference_producesError() {
        CompiledUnit compiled = compile("123 instanceof AnUnknownType");

        assertThat(compiled.getMessages().hasErrors(), is(true));
        assertThat(compiled.getMessages().hasErrorWithCode(TYPE_NOT_FOUND.getCode()), is(true));
    }

    @Test
    void ambiguousTypeReference_producesError() {
        CompiledUnit compiled = compile(
                "import " + RefTypes.class.getName() + ".aType\n" +
                "import " + AType.class.getPackage().getName() + ".*\n" +
                "import " + org.orthodox.universel.util.more.AType.class.getPackage().getName() + ".*\n" +
                "aType() instanceof AType"
        );

        assertThat(compiled.getMessages().hasErrors(), is(true));
        assertThat(compiled.getMessages().hasErrorWithCode(TYPE_AMBIGUOUS.getCode()), is(true));
    }
}
