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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.array;

import org.beanplanet.core.beans.JavaBean;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.tools.BytecodeOutput;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;

public class MultiStepBigDecimalPropertyArrayNavigationTest {
    @Test
    void read_reduction() {
        // Given
        final BigDecimal V1 = new BigDecimal("1");
        final BigDecimal V2 = new BigDecimal("11");
        final BigDecimal V3 = new BigDecimal("111");
        final BeanWithProperties[] R1 = {
            new BeanWithProperties().withBigDecimalProperty(V1),
            new BeanWithProperties().withBigDecimalProperty(V2),
            new BeanWithProperties().withBigDecimalProperty(V3)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(R1);

        // Then
        assertThat(execute("referenceArrayProperty\\bigDecimalProperty\\[[]]", binding), equalTo(new BigDecimal[] {V1, V2, V3 }));
    }

    @Test
    void read_reduction_nullValue() {
        assertThat(execute("referenceArrayProperty\\bigDecimalProperty\\[[]]", new BeanWithProperties()), nullValue());
// TODO: This is a great test, currently failing and should be made to work
//        assertThat(execute("referenceArrayProperty\\bigDecimalProperty\\[[]]",
//                           new BeanWithProperties().withReferenceArrayProperty(new BeanWithProperties[5])), nullValue());
    }

    @Test
    void read_nullContext() {
        assertThat(execute(compile("referenceArrayProperty\\bigDecimalProperty\\[[]]", BeanWithProperties.class), null), nullValue());
    }


    @Test
    void read_PredicateFiltered() {
        final BigDecimal[] value = {BigDecimal.ZERO, BigDecimal.ONE};
        final BeanWithProperties binding = new JavaBean<>(new BeanWithProperties()).with("bigDecimalArrayProperty", value).getBean();
        assertThat(execute("bigDecimalArrayProperty[true]\\[[]]", binding), equalTo(value));
        assertThat(execute("import java.lang.Boolean.* bigDecimalArrayProperty[TRUE]\\[[]]", binding), equalTo(value));
        assertThat(execute("import java.lang.Boolean.* bigDecimalArrayProperty[1 == 1]\\[[]]", binding), equalTo(value));

        assertThat(execute("bigDecimalArrayProperty[false]\\[[]]", binding), equalTo(new BigDecimal[]{}));
        assertThat(execute("import java.lang.Boolean.* bigDecimalArrayProperty[FALSE]\\[[]]", binding), equalTo(new BigDecimal[]{}));
        assertThat(execute("import java.lang.Boolean.* bigDecimalArrayProperty[1 == 0]\\[[]]", binding), equalTo(new BigDecimal[]{}));
        assertThat(execute("import java.lang.Boolean.* bigDecimalArrayProperty[TRUE][false][true]\\[[]]", binding), equalTo(new BigDecimal[]{}));
    }

    @Test
    void read_IndexFiltered() {
        // Given
        final int ARR_SIZE = 4;
        final BigDecimal[] value = range(0, ARR_SIZE).mapToObj(i -> new BigDecimal(valueOf(i))).toArray(BigDecimal[]::new);
        final BeanWithProperties binding = new JavaBean<>(new BeanWithProperties()).with("bigDecimalArrayProperty", value).getBean();

        // Then
        range(0, value.length).forEach(i ->  assertThat(execute(format("bigDecimalArrayProperty[%d]\\[[]]", i), binding), equalTo(new BigDecimal[] { value[i] })));

        assertThrows(IndexOutOfBoundsException.class, () -> execute(format("bigDecimalArrayProperty[%d]\\[[]]", value.length), binding));
    }

    @Test
    void read_IndexRangeFiltered() {
        // Given
        final int ARR_SIZE = 10;
        final BigDecimal[] value = range(0, ARR_SIZE).mapToObj(i -> new BigDecimal(valueOf(i))).toArray(BigDecimal[]::new);
        final BeanWithProperties binding = new JavaBean<>(new BeanWithProperties()).with("bigDecimalArrayProperty", value).getBean();

        // Then
        assertThat(execute(format("bigDecimalArrayProperty[0..<%d]\\[[]]", ARR_SIZE), binding), equalTo(value));
        assertThat(execute("bigDecimalArrayProperty[0..2]\\[[]]", binding), equalTo(range(0, 3).mapToObj(i -> new BigDecimal(valueOf(i))).toArray(BigDecimal[]::new)));
        assertThat(execute("bigDecimalArrayProperty[0f..2f]\\[[]]", binding), equalTo(range(0, 3).mapToObj(i -> new BigDecimal(valueOf(i))).toArray(BigDecimal[]::new)));
        assertThat(execute("bigDecimalArrayProperty[0I..2I]\\[[]]", binding), equalTo(range(0, 3).mapToObj(i -> new BigDecimal(valueOf(i))).toArray(BigDecimal[]::new)));

        assertThat(execute("bigDecimalArrayProperty[5..<7]\\[[]]", binding), equalTo(range(5, 7).mapToObj(i -> new BigDecimal(valueOf(i))).toArray(BigDecimal[]::new)));

        assertThrows(IndexOutOfBoundsException.class, () -> execute(format("bigDecimalArrayProperty[0..%d]\\[[]]", ARR_SIZE), binding));
        assertThrows(IndexOutOfBoundsException.class, () -> execute(format("bigDecimalArrayProperty[0<..5]\\[[]]", ARR_SIZE), binding));
    }

    @Test
    void read_multiArraySteps_reduction() {
        // Given
        final BigDecimal B1V1 = new BigDecimal("11");
        final BigDecimal B1V2 = new BigDecimal("12");
        final BigDecimal B1V3 = new BigDecimal("13");
        final BigDecimal B2V1 = new BigDecimal("21");
        final BigDecimal B2V2 = new BigDecimal("22");
        final BigDecimal B3V1 = new BigDecimal("31");
        final BeanWithProperties binding
            = new BeanWithProperties()
                  .withReferenceArrayProperty(new BeanWithProperties[]{
                      new BeanWithProperties().withReferenceArrayProperty(new BeanWithProperties[]{
                          new BeanWithProperties().withBigDecimalProperty(B1V1),
                          new BeanWithProperties().withBigDecimalProperty(B1V2),
                          new BeanWithProperties().withBigDecimalProperty(B1V3)
                      }),
                      new BeanWithProperties().withReferenceArrayProperty(new BeanWithProperties[]{
                          new BeanWithProperties().withBigDecimalProperty(B2V1),
                          new BeanWithProperties().withBigDecimalProperty(B2V2)
                      }),
                      new BeanWithProperties().withReferenceArrayProperty(new BeanWithProperties[]{
                          new BeanWithProperties().withBigDecimalProperty(B3V1)
                      }),
                  });

        // Then
        assertThat(execute("referenceArrayProperty\\referenceArrayProperty\\bigDecimalProperty\\[[]]", binding),
                   equalTo(new BigDecimal[] { B1V1, B1V2, B1V3, B2V1, B2V2, B3V1}));
    }
}
