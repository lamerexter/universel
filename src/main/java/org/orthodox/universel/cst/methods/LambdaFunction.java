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

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Objects;

/**
 * A Universel method declaration on the Abstract Syntax Tree (AST).
 */
public class LambdaFunction extends AbstractMethodDeclaration implements CompositeNode {
    private final NamePath nameHints;

    public LambdaFunction(final Modifiers modifiers,
                          final NodeSequence<TypeParameter> typeParameters,
                          final TypeReference returnType,
                          final NamePath nameHints,
                          final NodeSequence<Parameter> parameters,
                          final NodeSequence<Node> body) {
        this(modifiers, typeParameters, null, returnType, nameHints, parameters, body);
    }

    public LambdaFunction(final Modifiers modifiers,
                          final NodeSequence<TypeParameter> typeParameters,
                          final TypeReference declaringType,
                          final TypeReference returnType,
                          final NamePath nameHints,
                          final NodeSequence<Parameter> parameters,
                          final NodeSequence<Node> body) {
        super(modifiers, typeParameters, declaringType, returnType, parameters, body);
        this.nameHints = nameHints;
    }

    public NamePath getNameHints() {
        return nameHints;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof LambdaFunction)) return false;
        if (!super.equals(o)) return false;
        LambdaFunction nodes = (LambdaFunction) o;
        return Objects.equals(getNameHints(), nodes.getNameHints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNameHints());
    }

    @Override
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitLambdaFunction(this);
    }
}
