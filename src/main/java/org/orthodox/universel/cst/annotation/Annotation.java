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

import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.QualifiedIdentifier;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

/**
 * The superclass of all AST annotation nodes.
 * 
 * @author Gary Watson
 */
public abstract class Annotation extends Node {
   /** The name of the annotation. */
   private QualifiedIdentifier name;

   /**
    * Constructs an annotation AST node at the given start point.
    * 
    * @param tokenImage the image representing this cst annotation.
    * @param name the name of the annotation.
    */
   public Annotation(TokenImage tokenImage, QualifiedIdentifier name) {
      super(tokenImage);
      this.name = name;
   }

   /**
    * Gets the name of the annotation.
    * 
    * @return the name of the annotation.he name of the annotation.
    */
   public QualifiedIdentifier getName() {
      return name;
   }

   /**
    * Sets the name of the annotation.
    * 
    * @param name the name of the annotation.
    */
   public void setName(QualifiedIdentifier name) {
      this.name = name;
   }

   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitAnnotation(this);
   }
}
