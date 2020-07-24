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

package org.orthodox.universel.symanticanalysis.name;

import org.beanplanet.core.collections.ListBuilder;
import org.beanplanet.core.models.path.SimpleNamePath;
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.conditionals.IfStatement;
import org.orthodox.universel.ast.functional.FunctionalInterfaceObject;
import org.orthodox.universel.ast.literals.NullLiteralExpr;
import org.orthodox.universel.ast.methods.GeneratedStaticLambdaFunction;
import org.orthodox.universel.ast.methods.LambdaFunction;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.ast.navigation.ReductionNodeTest;
import org.orthodox.universel.ast.type.Parameter;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.compiler.BoundScope;
import org.orthodox.universel.compiler.BytecodeHelper;
import org.orthodox.universel.compiler.Scope;
import org.orthodox.universel.exec.navigation.NavigationStreamFunctions;
import org.orthodox.universel.exec.operators.range.NumericRange;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.ResolvedTypeReference;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;
import org.orthodox.universel.symanticanalysis.navigation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.beanplanet.core.lang.TypeUtil.isPrimitiveType;
import static org.orthodox.universel.ast.Modifiers.FINAL;
import static org.orthodox.universel.ast.Modifiers.valueOf;
import static org.orthodox.universel.ast.Type.forClass;
import static org.orthodox.universel.ast.TypeInferenceUtil.isPredicate;
import static org.orthodox.universel.compiler.TransformationUtil.autoBoxIfNecessary;
import static org.orthodox.universel.compiler.TransformationUtil.autoBoxOrPromoteIfNecessary;

public class NavigationResolver extends AbstractSemanticAnalyser {

    @Override
    public Node visitNavigationStream(final NavigationStream node) {
        NavigationStream transformedStream = (NavigationStream) super.visitNavigationStream(node);

        if (transformedStream.getSteps().isEmpty() || !transformedStream.getSteps().stream().allMatch(n -> n instanceof NavigationStep))
            return transformedStream;

        List<NavigationStage> stages = realise(transformedStream);
        if (stages == null) return node;

        List<Node> multipleCardinalityTransformedSteps = sequenceAnalysis(stages);

        //--------------------------------------------------------------------------------------------------------------
        // Add null/optional safety checks to stream steps 1..n (not the first)
        //--------------------------------------------------------------------------------------------------------------
        Node lastNullSafeNavigation = null;
        for (int n = multipleCardinalityTransformedSteps.size() - 1; n > 0; n--) {
            Node transformStep = multipleCardinalityTransformedSteps.get(n);

            // Navigation is via reference types only, so box if necessary
            if (isPrimitiveType(transformStep.getTypeDescriptor())) {
                transformStep = new BoxConversion(transformStep);
            }
            Node transformedNullSafeStep = InternalNodeSequence
                                               .builder()
                                               .add(multipleCardinalityTransformedSteps.get(n - 1))
                                               .add(new IfStatement(transformStep.getTokenImage(),
                                                                    new NullTestExpression(transformStep
                                                                                               .getTokenImage(),
                                                                                           new JvmInstructionNode(transformStep
                                                                                                                      .getTokenImage()) {
                                                                                               @Override
                                                                                               public void emit(BytecodeHelper bch) {
                                                                                                   bch.emitDuplicate();
                                                                                               }
                                                                                           },
                                                                                           true
                                                                    ),
                                                                    lastNullSafeNavigation == null ? transformStep : lastNullSafeNavigation,
                                                                    Collections
                                                                        .emptyList(),
                                                                    InternalNodeSequence
                                                                        .builder()
                                                                        .add(new JvmInstructionNode(transformStep
                                                                                                        .getTokenImage()) {
                                                                            @Override
                                                                            public void emit(BytecodeHelper bch) {
                                                                                bch.emitPop();
                                                                            }
                                                                        })
                                                                        .add(new NullLiteralExpr(transformStep
                                                                                                     .getTokenImage(),
                                                                                                 lastNullSafeNavigation == null ? transformStep
                                                                                                                                      .getTypeDescriptor() : lastNullSafeNavigation
                                                                                                                                                                 .getTypeDescriptor()
                                                                        ))
                                                                        .build()
                                               ))
                                               .build();
            lastNullSafeNavigation = transformedNullSafeStep;
        }

        //--------------------------------------------------------------------------------------------------------------
        // Specifically cater for one-step navigation. This will be quite common place and should pass-through untouched
        // since there is no LHS context which is null unsafe (e.g. a local primitive variable value).
        //--------------------------------------------------------------------------------------------------------------
        if (lastNullSafeNavigation == null) {
            lastNullSafeNavigation = multipleCardinalityTransformedSteps.get(0);
        }

        return lastNullSafeNavigation;
    }

    private List<NavigationStage> realise(final NavigationStream navigationStream) {
        //--------------------------------------------------------------------------------------------------------------
        // Given the scopes in play assume the first step is relative and make absolute.
        //--------------------------------------------------------------------------------------------------------------
        NavigationStep<?> initialStepResolved = resolveStep((NavigationStep<?>) navigationStream.getSteps().get(0));
        final NavigationStream absoluteStream = initialStepResolved == null ?
                                                navigationStream :
                                                new NavigationStream(ListBuilder.<Node>builder()
                                                                         .add(initialStepResolved)
                                                                         .addAll(navigationStream.getSteps())
                                                                         .build());

        //--------------------------------------------------------------------------------------------------------------
        // Transform the logical navigation stream to a physical implementation. The Navigation Stream will be totally
        // transformed into a sequence of other statements/nodes. It is therefore an error if a step cannot be transformed.
        //
        // For single-step navigation the step will be transformed with no intermediate stream.
        // For multi-step navigation where all steps are single cardinality the steps will be transformed with no
        // intermediate stream.
        // For multi-step navigation where one or more steps of multiple cardinalities (i.e. Collection<T>,
        // Iterable<T>, Array<T>) an intermediate stream will be created.
        //--------------------------------------------------------------------------------------------------------------
        List<NavigationStage> navSages = new ArrayList<>(absoluteStream.getSteps().size());
        boolean inSequence = false;
        for (int n = 0; n < absoluteStream.getSteps().size(); n++) {
            NavigationStage previousStep = n > 0 ? navSages.get(n - 1) : null;
            Node step = absoluteStream.getSteps().get(n);

            Scope previousStepScope = null;
            try {
                if (previousStep != null) {
                    getContext().pushScope(previousStepScope = new BoundScope(previousStep.isSequence() ?
                                                                              previousStep.getNode().getType().getComponentType().getTypeClass() :
                                                                              previousStep.getNode().getTypeDescriptor(),
                                                                              getContext().getNavigatorRegistry()
                    ));
                }

                //------------------------------------------------------------------------------------------------------
                // Map / Reduce handling
                //------------------------------------------------------------------------------------------------------
                Node transformStep = transformStep((NavigationStep<?>) step);

                if (Objects.equals(transformStep, step)) {
                    return null; // Can't proceed yet
//                    getContext().addError(UNRESOLVED_STEP.withRelatedObject(step));
//                    return null; // pointless continuing further along this navigation path now...
                }

                final boolean isSequence = isSequenceType(transformStep);
                final boolean isReduction = (((NavigationStep<?>) step).getNodeTest() instanceof ReductionNodeTest);
                navSages.add(isReduction ? new ReduceStage(transformStep, isSequence, inSequence) : new MapStage(transformStep, isSequence, inSequence));
                inSequence = (inSequence || isSequence);
//                inSequence = (inSequence || isSequence) && !isReduction;

                //------------------------------------------------------------------------------------------------------
                // Filter handling
                //------------------------------------------------------------------------------------------------------
                for (Node filter : ((NavigationStep<?>) step).getFilters()) {
                    Type filterType = filter.getType();
                    if (filterType == null) return null; // Can't proceed yet

                    if (isPredicate(filterType)) {
                        navSages.add(new PredicateFilterStage(filter, inSequence));
                    } else if (isIndexFilter(filterType)) {
                        navSages.add(new IndexFilterStage(filter, inSequence));
                    } else if (isIndexRangeFilter(filterType)) {
                        navSages.add(new IndexRangeFilterStage(filter, inSequence));
                    }
                }
            } finally {
                if (previousStepScope != null) getContext().popScope();
            }
        }

        return navSages;
    }

    private boolean isIndexRangeFilter(final Type filterType) {
        return NumericRange.class.isAssignableFrom(filterType.getTypeClass());
    }

    private boolean isIndexFilter(final Type filterType) {
        return TypeInferenceUtil.isIntegerType(filterType);
    }

    private List<Node> sequenceAnalysis(final List<NavigationStage> stages) {
        //--------------------------------------------------------------------------------------------------------------
        // Transform the single-cardinality steps back to multi-cardinalities, where applicable.
        //--------------------------------------------------------------------------------------------------------------
        List<Node> multipleCardinalityTransformedSteps = new ArrayList<>(stages.size());
        boolean inStream = false;
        Type prevMapStageType = null;
        for (int n = 0; n < stages.size(); n++) {
            NavigationStage previousStage = n > 0 ? stages.get(n - 1) : null;
            NavigationStage stage = stages.get(n);
            Node step = stage.getNode();

//            if (n != stages.size() - 1) {
            boolean isLastStage = n == stages.size() - 1;
            if (stage.isReduce()) {
                if (!isLastStage) {
                    inStream = true;
                    // Convert to stream
                    step = new TypeConversion(step, new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), step.getType().getComponentType()));
                }
            } else if (stage.isMap()) {
                if (stage.isSequence()) {
                    prevMapStageType = step.getType().getComponentType();
                    if (inStream) {
                        // Convert sequence to stream and flatMap into existing stream
                        LambdaFunction conversionLambda = new GeneratedStaticLambdaFunction(new ResolvedTypeReference(step.getTokenImage(), forClass(Stream.class)),
                                                                                            new SimpleNamePath("nav", "flatMap", "stage", String.valueOf(n), "fio"),
                                                                                            NodeSequence.<Parameter>builder()
                                                                                                .add(new Parameter(valueOf(FINAL),
                                                                                                                   new ResolvedTypeReference(previousStage.getNode().getTokenImage(), prevMapStageType),
                                                                                                                   false,
                                                                                                                   new Name(previousStage.getNode().getTokenImage(), "step")
                                                                                                ))
                                                                                                .build(),
                                                                                            NodeSequence.builder()
                                                                                                        .add(new ReturnStatement(
                                                                                                                 InternalNodeSequence.builder()
                                                                                                                                     .add(new LoadLocal(previousStage.getNode().getTokenImage(), prevMapStageType, 0))
                                                                                                                                     .add(new TypeConversion(step, new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), step.getType().getComponentType())))
                                                                                                                                     .build()
                                                                                                             )
                                                                                                        )
                                                                                                        .build()
                        );
                        step = InternalNodeSequence.builder()
                                                   .add(new InstanceMethodCall(step.getTokenImage(),
                                                                               new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                               new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), step.getType().getComponentType()),
                                                                               "flatMap",
                                                                               singletonList(new ResolvedTypeReferenceOld(Function.class)),
                                                                               singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                                           Function.class, Object.class, singletonList(Object.class), "apply",
                                                                                                                           conversionLambda
                                                                                             )
                                                                               )
                                                   ))
                                                   .build();

                    } else if (!isLastStage) {
                        inStream = true;
                        // Convert to stream
                        step = new TypeConversion(step, new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), step.getType().getComponentType()));
                    }
                } else if (inStream) {

                    // Convert singleton to singleton stream and insert into flatmap
                    LambdaFunction generatedMethod = new GeneratedStaticLambdaFunction(new ResolvedTypeReferenceOld(step),
                                                                                       new SimpleNamePath("nav", "map", "stage", String.valueOf(n), "fio"),
                                                                                       NodeSequence.<Parameter>builder()
                                                                                           .add(new Parameter(valueOf(FINAL),
                                                                                                              new ResolvedTypeReferenceOld(previousStage.getNode().getTokenImage(), prevMapStageType.getTypeClass()),
                                                                                                              false,
                                                                                                              new Name(previousStage.getNode().getTokenImage(), "step")
                                                                                           ))
                                                                                           .build(),
                                                                                       NodeSequence.builder()
                                                                                                   .add(new LoadLocal(previousStage.getNode().getTokenImage(), prevMapStageType.getTypeClass(), 0))
                                                                                                   .add(new ReturnStatement(step))
                                                                                                   .build()
                    );
                    step = InternalNodeSequence.builder()
                                               .add(new InstanceMethodCall(step.getTokenImage(),
                                                                           new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                           new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), step.getType()),
                                                                           "map",
                                                                           singletonList(new ResolvedTypeReferenceOld(Function.class)),
                                                                           singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                                       Function.class, Object.class, singletonList(Object.class), "apply",
                                                                                                                       generatedMethod
                                                                                         )
                                                                           )
                                               ))
                                               .build();
                } else {
                    prevMapStageType = step.getType();
                }
            } else if (stage.isFilter()) {
                if (stage instanceof PredicateFilterStage) {
                    if (inStream) {
                        // Simply translate the filter to a stream filter operation
                        LambdaFunction generatedMethod = new GeneratedStaticLambdaFunction(new ResolvedTypeReferenceOld(step.getTokenImage(), boolean.class),
                                                                                           new SimpleNamePath("nav", "predicatefilter", "stage", String.valueOf(n), "fio"),
                                                                                           NodeSequence.<Parameter>builder()
                                                                                               .add(new Parameter(valueOf(FINAL),
                                                                                                                  new ResolvedTypeReferenceOld(previousStage.getNode().getTokenImage(), prevMapStageType.getTypeClass()),
                                                                                                                  false,
                                                                                                                  new Name(previousStage.getNode().getTokenImage(), "filter")
                                                                                               ))
                                                                                               .build(),
                                                                                           NodeSequence.builder()
                                                                                                       .add(new LoadLocal(previousStage.getNode().getTokenImage(), (TypeReference) prevMapStageType, 0))
                                                                                                       .add(new ReturnStatement(autoBoxIfNecessary(step, boolean.class)))
                                                                                                       .build()
                        );
                        step = InternalNodeSequence.builder()
                                                   .add(new InstanceMethodCall(step.getTokenImage(),
                                                                               new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                               new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), prevMapStageType),
                                                                               "filter",
                                                                               singletonList(new ResolvedTypeReferenceOld(Predicate.class)),
                                                                               singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                                           Predicate.class, boolean.class, singletonList(Object.class), "test",
                                                                                                                           generatedMethod
                                                                                             )
                                                                               )
                                                   ))
                                                   .build();
                    }
                } else if (stage instanceof IndexFilterStage) {
                    if (inStream) {
                        step = InternalNodeSequence.builder()
                                                   .add(new InstanceMethodCall(step.getTokenImage(),
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
                                                   .add(new StaticMethodCall(step.getTokenImage(),
                                                                             new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                             new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), prevMapStageType),
                                                                             "of",
                                                                             singletonList(new ResolvedTypeReference(step.getTokenImage(), forClass(Object.class))),
                                                                             singletonList(new InstanceMethodCall(step.getTokenImage(),
                                                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), List.class),
                                                                                                                  new ResolvedTypeReference(step.getTokenImage(), forClass(Object.class)),
                                                                                                                  "get",
                                                                                                                  singletonList(new ResolvedTypeReference(step.getTokenImage(), forClass(int.class))),
                                                                                                                  singletonList(autoBoxOrPromoteIfNecessary(step, int.class))
                                                                             ))
                                                   ))
                                                   .resultType(new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), prevMapStageType))
                                                   .build();
                    }
                } else if (stage instanceof IndexRangeFilterStage) {
                    if (inStream) {
                        step = new StaticMethodCall(step.getTokenImage(),
                                                    new ResolvedTypeReferenceOld(step.getTokenImage(), NavigationStreamFunctions.class),
                                                    new ParameterisedTypeImpl(step.getTokenImage(), forClass(Stream.class), prevMapStageType),
                                                    "indexRangeStream",
                                                    asList(new ResolvedTypeReference(step.getTokenImage(), forClass(List.class)),
                                                           new ResolvedTypeReference(step.getTokenImage(), forClass(NumericRange.class))
                                                    ),
                                                    asList(new InstanceMethodCall(step.getTokenImage(),
                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), Stream.class),
                                                                                  new ResolvedTypeReferenceOld(step.getTokenImage(), Object.class),
                                                                                  "collect",
                                                                                  singletonList(new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class)),
                                                                                  singletonList(new StaticMethodCall(step.getTokenImage(),
                                                                                                                     new ResolvedTypeReferenceOld(step.getTokenImage(), Collectors.class),
                                                                                                                     new ResolvedTypeReferenceOld(step.getTokenImage(), Collector.class),
                                                                                                                     "toList"
                                                                                  ))
                                                           ),
                                                           step
                                                    )
                        );
                    }
                }
            }

            multipleCardinalityTransformedSteps.add(step);
        }

        return multipleCardinalityTransformedSteps;
    }

    private NavigationStep<?> resolveStep(final NavigationStep<?> step) {
        return getContext().scopes()
                           .map(s -> s.resolveInitial(step))
                           .filter(Objects::nonNull)
                           .findFirst().orElse(null);
    }

    private boolean isSequenceType(final Node node) {
        return node.getType() != null && node.getType().isSequence();
    }

    private Node transformStep(final NavigationStep step) {
        return getContext().scopes()
                           .map(s -> s.navigate(step))
                           .filter(Objects::nonNull)
                           .filter(n -> !Objects.equals(step, n))
                           .findFirst().orElse(step);
    }
}
