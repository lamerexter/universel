package org.orthodox.universel.ast.allocation;

import org.orthodox.universel.ast.CompositeNode;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;

import java.util.List;
import java.util.Objects;

/**
 * Contains the array dimension and initialiser expressions for arrays created
 * through the Universal Expression Language.
 */
public class ArrayDimensionsAndInitialiser extends Node implements CompositeNode {
   /** The expressions which comprise the dimensions of the array. */
   private final List<Node> dimSizes;

   /** The expression(s) used to initialise the array. */
   private final ArrayInitialiserExpression arrayInitialiserExpression;

   public ArrayDimensionsAndInitialiser(final TokenImage tokenImage,
                                        final List<Node> dimSizes,
                                        final ArrayInitialiserExpression arrayInitialiserExpression) {
      super(tokenImage);
      this.dimSizes = dimSizes;
      this.arrayInitialiserExpression = arrayInitialiserExpression;
   }

   /**
    * Gets the expressions which comprise the dimensions of the array.
    *
    * @return the expressions which comprise the dimensions of the array.
    */
   public final List<Node> getDimSizes() {
      return dimSizes;
   }

   /**
    * Gets the expression(s) used to initialise the array.
    *
    * @return the expression(s) used to initialise the array.
    */
   public final ArrayInitialiserExpression getArrayInitialiserExpression() {
      return arrayInitialiserExpression;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof ArrayDimensionsAndInitialiser)) return false;
      ArrayDimensionsAndInitialiser that = (ArrayDimensionsAndInitialiser) o;
      return Objects.equals(getDimSizes(), that.getDimSizes()) &&
             Objects.equals(getArrayInitialiserExpression(), that.getArrayInitialiserExpression());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getDimSizes(), getArrayInitialiserExpression());
   }

   @Override
   public List<Node> getChildNodes() {
      return null;
   }
}
