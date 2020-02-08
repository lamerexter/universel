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

import org.orthodox.universel.cst.QualifiedIdentifier;
import org.orthodox.universel.cst.TokenImage;

import java.util.List;

/**
 * AST representation of a multi-member (vame-value pair) annotation.
 * 
 * @author Gary Watson
 */
public class NormalAnnotation extends Annotation {
   /** The member name-values associated with the annotation. */
   private List<MemberValuePair> members;
   
   /**
    * Constructs a multi-member annotation AST node at the given start point and with the specified name
    * and name-value pairs.
    *
    * @param tokenImage the image representing this cst annotation.
    * @param name the name of the annotation.
    * @param members the name-value members associated with the annotation.
    */
   public NormalAnnotation(TokenImage tokenImage, QualifiedIdentifier name, List<MemberValuePair> members) {
      super(tokenImage, name);
      this.members = members;
   }

   /**
    * Gets the member name-values associated with the annotation.
    * 
    * @return the member name-values associated with the annotation.
    */
   public List<MemberValuePair> getMembers() {
      return members;
   }

   /**
    * Sets the member name-values associated with the annotation.
    * 
    * @param members the member name-values associated with the annotation.
    */
   public void setMembers(List<MemberValuePair> members) {
      this.members = members;
   }
}
