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
package org.orthodox.universel.ast.allocation;

import org.orthodox.universel.cst.CompositeNode;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Abstract Syntax Tree (AST) of the expression(s) used to initialise an array.
 */
public class ArrayInitialiserExpression extends Expression implements CompositeNode {
   /** The expressions used to initialise the array. */
   private final List<Node> initialisers;

   /**
    * Constructs a member-value pair from the start point and values.
    *
    * @param tokenImage the parser token image backing this expression.
    * @param initialisers the values used to initialise the array.
    */
   public ArrayInitialiserExpression(final TokenImage tokenImage, final List<Node> initialisers) {
      super(tokenImage);
      this.initialisers = initialisers;
   }

   /**
    * Gets the expressions used to initialise the array.
    *
    * @return the expressions used to initialise the array, which may be null if none were specified.
    */
   public List<Node> getInitialisers() {
      return initialisers;
   }

   @Override
   public List<Node> getChildNodes() {
      return initialisers == null ? Collections.emptyList() : initialisers;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof ArrayInitialiserExpression)) return false;
      if (!super.equals(o)) return false;
      ArrayInitialiserExpression nodes = (ArrayInitialiserExpression) o;
      return Objects.equals(getInitialisers(), nodes.getInitialisers());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getInitialisers());
   }
}
