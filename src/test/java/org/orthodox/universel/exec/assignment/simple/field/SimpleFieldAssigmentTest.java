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

package org.orthodox.universel.exec.assignment.simple.field;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import java.math.BigInteger;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

/**
 * Tests for the basic field assignment functionality.
 */
public class SimpleFieldAssigmentTest {
    @Test
    void withPrimitiveValue_returnsAssignmentValue() {
        assertThat(execute("static int field   field = 1234"), equalTo(1234));
    }

    @Test
    void withPrimitiveValue_returnsUnboxedAssignmentValue() {
        assertThat(execute("static int field   field = Integer(1234)"), equalTo(1234));
    }

    @Test
    void withTypeValue_returnsAssignmentValue() {
        assertThat(execute("static Class<?> field   field = Short"), equalTo(Short.class));
    }

    @Test
    void withReferenceValue_returnsAssignmentValue() {
        assertThat(execute(format("import %s static %s field   field = %s(12345)",
                                  BeanWithProperties.class.getName(),
                                  BeanWithProperties.class.getSimpleName(),
                                  BeanWithProperties.class.getSimpleName()
                           )
                   ),
                   equalTo(new BeanWithProperties(12345))
        );
    }

    @Test
    void withExpressionValue_returnsAssignmentValue() {
        assertThat(execute(format("import %s static BigInteger field   field = %s(12345).intProperty * 10I",
                                  BeanWithProperties.class.getName(),
                                  BeanWithProperties.class.getSimpleName()
                           )
                   ),
                   equalTo(new BigInteger("12345").multiply(BigInteger.TEN))
        );
    }

    @Test
    void fieldAssigned_containsAssignedValue() {
        assertThat(execute("static int field   field = 1234 field"), equalTo(1234));
    }
}
