package org.orthodox.universel.ast;

import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.List;

/**
 * Abstract Syntax Tree field declaration node.
 * 
 * @author Gary Watson
 */
public class FieldDeclaration extends AbstractVariableDeclaration {
   /**
    * Instantiates a new field declaration.
    * 
    * @param modifiers the modifiers applied to the type declaration.
    * @param declarationType the type associated with this field declaration.
    * @param variableDeclarations a list of the variable declarations associated with this declaration instance.
    */
   public FieldDeclaration(Modifiers modifiers,
                           TypeReference declarationType,
                           List<VariableDeclaration> variableDeclarations) {
      super(modifiers, declarationType, variableDeclarations);
   }

   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitFieldDeclaration(this);
   }
}
