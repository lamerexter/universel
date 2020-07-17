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
import org.orthodox.universel.ast.InstanceMethodCall;
import org.orthodox.universel.ast.LoadLocal;
import org.orthodox.universel.ast.StaticMethodCall;
import org.orthodox.universel.ast.navigation.NavigationAxis;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.cst.MethodCall;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Type;
import org.orthodox.universel.cst.methods.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.declaration.TypeDeclaration;
import org.orthodox.universel.cst.type.declaration.TypeDeclarationReference;
import org.orthodox.universel.cst.type.reference.TypeReference;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.orthodox.universel.compiler.TransformationUtil.autoBoxOrPromoteIfNecessary;

public class TypeDeclarationScope implements Scope {

    private final TypeDeclaration typeDeclaration;

    public TypeDeclarationScope(final TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    @Override
    public Node navigate(final NavigationStep<?> step) {
        if ( !step.getAxis().equals(NavigationAxis.DEFAULT.getCanonicalName()) ||
             !(step.getNodeTest() instanceof MethodCall) ) return step;

        final MethodCall methodCall = (MethodCall)step.getNodeTest();
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

    public List<Type> resolveType(NamePath name) {
        if ( typeDeclaration.getName().getName().equals(name.join(".")) ) {
            return singletonList(new TypeDeclarationReference(typeDeclaration));
        }

        return emptyList();
    }

}
