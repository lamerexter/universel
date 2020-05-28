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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;

public class MultiStepBigIntegerPropertyNavigationTest {
    private BeanWithProperties binding;
    private BeanWithProperties firstStep;
    private BeanWithProperties secondStep;

    @BeforeEach
    void setUp() {
        binding = new BeanWithProperties();
        firstStep = new BeanWithProperties();
        secondStep = new BeanWithProperties();
        binding.setReferenceProperty(firstStep);
        firstStep.setReferenceProperty(secondStep);

        firstStep.setBigIntegerProperty(BigInteger.ONE);
        secondStep.setBigIntegerProperty(BigInteger.TEN);
    }

    @Test
    void bigIntegerPropertyRead() {
        assertThat(execute("referenceProperty\\bigIntegerProperty", binding), sameInstance(firstStep.getBigIntegerProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\bigIntegerProperty", binding), sameInstance(secondStep.getBigIntegerProperty()));
    }

    @Test
    void nullValuePropertyRead_innermostStep() {
        assertThat(execute("referenceProperty\\referenceProperty\\referenceProperty", binding), nullValue());

        secondStep.setBigIntegerProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\bigIntegerProperty", binding), nullValue());
    }

    @Test
    void nullValuePropertyRead_intermediateStep() {
        firstStep.setReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\bigIntegerProperty", binding), nullValue());
    }

    @Test
    void nullValuePropertyRead_outermostStep() {
        binding.setReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\bigIntegerProperty", binding), nullValue());
    }

}
