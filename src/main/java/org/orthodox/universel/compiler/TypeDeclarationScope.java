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

package org.orthodox.universel.compiler;

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationAxis;
import org.orthodox.universel.ast.navigation.NavigationAxisAndNodeTest;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.ast.type.Parameter;
import org.orthodox.universel.ast.type.declaration.FieldRead;
import org.orthodox.universel.ast.type.declaration.FieldWrite;
import org.orthodox.universel.ast.type.declaration.TypeDeclaration;
import org.orthodox.universel.ast.type.declaration.TypeDeclarationReference;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.orthodox.universel.compiler.TransformationUtil.autoBoxOrPromoteIfNecessary;

public class TypeDeclarationScope implements Scope {

    private final TypeDeclaration typeDeclaration;

    public TypeDeclarationScope(final TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    @Override
    public Node navigate(final NavigationAxisAndNodeTest<?> step) {
        if ( !step.getAxis().equals(NavigationAxis.DEFAULT.getCanonicalName()) ) return step;

        if ( step.getNodeTest() instanceof MethodCall ) return navigateMethodCall((NavigationAxisAndNodeTest<MethodCall>)step);
        if ( step.getNodeTest() instanceof NameTest) return navigateName((NavigationAxisAndNodeTest<NameTest>)step);

        return step;
    }

    private Node navigateMethodCall(final NavigationAxisAndNodeTest<MethodCall> step) {
        final MethodCall methodCall = step.getNodeTest();
        return typeDeclaration.getMethods()
                              .stream()
                              .filter(methodDeclaration -> Objects.equals(methodDeclaration.getName(),
                                                                          methodCall.getName().getName()
                              ))
                              .filter(methodCall::parameterTypesMatch)
                              .findFirst()
                              .map(methodDeclaration -> resolveMethodCall(methodCall, methodDeclaration))
                              .orElse(step);
    }

    private Node resolveMethodCall(final MethodCall methodCall, final MethodDeclaration methodDeclaration) {
        List<TypeReference> targetArgumentTypes = methodDeclaration
                                                      .getParameters()
                                                      .getNodes()
                                                      .stream()
                                                      .map(Parameter::getType)
                                                      .collect(Collectors.toList());
        return methodDeclaration.getModifiers().isStatic() ?
               new StaticMethodCall(methodCall.getTokenImage(),
                                    methodDeclaration.getDeclaringType(),
                                    methodDeclaration.getReturnType(),
                                    methodDeclaration.getName(),
                                    targetArgumentTypes,
                                    autoBoxOrPromoteIfNecessary(methodCall.getParameters(), targetArgumentTypes.stream()
                                                                                                               .map(Node::getTypeDescriptor)
                                                                                                               .collect(Collectors.toList())))
               :
               InternalNodeSequence.builder()
                                   .add(new LoadLocal(methodCall.getTokenImage(), new TypeDeclarationReference(typeDeclaration), 0))
                                   .add(new InstanceMethodCall(methodCall.getTokenImage(),
                                                               methodDeclaration.getDeclaringType(),
                                                               methodDeclaration.getReturnType(),
                                                               methodDeclaration.getName(),
                                                               targetArgumentTypes,
                                                               autoBoxOrPromoteIfNecessary(methodCall.getParameters(), targetArgumentTypes.stream()
                                                                                                                                          .map(Node::getTypeDescriptor)
                                                                                                                                          .collect(Collectors.toList()))))
                                   .build();
    }

    private Node navigateName(final NavigationAxisAndNodeTest<NameTest> step) {
        final NameTest nameTest = step.getNodeTest();
        return findFieldByName(nameTest)
                              .map(fieldDeclaration -> resolveFieldGet(step, fieldDeclaration))
                              .findFirst()
                              .orElse(step);
    }

    private Stream<FieldDeclaration> findFieldByName(final NameTest nameTest) {
        return typeDeclaration.getFields()
                              .stream()
                              .filter(fieldDeclaration -> fieldDeclaration.getVariableDeclarations()
                                                                          .stream()
                                                                          .map(VariableDeclaration::getId)
                                                                          .map(VariableDeclarationId::getName).collect(Collectors.toSet()).contains(nameTest.getName())
                              );
    }

    private Node resolveFieldGet(final NavigationAxisAndNodeTest<NameTest> step, final FieldDeclaration fieldDeclaration) {
        return new FieldRead(step.getTokenImage(),
                             fieldDeclaration.getModifiers().isStatic(),
                             new TypeDeclarationReference(typeDeclaration),
                             fieldDeclaration.getDeclarationType(),
                             step.getNodeTest().getName()
        );
    }

    public List<Type> resolveType(NamePath name) {
        if ( typeDeclaration.getName().getName().equals(name.join(".")) ) {
            return singletonList(new TypeDeclarationReference(typeDeclaration));
        }

        return emptyList();
    }

    public Node assign(Node rValue, NavigationAxisAndNodeTest<?> step) {
        if ( !step.getAxis().equals(NavigationAxis.DEFAULT.getCanonicalName()) ) return step;

        if ( step.getNodeTest() instanceof NameTest) return assignToName(rValue, (NavigationAxisAndNodeTest<NameTest>)step);

        return null;
    }

    private Node assignToName(final Node fieldValue, final NavigationAxisAndNodeTest<NameTest> step) {
//        if ( fieldValue.getType() == null || fieldValue.getType().getTypeClass() == null) return null;

        return findFieldByName(step.getNodeTest())
//                   .filter(f -> f.getDeclarationType() != null && f.getDeclarationType().getTypeDescriptor() != null)
//                   .filter(f -> TransformationUtil.isAssignmentCompatible(fieldValue.getType().getTypeClass(), f.getDeclarationType().getTypeDescriptor()))
            .map(f -> new FieldWrite(step.getTokenImage(),
                                     f.getModifiers().isStatic(),
                                     new TypeDeclarationReference(typeDeclaration),
                                     f.getDeclarationType(),
                                     step.getNodeTest().getName(),
                                     fieldValue
                                     ))
            .findFirst()
            .orElse(null);
    }

}
