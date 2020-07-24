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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property.nested.collections.set;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.exec.TypedValue;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedType;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiStepBigDecimalPropertySetNavigationTest {
    @Test
    void read_reduction() {
        // Given
        final BigDecimal V1 = new BigDecimal("1");
        final BigDecimal V2 = new BigDecimal("11");
        final BigDecimal V3 = new BigDecimal("111");
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withBigDecimalProperty(V1),
            new BeanWithProperties().withBigDecimalProperty(V2),
            new BeanWithProperties().withBigDecimalProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\bigDecimalProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, BigDecimal.class);
    }

    @Test
    void read_multiSetSteps_reduction() {
        // Given
        final BigDecimal B1V1 = new BigDecimal("11");
        final BigDecimal B1V2 = new BigDecimal("12");
        final BigDecimal B1V3 = new BigDecimal("13");
        final BigDecimal B2V1 = new BigDecimal("21");
        final BigDecimal B2V2 = new BigDecimal("22");
        final BigDecimal B3V1 = new BigDecimal("31");
        final BigDecimal ROGUE = new BigDecimal("999");
        final BeanWithProperties binding
            = new BeanWithProperties()
                  .withReferenceSetProperty(new LinkedHashSet<>(asList(
                      new BeanWithProperties().withReferenceSetProperty(new LinkedHashSet<>(asList(
                          new BeanWithProperties().withBigDecimalProperty(B1V1),
                          new BeanWithProperties().withBigDecimalProperty(B1V2),
                          new BeanWithProperties().withBigDecimalProperty(B1V3)
                      ))),
                      new BeanWithProperties().withReferenceSetProperty(new LinkedHashSet<>(asList(
                          new BeanWithProperties().withBigDecimalProperty(B2V1),
                          new BeanWithProperties().withBigDecimalProperty(B2V2),
                          new BeanWithProperties().withBigDecimalProperty(ROGUE)
                      ))),
                      new BeanWithProperties().withReferenceSetProperty(new LinkedHashSet<>(asList(
                          new BeanWithProperties().withBigDecimalProperty(ROGUE),
                          new BeanWithProperties().withBigDecimalProperty(B3V1)
                      )))
                  )));

        // Then
        assertThat(execute("referenceSetProperty\\referenceSetProperty\\bigDecimalProperty\\[]", binding),
                   equalTo(asList(B1V1, B1V2, B1V3, B2V1, B2V2, ROGUE, ROGUE, B3V1))
        );
        assertThat(execute("referenceSetProperty\\referenceSetProperty\\bigDecimalProperty\\{}", binding),
                   equalTo(new LinkedHashSet<>(asList(B1V1, B1V2, B1V3, B2V1, B2V2, ROGUE, B3V1)))
        );
    }
}
