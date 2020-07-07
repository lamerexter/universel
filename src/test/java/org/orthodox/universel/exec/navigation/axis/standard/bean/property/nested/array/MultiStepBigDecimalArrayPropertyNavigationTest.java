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

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class MultiStepBigDecimalArrayPropertyNavigationTest {
    @Test
    void bigDecimalRead_reduction() {
        // Given
        final BigDecimal B1 = new BigDecimal("1");
        final BigDecimal B2 = new BigDecimal("11");
        final BigDecimal B3 = new BigDecimal("111");
        final BeanWithProperties[] R1 = {
            new BeanWithProperties().withBigDecimalProperty(B1),
            new BeanWithProperties().withBigDecimalProperty(B2),
            new BeanWithProperties().withBigDecimalProperty(B3)
        };
        final BeanWithProperties binding = new BeanWithProperties().withReferenceArrayProperty(R1);

        // Then
        assertThat(execute("referenceArrayProperty\\bigDecimalProperty\\[[]]", binding), equalTo(new BigDecimal[] {B1, B2, B3 }));
    }
}
