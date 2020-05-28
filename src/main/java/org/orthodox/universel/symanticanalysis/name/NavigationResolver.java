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

import org.orthodox.universel.ast.InstanceMethodCall;
import org.orthodox.universel.ast.StaticMethodCall;
import org.orthodox.universel.ast.conditionals.IfStatement;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.compiler.*;
import org.orthodox.universel.cst.ImportDecl;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.NullTestExpression;
import org.orthodox.universel.cst.Script;
import org.orthodox.universel.cst.literals.NullLiteralExpr;
import org.orthodox.universel.cst.type.MethodDeclaration;
import org.orthodox.universel.cst.type.declaration.ClassDeclaration;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.beanplanet.core.lang.TypeUtil.isPrimitiveType;
import static org.orthodox.universel.compiler.Messages.NavigationExpression.UNRESOLVED_STEP;

public class NavigationResolver extends AbstractSemanticAnalyser {

    @Override
    public Node visitClassDeclaration(ClassDeclaration node) {
        getContext().pushScope(new TypeDeclarationScope(node));

        try {
            return super.visitClassDeclaration(node);
        } finally {
            getContext().popScope();
        }
    }

    @Override
    public Node visitImportDeclaration(final ImportDecl node) {
        getContext().pushScope(new ImportScope(node, getContext().getMessages()));
        return super.visitImportDeclaration(node);
    }

    @Override
    public Node visitMethodDeclaration(final MethodDeclaration node) {
        getContext().pushScope(new MethodScope(node));

        try {
            return super.visitMethodDeclaration(node);
        } finally {
            getContext().popScope();
        }
    }

    @Override
    public Node visitNavigationStream(final NavigationStream node) {
        final NavigationStream transformedStream = (NavigationStream)super.visitNavigationStream(node);

        if (transformedStream.getChildNodes().isEmpty() || !transformedStream.getChildNodes().stream().allMatch(n -> n instanceof NavigationStep))
            return transformedStream;

        //--------------------------------------------------------------------------------------------------------------
        // The Navigation Stream will be totally transformed into a sequence of other statements/nodes. It is therefore
        // an error if a step cannot be transformed.
        // Determine stream status. Is the LHS context multiple cardinatlity (i.e. Collection, Iterable, Stream ... ?)
        //--------------------------------------------------------------------------------------------------------------
        List<Node> transformedSteps = new ArrayList<>(transformedStream.getSteps().size());
        boolean first = true;
        boolean hasErrors = false;
        boolean isStream = false;
        for (int n=0; n < transformedStream.getSteps().size(); n++) {
            Node child = transformedStream.getSteps().get(n);
            Node transformStep = transformStep((NavigationStep<?>) child);

            if (first) {
                first = false;
            } else {
                getContext().popScope();
            }

            //--------------------------------------------------------------------------------------------------------------
            // If we're dealing with multiple cardinality here (i.e. Collection etc), convert navigation stream to
            // a physical Stream.
            //--------------------------------------------------------------------------------------------------------------
            final boolean inStream = isStream;
            isStream = isStream || determineStreamStatus(transformStep);
            NameScope nameScope = null;
            if ( isStream ) {
                if ( isMultipleCardinality(transformStep) ) {
                    Class<?> componentTypeDescriptor = transformStep.getTypeDescriptor().getComponentType();
                    transformStep = new TypeConversion(transformStep, Stream.class);
                    if ( inStream ) {
                        transformStep = new InstanceMethodCall(Stream.class, transformStep.getTokenImage(), transformStep.getTypeDescriptor(), "flatMap");
                    }
                    nameScope = new BoundScope(componentTypeDescriptor, getContext().getNavigatorRegistry());
                } else {
                    nameScope = new BoundScope(transformStep.getTypeDescriptor(), getContext().getNavigatorRegistry());
                    transformStep = new InstanceMethodCall(Stream.class,
                                                           transformStep.getTokenImage(),
                                                           Stream.class,
                                                           "map",
                                                           singletonList(Function.class),
                                                           singletonList(new StaticMethodCall(Function.class,
                                                                                transformStep.getTokenImage(),
                                                                                getClass(),
                                                                                "doIt"
                                                           )));
                }
            } else {
                nameScope = new BoundScope(transformStep.getTypeDescriptor(), getContext().getNavigatorRegistry());
            }

            transformedSteps.add(transformStep);
            getContext().pushScope(nameScope);

            if (Objects.equals(transformStep, child)) {
                getContext().getMessages().addError(UNRESOLVED_STEP.withRelatedObject(child));
                hasErrors = true;
                break; // pointless continuing further along this navigation path now...
            }

//            if ( n==1 ) break;
        }
        if (!first)
            getContext().popScope();

        if (hasErrors)
            return transformedStream;

        //--------------------------------------------------------------------------------------------------------------
        // Add null/optional safety checks to stream steps 1..n (not the first)
        //--------------------------------------------------------------------------------------------------------------
        Node lastNullSafeNavigation = null;
        for (int n = transformedSteps.size() - 1; n > 0; n--) {
            Node transformStep = transformedSteps.get(n);

            // Navigation is via reference types only, so box if necessary
            if ( isPrimitiveType(transformStep.getTypeDescriptor()) ) {
                transformStep = new BoxConversion(transformStep);
            }
            Node transformedNullSafeStep = InternalNodeSequence
                                               .builder()
                                               .add(transformedSteps.get(n - 1))
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
            lastNullSafeNavigation = transformedSteps.get(0);
//            if (isPrimitiveType(lastNullSafeNavigation.getTypeDescriptor())) {
//                lastNullSafeNavigation = new BoxConversion(lastNullSafeNavigation);
//            }
        }

        return lastNullSafeNavigation;
    }

    private boolean isMultipleCardinality(final Node node) {
        return node.getTypeDescriptor() != null && node.getTypeDescriptor().isArray();
    }

    private boolean determineStreamStatus(final Node node) {
        return isMultipleCardinality(node);
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
//    public Name visitName(Name node) {
//        //--------------------------------------------------------------------------------------------------------------
//        // Check scopes and resolve name to the innermost.
//        // TODO: now this is Script scope ONLY!
//        //--------------------------------------------------------------------------------------------------------------
//        final Class<?> bindingType = getContext().getBindingType();
//        if (bindingType == null)
//            return node;
//
//        return accessNameOnBinding(node);
//    }
//
//    private Node accessNameOnBinding(Name name) {
//        //--------------------------------------------------------------------------------------------------------------
//        // Readable property.
//        //--------------------------------------------------------------------------------------------------------------
//        TypePropertiesSource<?> properties = new TypePropertiesSource<>(getContext().getBindingType());
//        if (!properties.isReadableProperty(name.getName()))
//            return name;
//
//        Method readMethod = properties.assertAndGetPropertyDescriptor(name.getName()).getReadMethod();
//        return new BindingMethodCall(name.getTokenImage(), readMethod, singletonList(new StringLiteralExpr(TokenImage
//                                                                                                               .builder()
//                                                                                                               .range(
//                                                                                                                   name)
//                                                                                                               .image(
//                                                                                                                   readMethod
//                                                                                                                       .getName())
//                                                                                                               .build(),
//                                                                                                           ""
//        )));
//    }


    @Override
    public Node visitScript(final Script node) {
        getContext().pushScope(new ImportScope(getContext().getDefaultImports(), getContext().getMessages()));

        try {
            return super.visitScript(node);
        } finally {
            getContext().popScope();
        }
    }
}
