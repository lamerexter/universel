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

package org.orthodox.universel.symanticanalysis;

import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.UniversalVisitorAdapter;

import java.util.Objects;

/**
 * Iterates over the AST, performing a depth-first post-order traversal to establish the types on the AST. Essentially,
 * known types flow upwards from the leaves on the tree, forming a type tree from the ground up.
 *
 * <ul>
 * <li>{@link org.orthodox.universel.cst.types.ReferenceType}
 * </ul>
 *
 * and resolving them to actiual types.
 */
public class CompositeSemanticAnalyser implements SemanticAnalyser, Logger {
    private final SemanticAnalyser[] analysers;

    private static final int MAX_CHANGE_ITERATIONS = 20;

    public CompositeSemanticAnalyser(final SemanticAnalyser ... analysers) {
        this.analysers = analysers;
    }

    @Override
    public Node performAnalysis(final SemanticAnalysisContext context, Node from) {
        int i=0;
        Node lastNode = from;
        while (++i <= MAX_CHANGE_ITERATIONS)  {
            for (SemanticAnalyser analyser : analysers) {
                from = analyser.performAnalysis(context, from);
            }
            if ( Objects.equals(lastNode, from) ) break;
            lastNode = from;
        }

        if ( i == MAX_CHANGE_ITERATIONS ) {
            warning("Maximum semantic analysis iterations reached. This could indicate a compilation issue.");
        }

        return from;
    }
}
