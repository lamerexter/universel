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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.collections.list;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.exec.TypedValue;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedType;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiStepBigDecimalPropertyListNavigationTest {
    @Test
    void read_reduction() {
        // Given
        final BigDecimal V1 = new BigDecimal("1");
        final BigDecimal V2 = new BigDecimal("11");
        final BigDecimal V3 = new BigDecimal("111");
        final List<BeanWithProperties> collection = asList(
            new BeanWithProperties().withBigDecimalProperty(V1),
            new BeanWithProperties().withBigDecimalProperty(V2),
            new BeanWithProperties().withBigDecimalProperty(V3)
        );
        final BeanWithProperties binding = new BeanWithProperties().withReferenceListProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceListProperty\\bigDecimalProperty\\[]", binding);

        // Then
        assertResultIsParameterisedType(result, asList(V1, V2, V3), List.class, BigDecimal.class);
    }
}
