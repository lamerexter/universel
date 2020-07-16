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

import org.orthodox.universel.cst.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NavigationStep<N extends NodeTest> extends Expression implements CompositeNode {
    protected String axis;

    protected N nodeTest;

    protected List<Node> predicates;

    public NavigationStep(TokenImage tokenImage, String axis, N nodeTest, List<Node> predicates) {
        super(tokenImage);
        this.axis = axis;
        this.nodeTest = nodeTest;
        this.predicates = predicates;
    }

    public NavigationStep(TokenImage tokenImage, NavigationAxis axis, N nodeTest) {
        this(tokenImage, axis.getCanonicalName(), nodeTest, null);
    }

    public NavigationStep(TokenImage tokenImage, NavigationAxis axis, N nodeTest, List<Node> predicates) {
        this(tokenImage, axis.getCanonicalName(), nodeTest, predicates);
    }

    public String getAxis() {
        return axis;
    }

    public N getNodeTest() {
        return nodeTest;
    }

    public List<Node> getPredicates() {
        return predicates == null ? emptyList() : predicates;
    }

   @Override
   public boolean equals(final Object o) {
      if (this == o)
         return true;
      if (!(o instanceof NavigationStep))
         return false;
      if (!super.equals(o))
         return false;
      NavigationStep<?> that = (NavigationStep<?>) o;
      return Objects.equals(getAxis(), that.getAxis()) && Objects.equals(getNodeTest(), that.getNodeTest()) && Objects
              .equals(getPredicates(), that.getPredicates());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getAxis(), getNodeTest(), getPredicates());
   }

   @Override
   public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNavigationStep(this);
    }

    @Override
    public List<Node> getChildNodes() {
        return nodeTest == null ? emptyList() : singletonList((Node)nodeTest);
    }
}