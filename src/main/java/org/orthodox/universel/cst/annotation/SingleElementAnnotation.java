/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

package org.orthodox.universel.cst.annotation;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.QualifiedIdentifier;
import org.orthodox.universel.cst.TokenImage;

/**
 * AST representation of a single-member (value, with an assumed "value" name attribute) annotation.
 * 
 * @author Gary Watson
 */
public class SingleElementAnnotation extends Annotation {
   /** The value expression associated with the single member annotation. */
   private Expression valueExpression;
   
   /**
    * Constructs a single-member annotation AST node at the given start point and with the specified name
    * and single member value expression.
    *
    * @param tokenImage the image representing this cst annotation.
    * @param name the name of the annotation.
    * @param valueExpression the value expression associated with the single member annotation.
    */
   public SingleElementAnnotation(TokenImage tokenImage, QualifiedIdentifier name, Expression valueExpression) {
      super(tokenImage, name);
      this.valueExpression = valueExpression;
   }

   /**
    * Gets the value expression associated with the single member annotation.
    * 
    * @return the value expression associated with the single member annotation.
    */
   public Expression getValueExpression() {
      return valueExpression;
   }

   /**
    * Sets the value expression associated with the single member annotation
    * 
    * @param valueExpression the value expression associated with the single member annotation.
    */
   public void setValueExpression(Expression valueExpression) {
      this.valueExpression = valueExpression;
   }
}
