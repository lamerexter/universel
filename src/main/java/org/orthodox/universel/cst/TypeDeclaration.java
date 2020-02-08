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

import static org.beanplanet.core.util.CollectionUtil.nullSafe;

/**
 * Super class of AST nodes that are type declarations.
 */
public class TypeDeclaration extends AbstractCompositeNode {
   /** The modifiers associated with the type declaration. */
   private Modifiers modifiers;
   /** The name defined by this type declaration. */
   private String name;
   /** The declared members of this type. */
   private List<Node> members;
   /** The parameterised types associated with this type declaration. */
   private List<TypeParameter> typeParameters; 
   /** The names of the types this declared type extends. */
   private List<ClassOrInterfaceType> extendsList;
   /** The names of the types this declared type implements. */
   private List<ClassOrInterfaceType> implementsList;

   /**
    * Instantiates a new type declaration
    *
    * @param tokenImage the parser token image.
    * @param name the name defined by this type declaration.
    * @param modifiers the modifiers applied to the type declaration.
    * @param members the declared members of this type.
    * @param typeParameters the parameterised types associated with this type declaration.
    * @param extendsList the types this declared type extends.
    * @param implementsList the types this declared type implements.
    */
   public TypeDeclaration(TokenImage tokenImage,
                          String name,
                          Modifiers modifiers,
                          List<Node> members,
                          List<TypeParameter> typeParameters,
                          List<ClassOrInterfaceType> extendsList,
                          List<ClassOrInterfaceType> implementsList) {
      super(tokenImage);
      this.name = name;
      this.modifiers = modifiers;
      this.members = assignParent(members);
      this.typeParameters = assignParent(typeParameters);
      this.extendsList = assignParent(extendsList);
      this.implementsList = assignParent(implementsList);
   }

   /**
    * Instantiates a new type declaration
    *
    * @param tokenImage the parser token image.
    * @param name the name defined by this type declaration.
    * @param modifiers the modifiers applied to the type declaration.
    * @param members the declared members of this type.
    */
   public TypeDeclaration(TokenImage tokenImage,
                          String name,
                          Modifiers modifiers,
                          List<Node> members) {
      this(tokenImage, name, modifiers, members, null);
   }

   /**
    * Instantiates a new type declaration
    *
    * @param tokenImage the parser token image.
    * @param name the name defined by this type declaration.
    * @param modifiers the modifiers applied to the type declaration.
    * @param members the declared members of this type.
    * @param implementsList the types this declared type implements.
    */
   public TypeDeclaration(TokenImage tokenImage,
                          String name,
                          Modifiers modifiers,
                          List<Node> members,
                          List<ClassOrInterfaceType> implementsList) {
      this(tokenImage, name, modifiers, members, null, null, implementsList);
   }

   /**
    * Gets the name defined by this type declaration.
    * 
    * @return the name defined by this type declaration.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the name defined by this type declaration.
    * 
    * @param name the name defined by this type declaration.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Returns the modifiers associated with the type declaration.
    * 
    * @return the modifiers associated with the type declaration
    */
   public Modifiers getModifiers() {
      return modifiers;
   }

   /**
    * Sets the modifiers associated with the type declaration.
    * 
    * @param modifiers the modifiers to associate with the type declaration.
    */
   public void setModifiers(Modifiers modifiers) {
      this.modifiers = modifiers;
   }

   /**
    * Gets the declared members of this type.
    * 
    * @return the declared members of this type, which may be null.
    */
   public List<Node> getMembers() {
      return members;
   }

   /**
    * Sets the declared members of this type.
    * 
    * @param members the declared members of this type.
    */
   public void setMembers(List<Node> members) {
      this.members = members;
   }

   /**
    * Gets the parameterised types associated with this type declaration.
    * 
    * @return the parameterised types associated with this type declaration.
    */
   public List<TypeParameter> getTypeParameters() {
      return typeParameters;
   }

   /**
    * Sets the parameterised types associated with this type declaration.
    * 
    * @param typeParameters the parameterised types associated with this type declaration.
    */
   public void setTypeParameters(List<TypeParameter> typeParameters) {
      this.typeParameters = typeParameters;
   }

   /**
    * Gets the names of the types this declared type extends.
    * 
    * @return the names of the types this declared type extends.
    */
   public List<ClassOrInterfaceType> getExtendsList() {
      return extendsList;
   }

   /**
    * Sets the names of the types this declared type extends.
    * 
    * @param extendsList the names of the types this declared type extends.
    */
   public void setExtendsList(List<ClassOrInterfaceType> extendsList) {
      this.extendsList = extendsList;
   }

   /**
    * Gets the names of the types this declared type implements.
    * 
    * @return the names of the types this declared type implements.
    */
   public List<ClassOrInterfaceType> getImplementsList() {
      return implementsList;
   }

   /**
    * Sets the names of the types this declared type implements.
    * 
    * @param implementsList the names of the types this declared type implements.
    */
   public void setImplementsList(List<ClassOrInterfaceType> implementsList) {
      this.implementsList = implementsList;
   }
   
   public boolean hasMemberOfType(Class<? extends Node> nodeType) {
      for (Node member : nullSafe(members)) {
         if (nodeType.isAssignableFrom(member.getClass())) {
            return true;
         }
      }
      
      return false;
   }
}