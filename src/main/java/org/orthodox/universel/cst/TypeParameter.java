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

import org.orthodox.universel.cst.type.reference.ClassOrInterfaceType;

import java.util.List;
import java.util.Objects;

/**
 * A parameterised type specification, as defined by the Java 5 SE language level specification
 * for Generics.
 */
public class TypeParameter extends Node {
   private Name name;
   private ParameterisedTypeBinding typeBound;
   private List<ClassOrInterfaceType> boundingTypes;
   
   /**
    * Instantiates a new type declaration
    *
    * @param name the name defined by this type declaration.
    * @param typeBound the type binding applied to the source.
    * @param boundingTypes the bounding types applied.
    */
   public TypeParameter(Name name, ParameterisedTypeBinding typeBound, List<ClassOrInterfaceType> boundingTypes) {
      super(TokenImage.builder()
            .range(name)
            .range(boundingTypes)
            .build()
      );
      this.name = name;
      this.typeBound = typeBound;
      this.boundingTypes = boundingTypes;
   }

   public Name getName() {
      return name;
   }

   public ParameterisedTypeBinding getTypeBound() {
      return typeBound;
   }

   public List<ClassOrInterfaceType> getBoundingTypes() {
      return boundingTypes;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof TypeParameter)) return false;
      if (!super.equals(o)) return false;
      TypeParameter that = (TypeParameter) o;
      return Objects.equals(getName(), that.getName()) &&
             getTypeBound() == that.getTypeBound() &&
             Objects.equals(getBoundingTypes(), that.getBoundingTypes());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getName(), getTypeBound(), getBoundingTypes());
   }
}