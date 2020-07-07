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

package org.orthodox.universel.exec.navigation.impl.reduce;

import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.streams.StreamUtil;
import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.allocation.ArrayCreationExpression;
import org.orthodox.universel.ast.allocation.ArrayDimensionsAndInitialiser;
import org.orthodox.universel.ast.functional.FunctionalInterfaceObject;
import org.orthodox.universel.ast.navigation.ArrayNodeTest;
import org.orthodox.universel.ast.navigation.ListNodeTest;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.SetNodeTest;
import org.orthodox.universel.cst.JavaType;
import org.orthodox.universel.cst.Name;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.literals.DecimalIntegerLiteralExpr;
import org.orthodox.universel.cst.type.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReference;
import org.orthodox.universel.cst.type.reference.TypeReference;
import org.orthodox.universel.exec.navigation.MappingNavigator;
import org.orthodox.universel.exec.navigation.Navigator;
import org.orthodox.universel.exec.navigation.ReductionNavigator;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.beanplanet.core.lang.TypeUtil.forName;
import static org.orthodox.universel.cst.Modifiers.*;
import static org.orthodox.universel.cst.Modifiers.FINAL;

@Navigator
public class UniversalCollectionReductionNavigators {
    @ReductionNavigator(axis = "default", collectionType = List.class)
    public static Node toListReduction(final Class<?> fromType, final NavigationStep<ListNodeTest> step) {
        return InternalNodeSequence.builder()
                                   .add(new InstanceMethodCall(step.getTokenImage(),
                                                               new ResolvedTypeReference(step.getTokenImage(), Stream.class),
                                                               new ResolvedTypeReference(step.getTokenImage(), Object.class),
                                                               "collect",
                                                               singletonList(new ResolvedTypeReference(step.getTokenImage(), Collector.class)),
                                                               singletonList(new StaticMethodCall(step.getTokenImage(),
                                                                                           new ResolvedTypeReference(step.getTokenImage(), Collectors.class),
                                                                                           new ResolvedTypeReference(step.getTokenImage(), Collector.class),
                                                                                           "toList"
                                                               ))
                                   ))
                                   .resultType(new JavaType(List.class))
                                   .build();
    }

    @ReductionNavigator(axis = "default", collectionType = Set.class)
    public static Node toSetReduction(final Class<?> fromType, final NavigationStep<SetNodeTest> step) {
        return InternalNodeSequence.builder()
                                   .add(new InstanceMethodCall(step.getTokenImage(),
                                                               new ResolvedTypeReference(step.getTokenImage(), Stream.class),
                                                               new ResolvedTypeReference(step.getTokenImage(), Object.class),
                                                               "collect",
                                                               singletonList(new ResolvedTypeReference(step.getTokenImage(), Collector.class)),
                                                               singletonList(new StaticMethodCall(step.getTokenImage(),
                                                                                                  new ResolvedTypeReference(step.getTokenImage(), Collectors.class),
                                                                                                  new ResolvedTypeReference(step.getTokenImage(), Collector.class),
                                                                                                  "toSet"
                                                               ))
                                   ))
                                   .resultType(new JavaType(Set.class))
                                   .build();
    }

    @ReductionNavigator(axis = "default", collectionType = Object[].class)
    public static Node toArrayReduction(final Class<?> fromType, final NavigationStep<ArrayNodeTest> step) {
        TypeReference arrayType = new ResolvedTypeReference(step.getTokenImage(), forName(fromType, 1));

        Stream s;
        if ( TypeUtil.isPrimitiveType(fromType) ) {
            return InternalNodeSequence.builder()
                                       .add(new InstanceMethodCall(step.getTokenImage(),
                                                                   new ResolvedTypeReference(step.getTokenImage(), Stream.class),
                                                                   new ResolvedTypeReference(step.getTokenImage(), Object.class),
                                                                   "collect",
                                                                   singletonList(new ResolvedTypeReference(step.getTokenImage(), Collector.class)),
                                                                   singletonList(
                                                                       new StaticMethodCall(step.getTokenImage(),
                                                                                            new ResolvedTypeReference(step.getTokenImage(), StreamUtil.class),
                                                                                            new ResolvedTypeReference(step.getTokenImage(), Collector.class),
                                                                                            "to" + StringUtil.initCap(fromType.getSimpleName())+"Array"
                                                                       )

                                                                   )
                                            )
                                       )
                                       .resultType(arrayType)
                                       .build();
        } else {
            MethodDeclaration generatedMethod = new MethodDeclaration(valueOf(PRIVATE | STATIC | SYNTHETIC), null, null, arrayType, "nav$step" + 999 + "$fio",
                                                                      NodeSequence.<Parameter>builder()
                                                                          .add(new Parameter(valueOf(FINAL),
                                                                                             new ResolvedTypeReference(step.getTokenImage(), int.class),
                                                                                             false,
                                                                                             new Name(step.getTokenImage(), "arraySize")
                                                                          ))
                                                                          .build(),
                                                                      NodeSequence.builder()
                                                                                  .add(new ReturnStatement(new ArrayCreationExpression(step.getTokenImage(),
                                                                                                                                       new ResolvedTypeReference(step.getTokenImage(), fromType),
                                                                                                                                       asList(new LoadLocal(step.getTokenImage(), int.class, 0)),
                                                                                                                                       null)
                                                                                  ))
                                                                                  .build()
            );

            return InternalNodeSequence.builder()
                                       .add(new InstanceMethodCall(step.getTokenImage(),
                                                                   new ResolvedTypeReference(step.getTokenImage(), Stream.class),
                                                                   new ResolvedTypeReference(step.getTokenImage(), Object[].class),
                                                                   "toArray",
                                                                   singletonList(new ResolvedTypeReference(step.getTokenImage(), IntFunction.class)),
                                                                   singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                               IntFunction.class,
                                                                                                               Object.class, //forName(fromType, 1),
                                                                                                               singletonList(int.class),
                                                                                                               "apply",
                                                                                                               generatedMethod
                                                                                 )

                                                                   )
                                            )
                                       )
                                       .resultType(arrayType)
                                       .build();
        }
    }
}
