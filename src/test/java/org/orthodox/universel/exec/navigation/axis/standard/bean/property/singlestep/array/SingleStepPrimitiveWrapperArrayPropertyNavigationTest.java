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
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class SingleStepPrimitiveWrapperArrayPropertyNavigationTest {
    @Test
    void booleanWrapperArrayPropertyRead() {
        Boolean[] value = {true, false, true};
        assertThat(execute("booleanWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("booleanWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void byteWrapperArrayPropertyRead() {
        Byte[] value = {Byte.MAX_VALUE, Byte.MAX_VALUE};
        assertThat(execute("byteWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("byteWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void charWrapperArrayPropertyRead() {
        Character[] value = {'a', 'z'};
        assertThat(execute("charWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("charWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void doubleWrapperArrayPropertyRead() {
        Double[] value = {Double.MAX_VALUE, Double.MIN_VALUE};
        assertThat(execute("doubleWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("doubleWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void floatWrapperArrayPropertyRead() {
        Float[] value = {Float.MAX_VALUE, Float.MIN_VALUE};
        assertThat(execute("floatWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("floatWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void intWrapperArrayPropertyRead() {
        Integer[] value = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertThat(execute("intWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("intWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void longWrapperArrayPropertyRead() {
        Long[] value = {Long.MAX_VALUE, Long.MIN_VALUE};
        assertThat(execute("longWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("longWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void shortWrapperArrayPropertyRead() {
        Short[] value = {Short.MAX_VALUE, Short.MIN_VALUE};
        assertThat(execute("shortWrapperArrayProperty", new JavaBean<>(new BeanWithProperties()).with("shortWrapperArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }
}
