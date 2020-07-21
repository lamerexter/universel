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

package org.orthodox.universel.symanticanalysis.conversion;

import org.orthodox.universel.ast.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class  BinaryExpressionOperatorMethodCall extends Node implements CompositeNode {
    private final Operator operator;
    private final Method operatorMethod;
    private final List<Node> parameters;

    public BinaryExpressionOperatorMethodCall(TokenImage tokenImage, Operator operator, Method operatorMethod, List<Node> parameters) {
        super(tokenImage, operatorMethod.getReturnType());
        this.operator = operator;
        this.operatorMethod = operatorMethod;
        this.parameters = parameters;
    }

    public Operator getOperator() {
        return operator;
    }

    public Method getOperatorMethod() {
        return operatorMethod;
    }

    public List<Node> getParameters() {
        return parameters;
    }

    @Override
    public List<Node> getChildNodes() {
        return getParameters();
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BinaryExpressionOperatorMethodCall))
            return false;
        if (!super.equals(o))
            return false;
        BinaryExpressionOperatorMethodCall nodes = (BinaryExpressionOperatorMethodCall) o;
        return operator == nodes.operator && Objects.equals(operatorMethod, nodes.operatorMethod) && Objects.equals(parameters, nodes.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), operator, operatorMethod, parameters);
    }
}
