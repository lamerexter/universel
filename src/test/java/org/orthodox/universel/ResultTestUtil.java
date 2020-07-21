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

package org.orthodox.universel;

import org.orthodox.universel.ast.ParameterisedType;
import org.orthodox.universel.exec.TypedValue;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Testing utilities for checking the result and result type return from execution.
 */
public class ResultTestUtil {
    public static void assertResultIsParameterisedType(final TypedValue result,
                                                       final Object resultValue,
                                                       final Class<?> rawType,
                                                       final Class<?> ... parameterisedTypes) {
        assertThat("Result value matches", result.getValue(), equalTo(resultValue));
        assertThat("Result is a parameterised type", result.getValueType(), instanceOf(ParameterisedType.class));

        ParameterisedType resultPt = (ParameterisedType)result.getValueType();

        assertThat("Parameterised type result has correct number of parameter types", resultPt.getTypeParameters().size(), equalTo(parameterisedTypes.length));
        assertThat("Parameterised type raw result matches", resultPt.getRawType().getTypeClass(), equalTo(rawType));

        IntStream.range(0, parameterisedTypes.length)
                 .forEach(i -> assertThat("Parameterised type result has correct parameter type ["+parameterisedTypes[i].getSimpleName()+"] at index "+i,
                                          resultPt.getTypeParameters().get(i).getTypeClass(), equalTo(parameterisedTypes[i])));
    }

    public static <T, R> void assertResultIsParameterisedStream(final TypedValue result,
                                                                final List<?> resultsList,
                                                                final Class<?> ... parameterisedTypes) {
        assertThat("Result is a stream", result.getValue(), instanceOf(Stream.class));
        assertThat("Result stream values matche expected", ((Stream<?>)result.getValue()).collect(Collectors.toList()), equalTo(resultsList));
        assertThat("Result is a parameterised type", result.getValueType(), instanceOf(ParameterisedType.class));

        ParameterisedType resultPt = (ParameterisedType)result.getValueType();

        assertThat("Parameterised type result has correct number of parameter types", resultPt.getTypeParameters().size(), equalTo(parameterisedTypes.length));

        IntStream.range(0, parameterisedTypes.length)
                 .forEach(i -> assertThat("Parameterised type result has correct parameter type ["+parameterisedTypes[i].getSimpleName()+"] at index "+i,
                                          resultPt.getTypeParameters().get(i).getTypeClass(), equalTo(parameterisedTypes[i])));
    }
}
