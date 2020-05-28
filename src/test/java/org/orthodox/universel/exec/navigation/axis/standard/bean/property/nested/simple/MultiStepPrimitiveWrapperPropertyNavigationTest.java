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

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;

public class MultiStepPrimitiveWrapperPropertyNavigationTest {
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
    }

    @Test
    void booleanRead() {
        firstStep.setBooleanProperty(true);
        secondStep.setBooleanProperty(true);
        assertThat(execute("referenceProperty\\booleanWrapperProperty", binding), sameInstance(firstStep.getBooleanWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty", binding), sameInstance(firstStep.getReferenceProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\booleanWrapperProperty", binding), sameInstance(
            secondStep.getBooleanWrapperProperty()));
    }

    @Test
    void booleanRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\booleanWrapperProperty", binding), nullValue());
    }

    @Test
    void booleanRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\booleanWrapperProperty", binding), nullValue());
    }

    @Test
    void booleanRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\booleanWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void byteRead() {
        firstStep.setByteWrapperProperty(Byte.MAX_VALUE);
        secondStep.setByteWrapperProperty(Byte.MIN_VALUE);
        assertThat(execute("referenceProperty\\byteWrapperProperty", binding), sameInstance(firstStep.getByteWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\byteWrapperProperty", binding), sameInstance(
            secondStep.getByteWrapperProperty()));
    }

    @Test
    void byteRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\byteWrapperProperty", binding), nullValue());
    }

    @Test
    void byteRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\byteWrapperProperty", binding), nullValue());
    }

    @Test
    void byteRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\byteWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void charRead() {
        firstStep.setCharWrapperProperty('z');
        secondStep.setCharWrapperProperty('Z');
        assertThat(execute("referenceProperty\\charWrapperProperty", binding), sameInstance(firstStep.getCharWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\charWrapperProperty", binding), sameInstance(
            secondStep.getCharWrapperProperty()));
    }

    @Test
    void charRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\charWrapperProperty", binding), nullValue());
    }

    @Test
    void charRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\charWrapperProperty", binding), nullValue());
    }

    @Test
    void charRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\charWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void doubleRead() {
        firstStep.setDoubleWrapperProperty(Double.MAX_VALUE);
        secondStep.setDoubleWrapperProperty(Double.MIN_VALUE);
        assertThat(execute("referenceProperty\\doubleWrapperProperty", binding), sameInstance(firstStep.getDoubleWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\doubleWrapperProperty", binding), sameInstance(
            secondStep.getDoubleWrapperProperty()));
    }

    @Test
    void doubleRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\doubleWrapperProperty", binding), nullValue());
    }

    @Test
    void doubleRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\doubleWrapperProperty", binding), nullValue());
    }

    @Test
    void doubleRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\doubleWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void floatRead() {
        firstStep.setFloatWrapperProperty(Float.MAX_VALUE);
        secondStep.setFloatWrapperProperty(Float.MIN_VALUE);
        assertThat(execute("referenceProperty\\floatWrapperProperty", binding), sameInstance(firstStep.getFloatWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\floatWrapperProperty", binding), sameInstance(
            secondStep.getFloatWrapperProperty()));
    }

    @Test
    void floatRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\floatWrapperProperty", binding), nullValue());
    }

    @Test
    void floatRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\floatWrapperProperty", binding), nullValue());
    }

    @Test
    void floatRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\floatWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void intRead() {
        firstStep.setIntWrapperProperty(Integer.MAX_VALUE);
        secondStep.setIntWrapperProperty(Integer.MIN_VALUE);
        assertThat(execute("referenceProperty\\intWrapperProperty", binding), sameInstance(firstStep.getIntWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\intWrapperProperty", binding), sameInstance(secondStep
                                                                                                                  .getIntWrapperProperty()));
    }

    @Test
    void intRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\intWrapperProperty", binding), nullValue());
    }

    @Test
    void intRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\intWrapperProperty", binding), nullValue());
    }

    @Test
    void intRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\intWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void longRead() {
        firstStep.setLongWrapperProperty(Long.MAX_VALUE);
        secondStep.setLongWrapperProperty(Long.MIN_VALUE);
        assertThat(execute("referenceProperty\\longWrapperProperty", binding), sameInstance(firstStep.getLongWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\longWrapperProperty", binding), sameInstance(
            secondStep.getLongWrapperProperty()));
    }

    @Test
    void longRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\longWrapperProperty", binding), nullValue());
    }

    @Test
    void longRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\longWrapperProperty", binding), nullValue());
    }

    @Test
    void longRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\longWrapperProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void shortRead() {
        firstStep.setShortWrapperProperty(Short.MAX_VALUE);
        secondStep.setShortWrapperProperty(Short.MAX_VALUE);
        assertThat(execute("referenceProperty\\shortWrapperProperty", binding), sameInstance(firstStep.getShortWrapperProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\shortWrapperProperty", binding), sameInstance(
            secondStep.getShortWrapperProperty()));
    }

    @Test
    void shortRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\shortWrapperProperty", binding), nullValue());
    }

    @Test
    void shortRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\shortWrapperProperty", binding), nullValue());
    }

    @Test
    void shortRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\shortWrapperProperty", BeanWithProperties.class), null), nullValue());
    }
}
