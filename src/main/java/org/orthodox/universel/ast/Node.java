/*
 *  MIT Licence:
 *
 *  Copyright (c) 2018 Orthodox Engineering Ltd
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

import org.beanplanet.core.util.PropertyBasedToStringBuilder;

import java.util.Collection;
import java.util.List;

/**
 * The superclass of all UEL Abstact Syntax Tree (AST) elements.
 * 
 * @author Gary Watson
 */
public abstract class Node {
   public static final Node[] EMPTY_NODES_ARRAY = new Node[0];
   
   private Node parent;
   
   /** The type of the node, if known from its static context. */
   private Class<?> type;

   /** The line the token image of this node begins. */
   private int startLine;
   /** The column the token image of this node begins. */
   private int startColumn;
   /** The line the token image of this node ends. */
   private int endLine;
   /** The column the token image of this node ends. */
   private int endColumn;

   private String image;

   public Node() {
      this(-1, -1, -1, -1, null);
   }

   /**
    * Constructs a new AST node instance.
    * 
    * @param startLine the line the token image of this node begins.
    * @param startColumn the column the token image of this node begins.
    * @param endLine the line the token image of this node ends.
    * @param endColumn the column the token image of this node ends.
    * @param image the token image of this AST node.
    */
   public Node(int startLine, int startColumn, int endLine, int endColumn, String image) {
      this.startLine = startLine;
      this.startColumn = startColumn;
      this.endLine = endLine;
      this.endColumn = endColumn;
      this.image = image;
   }

   /**
    * Constructs a new AST node instance.
    * 
    * @param startLine the line the token image of this node begins.
    * @param startColumn the column the token image of this node begins.
    * @param endLine the line the token image of this node ends.
    * @param endColumn the column the token image of this node ends.
    */
   public Node(int startLine, int startColumn, int endLine, int endColumn) {
      this(startLine, startColumn, endLine, endColumn, null);
   }

   /**
    * Constructs a new AST node instance.
    * 
    * @param startLine the line the token image of this node begins.
    * @param startColumn the column the token image of this node begins.
    * @param image the token image of this AST node.
    */
   public Node(int startLine, int startColumn, String image) {
      this(startLine, startColumn, -1, -1, image);
   }

   /**
    * Constructs a new AST node instance.
    * 
    * @param startLine the line the token image of this node begins.
    * @param startColumn the column the token image of this node begins.
    */
   public Node(int startLine, int startColumn) {
      this(startLine, startColumn, -1, -1, null);
   }

   public Node clone() {
       return null;
   }

   public boolean accept(UniversalCodeVisitor visitor) {
      // GAW TODO: remove once all implementations in place
      return true;
   }
   
   /**
    * Gets the type of the node, if known from its static context. This is generally the
    * type returned by execution of the node.
    * 
    * @return the type of the node, or null if the type is unknown.
    */
   public Class<?> getType() {
      return type;
   }

   /**
    * Sets the type of the node, if known from its static context.
    * 
    * @param type the type of the node, if known from its static context, or null if the type is unknown.
    */
   public void setType(Class<?> type) {
      this.type= type ;
   }

   public Node getParent() {
      return parent;
   }

   public void setParent(Node parent) {
      this.parent = parent;
   }

   /**
    * Gets the line the token image of this node begins.
    * 
    * @return the line the token image of this node begins.
    */
   public int getStartLine() {
      return startLine;
   }

   /**
    * Sets the line the token image of this node begins.
    * 
    * @param startLine the line the token image of this node begins.
    */
   public void setStartLine(int startLine) {
      this.startLine = startLine;
   }

   /**
    * Gets the column the token image of this node begins.
    * 
    * @return the column the token image of this node begins.
    */
   public int getStartColumn() {
      return startColumn;
   }

   /**
    * Sets the column the token image of this node begins.
    * 
    * @param startColumn the column the token image of this node begins.
    */
   public void setStartColumn(int startColumn) {
      this.startColumn = startColumn;
   }

   /**
    * Gets the line the token image of this node ends.
    * 
    * @return the line the token image of this node ends.
    */
   public int getEndLine() {
      return endLine;
   }

   /**
    * Sets the line the token image of this node ends.
    * 
    * @param endLine the line the token image of this node ends.
    */
   public void setEndLine(int endLine) {
      this.endLine = endLine;
   }

   /**
    * Gets the column the token image of this node ends.
    * 
    * @return the column the token image of this node ends.
    */
   public int getEndColumn() {
      return endColumn;
   }

   /**
    * Sets the column the token image of this node ends.
    * 
    * @param endColumn the column the token image of this node ends.
    */
   public void setEndColumn(int endColumn) {
      this.endColumn = endColumn;
   }

   /**
    * @return the image
    */
   public String getImage() {
      return image;
   }

   /**
    * @param image the image to set
    */
   public void setImage(String image) {
      this.image = image;
   }

   public String getCanonicalString() {
      return new PropertyBasedToStringBuilder(this).withProperties("startLine", "startColumn", "endLine", "endColumn", "image").build();
   }

   @Override
   public String toString() {
      return getCanonicalString();
   }
}
