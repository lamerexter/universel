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

import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.ast.type.reference.TypeReference;

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
    /**
     * A factory for the instance on which this method call will take place.
     */
    private final Node instance;

    /**
     * A reference to the type declaring the static method.
     */
    private final TypeReference declaringType;

    /**
     * The return type of the method call.
     */
    private final TypeReference returnType;

    /**
     * The name of the method.
     */
    private final String name;

    /**
     * The types of parameters of this method call.
     */
    private final List<TypeReference> parameterTypes;

    /**
     * The parameter expressions of this method call.
     */
    private final List<Node> parameters;


    /**
     * Constructs an instance method call expression, consisting of a name zero parameters.
     *
     * @param typeDescriptor the type of the node.
     * @param tokenImage     the parser token image.
     * @param declaringClass the enclosing type within which the instance method is declared.
     * @param name           the method name.
     */
    public InstanceMethodCall(final Class<?> typeDescriptor,
                              final TokenImage tokenImage,
                              final Class<?> declaringClass,
                              final String name
    ) {
        this(typeDescriptor, tokenImage, declaringClass, name, emptyList());
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     * @param tokenImage     the parser token image.
     * @param declaringClass the enclosing type within which the instance method is declared.
     * @param name           the method name.
     * @param parameters     the parameter expressions of this method call.
     */
    public InstanceMethodCall(final Class<?> typeDescriptor,
                              final TokenImage tokenImage,
                              final Class<?> declaringClass,
                              final String name,
                              final Node... parameters
    ) {
        this(typeDescriptor, tokenImage, declaringClass, name, asList(parameters));
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor   the type of the node, or null if unknown at this time.
     * @param tokenImage       the parser token image.
     * @param declaringClass   the enclosing type within which the static method is declared.
     * @param name             the method name.
     * @param parameterClasses the types of parameters of this method call.
     * @param parameters       the parameter expressions of this method call.
     */
    public InstanceMethodCall(final Class<?> typeDescriptor,
                              final TokenImage tokenImage,
                              final Class<?> declaringClass,
                              final String name,
                              final List<Class<?>> parameterClasses,
                              final List<Node> parameters
    ) {
        this(typeDescriptor, tokenImage, null, declaringClass, name, parameterClasses, parameters);
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
        this(typeDescriptor, tokenImage, null, declaringClass, name, parameters.stream().map(Node::getTypeDescriptor).collect(Collectors.toList()),parameters);
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param typeDescriptor   the type of the node, or null if unknown at this time.
     * @param tokenImage       the parser token image.
     * @param instance         a factory for the instance on which this method call will take place, which may be null to indicate
     *                         the instance has been created outside the scope of this call and is already at the top of the execution stack.
     * @param declaringClass   the enclosing type within which the static method is declared.
     * @param name             the method name.
     * @param parameterClasses the types of parameters of this method call.
     * @param parameters       the parameter expressions of this method call.
     */
    public InstanceMethodCall(final Class<?> typeDescriptor,
                              final TokenImage tokenImage,
                              final Node instance,
                              final Class<?> declaringClass,
                              final String name,
                              final List<Class<?>> parameterClasses,
                              final List<Node> parameters
    ) {
        super(tokenImage, typeDescriptor);

        this.declaringType = new ResolvedTypeReferenceOld(tokenImage, declaringClass);
        this.instance = instance;
        this.returnType = new ResolvedTypeReferenceOld(tokenImage, typeDescriptor);
        this.parameterTypes = IntStream.range(0, parameterClasses.size()).mapToObj(i -> new ResolvedTypeReferenceOld(parameters.get(i).getTokenImage(), parameterClasses.get(0))).collect(Collectors.toList());
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero parameters.
     *
     * @param tokenImage    the parser token image.
     * @param declaringType reference to the type declaring the static method.
     * @param returnType    the return type of the method call.
     * @param name          the method name.
     */
    public InstanceMethodCall(final TokenImage tokenImage,
                              final TypeReference declaringType,
                              final TypeReference returnType,
                              final String name
    ) {
        this(tokenImage, declaringType, returnType, name, emptyList(), emptyList());
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero parameters.
     *
     * @param tokenImage    the parser token image.
     * @param instance       a factory for the instance on which this method call will take place, which may be null to indicate
     *                       the instance has been created outside the scope of this call and is already at the top of the execution stack.
     * @param declaringType reference to the type declaring the static method.
     * @param returnType    the return type of the method call.
     * @param name          the method name.
     */
    public InstanceMethodCall(final TokenImage tokenImage,
                              final Node instance,
                              final TypeReference declaringType,
                              final TypeReference returnType,
                              final String name
    ) {
        this(tokenImage, instance, declaringType, returnType, name, emptyList(), emptyList());
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param tokenImage     the parser token image.
     * @param declaringType  reference to the type declaring the static method.
     * @param returnType     the return type of the method call.
     * @param name           the method name.
     * @param parameterTypes types of parameters of this method call.
     * @param parameters     the parameter expressions of this method call.
     */
    public InstanceMethodCall(final TokenImage tokenImage,
                              final TypeReference declaringType,
                              final TypeReference returnType,
                              final String name,
                              final List<TypeReference> parameterTypes,
                              final List<Node> parameters
    ) {
        this(tokenImage, null, declaringType, returnType, name, parameterTypes, parameters);
    }

    /**
     * Constructs an instance method call expression, consisting of a name and zero or more parameters.
     *
     * @param tokenImage     the parser token image.
     * @param instance       a factory for the instance on which this method call will take place, which may be null to indicate
     *                       the instance has been created outside the scope of this call and is already at the top of the execution stack.
     * @param declaringType  reference to the type declaring the static method.
     * @param returnType     the return type of the method call.
     * @param name           the method name.
     * @param parameterTypes types of parameters of this method call.
     * @param parameters     the parameter expressions of this method call.
     */
    public InstanceMethodCall(final TokenImage tokenImage,
                              final Node instance,
                              final TypeReference declaringType,
                              final TypeReference returnType,
                              final String name,
                              final List<TypeReference> parameterTypes,
                              final List<Node> parameters
    ) {
        super(tokenImage, returnType);

        this.instance = instance;
        this.declaringType = declaringType;
        this.returnType = returnType;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public Class<?> getTypeDescriptor() {
        return returnType == null ? super.getTypeDescriptor() : returnType.getTypeDescriptor();
    }

    /**
     * Gets the factory for the instance on which this method call will take place.
     *
     * @return the factory for the instance on which this method call will take place, which may be null to indicate the
     * instance creation lies outside the scope of this method call and is already at the top of the execution stack.
     */
    public Node getInstance() {
        return instance;
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
        return declaringType == null ? null : declaringType.getTypeDescriptor();
    }

    /**
     * Gets the types of parameters of this method call.
     *
     * @return the types of parameters of this method call.
     */
    public List<Class<?>> getParameterClasses() {
        return parameterTypes.stream().map(Node::getTypeDescriptor).collect(Collectors.toList());
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
        return Objects.equals(declaringType, nodes.declaringType)
               && Objects.equals(returnType, nodes.returnType)
               && Objects.equals(name, nodes.name)
               && Objects.equals(parameterTypes, nodes.parameterTypes)
               && Objects.equals(parameters, nodes.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), declaringType, returnType, name, parameterTypes, parameters);
    }

}
