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

package org.orthodox.universel.symanticanalysis.methods;

import org.orthodox.universel.ast.navigation.NavigationAxis;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.cst.MethodCall;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;

import java.util.Objects;

import static org.orthodox.universel.compiler.Messages.MethodCall.METHOD_NOT_FOUND;

/**
 * Runs at the end of semantic analysis to report on method calls which were not resolved.
 */
public class UnresolvedMethodCallReporter extends AbstractSemanticAnalyser {
    @Override
    public Node visitNavigationStream(final NavigationStream node) {
        node.getSteps().stream()
            .filter(NavigationStep.class::isInstance)
            .map(NavigationStep.class::cast)
            .filter(s -> s.getNodeTest() instanceof MethodCall)
            .filter(s -> Objects.equals(s.getAxis(), NavigationAxis.DEFAULT.getCanonicalName()))
            .forEach(s -> getContext().addError(METHOD_NOT_FOUND
                                                    .relatedObject(s)
                                                    .withParameters(((MethodCall)s.getNodeTest()).getName().getName())

            ));

        return node;
    }
}
