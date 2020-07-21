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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.collections;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;

import java.util.List;

import static java.util.Arrays.asList;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedStream;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedType;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiMapFilterReduceTest {
    private static final String V1 = "Hello";
    private static final String V2 = " ";
    private static final String V3 = "World";
    private static final String V4 = "!";

    private static final BeanWithProperties B1 = new BeanWithProperties().withStringProperty(V1);
    private static final BeanWithProperties B2 = new BeanWithProperties().withStringProperty(V2);
    private static final BeanWithProperties B3 = new BeanWithProperties().withStringProperty(V3);
    private static final BeanWithProperties B4 = new BeanWithProperties().withStringProperty(V4);

    @Test
    void read_multiMapReduce() {
        assertResultIsParameterisedType(executeWithResult("referenceProperty.referenceListProperty.referenceProperty.[[]].stringProperty.[]", createBinding()),
                                        asList(V1, V2, V3, V4), List.class, String.class);
    }

    @Test
    void read_multiMapStream() {
        assertResultIsParameterisedStream(executeWithResult("referenceProperty.referenceListProperty.referenceProperty.[[]].stringProperty", createBinding()),
                                          asList(V1, V2, V3, V4), String.class);
    }

    @Test
    void read_multiContiguousReduce() {
        assertResultIsParameterisedType(executeWithResult("referenceProperty.referenceListProperty.referenceProperty.[[]].[].{}.[]", createBinding()),
                                        asList(B1, B2, B3, B4), List.class, BeanWithProperties.class);
    }

    @Test
    void read_multiContiguousReduceThenMapStream() {
        assertResultIsParameterisedStream(executeWithResult("referenceProperty.referenceListProperty.referenceProperty.[[]].[].{}.[].stringProperty", createBinding()),
                                          asList(V1, V2, V3, V4), String.class);
    }

    private BeanWithProperties createBinding() {
        BeanWithProperties binding = new BeanWithProperties().withReferenceProperty(
            new BeanWithProperties().withReferenceListProperty(
                asList(
                    new BeanWithProperties().withReferenceProperty(B1),
                    new BeanWithProperties().withReferenceProperty(B2),
                    new BeanWithProperties().withReferenceProperty(B3),
                    new BeanWithProperties().withReferenceProperty(B4)
                )
            )
        );
        return binding;
    }
}
