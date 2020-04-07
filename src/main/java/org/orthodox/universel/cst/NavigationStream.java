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

package org.orthodox.universel.cst;

import java.util.List;

import static org.orthodox.universel.cst.TokenImage.range;

/**
 * A universal navigation stream, comsisting of a number of navigation steps.
 */
public class NavigationStream extends Expression implements CompositeNode {
    /**
     * The steps that comprise this stream.
     */
    private List<Node> steps;

    /**
     * Consructs a method call expression, consisting of a name and zero or more parameters.
     *
     * @param steps the steps that comprise this stream.
     */
    public NavigationStream(List<Node> steps) {
        super(range(steps));
        this.steps = steps;
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNavigationStream(this);
    }

    public List<Node> getSteps() {
        return steps;
    }

    @Override
    public List<Node> getChildNodes() {
        return getSteps();
    }
}
