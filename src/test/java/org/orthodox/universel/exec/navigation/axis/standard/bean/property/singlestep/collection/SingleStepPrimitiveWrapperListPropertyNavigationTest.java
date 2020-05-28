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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.singlestep.collection;

import org.beanplanet.core.beans.JavaBean;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class SingleStepPrimitiveWrapperListPropertyNavigationTest {
    @Test
    void booleanPrimitiveListPropertyRead() {
        List<Boolean> value = asList(true, false, true);
        assertThat(execute("booleanWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("booleanWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void byteWrapperListPropertyRead() {
        List<Byte> value = asList(Byte.MAX_VALUE, Byte.MAX_VALUE);
        assertThat(execute("byteWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("byteWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void charWrapperListPropertyRead() {
        List<Character> value = asList('a', 'z');
        assertThat(execute("charWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("charWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void doubleWrapperListPropertyRead() {
        List<Double> value = asList(Double.MAX_VALUE, Double.MIN_VALUE);
        assertThat(execute("doubleWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("doubleWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void floatWrapperListPropertyRead() {
        List<Float> value = asList(Float.MAX_VALUE, Float.MIN_VALUE);
        assertThat(execute("floatWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("floatWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void intWrapperListPropertyRead() {
        List<Integer> value = asList(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertThat(execute("intWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("intWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void longWrapperListPropertyRead() {
        List<Long> value = asList(Long.MAX_VALUE, Long.MIN_VALUE);
        assertThat(execute("longWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("longWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void shortWrapperListPropertyRead() {
        List<Short> value = asList(Short.MAX_VALUE, Short.MIN_VALUE);
        assertThat(execute("shortWrapperListProperty", new JavaBean<>(new BeanWithProperties()).with("shortWrapperListProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }
}
