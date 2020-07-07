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

package org.orthodox.universel.exec.navigation.axis.standard.bean.property;

import org.orthodox.universel.BeanWithProperties;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaGetterTester {
    private static final MethodType SIGNATURE = MethodType.methodType(BigDecimal.class, BeanWithProperties.class);//signature of method INVOK
    private static Function<BeanWithProperties, BigDecimal> BD_GETTER;

    public static final BigDecimal L11 = new BigDecimal("1");
    public static final BigDecimal L12 = new BigDecimal("11");
    public static final BigDecimal L13 = new BigDecimal("111");
    public static final BeanWithProperties[] R1 = {
        new BeanWithProperties().withBigDecimalProperty(L11),
        new BeanWithProperties().withBigDecimalProperty(L12),
        new BeanWithProperties().withBigDecimalProperty(L13)
    };

    public static final void main(String ... args) throws Throwable {
        System.out.println(Arrays.stream(R1)
                                 .map(b -> b.getBigDecimalProperty())
                                 .collect(Collectors.toList()));
    }

    public static final void main1(String ... args) throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();

        final CallSite site = LambdaMetafactory.metafactory(lookup,
                                                            "apply",
                                                            MethodType.methodType(Function.class),
                                                            MethodType.methodType(Object.class, Object.class),
                                                            lookup.findStatic(LambdaGetterTester.class, "doit", SIGNATURE),
                                                            SIGNATURE);
        BD_GETTER = (Function)site.getTarget().invokeExact();
        Function<BeanWithProperties, BigDecimal> BD_GETTER1 = (Function)site.getTarget().invokeExact();
        // Now test it!
        List<BigDecimal> result = Arrays.asList(R1)
                                         .stream()
                                         .map(BD_GETTER)
                                         .collect(Collectors.toList());
        System.out.println("Result is "+result);
        System.out.println("#1 = "+BD_GETTER+" #2 = "+BD_GETTER1);
    }

    public static BigDecimal doit(BeanWithProperties bean) {
        return bean.getBigDecimalProperty();
    }
}
