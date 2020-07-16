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

package org.orthodox.universel.cst;

import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;

/**
 * Represents a field of an existing class.
 *
 * @author Gary Watsob
 */
public class ClassField implements Field {
   /** The reflection based field backing this field model. */
   private java.lang.reflect.Field field;

   public ClassField() {
   }

   public ClassField(java.lang.reflect.Field field) {
      this.field = field;
   }

   /**
    * Gets the reflection based field backing this field model.
    *
    * @return the reflection based field backing this field model.
    */
   public java.lang.reflect.Field getField() {
      return field;
   }

   /**
    * Returns the type which declares this field.
    *
    * @return the field's directly enclosing type.
    */
   public Type getDeclaringType() {
      return new ResolvedTypeReferenceOld(field.getDeclaringClass());
   }

   /**
    * Returns the modifiers assoiated with this field.
    *
    * @return the field's modifiers.
    */
   public Modifiers getModifiers() {
      return Modifiers.valueOf(field.getModifiers());
   }

   /**
    * Returns the type of the field.
    *
    * @return the field's type.
    */
   public Type getType() {
      return new ResolvedTypeReferenceOld(field.getType());
   }

   /**
    * Returns the name of the field.
    *
    * @return the field's name.
    */
   public String getName() {
      return field.getName();
   }
}
