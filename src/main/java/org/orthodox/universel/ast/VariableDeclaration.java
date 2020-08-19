package org.orthodox.universel.ast;

import org.beanplanet.core.collections.ListBuilder;

import java.util.List;
import java.util.Objects;

public class VariableDeclaration extends Node implements CompositeNode {
   /** The variable declaration identification information associated with this declaration. */
   private final VariableDeclarationId id;
   
   /** The initialisation expression associated with this declaration. */
   private final Expression initialiser;


   /**
    * Instantiates a new field declaration.
    * 
    * @param id the variable declaration identification information associated with this declaration
    * @param initialiser the initialisation expression associated with this declaration.
    */
   public VariableDeclaration(final VariableDeclarationId id,
                              final Expression initialiser) {
      super(TokenImage.range(id, initialiser));
      this.id = id;
      this.initialiser = initialiser;
   }
   
   /**
    * Gets the variable declaration identification information associated with this declaration.
    * 
    * @return the variable declaration identification information associated with this declaration.
    */
   public VariableDeclarationId getId() {
      return id;
   }

   /**
    * Gets the initialisation expression associated with this declaration.
    * 
    * @return the initialisation expression associated with this declaration, which may be null.
    */
   public Expression getInitialiser() {
      return initialiser;
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof VariableDeclaration)) return false;
      if (!super.equals(o)) return false;
      VariableDeclaration nodes = (VariableDeclaration) o;
      return Objects.equals(getId(), nodes.getId()) &&
             Objects.equals(getInitialiser(), nodes.getInitialiser());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getId(), getInitialiser());
   }

   /**
    * Gets a list of the children of this node.
    *
    * @return the children of this node, which may be empty but not null.
    */
   @Override
   public List<Node> getChildNodes() {
      return ListBuilder.<Node>builder()
                        .addNotNull(id, initialiser)
                        .build();
   }
}
