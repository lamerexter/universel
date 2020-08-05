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

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.CompositeNode;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.symanticanalysis.name.InternalNodeSequence.emptyNodeSequence;

public class NavigationStep<N extends NodeTest> extends Expression implements CompositeNode {
    private final NavigationAxisAndNodeTest<N> navigationAxisAndNodeTest;

    private final InternalNodeSequence filters;

    public NavigationStep(final TokenImage tokenImage,
                          final NavigationAxisAndNodeTest<N> navigationAxisAndNodeTest,
                          final InternalNodeSequence filters) {
        super(tokenImage);
        this.navigationAxisAndNodeTest = navigationAxisAndNodeTest;
        this.filters = filters;
    }

    public NavigationStep(final TokenImage tokenImage, final NavigationAxisAndNodeTest<N> navigationAxisAndNodeTest) {
        this(tokenImage, navigationAxisAndNodeTest, null);
    }

    public NavigationAxisAndNodeTest<N> getNavigationAxisAndNodeTest() {
        return navigationAxisAndNodeTest;
    }

    public InternalNodeSequence getFilters() {
        return filters == null ? emptyNodeSequence() : filters;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigationStep)) return false;
        if (!super.equals(o)) return false;
        NavigationStep<?> that = (NavigationStep<?>) o;
        return Objects.equals(getNavigationAxisAndNodeTest(), that.getNavigationAxisAndNodeTest()) &&
               Objects.equals(getFilters(), that.getFilters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNavigationAxisAndNodeTest(), getFilters());
    }

    //   @Override
//   public Node accept(UniversalCodeVisitor visitor) {
//        return visitor.visitNavigationStep(this);
//    }

    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                          .addNotNull(navigationAxisAndNodeTest)
                          .addNotNull(filters)
                          .build();
    }
}
