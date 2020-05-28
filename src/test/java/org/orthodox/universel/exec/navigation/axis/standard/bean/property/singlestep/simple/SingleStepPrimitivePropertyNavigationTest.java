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

public class SingleStepPrimitivePropertyNavigationTest {
    @Test
    void booleanRead() {
        assertThat(execute("booleanProperty", new JavaBean<>(new BeanWithProperties()).with("booleanProperty", true).getBean()), equalTo(true));
    }

    @Test
    void booleanRead_nullContext() {
        assertThat(execute(compile("booleanProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void byteRead() {
        assertThat(execute("byteProperty", new JavaBean<>(new BeanWithProperties()).with("byteProperty", Byte.MAX_VALUE).getBean()), equalTo(Byte.MAX_VALUE));
    }

    @Test
    void byteRead_nullContext() {
        assertThat(execute(compile("byteProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void charRead() {
        assertThat(execute("charProperty", new JavaBean<>(new BeanWithProperties()).with("charProperty", 'z').getBean()), equalTo('z'));
    }

    @Test
    void charRead_nullContext() {
        assertThat(execute(compile("charProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void doubleRead() {
        assertThat(execute("doubleProperty", new JavaBean<>(new BeanWithProperties()).with("doubleProperty", Double.MAX_VALUE).getBean()), equalTo(Double.MAX_VALUE));
    }

    @Test
    void doubleRead_nullContext() {
        assertThat(execute(compile("doubleProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void floatRead() {
        assertThat(execute("floatProperty", new JavaBean<>(new BeanWithProperties()).with("floatProperty", Float.MAX_VALUE).getBean()), equalTo(Float.MAX_VALUE));
    }

    @Test
    void floatRead_nullContext() {
        assertThat(execute(compile("floatProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void intRead() {
        assertThat(execute("intProperty", new JavaBean<>(new BeanWithProperties()).with("intProperty", Integer.MAX_VALUE).getBean()), equalTo(Integer.MAX_VALUE));
    }

    @Test
    void intRead_nullContext() {
        assertThat(execute(compile("intProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void longRead() {
        assertThat(execute("longProperty", new JavaBean<>(new BeanWithProperties()).with("longProperty", Long.MAX_VALUE).getBean()), equalTo(Long.MAX_VALUE));
    }

    @Test
    void longRead_nullContext() {
        assertThat(execute(compile("longProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void shortRead() {
        assertThat(execute("shortProperty", new JavaBean<>(new BeanWithProperties()).with("shortProperty", Short.MAX_VALUE).getBean()), equalTo(Short.MAX_VALUE));
    }

    @Test
    void shortRead_nullContext() {
        assertThat(execute(compile("shortProperty", BeanWithProperties.class), null), nullValue());
    }
}
