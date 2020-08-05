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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.singlestep.array;

import org.beanplanet.core.beans.JavaBean;
import org.beanplanet.core.io.resource.Resource;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.tools.BytecodeOutput;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.orthodox.universel.Universal.*;
import static org.orthodox.universel.tools.BytecodeOutput.printClass;

public class SingleStepBigDecimalArrayPropertyNavigationTest {
    @Test
    void read() {
        final BigDecimal[] value = {BigDecimal.ZERO, BigDecimal.ONE};
        final BeanWithProperties binding = new JavaBean<>(new BeanWithProperties()).with("bigDecimalArrayProperty", value).getBean();
        assertThat(execute("bigDecimalArrayProperty", binding), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void filtered_implicitSelf() {
        final BigDecimal[] value = {BigDecimal.ZERO, BigDecimal.ONE, new BigDecimal("3"), new BigDecimal("4"), new BigDecimal("5")};
        final BeanWithProperties binding = new JavaBean<>(new BeanWithProperties()).with("bigDecimalArrayProperty", value).getBean();

        assertThat(executeWithResult("bigDecimalArrayProperty[toBigInteger() < 3I]", binding).as(BigDecimal[].class),
                   equalTo(new BigDecimal[] { BigDecimal.ZERO, BigDecimal.ONE }));
        assertThat(executeWithResult("bigDecimalArrayProperty[toBigInteger() < 3I && toBigInteger() > 0]", binding).as(BigDecimal[].class),
                   equalTo(new BigDecimal[] { BigDecimal.ONE }));
        assertThat(executeWithResult("bigDecimalArrayProperty[toBigInteger() < 3I and toBigInteger() > 0]", binding).as(BigDecimal[].class),
                   equalTo(new BigDecimal[] { BigDecimal.ONE }));
        assertThat(executeWithResult("bigDecimalArrayProperty[toBigInteger() < 3I][toBigInteger() > 0]", binding).as(BigDecimal[].class),
                   equalTo(new BigDecimal[] { BigDecimal.ONE }));
        assertThat(executeWithResult("bigDecimalArrayProperty[toBigInteger() < 3I][toBigInteger() > 0]", binding).as(BigDecimal[].class),
                   equalTo(new BigDecimal[] { BigDecimal.ONE }));
    }

    @Test
    void read_nullValue() {
        assertThat(execute("bigDecimalArrayProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void read_nullContext() {
        assertThat(execute(compile("bigDecimalArrayProperty", BeanWithProperties.class), null), nullValue());
    }
}
