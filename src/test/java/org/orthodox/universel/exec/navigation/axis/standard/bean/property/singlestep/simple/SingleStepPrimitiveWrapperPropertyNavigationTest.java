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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.singlestep.simple;

import org.beanplanet.core.beans.JavaBean;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;

public class SingleStepPrimitiveWrapperPropertyNavigationTest {
    @Test
    void booleanRead() {
        assertThat(execute("booleanWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("booleanWrapperProperty", true).getBean()), equalTo(true));
    }

    @Test
    void booleanWrapperRead_nullValue() {
        assertThat(execute("booleanWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void booleanWrapperRead_nullContext() {
        assertThat(execute(compile("booleanWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void byteRead() {
        assertThat(execute("byteWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("byteWrapperProperty", Byte.MAX_VALUE).getBean()), equalTo(Byte.MAX_VALUE));
    }

    @Test
    void byteWrapperRead_nullValue() {
        assertThat(execute("byteWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void byteWrapperRead_nullContext() {
        assertThat(execute(compile("byteWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void charRead() {
        assertThat(execute("charWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("charWrapperProperty", 'z').getBean()), equalTo('z'));
    }

    @Test
    void charWrapperRead_nullValue() {
        assertThat(execute("charWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void charWrapperRead_nullContext() {
        assertThat(execute(compile("charWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void doubleRead() {
        assertThat(execute("doubleWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("doubleWrapperProperty", Double.MAX_VALUE).getBean()), equalTo(Double.MAX_VALUE));
    }

    @Test
    void doubleWrapperRead_nullValue() {
        assertThat(execute("doubleWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void doubleWrapperRead_nullContext() {
        assertThat(execute(compile("doubleWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void floatRead() {
        assertThat(execute("floatWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("floatWrapperProperty", Float.MAX_VALUE).getBean()), equalTo(Float.MAX_VALUE));
    }

    @Test
    void floatWrapperRead_nullValue() {
        assertThat(execute("floatWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void floatWrapperRead_nullContext() {
        assertThat(execute(compile("floatWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void intRead() {
        assertThat(execute("intWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("intWrapperProperty", Integer.MAX_VALUE).getBean()), equalTo(Integer.MAX_VALUE));
    }

    @Test
    void intWrapperRead_nullValue() {
        assertThat(execute("intWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void intWrapperRead_nullContext() {
        assertThat(execute(compile("intWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void longRead() {
        assertThat(execute("longWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("longWrapperProperty", Long.MAX_VALUE).getBean()), equalTo(Long.MAX_VALUE));
    }

    @Test
    void longWrapperRead_nullValue() {
        assertThat(execute("longWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void longWrapperRead_nullContext() {
        assertThat(execute(compile("longWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void shortRead() {
        assertThat(execute("shortWrapperProperty", new JavaBean<>(new BeanWithProperties()).with("shortWrapperProperty", Short.MAX_VALUE).getBean()), equalTo(Short.MAX_VALUE));
    }

    @Test
    void shortWrapperRead_nullValue() {
        assertThat(execute("shortWrapperProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void shortRead_nullContext() {
        assertThat(execute(compile("shortWrapperProperty", BeanWithProperties.class), null), nullValue());
    }
}
