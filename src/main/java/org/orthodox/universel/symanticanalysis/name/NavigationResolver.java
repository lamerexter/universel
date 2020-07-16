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
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.ast.navigation.ReductionNodeTest;
import org.orthodox.universel.compiler.*;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.literals.NullLiteralExpr;
import org.orthodox.universel.cst.methods.GeneratedStaticLambdaFunction;
import org.orthodox.universel.cst.methods.LambdaFunction;
import org.orthodox.universel.cst.methods.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.declaration.ClassDeclaration;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;
import org.orthodox.universel.symanticanalysis.navigation.MapStage;
import org.orthodox.universel.symanticanalysis.navigation.NavigationStage;
import org.orthodox.universel.symanticanalysis.navigation.ReduceStage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.beanplanet.core.lang.TypeUtil.isPrimitiveType;
import static org.orthodox.universel.cst.Modifiers.*;

public class NavigationResolver extends AbstractSemanticAnalyser {

//    @Override
//    public Node visitClassDeclaration(ClassDeclaration node) {
//        getContext().pushScope(new TypeDeclarationScope(node));
//
//        try {
//            return super.visitClassDeclaration(node);
//        } finally {
//            getContext().popScope();
//        }
//    }

//    @Override
//    public Node visitImportDeclaration(final ImportDecl node) {
//        getContext().pushScope(new ImportScope(node, getContext().getMessages()));
//        return super.visitImportDeclaration(node);
//    }

//    @Override
//    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
//        getContext().pushScope(new MethodScope(node));
//
//        try {
//            return super.visitMethodDeclaration(node);
//        } finally {
//            getContext().popScope();
//        }
//    }

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

                Node transformStep = transformStep((NavigationStep<?>) step);

                if (Objects.equals(transformStep, step)) {
                    return null; // Can't proceed yet
//                    getContext().addError(UNRESOLVED_STEP.withRelatedObject(step));
//                    return null; // pointless continuing further along this navigation path now...
                }

                final boolean isSequence = isSequenceType(transformStep);
                final boolean isReduction = (((NavigationStep<?>) step).getNodeTest() instanceof ReductionNodeTest);
                navSages.add(isReduction ? new ReduceStage(transformStep, isSequence, inSequence) : new MapStage(transformStep, isSequence, inSequence));
                inSequence = (inSequence || isSequence) && !isReduction;
            } finally {
                if (previousStepScope != null) getContext().popScope();
            }
        }

        return navSages;
    }

    private List<Node> sequenceAnalysis(final List<NavigationStage> stages) {
        //--------------------------------------------------------------------------------------------------------------
        // Transform the single-cardinality steps back to multi-cardinalities, where applicable.
        //--------------------------------------------------------------------------------------------------------------
        List<Node> multipleCardinalityTransformedSteps = new ArrayList<>(stages.size());
        boolean inStream = false;
        for (int n = 0; n < stages.size(); n++) {
            NavigationStage previousStep = n > 0 ? stages.get(n - 1) : null;
            NavigationStage stage = stages.get(n);
            Node step = stage.getNode();

            if (n != stages.size() - 1) {

                if (stage.isSequence()) {
                    if (inStream) {

                    } else {
                        inStream = true;
                        // Convert to stream
                        step = new TypeConversion(step, Stream.class);
                    }
                } else if (inStream) {
                    Stream s;
                    // Convert singleton to singleton stream and insert into flatmap
                    Class<?> previousStepType = previousStep.isSequence() ? previousStep.getType().getComponentType().getTypeClass() : previousStep.getTypeDescriptor();
                    LambdaFunction generatedMethod = new GeneratedStaticLambdaFunction(new ResolvedTypeReferenceOld(step),
                                                                                       new SimpleNamePath("nav", "step", String.valueOf(n), "fio"),
                                                                                       NodeSequence.<Parameter>builder()
                                                                                           .add(new Parameter(valueOf(FINAL),
                                                                                                              new ResolvedTypeReferenceOld(previousStep.getNode().getTokenImage(), previousStepType),
                                                                                                              false,
                                                                                                              new Name(previousStep.getNode().getTokenImage(), "step")
                                                                                           ))
                                                                                           .build(),
                                                                                       NodeSequence.builder()
                                                                                                   .add(new LoadLocal(previousStep.getNode().getTokenImage(), previousStepType, 0))
                                                                                                   .add(new ReturnStatement(step))
                                                                                                   .build()
                    );
                    step = InternalNodeSequence.builder()
                                               .add(new InstanceMethodCall(Stream.class,
                                                                           step.getTokenImage(),
                                                                           Stream.class,
                                                                           "map",
                                                                           singletonList(Function.class),
                                                                           singletonList(new FunctionalInterfaceObject(step.getTokenImage(),
                                                                                                                       Function.class, Object.class, singletonList(Object.class), "apply",
                                                                                                                       generatedMethod
                                                                                         )
                                                                           )
                                               ))
                                               .build();
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

    public static Function<BigDecimal, ?> doIt() {
        return new Function<BigDecimal, Object>() {

            @Override
            public Object apply(final BigDecimal bigDecimal) {
                return BigDecimal.TEN;
            }
        };
    }

//    @Override
//    public Node visitScript(final Script node) {
//        getContext().pushScope(new ImportScope(getContext().getDefaultImports(), getContext().getMessages()));
//
//        try {
//            return super.visitScript(node);
//        } finally {
//            getContext().popScope();
//        }
//    }
}
