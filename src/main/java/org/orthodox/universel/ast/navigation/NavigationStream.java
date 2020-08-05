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

package org.orthodox.universel.ast.navigation;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.CompositeNode;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.UniversalCodeVisitor;
import org.orthodox.universel.symanticanalysis.navigation.NavigationStage;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.ast.TokenImage.range;

public class NavigationStream extends Node implements CompositeNode {
    private final List<Node> inputSteps;
    private final List<NavigationStage> targetStages;

    public NavigationStream(List<Node> inputSteps, List<NavigationStage> targetStages) {
        super(range(inputSteps));
        this.inputSteps = inputSteps;
        this.targetStages = targetStages;
    }

    public List<Node> getInputSteps() {
        return inputSteps == null ? Collections.emptyList() : inputSteps;
    }

    public List<NavigationStage> getTargetStages() {
        return targetStages == null ? Collections.emptyList() : targetStages;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigationStream)) return false;
        if (!super.equals(o)) return false;
        NavigationStream nodes = (NavigationStream) o;
        return Objects.equals(getInputSteps(), nodes.getInputSteps()) &&
               Objects.equals(getTargetStages(), nodes.getTargetStages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInputSteps(), getTargetStages());
    }

    /**
     * Gets a list of the children of this node.
     *
     * @return the children of this node, which may be empty but not null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                          .addAll(getInputSteps())
                          .addAll((List)getTargetStages())
                          .build();
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNavigationStream(this);
    }

}
