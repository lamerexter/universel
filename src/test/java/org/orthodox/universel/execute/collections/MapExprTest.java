/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

package org.orthodox.universel.execute.collections;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.collections.MapEntryExpr;
import org.orthodox.universel.ast.collections.MapExpr;
import org.orthodox.universel.ast.literals.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;

public class MapExprTest {
    @Test
    void emptyMap() {
        // When
        LinkedHashMap result = Universal.execute(LinkedHashMap.class, "{:}");

        // Then
        assertThat(result, equalTo(Collections.emptyMap()));
    }

    @Test
    void singletonMap() {
        // Given
        LinkedHashMap<Object, Object> expectedResult = new LinkedHashMap<>();
        expectedResult.put("theKey", true);

        // When
        LinkedHashMap result = Universal.execute(LinkedHashMap.class, "{ 'theKey' : true}");

        // Then
        assertThat(result, equalTo(expectedResult));
    }

    @Test
    void multiElementMap() {
        // Given
        Map<String, Object> binding = new HashMap<>();
        binding.put("name", "Fred");

        LinkedHashMap<Object, Object> expectedResult = new LinkedHashMap<>();
        expectedResult.put(1, "one");
        expectedResult.put("Hello World", "Hello World, Fred!");
        expectedResult.put(3, new BigInteger("33"));
        expectedResult.put(true, 4);
        expectedResult.put(false, 5);
        expectedResult.put(2.5f, 6);
        expectedResult.put(7, 7);
        expectedResult.put(new BigDecimal(3.5), 4.5d);

        // When
        LinkedHashMap result = Universal.execute(LinkedHashMap.class,
                                                 "{1 : 'one', \"Hello World\" : 2, 3 : 33I, true : 4, false : 5, 2.5f : 6, 7 : 7, 3.5D : 4.5d, 'Hello World' : \"Hello World, ${name}!\"}",
                                                 binding);

        // Then
        assertThat(result, equalTo(expectedResult));
    }

    @Test
    void mapOfMap() {
        // Given
        LinkedHashMap<Object, Object> expectedInnerResult = new LinkedHashMap<>();
        expectedInnerResult.put(2, "inner");
        LinkedHashMap<Object, Object> expectedResult = new LinkedHashMap<>();
        expectedResult.put(1, expectedInnerResult);


        // When
        LinkedHashMap result = Universal.execute(LinkedHashMap.class, "{1 : {2 : 'inner'}}");

        // Then
        assertThat(result, equalTo(expectedResult));
    }
}