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

package org.orthodox.universel.exec.navigation.axis.standard.bean.method.nested.array;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class MultiStepBigDecimalMethodArrayNavigationTest {
    private static final BigDecimal V1 = new BigDecimal("1.1");
    private static final BigDecimal V2 = new BigDecimal("11.6");
    private static final BigDecimal V3 = new BigDecimal("111.9");

    @Test
    void methodCall_noArgs_reduction() {
        assertThat(execute("getReferenceArrayProperty()\\getBigDecimalProperty()\\toBigInteger()\\[[]]", createBinding()),
                   equalTo(new BigInteger[]{V1.toBigInteger(), V2.toBigInteger(), V3.toBigInteger()})
        );
    }

    @Test
    void methodCall_singleArgMatchesFormalType_reduction() {
        assertThat(execute("getReferenceArrayProperty()\\getBigDecimalProperty()\\toBigInteger()\\add(1I)\\[[]]", createBinding()),
                   equalTo(new BigInteger[]{V1.toBigInteger().add(ONE), V2.toBigInteger().add(ONE), V3.toBigInteger().add(ONE)})
        );
    }

    private BeanWithProperties createBinding() {
        final BeanWithProperties[] R1 = {
            new BeanWithProperties().withBigDecimalProperty(V1),
            new BeanWithProperties().withBigDecimalProperty(V2),
            new BeanWithProperties().withBigDecimalProperty(V3)
        };
        return new BeanWithProperties().withReferenceArrayProperty(R1);
    }
}