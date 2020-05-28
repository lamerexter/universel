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

package org.orthodox.universel.cst;

import org.orthodox.universel.ast.navigation.NodeTest;
import org.orthodox.universel.cst.type.MethodDeclaration;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.compiler.TransformationUtil.callableTypesMatch;

/**
 * A method call expression, consisting of a name and zero or more parameter expressions.
 */
public class MethodCall extends Expression implements NodeTest, CompositeNode {
    /**
     * The name of the method.
     */
    private final Name name;
    /**
     * The parameter expressions of this method call.
     */
    private final List<Node> parameters;

    /**
     * Consructs a method call expression, consisting of a name and zero or more parameters.
     *
     * @param tokenImage the parser token image.
     * @param name the method name.
     * @param parameters the parameter expressions of this method call.
     */
   public MethodCall(TokenImage tokenImage, Name name, List<Node> parameters) {
        super(tokenImage);
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MethodCall))
            return false;
        if (!super.equals(o))
            return false;
        MethodCall nodes = (MethodCall) o;
        return Objects.equals(name, nodes.name) &&
               Objects.equals(parameters, nodes.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, parameters);
    }

    /**
     * Gets the name of the method being called.
     *
     * @return the name of the method.
     */
    public Name getName() {
        return name;
    }

    /**
     * Gets the parameter expressions of this method call.
     *
     * @return he parameter expressions of this method call.
     */
    public List<Node> getParameters() {
        return parameters != null ? parameters : Collections.emptyList();
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitMethodCall(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Node> getChildNodes() {
        return parameters == null ? Collections.emptyList() : parameters;
    }

    /**
     * Determines whether this method call can be applied to the given method.
     *
     * @param methodDeclaration the method to e determined as callable.
     * @return true if all of the parameters of this method call type match all of the parameters of the given method, false otherwise.
     */
    public boolean parameterTypesMatch(MethodDeclaration methodDeclaration) {
        return callableTypesMatch(this, methodDeclaration);
    }
}
