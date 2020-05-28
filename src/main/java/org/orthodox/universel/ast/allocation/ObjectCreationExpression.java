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

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;


/**
 * An explicit object creation expression on the Abstract Syntax Tree (AST).
 */
public class ObjectCreationExpression extends Expression implements CompositeNode {
   /** The type created by this object creation expression. */
   private TypeReference type;

   /** The positional arguments to the creation expression. */
   private List<Node> parameters;

   public ObjectCreationExpression(TokenImage tokenImage, TypeReference type, Node ... parameters) {
      this(tokenImage, type, parameters == null ? emptyList() : asList(parameters));
   }

   public ObjectCreationExpression(TokenImage tokenImage, TypeReference type, List<Node> parameters) {
      super(tokenImage);
      this.type = type;
      this.parameters = parameters;
   }

   public TypeReference getType() {
      return type;
   }

   public List<Node> getParameters() {
      return parameters == null ? emptyList() : parameters;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof ObjectCreationExpression)) return false;
      if (!super.equals(o)) return false;
      ObjectCreationExpression nodes = (ObjectCreationExpression) o;
      return Objects.equals(getType(), nodes.getType()) &&
             Objects.equals(getParameters(), nodes.getParameters());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getType(), getParameters());
   }

   @Override
   public List<Node> getChildNodes() {
      return ListBuilder.<Node>builder()
                        .addNotNull(type)
                        .addAllNotNull(parameters)
                        .build();
   }

   public Class<?> getTypeDescriptor() {
      return type == null ? super.getTypeDescriptor() : type.getTypeDescriptor();
   }

   @Override
   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitObjectCreationExpression(this);
   }
}
