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
package org.orthodox.universel.ast.allocation;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.reference.ArrayTypeReference;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

/**
 * An array creation expression on the Abstract Syntax Tree (AST).
 */
public class ArrayCreationExpression extends Expression implements CompositeNode {
   /** The innermost component type of the array. */
   private final TypeReference componentType;

   /** The expressions which comprise the dimensions of the array. */
   private final List<Node> dimensionExpressions;

   /** The expressions used to initialise the array. */
   private final ArrayInitialiserExpression initialiserExpression;


   public ArrayCreationExpression(TokenImage tokenImage,
                                  TypeReference componentType,
                                  List<Node> dimensionExpressions,
                                  ArrayInitialiserExpression initialiserExpression) {
      super(tokenImage);
      this.componentType = componentType;
      this.dimensionExpressions = dimensionExpressions;
      this.initialiserExpression = initialiserExpression;
   }

   /**
    *  Gets the innermost component type of the array.
    *
    * @return the innermost component type of the array.
    */
   public TypeReference getComponentType() {
      return componentType;
   }

   /**
    * Gets the expressions which comprise the dimensions of the array.
    *
    * @return the expressions which comprise the dimensions of the array.
    */
   public List<Node> getDimensionExpressions() {
      return dimensionExpressions == null ? emptyList() : dimensionExpressions;
   }

   /**
    * Gets the expressions used to initialise the array.
    *
    * @return the expressions used to initialise the array.
    */
   public ArrayInitialiserExpression getInitialiserExpression() {
      return initialiserExpression;
   }

   @Override
   public List<Node> getChildNodes() {
      return ListBuilder.<Node>builder()
                 .addNotNull(componentType)
                 .addAllNotNull(dimensionExpressions)
                 .addNotNull(initialiserExpression)
          .build();
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof ArrayCreationExpression)) return false;
      if (!super.equals(o)) return false;
      ArrayCreationExpression nodes = (ArrayCreationExpression) o;
      return Objects.equals(getComponentType(), nodes.getComponentType()) &&
             Objects.equals(getDimensionExpressions(), nodes.getDimensionExpressions()) &&
             Objects.equals(getInitialiserExpression(), nodes.getInitialiserExpression());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getComponentType(), getDimensionExpressions(), getInitialiserExpression());
   }

   @Override
   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitArrayCreationExpression(this);
   }

   public TypeReference getType() {
      return new ArrayTypeReference(getTokenImage(), getComponentType(), getDimensionExpressions().size()) ;
   }
}
