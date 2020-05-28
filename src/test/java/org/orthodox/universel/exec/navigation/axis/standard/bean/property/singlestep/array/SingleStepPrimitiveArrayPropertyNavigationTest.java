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

public class SingleStepPrimitiveArrayPropertyNavigationTest {
    @Test
    void booleanPrimitiveArrayPropertyRead() {
        boolean[] value = {true, false, true};
        assertThat(execute("booleanArrayProperty", new JavaBean<>(new BeanWithProperties()).with("booleanArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void byteArrayPropertyRead() {
        byte[] value = {Byte.MAX_VALUE, Byte.MAX_VALUE};
        assertThat(execute("byteArrayProperty", new JavaBean<>(new BeanWithProperties()).with("byteArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void charArrayPropertyRead() {
        char[] value = {'a', 'z'};
        assertThat(execute("charArrayProperty", new JavaBean<>(new BeanWithProperties()).with("charArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void doubleArrayPropertyRead() {
        double[] value = {Double.MAX_VALUE, Double.MIN_VALUE};
        assertThat(execute("doubleArrayProperty", new JavaBean<>(new BeanWithProperties()).with("doubleArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void floatArrayPropertyRead() {
        float[] value = {Float.MAX_VALUE, Float.MIN_VALUE};
        assertThat(execute("floatArrayProperty", new JavaBean<>(new BeanWithProperties()).with("floatArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void intArrayPropertyRead() {
        int[] value = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertThat(execute("intArrayProperty", new JavaBean<>(new BeanWithProperties()).with("intArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void longArrayPropertyRead() {
        long[] value = {Long.MAX_VALUE, Long.MIN_VALUE};
        assertThat(execute("longArrayProperty", new JavaBean<>(new BeanWithProperties()).with("longArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }

    @Test
    void shortArrayPropertyRead() {
        short[] value = {Short.MAX_VALUE, Short.MIN_VALUE};
        assertThat(execute("shortArrayProperty", new JavaBean<>(new BeanWithProperties()).with("shortArrayProperty", value).getBean()), allOf(equalTo(value), sameInstance(value)));
    }
}
