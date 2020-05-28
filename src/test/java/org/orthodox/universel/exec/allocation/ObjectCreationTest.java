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

package org.orthodox.universel.exec.allocation;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class ObjectCreationTest {
    @Test
    void noArgs() {
        assertThat(execute(format("import %s %s()", BeanWithProperties.class.getName(), BeanWithProperties.class.getSimpleName())), equalTo(new BeanWithProperties()));
    }

    @Test
    void singlePrimitiveArg() {
        assertThat(execute(format("import %s %s(1234)", BeanWithProperties.class.getName(), BeanWithProperties.class.getSimpleName())), equalTo(new BeanWithProperties(1234)));
    }

    @Test
    void singlePrimitiveArg_boxed() {
        assertThat(execute(format("import %s %s(5678L)", BeanWithProperties.class.getName(), BeanWithProperties.class.getSimpleName())), equalTo(new BeanWithProperties((Long)5678L)));
    }

    @Test
    void singlePrimitiveArg_unboxed() {
        assertThat(execute(format("import %s %s(Float('12.5'))", BeanWithProperties.class.getName(), BeanWithProperties.class.getSimpleName())), equalTo(new BeanWithProperties(new Float("12.5"))));
    }
}
