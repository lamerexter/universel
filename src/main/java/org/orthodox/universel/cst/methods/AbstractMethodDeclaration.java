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

package org.orthodox.universel.cst.methods;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.ast.NodeSequence.emptyNodeSequence;

/**
 * A Universel method declaration on the Abstract Syntax Tree (AST).
 */
public abstract class AbstractMethodDeclaration extends Node implements CompositeNode {
    private final Modifiers modifiers;
    private final NodeSequence<TypeParameter> typeParameters;
    private TypeReference declaringType;
    private final TypeReference returnType;
    private final NodeSequence<Parameter> parameters;
    private final NodeSequence<Node> body;

    public AbstractMethodDeclaration(final Modifiers modifiers,
                                     final NodeSequence<TypeParameter> typeParameters,
                                     final TypeReference declaringType,
                                     final TypeReference returnType,
                                     final NodeSequence<Parameter> parameters,
                                     final NodeSequence<Node> body) {
        super(TokenImage.builder()
                        .add(modifiers)
                        .add(typeParameters)
                        .add(returnType)
                        .add(parameters)
                        .add(body)
                        .build());
        this.modifiers = modifiers;
        this.typeParameters = typeParameters;
        this.declaringType = declaringType;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    public Modifiers getModifiers() {
        return modifiers;
    }

    public NodeSequence<TypeParameter> getTypeParameters() {
        return typeParameters == null ? emptyNodeSequence() : typeParameters;
    }

    public TypeReference getDeclaringType() {
        return declaringType;
    }

    public void setDeclaringType(final TypeReference declaringType) {
        this.declaringType = declaringType;
    }

    public TypeReference getReturnType() {
        return returnType;
    }

    public NodeSequence<Parameter> getParameters() {
        return parameters == null ? NodeSequence.emptyNodeSequence() : parameters;
    }

    public NodeSequence<Node> getBody() {
        return body;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMethodDeclaration)) return false;
        if (!super.equals(o)) return false;
        AbstractMethodDeclaration nodes = (AbstractMethodDeclaration) o;
        return Objects.equals(getModifiers(), nodes.getModifiers()) &&
               Objects.equals(getTypeParameters(), nodes.getTypeParameters()) &&
               Objects.equals(getDeclaringType(), nodes.getDeclaringType()) &&
               Objects.equals(getReturnType(), nodes.getReturnType()) &&
               Objects.equals(getParameters(), nodes.getParameters()) &&
               Objects.equals(getBody(), nodes.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                            getModifiers(),
                            getTypeParameters(),
                            getDeclaringType(),
                            getReturnType(),
                            getParameters(),
                            getBody()
        );
    }

    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                   .addNotNull(modifiers)
                   .addAllNotNull(typeParameters == null ? null : typeParameters.getNodes())
                   .addNotNull(declaringType)
                   .addNotNull(returnType)
                   .addAllNotNull(parameters.getNodes())
                   .addAllNotNull(body.getNodes())
            .build();
    }
}
