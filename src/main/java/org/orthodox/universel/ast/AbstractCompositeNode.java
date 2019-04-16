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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractCompositeNode extends Node implements CompositeNode {
   /**
    * Constructs a new AST composite node instance with no initial configuration.
    */
   public AbstractCompositeNode() {
   }
   
//   /**
//    * Constructs a new AST composite node instance from the given nodes.
//    *
//    * @param objects the nodes that will comprise this composite.
//    */
//   public AbstractCompositeNode(Object ...objects) {
//      setStartAndEndPositions(objects);
//   }
//
//   /**
//    * Constructs a new AST composite node instance with the given start and end information.
//    *
//    * @param startLine the line the token image of this node begins.
//    * @param startColumn the column the token image of this node begins.
//    */
//   public AbstractCompositeNode(int startLine, int startColumn) {
//      super(startLine, startColumn);
//   }
//
//   /**
//    * Constructs a new AST composite node instance with the given start line and ending line information.
//    *
//    * @param startLine the line the token image of this node begins.
//    * @param startColumn the column the token image of this node begins.
//    * @param endLine the line the token image of this node ends.
//    * @param endColumn the column the token image of this node ends.
//    */
//   public AbstractCompositeNode(int startLine, int startColumn, int endLine, int endColumn) {
//      super(startLine, startColumn, endLine, endColumn);
//   }
//
//   /**
//    * Constructs a new AST composite node instance with the given start line and token image information.
//    *
//    * @param startLine the line the token image of this node begins.
//    * @param startColumn the column the token image of this node begins.
//    * @param image the token image of this AST node.
//    */
//   public AbstractCompositeNode(int startLine, int startColumn, String image) {
//      super(startLine, startColumn, image);
//   }

   /**
    * Returns an iterator over a set nodes of this composite.
    * 
    * @return an Iterator over the child nodes of this composite node.
    */
   public Iterator<Node> iterator() {
      List<Node> childNodes = getChildNodes();
      return childNodes != null ? childNodes.iterator() : Collections.<Node>emptyList().iterator();
   }

   protected <T extends Node> T assignParent(T node) {
      if (node != null) {
         node.setParent(this);
      }
      
      return node;
   }
   
   protected <T extends Collection<? extends Node>> T assignParent(T nodes) {
      if (nodes != null) {
         for (Node node : nodes) {
            assignParent(node);
         }
      }
      
      return nodes;
   }
}
