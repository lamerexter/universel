/******************************************************************************* 
 * Copyright 2004-2009 BeanPlanet Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.orthodox.universel.ast.navigation;

import org.orthodox.universel.ast.CompositeNode;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NavigationFilterStep extends Node implements CompositeNode {
    protected Node filterExpression;

    public NavigationFilterStep(final TokenImage tokenImage, final Node filterExpression) {
        super(tokenImage, filterExpression.getType());
        this.filterExpression = filterExpression;
    }

    public Node getFilterExpression() {
        return filterExpression;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigationFilterStep)) return false;
        if (!super.equals(o)) return false;
        NavigationFilterStep nodes = (NavigationFilterStep) o;
        return Objects.equals(getFilterExpression(), nodes.getFilterExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFilterExpression());
    }

    @Override
    public List<Node> getChildNodes() {
        return filterExpression == null ? emptyList() : singletonList(filterExpression);
    }

    public NavigationFilterStep accept(UniversalCodeVisitor visitor) {
        return visitor.visitNavigationFilterStep(this);
    }

}
