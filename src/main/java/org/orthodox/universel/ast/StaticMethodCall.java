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

package org.orthodox.universel.ast;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReference;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

/**
 * A static method call expression, consisting of a name and zero or more parameter expressions.
 */
public class StaticMethodCall extends Expression implements CompositeNode {
    /** A reference to the type declaring the static method. */
    private final TypeReference declaringType;

    /** The return type of the method call. */
    private final TypeReference returnType;

    /** The types of parameters of this method call. */
    private final List<TypeReference> parameterTypes;

    /** The parameter expressions of this method call. */
    private final List<Node> parameters;


    /** The enclosing type within which the static method is declared. */
    private final Class<?> declaringClass;

    /**
     * The name of the method.
     */
    private final String name;

    /**
     * The types of parameters of this method call.
     */
    private final List<Class<?>> parameterClasses;

    /**
     * Consructs a static method call expression, consisting of a name zero parameters.
     *
     * @param typeDescriptor the type of the node.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the static method is declared.
     * @param name the method name.
     */
    public StaticMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name) {
        this(typeDescriptor, tokenImage, declaringClass, name, Collections.emptyList());
    }

    /**
     * Constructs a static method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the static method is declared.
     * @param name the method name.
     * @param parameters the parameter expressions of this method call.
     */
    public StaticMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name, Node ... parameters) {
        this(typeDescriptor, tokenImage, declaringClass, name, asList(parameters));
    }

    /**
     * Constructs a static method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the static method is declared.
     * @param name the method name.
     * @param parameters the parameter expressions of this method call.
     */
    public StaticMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name, List<Node> parameters) {
        this(tokenImage,
             new ResolvedTypeReference(tokenImage, declaringClass),
             new ResolvedTypeReference(tokenImage, typeDescriptor),
             name,
             parameters.stream().map(p -> new ResolvedTypeReference(p.getTokenImage(), p.getTypeDescriptor())).collect(Collectors.toList()),
             parameters
        );
    }

    /**
     * Constructs a static method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the static method is declared.
     * @param name the method name.
     * @param parameterClasses the types of parameters of this method call.
     * @param parameters the parameter expressions of this method call.
     */
    public StaticMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name, List<Class<?>> parameterClasses, List<Node> parameters) {
        this(tokenImage,
             new ResolvedTypeReference(tokenImage, declaringClass),
             new ResolvedTypeReference(tokenImage, typeDescriptor),
             name,
             IntStream.range(0, parameterClasses.size()).mapToObj(i -> new ResolvedTypeReference(parameters.get(i).getTokenImage(), parameterClasses.get(0))).collect(Collectors.toList()),
             parameters
        );
    }

    /**
     * Constructs a static method call expression, consisting of a name and zero or more parameters.
     *
     * @param tokenImage the parser token image.
     * @param declaringType reference to the type declaring the static method.
     * @param returnType the return type of the method call.
     * @param name the method name.
     * @param parameterTypes types of parameters of this method call.
     * @param parameters the parameter expressions of this method call.
     */
    public StaticMethodCall(TokenImage tokenImage,
                            TypeReference declaringType,
                            TypeReference returnType,
                            String name,
                            List<TypeReference> parameterTypes,
                            List<Node> parameters) {
        super(tokenImage);
        this.declaringClass = declaringType.getTypeDescriptor();
        this.parameterClasses = parameterTypes.stream().map(Node::getTypeDescriptor).collect(Collectors.toList());

        this.declaringType = declaringType;
        this.returnType = returnType;
        this.name = name;
        this.parameterTypes = IntStream.range(0, parameterClasses.size()).mapToObj(i -> new ResolvedTypeReference(parameters.get(i).getTokenImage(), parameterClasses.get(0))).collect(Collectors.toList());
        this.parameters = parameters;
    }

    public Class<?> getTypeDescriptor() {
        return returnType == null ? super.getTypeDescriptor() : returnType.getTypeDescriptor();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StaticMethodCall))
            return false;
        if (!super.equals(o))
            return false;
        StaticMethodCall nodes = (StaticMethodCall) o;
        return Objects.equals(declaringClass, nodes.declaringClass)
               && Objects.equals(declaringType, nodes.declaringType)
               && Objects.equals(returnType, nodes.returnType)
               && Objects.equals(name, nodes.name)
               && Objects.equals(parameterTypes, nodes.parameterTypes)
               && Objects.equals(parameters, nodes.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), declaringClass, declaringType, returnType, name, parameterTypes, parameters);
    }

    /**
     * Gets the enclosing type within which the static method is declared.
     *
     * @return the enclosing type within which the static method is declared.
     */
    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    /**
     * Gets a reference to the type declaring the static method.
     *
     * @return a reference to the type declaring the static method.
     */
    public TypeReference getDeclaringType() {
        return declaringType;
    }

    /**
     * Gets the return type of the method call.
     *
     * @return the return type of the method call.
     */
    public TypeReference getReturnType() {
        return returnType;
    }

    /**
     * Gets the types of parameters of this method call.
     *
     * @return the types of parameters of this method call.
     */
    public List<TypeReference> getParameterTypes() {
        return parameterTypes;
    }

    /**
     * Gets the name of the method being called.
     *
     * @return the name of the method.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the types of parameters of this method call.
     *
     * @return the types of parameters of this method call.
     */
    public List<Class<?>> getParameterClasses() {
        return parameterClasses;
    }

    /**
     * Gets the parameter expressions of this method call.
     *
     * @return he parameter expressions of this method call.
     */
    public List<Node> getParameters() {
        return parameters != null ? parameters : Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                   .addNotNull(declaringType, returnType)
                   .addAllNotNull(parameterTypes)
                   .addAllNotNull(parameters)
                   .build();
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitMethodCall(this);
    }
}
