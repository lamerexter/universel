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

package org.orthodox.universel.cst.type.declaration;

import org.beanplanet.core.collections.ListBuilder;
import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.core.models.path.SimpleNamePath;
import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.ast.PackageDeclaration;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.methods.MethodDeclaration;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * Super class of AST nodes that are type declarations.
 */
public class TypeDeclaration extends Node implements CompositeNode {
   /** The modifiers associated with this type declaration. */
   private final Modifiers modifiers;

   /** The package in which this type is declared. */
   private final PackageDeclaration packageDeclaration;

   /** The simple name of the type declared. */
   private final Name name;

   /** The type parameters associated with the declared type. */
   private final NodeSequence<TypeParameter> typeParameters;

   /** The names of the types this declared type extends. */
   private List<TypeReference> extendsList;

   /** The names of the types this declared type implements. */
   private List<TypeReference> implementsList;

   /** The members declared by the type. */
   private final NodeSequence<Node> members;

   /**
    * Instantiates a new type declaration
    *
    * @param modifiers the modifiers associated with this type declaration.
    * @param packageDeclaration the package in which this type is declared.
    * @param name the simple name of the type declared.
    * @param typeParameters the type parameters associated with the declared type.
    * @param members the members declared by the type.
    */
   public TypeDeclaration(Modifiers modifiers,
                          PackageDeclaration packageDeclaration,
                          Name name,
                          NodeSequence<TypeParameter> typeParameters,
                          NodeSequence<Node> members,
                          List<TypeReference> extendsList,
                          List<TypeReference> implementsList) {
      super(TokenImage.builder()
                      .range(modifiers, packageDeclaration, name, typeParameters, members)
                      .build());
      this.modifiers = modifiers;
      this.packageDeclaration = packageDeclaration;
      this.name = name;
      this.typeParameters = typeParameters;
      this.members = members;
      this.extendsList = extendsList;
      this.implementsList = implementsList;

   }


   /**
    * Gets the modifiers associated with this type declaration.
    *
    * @return the modifiers associated with this type declaration.
    */
   public Modifiers getModifiers() {
      return modifiers;
   }

   /**
    * Gets the package in which this type is declared
    *
    * @return the package in which this type is declared
    */
   public PackageDeclaration getPackageDeclaration() {
      return packageDeclaration;
   }

   /**
    * Gets the simple name of the type declared.
    *
    * @return the simple name of the type declared.
    */
   public Name getName() {
      return name;
   }

   /**
    * Gets the type parameters associated with the declared type.
    *
    * @return the type parameters associated with the declared type.
    */
   public NodeSequence<TypeParameter> getTypeParameters() {
      return typeParameters == null ? NodeSequence.emptyNodeSequence() : typeParameters;
   }

   /**
    * Gets the members declared by the type.
    *
    * @return the members declared by the type.
    */
   public NodeSequence<Node> getMembers() {
      return members == null ? NodeSequence.emptyNodeSequence() : members;
   }

   /**
    * Gets the members declared by the type.
    *
    * @return the members declared by the type.
    */
   public List<MethodDeclaration> getMethods() {
      return getMembers().getNodes().stream().filter(MethodDeclaration.class::isInstance).map(MethodDeclaration.class::cast).collect(Collectors.toList());
   }

   /**
    * Gets the names of the types this declared type extends.
    * 
    * @return the names of the types this declared type extends.
    */
   public List<TypeReference> getExtendsList() {
      return extendsList == null ? emptyList() : extendsList;
   }

   /**
    * Gets the names of the types this declared type implements.
    * 
    * @return the names of the types this declared type implements.
    */
   public List<TypeReference> getImplementsList() {
      return implementsList == null ? emptyList() : implementsList;
   }

   @Override
   public List<Node> getChildNodes() {
      return ListBuilder.<Node>builder()
                 .addNotNull(modifiers, packageDeclaration, name)
                 .addAllNotNull(getTypeParameters().getNodes())
                 .addAllNotNull(getMembers().getNodes())
                 .addAllNotNull(extendsList)
                 .addAllNotNull(implementsList)
                 .build();
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof TypeDeclaration)) return false;
      if (!super.equals(o)) return false;
      TypeDeclaration nodes = (TypeDeclaration) o;
      return Objects.equals(getModifiers(), nodes.getModifiers()) &&
             Objects.equals(getPackageDeclaration(), nodes.getPackageDeclaration()) &&
             Objects.equals(getName(), nodes.getName()) &&
             Objects.equals(getTypeParameters(), nodes.getTypeParameters()) &&
             Objects.equals(getExtendsList(), nodes.getExtendsList()) &&
             Objects.equals(getImplementsList(), nodes.getImplementsList()) &&
             Objects.equals(getMembers(), nodes.getMembers());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(),
                          getModifiers(),
                          getPackageDeclaration(),
                          getName(),
                          getTypeParameters(),
                          getExtendsList(),
                          getImplementsList(),
                          getMembers()
      );
   }

   public NamePath getFullyQualifiedName() {
      return packageDeclaration != null ? packageDeclaration.getName().joinSingleton(name.getName()) : new SimpleNamePath(name.getName());
   }

   public boolean isSequance() {
      return false;
   }
}