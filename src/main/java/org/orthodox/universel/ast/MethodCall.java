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

package org.orthodox.universel.ast;

import java.util.Collections;
import java.util.List;

import static org.orthodox.universel.ast.TokenImage.range;

/**
 * A method call expression, consisting of a name and zero or more parameter expressions.
 */
public class MethodCall extends Expression {
    /** The name of the method. */
    private Name name;
    /** The parameter expressions of this method call. */
    private List<Expression> parameters;

    /**
     * Consructs a method call expression, consisting of a name and zero or more parameters.
     *
     * @param parameters the parameter expressions of this method call.
     */
    public MethodCall(Name name, List<Expression> parameters) {
        super(range(name, parameters));
        this.name = name;
        this.parameters = parameters;
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
    public List<Expression> getParameters() {
        return parameters != null ? parameters : Collections.emptyList();
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitMethodCall(this);
    }

    public static String helloWorld() {
        return "Hello World!";
    }
}
