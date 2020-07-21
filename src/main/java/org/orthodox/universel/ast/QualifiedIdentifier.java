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

package org.orthodox.universel.ast;

/**
 * Represents a qualified identifier, such as a package or import.
 * 
 * @author Gary Watson
 */
public class QualifiedIdentifier extends Expression {
   /** The declared name. */
   private String name;
   
   public QualifiedIdentifier(TokenImage tokenImage, String name) {
      super(tokenImage);
      this.name = name;
   }

   public String getQualifier() {
      if (name == null) return null;

      int lastDot = name.lastIndexOf('.');
      return lastDot > 0 ? name.substring(0, lastDot) : null;
   }

   /***
    * Gets the name, excluding any qualifier.
    *
    * @return the name part if this identifier.
    */
   public String getName() {
      if (name == null) return null;

      int lastDot = name.lastIndexOf('.');
      return lastDot > 0 ? name.substring(lastDot+1) : name;
   }

   /***
    * Gets the declared name.
    * 
    * @return the declared name.
    */
   public String getFullyQualifiedName() {
      return name;
   }

   /**
    * Sets the declared name.
    * 
    * @param name the declared name.
    */
   public void setFullyQualifiedName(String name) {
      this.name = name;
   }

   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitName(this);
   }
}
