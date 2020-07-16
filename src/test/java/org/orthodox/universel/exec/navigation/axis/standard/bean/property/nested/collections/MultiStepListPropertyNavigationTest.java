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
import org.orthodox.universel.cst.ParameterisedType;
import org.orthodox.universel.exec.TypedValue;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiStepListPropertyNavigationTest {
    @Test
    void typeRead_reduction() {
        // Given
        final List<BeanWithProperties> collection = asList(
            new BeanWithProperties().withTypeProperty(String.class),
            new BeanWithProperties().withTypeProperty(Integer.class),
            new BeanWithProperties().withTypeProperty(void.class)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(collection);

        // When

        // Then
        TypedValue result = executeWithResult("referenceListProperty\\typeProperty\\[]", binding);

        assertThat(result.getValue(), equalTo(asList(String.class, Integer.class, void.class)));
        assertThat(result.getValueType(), instanceOf(ParameterisedType.class));

        ParameterisedType resultPt = (ParameterisedType)result.getValueType();
        assertThat(resultPt.getRawType().getTypeClass(), equalTo(List.class));
        assertThat(resultPt.getTypeParameters().size(), equalTo(1));
        assertThat(resultPt.getTypeParameters().get(0).getTypeClass(), equalTo(Class.class));
    }
}
