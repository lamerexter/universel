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

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.orthodox.universel.ResultTestUtil.assertResultIsParameterisedType;
import static org.orthodox.universel.Universal.executeWithResult;

public class MultiStepBigIntegerPropertyListNavigationTest {
    @Test
    void read_reduction() {
        // Given
        final BigInteger V1 = new BigInteger("1");
        final BigInteger V2 = new BigInteger("11");
        final BigInteger V3 = new BigInteger("111");
        final Set<BeanWithProperties> collection = new LinkedHashSet<>(asList(
            new BeanWithProperties().withBigIntegerProperty(V1),
            new BeanWithProperties().withBigIntegerProperty(V2),
            new BeanWithProperties().withBigIntegerProperty(V3)
        ));
        final BeanWithProperties binding = new BeanWithProperties().withReferenceSetProperty(collection);

        // When
        TypedValue result = executeWithResult("referenceSetProperty\\bigIntegerProperty\\{}", binding);

        // Then
        assertResultIsParameterisedType(result, new LinkedHashSet<>(asList(V1, V2, V3)), LinkedHashSet.class, BigInteger.class);
    }
}
