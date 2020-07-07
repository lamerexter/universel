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

package org.orthodox.universel.ast.functional;

import org.orthodox.universel.cst.CompositeNode;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;
import org.orthodox.universel.cst.type.MethodDeclaration;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Represents implementation of a 'functional interface' through delegation to a declared method. In real terms. this creates
 * a Java 7 callsite, which masquerades as the functional interface type, which when called simply delegates to the
 * given method.
 */
public class FunctionalInterfaceObject extends Node implements CompositeNode {
    private final Class<?> sourceFunctionType;
    private final Class<?> sourceFunctionReturnType;
    private final List<Class<?>> sourceFunctionParameters;
    private final String sourceFunctionName;

    private final MethodDeclaration targetMethodPrototype;

    public FunctionalInterfaceObject(final TokenImage tokenImage,
                                     final Class<?> sourceFunctionType,
                                     final Class<?> sourceFunctionReturnType,
                                     final List<Class<?>> sourceFunctionParameters,
                                     final String sourceFunctionName,
                                     final MethodDeclaration targetMethodPrototype) {
        super(tokenImage, sourceFunctionType);
        this.sourceFunctionType = sourceFunctionType;
        this.sourceFunctionReturnType = sourceFunctionReturnType;
        this.sourceFunctionParameters = sourceFunctionParameters;
        this.sourceFunctionName = sourceFunctionName;
        this.targetMethodPrototype = targetMethodPrototype;
    }

    public Class<?> getSourceFunctionType() {
        return sourceFunctionType;
    }

    public Class<?> getSourceFunctionReturnType() {
        return sourceFunctionReturnType;
    }

    public List<Class<?>> getSourceFunctionParameters() {
        return sourceFunctionParameters;
    }

    public String getSourceFunctionName() {
        return sourceFunctionName;
    }

    public MethodDeclaration getTargetMethodPrototype() {
        return targetMethodPrototype;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionalInterfaceObject)) return false;
        if (!super.equals(o)) return false;
        FunctionalInterfaceObject that = (FunctionalInterfaceObject) o;
        return Objects.equals(getSourceFunctionType(), that.getSourceFunctionType()) &&
               Objects.equals(getSourceFunctionReturnType(), that.getSourceFunctionReturnType()) &&
               Objects.equals(getSourceFunctionParameters(), that.getSourceFunctionParameters()) &&
               Objects.equals(getSourceFunctionName(), that.getSourceFunctionName()) &&
               Objects.equals(getTargetMethodPrototype(), that.getTargetMethodPrototype());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSourceFunctionType(), getSourceFunctionReturnType(), getSourceFunctionParameters(), getSourceFunctionName(), getTargetMethodPrototype());
    }

    @Override
    public List<Node> getChildNodes() {
        return targetMethodPrototype == null ? emptyList() : singletonList(targetMethodPrototype);
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitFunctionalInterfaceObject(this);
    }

}
