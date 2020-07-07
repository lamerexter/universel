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

import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReference;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * An intance method call expression, consisting of a name and zero or more parameter expressions.
 */
public class InstanceMethodCall extends Expression implements CompositeNode {
    /** A reference to the type declaring the static method. */
    private final TypeReference declaringType;

    /** The return type of the method call. */
    private final TypeReference returnType;

    /**
     * The name of the method.
     */
    private final String name;

    /** The types of parameters of this method call. */
    private final List<TypeReference> parameterTypes;

    /**
     * The parameter expressions of this method call.
     */
    private final List<Node> parameters;



    /** The enclosing type within which the instance method is declared. */
    private final Class<?> declaringClass;

    /**
     * The types of parameters of this method call.
     */
    private final List<Class<?>> parameterClasses;

    /**
     * Constructs an instance method call expression, consisting of a name zero parameters.
     *
     * @param typeDescriptor the type of the node.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the instance method is declared.
     * @param name the method name.
     */
    public InstanceMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name) {
        this(typeDescriptor, tokenImage, declaringClass, name, emptyList());
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the instance method is declared.
     * @param name the method name.
     * @param parameters the parameter expressions of this method call.
     */
    public InstanceMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name, Node ... parameters) {
        this(typeDescriptor, tokenImage, declaringClass, name, asList(parameters));
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the instance method is declared.
     * @param name the method name.
     * @param parameters the parameter expressions of this method call.
     */
    public InstanceMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name, List<Node> parameters) {
        this(typeDescriptor, tokenImage, declaringClass, name, parameters.stream().map(Node::getTypeDescriptor).collect(Collectors.toList()),parameters);
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage the parser token image.
     * @param declaringClass the enclosing type within which the static method is declared.
     * @param name the method name.
     * @param parameterClasses the types of parameters of this method call.
     * @param parameters the parameter expressions of this method call.
     */
    public InstanceMethodCall(Class<?> typeDescriptor, TokenImage tokenImage, Class<?> declaringClass, String name, List<Class<?>> parameterClasses, List<Node> parameters) {
        super(tokenImage, typeDescriptor);
        this.declaringClass = declaringClass;
        this.parameterClasses = parameterClasses;

        this.declaringType = new ResolvedTypeReference(tokenImage, declaringClass);
        this.returnType = new ResolvedTypeReference(tokenImage, typeDescriptor);
        this.parameterTypes = IntStream.range(0, parameterClasses.size()).mapToObj(i -> new ResolvedTypeReference(parameters.get(i).getTokenImage(), parameterClasses.get(0))).collect(Collectors.toList());
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero parameters.
     *
     * @param tokenImage the parser token image.
     * @param declaringType reference to the type declaring the static method.
     * @param returnType the return type of the method call.
     * @param name the method name.
     */
    public InstanceMethodCall(TokenImage tokenImage,
                              TypeReference declaringType,
                              TypeReference returnType,
                              String name) {
        this(tokenImage, declaringType, returnType, name, emptyList(), emptyList());
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param tokenImage the parser token image.
     * @param declaringType reference to the type declaring the static method.
     * @param returnType the return type of the method call.
     * @param name the method name.
     * @param parameterTypes types of parameters of this method call.
     * @param parameters the parameter expressions of this method call.
     */
    public InstanceMethodCall(TokenImage tokenImage,
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
     * Gets the parameter expressions of this method call.
     *
     * @return he parameter expressions of this method call.
     */
    public List<Node> getParameters() {
        return parameters != null ? parameters : emptyList();
    }

    /**
     * Gets the enclosing type within which the instance method is declared.
     *
     * @return the enclosing type within which the instance method is declared.
     */
    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    /**
     * Gets the types of parameters of this method call.
     *
     * @return the types of parameters of this method call.
     */
    public List<Class<?>> getParameterClasses() {
        return parameterClasses;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Node> getChildNodes() {
        return parameters == null ? emptyList() : (List) parameters;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitMethodCall(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof InstanceMethodCall))
            return false;
        if (!super.equals(o))
            return false;
        InstanceMethodCall nodes = (InstanceMethodCall) o;
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

}
