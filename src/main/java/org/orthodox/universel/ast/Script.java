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

import java.util.List;

/**
 * The top-level goal symbol of the Universel Expression Language, equivalent to Java's Compilation Unit - but so
 * much more.
 *  
 * @author Gary Watson
 */
public class Script extends AbstractCompositeNode {
//   /** The package declaration associated with the script. */
//   private PackageDeclaration packageDeclaration;
//   /** The import declaration associated with the script. */
//   private ImportDeclaration imports;

   private List<Node> bodyElements;

//   /** The comments associated with the script. */
//   private List<Comment> comments;

   @SuppressWarnings({ "unchecked", "rawtypes" })
   public Script() {
//      this(null, null, (List)Collections.emptyList(), (List)Collections.emptyList());
   }

    @Override
    public List<Node> getChildNodes() {
      return bodyElements;
   }


//   public Script(PackageDeclaration packageDeclaration, ImportDeclaration imports, List<Node> expressions, List<Comment> comments) {
//      super(packageDeclaration, imports, expressions);
//      this.packageDeclaration = assignParent(packageDeclaration);
//      this.imports = assignParent(imports);
//      this.expressions = assignParent(expressions);
//      setComments(comments);
//   }
//
//   /**
//    * Gets the package declaration associated with the script. Any top-level, unqualified declarations will be placed in the
//    * declared package.
//    *
//    * @return the package declaration associated with the script, which may be null if there is none.
//    */
//   public PackageDeclaration getPackageDeclaration() {
//      return packageDeclaration;
//   }
//
//   /**
//    * Sets the package declaration associated with the script
//    *
//    * @param packageDeclaration the package declaration associated with the script.
//    */
//   public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
//      this.packageDeclaration = assignParent(packageDeclaration);
//   }
//
//   @Override
//   protected Node[] buildChildNodesList() {
//      ArrayList<Node> childList = new ArrayList<Node>();
//      if (packageDeclaration != null) {
//         childList.add(packageDeclaration);
//      }
//      if (imports != null) {
//         childList.add(imports);
//      }
//      if (expressions != null) {
//         childList.addAll(expressions);
//      }
//      if (comments != null) {
//         childList.addAll(comments);
//      }
//      return childList.toArray(new Node[childList.size()]);
//   }

//   /**
//    * Gets the import declaration associated with the script.
//    *
//    * @return the import declaration associated with the script.
//    */
//   public ImportDeclaration getImports() {
//      return imports;
//   }
//
//   /**
//    * Sets the import declaration associated with the script.
//    *
//    * @param imports the import declaration associated with the script.
//    */
//   public void setImports(ImportDeclaration imports) {
//      this.imports = assignParent(imports);
//      childNodesChanged();
//   }

   /**
    * @return the bodyElements the body elements that comprise the script.
    */
   public List<Node> getBodyElements() {
      return bodyElements;
   }

   /**
    * @param bodyElements the body elements to set
    */
   public void setBodyElements(List<Node> bodyElements) {
      this.bodyElements = assignParent(bodyElements);
   }
   
   /**
    * Gets the comments associated with the script.
    * 
    * @return the comments associated with the script or null if there are none.
    */
//   public List<Comment> getComments() {
//      return comments;
//   }
   
//   /**
//    * Sets the comments associated with the script.
//    *
//    * @param comments the comments associated with the script.
//    */
//   public void setComments(List<Comment> comments) {
//      this.comments = assignParent(comments);
//   }
    
   public String getCanonicalForm() {
      StringBuilder s = new StringBuilder();

//      if (packageDeclaration != null) {
//         s.append(packageDeclaration.getCanonicalForm()).append("\n");
//      }
//
//      if (imports != null) {
//         for (Node node : imports) {
//            s.append(node.getCanonicalForm()).append("\n");
//         }
//      }

      if (bodyElements != null) {
         for (Node node : bodyElements) {
            s.append(node.getCanonicalForm());
         }
      }
      
      return s.toString();
   }

   public boolean accept(UniversalCodeVisitor visitor) {
      visitor.visitScript(this);
      return true;
   }
}
