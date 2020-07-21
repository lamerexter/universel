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

package org.orthodox.universel.ast.annotation;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;

/**
 * Represents a member-value pair when used in context of annotations.
 * 
 * @author Gary Watson
 */
public class MemberValuePair extends Node {
   /** The name associated with this member-value pair. */
   private String name;
   /** The value associated with this member-value pair. */
   private Expression value;

   /**
    * Constructs a member-value pair from the given name and expression value.
    *
    * @param tokenImage the image representing this cst annotation.
    * @param name the name associated with this member-value pair.
    * @param value the value expression associated with this member-value pair
    */
   public MemberValuePair(TokenImage tokenImage, String name, Expression value) {
      super(tokenImage);
      this.name = name;
      this.value = value;
   }

   /**
    * Gets the name associated with this member-value pair.
    * 
    * @return the name associated with this member-value pair.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name associated with this member-value pair.
    * 
    * @param name the name associated with this member-value pair.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Gets the value associated with this member-value pair.
    * 
    * @return the value associated with this member-value pair.
    */
   public Expression getValue() {
      return value;
   }

   /**
    * Sets the value associated with this member-value pair.
    * 
    * @param value the value associated with this member-value pair.
    */
   public void setValue(Expression value) {
      this.value = value;
   }
}
