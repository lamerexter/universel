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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;

public class SingleStepReferenceTypePropertyNavigationTest {
    @Test
    void read() {
        // Given
        BeanWithProperties referencePropertyValue = new BeanWithProperties();
        referencePropertyValue.setIntProperty(999);

        // Then
        assertThat(execute("referenceProperty",
                           new JavaBean<>(new BeanWithProperties()).with("referenceProperty", referencePropertyValue).getBean()),
                   allOf(sameInstance(referencePropertyValue), equalTo(referencePropertyValue))
        );
    }

    @Test
    void read_nullValue() {
        assertThat(execute("referenceProperty", new BeanWithProperties()), nullValue());
    }

    @Test
    void read_nullContext() {
        assertThat(execute(compile("referenceProperty", BeanWithProperties.class), null), nullValue());
    }
}
