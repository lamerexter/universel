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
import org.beanplanet.core.models.path.SimpleNamePath;
import org.beanplanet.core.streams.StreamUtil;
import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.allocation.ArrayCreationExpression;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.ast.functional.FunctionalInterfaceObject;
import org.orthodox.universel.ast.navigation.ArrayNodeTest;
import org.orthodox.universel.ast.navigation.ListNodeTest;
import org.orthodox.universel.ast.navigation.NavigationAxisAndNodeTest;
import org.orthodox.universel.ast.navigation.SetNodeTest;
import org.orthodox.universel.ast.Name;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.ParameterisedTypeImpl;
import org.orthodox.universel.ast.methods.GeneratedStaticLambdaFunction;
import org.orthodox.universel.ast.methods.LambdaFunction;
import org.orthodox.universel.ast.type.Parameter;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.exec.navigation.Navigator;
import org.orthodox.universel.exec.navigation.ReductionNavigator;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.beanplanet.core.lang.TypeUtil.ensureNonPrimitiveType;
import static org.beanplanet.core.lang.TypeUtil.forName;
import static org.orthodox.universel.ast.Modifiers.FINAL;
import static org.orthodox.universel.ast.Modifiers.valueOf;

@Navigator
public class UniversalCollectionReductionNavigators {
    @ReductionNavigator(axis = "default", collectionType = List.class)
    public static Node toListReduction(final Class<?> fromType,
                                       final Node instanceReadAccessor,
                                       final NavigationAxisAndNodeTest<ListNodeTest> step
    ) {
        return InternalNodeSequence.builder()
                                   .add(new InstanceMethodCall(step.getTokenImage(),
                                                               instanceReadAccessor,
                                                               new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                               new ResolvedTypeReferenceOld(step.getTokenImage(), Object.class),
                                                               "collect",
                                                               singletonList(new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class)),
                                                               singletonList(new StaticMethodCall(step.getTokenImage(),
                                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), Collectors.class),
                                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class),
                                                                                                  "toList"
                                                               ))
                                   ))
                                   .resultType(new ParameterisedTypeImpl(new ResolvedTypeReferenceOld(step.getTokenImage(), List.class),
                                                                         asList(new ResolvedTypeReferenceOld(step.getTokenImage(), ensureNonPrimitiveType(fromType)))
                                   ))
                                   .build();
    }

    @ReductionNavigator(axis = "default", collectionType = Set.class)
    public static Node toSetReduction(final Class<?> fromType,
                                      final Node instanceReadAccessor,
                                      final NavigationAxisAndNodeTest<SetNodeTest> step
    ) {
        return InternalNodeSequence.builder()
                                   .add(new InstanceMethodCall(step.getTokenImage(),
                                                               instanceReadAccessor,
                                                               new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                               new ResolvedTypeReferenceOld(step.getTokenImage(), Object.class),
                                                               "collect",
                                                               singletonList(new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class)),
                                                               singletonList(new StaticMethodCall(step.getTokenImage(),
                                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), Collectors.class),
                                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class),
                                                                                                  "toCollection",
                                                                                                  singletonList(new ResolvedTypeReferenceOld(step.getTokenImage(), Supplier.class)),
                                                                                                  singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                                                              Supplier.class,
                                                                                                                                              Object.class,
                                                                                                                                              emptyList(),
                                                                                                                                              "get",
                                                                                                                                              new GeneratedStaticLambdaFunction(new ResolvedTypeReferenceOld(step.getTokenImage(), Collection.class),
                                                                                                                                                                                new SimpleNamePath("reduction", "fio"),
                                                                                                                                                                                NodeSequence.emptyNodeSequence(),
                                                                                                                                                                                NodeSequence.builder()
                                                                                                                                                                                            .add(new ReturnStatement(new ObjectCreationExpression(step.getTokenImage(),
                                                                                                                                                                                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), LinkedHashSet.class)
                                                                                                                                                                                                 ))
                                                                                                                                                                                            )
                                                                                                                                                                                            .build()
                                                                                                                                              )
                                                                                                                )

                                                                                                  )
                                                               ))
                                   ))
                                   .resultType(new ParameterisedTypeImpl(new ResolvedTypeReferenceOld(step.getTokenImage(), LinkedHashSet.class),
                                                                         asList(new ResolvedTypeReferenceOld(step.getTokenImage(), ensureNonPrimitiveType(fromType)))
                                   ))
                                   .build();
    }

    @ReductionNavigator(axis = "default", collectionType = Object[].class)
    public static Node toArrayReduction(final Class<?> fromType,
                                        final Node instanceReadAccessor,
                                        final NavigationAxisAndNodeTest<ArrayNodeTest> step
    ) {
        TypeReference arrayType = new ResolvedTypeReferenceOld(step.getTokenImage(), forName(fromType, 1));

        if (TypeUtil.isPrimitiveType(fromType)) {
            return InternalNodeSequence.builder()
                                       .add(new InstanceMethodCall(step.getTokenImage(),
                                                                   instanceReadAccessor,
                                                                   new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                   new ResolvedTypeReferenceOld(step.getTokenImage(), Object.class),
                                                                   "collect",
                                                                   singletonList(new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class)),
                                                                   singletonList(
                                                                       new StaticMethodCall(step.getTokenImage(),
                                                                                            new ResolvedTypeReferenceOld(step.getTokenImage(), StreamUtil.class),
                                                                                            new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class),
                                                                                            "to" + StringUtil.initCap(fromType.getSimpleName()) + "Array"
                                                                       )

                                                                   )
                                            )
                                       )
                                       .resultType(arrayType)
                                       .build();
        } else {
            LambdaFunction generatedMethod = new GeneratedStaticLambdaFunction(arrayType,
                                                                               new SimpleNamePath("nav", "step", "fio"),
                                                                               NodeSequence.<Parameter>builder()
                                                                                   .add(new Parameter(valueOf(FINAL),
                                                                                                      new ResolvedTypeReferenceOld(step.getTokenImage(), int.class),
                                                                                                      false,
                                                                                                      new Name(step.getTokenImage(), "arraySize")
                                                                                   ))
                                                                                   .build(),
                                                                               NodeSequence.builder()
                                                                                           .add(new ReturnStatement(new ArrayCreationExpression(step.getTokenImage(),
                                                                                                                                                new ResolvedTypeReferenceOld(step.getTokenImage(), fromType),
                                                                                                                                                asList(new LoadLocal(step.getTokenImage(), int.class, 0)),
                                                                                                                                                null
                                                                                           )
                                                                                           ))
                                                                                           .build()
            );

            return InternalNodeSequence.builder()
                                       .add(new InstanceMethodCall(step.getTokenImage(),
                                                                   new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                   new ResolvedTypeReferenceOld(step.getTokenImage(), Object[].class),
                                                                   "toArray",
                                                                   singletonList(new ResolvedTypeReferenceOld(step.getTokenImage(), IntFunction.class)),
                                                                   singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                               IntFunction.class,
                                                                                                               Object.class,
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
