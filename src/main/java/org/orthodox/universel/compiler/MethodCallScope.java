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

import org.orthodox.universel.cst.MethodCall;
import org.orthodox.universel.cst.UniversalCodeVisitor;

public interface MethodCallScope {
    /**
     * Determines whether this method call scope can definitively resolve a given method call.
     *
     * @param methodCall the method call to be determined within this scope.
     * @return true if method call can be resolved within this scope, false otherwise.
     */
    boolean canResolve(MethodCall methodCall);

    /**
     * Generates the code to invoke the given method, leaving any return value
     * on the evaluation stack.
     *
     * @param visitor the code generator in context of this method call invocation.
     * @param methodCall the method call whose invocation code is to be geneerated.
     */
    void generateCall(UniversalCodeVisitor visitor, MethodCall methodCall);

}
