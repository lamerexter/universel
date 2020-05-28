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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;

public class MultiStepPrimitivePropertyNavigationTest {
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
        assertThat(execute("referenceProperty\\booleanProperty", binding), equalTo(firstStep.isBooleanProperty()));
        assertThat(execute("referenceProperty\\referenceProperty", binding), sameInstance(firstStep.getReferenceProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\booleanProperty", binding), equalTo(secondStep.isBooleanProperty()));
    }

    @Test
    void booleanRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\booleanProperty", binding), nullValue());
    }

    @Test
    void booleanRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\booleanProperty", binding), nullValue());
    }

    @Test
    void booleanRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\booleanProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void byteRead() {
        firstStep.setByteProperty(Byte.MAX_VALUE);
        secondStep.setByteProperty(Byte.MIN_VALUE);
        assertThat(execute("referenceProperty\\byteProperty", binding), equalTo(firstStep.getByteProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\byteProperty", binding), equalTo(secondStep.getByteProperty()));
    }

    @Test
    void byteRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\byteProperty", binding), nullValue());
    }

    @Test
    void byteRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\byteProperty", binding), nullValue());
    }

    @Test
    void byteRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\byteProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void charRead() {
        firstStep.setCharProperty('z');
        secondStep.setCharProperty('Z');
        assertThat(execute("referenceProperty\\charProperty", binding), equalTo(firstStep.getCharProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\charProperty", binding), equalTo(secondStep.getCharProperty()));
    }

    @Test
    void charRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\charProperty", binding), nullValue());
    }

    @Test
    void charRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\charProperty", binding), nullValue());
    }

    @Test
    void charRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\charProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void doubleRead() {
        firstStep.setDoubleProperty(Double.MAX_VALUE);
        secondStep.setDoubleProperty(Double.MIN_VALUE);
        assertThat(execute("referenceProperty\\doubleProperty", binding), equalTo(firstStep.getDoubleProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\doubleProperty", binding), equalTo(secondStep.getDoubleProperty()));
    }

    @Test
    void doubleRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\doubleProperty", binding), nullValue());
    }

    @Test
    void doubleRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\doubleProperty", binding), nullValue());
    }

    @Test
    void doubleRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\doubleProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void floatRead() {
        firstStep.setFloatProperty(Float.MAX_VALUE);
        secondStep.setFloatProperty(Float.MIN_VALUE);
        assertThat(execute("referenceProperty\\floatProperty", binding), equalTo(firstStep.getFloatProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\floatProperty", binding), equalTo(secondStep.getFloatProperty()));
    }

    @Test
    void floatRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\floatProperty", binding), nullValue());
    }

    @Test
    void floatRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\floatProperty", binding), nullValue());
    }

    @Test
    void floatRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\floatProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void intRead() {
        firstStep.setIntProperty(Integer.MAX_VALUE);
        secondStep.setIntProperty(Integer.MIN_VALUE);
        assertThat(execute("referenceProperty\\intProperty", binding), equalTo(firstStep.getIntProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\intProperty", binding), equalTo(secondStep.getIntProperty()));
    }

    @Test
    void intRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\intProperty", binding), nullValue());
    }

    @Test
    void intRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\intProperty", binding), nullValue());
    }

    @Test
    void intRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\intProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void longRead() {
        firstStep.setLongProperty(Long.MAX_VALUE);
        secondStep.setLongProperty(Long.MIN_VALUE);
        assertThat(execute("referenceProperty\\longProperty", binding), equalTo(firstStep.getLongProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\longProperty", binding), equalTo(secondStep.getLongProperty()));
    }

    @Test
    void longRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\longProperty", binding), nullValue());
    }

    @Test
    void longRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\longProperty", binding), nullValue());
    }

    @Test
    void longRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\longProperty", BeanWithProperties.class), null), nullValue());
    }

    @Test
    void shortRead() {
        firstStep.setShortProperty(Short.MAX_VALUE);
        secondStep.setShortProperty(Short.MAX_VALUE);
        assertThat(execute("referenceProperty\\shortProperty", binding), equalTo(firstStep.getShortProperty()));
        assertThat(execute("referenceProperty\\referenceProperty\\shortProperty", binding), equalTo(secondStep.getShortProperty()));
    }

    @Test
    void shortRead_nullImmediateLhs() {
        binding.getReferenceProperty().withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\shortProperty", binding), nullValue());
    }

    @Test
    void shortRead_nullOutermostLhs() {
        binding.withReferenceProperty(null);
        assertThat(execute("referenceProperty\\referenceProperty\\shortProperty", binding), nullValue());
    }

    @Test
    void shortRead_nullContext() {
        assertThat(execute(compile("referenceProperty\\referenceProperty\\shortProperty", BeanWithProperties.class), null), nullValue());
    }
}
