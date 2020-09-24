package org.orthodox.universel.ast;

import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.ast.TokenImage.range;

/**
 * An abstract variable declaration, containing state and accessors common to most.
 */
public abstract class AbstractVariableDeclaration extends Node implements Declaration{
   /** The type modifiers associated with this declaration. */
   private Modifiers modifiers;

   /** The type associated with this field declaration. */
   private TypeReference declarationType;
   
   /** The individual variable declarations associated with this field declaration block. */
   private List<VariableDeclaration> variableDeclarations;

   /**
    * Instantiates a new field declaration.
    * 
    * @param modifiers the modifiers applied to the type declaration.
    * @param declarationType the type associated with this field declaration.
    * @param variableDeclarations a list of the variable declarations associated with this declaration instance.
    */
   @SuppressWarnings("unchecked")
   public AbstractVariableDeclaration(Modifiers modifiers,
                           TypeReference declarationType,
                           List<VariableDeclaration> variableDeclarations) {
      super(range(modifiers, (List)variableDeclarations));
      this.modifiers = modifiers;
      this.declarationType = declarationType;
      this.variableDeclarations = variableDeclarations;
   }

   /**
    * Gets the type modifiers associated with this declaration.
    * 
    * @return the type modifiers associated with this declaration.
    */
   public Modifiers getModifiers() {
      return modifiers;
   }

   /**
    * Gets the type associated with this field declaration.
    * 
    * @return the type associated with this field declaration.
    */
   public TypeReference getDeclarationType() {
      return declarationType;
   }

   /**
    * Gets the individual variable declarations associated with this field declaration block.
    * 
    * @return the individual variable declarations associated with this field declaration block.
    */
   public List<VariableDeclaration> getVariableDeclarations() {
      return variableDeclarations;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof AbstractVariableDeclaration)) return false;
      if (!super.equals(o)) return false;
      AbstractVariableDeclaration nodes = (AbstractVariableDeclaration) o;
      return Objects.equals(getModifiers(), nodes.getModifiers()) &&
             Objects.equals(getDeclarationType(), nodes.getDeclarationType()) &&
             Objects.equals(getVariableDeclarations(), nodes.getVariableDeclarations());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getModifiers(), getDeclarationType(), getVariableDeclarations());
   }
}
