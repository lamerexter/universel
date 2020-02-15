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

import org.orthodox.universel.cst.types.ClassOrInterfaceType;

import java.util.List;

/**
 * A parameterised type specification, as defined by the Java 5 SE language level specification
 * for Generics.
 * 
 * @author Gary Watson
 */
public class TypeParameter extends Node {
   private String name;
   private ParameterisedTypeBinding typeBound;
   private List<ClassOrInterfaceType> boundingTypes;
   
   /**
    * Instantiates a new type declaration
    *
    * @param tokenImage the image representing this cst type parameter.
    * @param name the name defined by this type declaration.
    * @param typeBound the type binding applied to the source.
    * @param boundingTypes the bounding types applied.
    */
   public TypeParameter(int startLine, int startColumn, String name, ParameterisedTypeBinding typeBound, List<ClassOrInterfaceType> boundingTypes) {
      super(TokenImage.builder()
            .startLine(startLine)
            .startColumn(startColumn)
            .range(boundingTypes)
            .build()
      );
      this.name = name;
      this.typeBound = typeBound;
      this.boundingTypes = boundingTypes;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ParameterisedTypeBinding getTypeBound() {
      return typeBound;
   }

   public void setTypeBound(ParameterisedTypeBinding typeBound) {
      this.typeBound = typeBound;
   }

   public List<ClassOrInterfaceType> getBoundingTypes() {
      return boundingTypes;
   }

   public void setBoundingTypes(List<ClassOrInterfaceType> boundingTypes) {
      this.boundingTypes = boundingTypes;
   }
}