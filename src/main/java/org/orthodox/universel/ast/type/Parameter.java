package org.orthodox.universel.ast.type;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;

/**
 * Universel definition of a parameter on the Abstract Syntax Tree (AST). Unlike Java parameters, these
 * can be named.
 */
public class Parameter extends Node implements CompositeNode {
   private final Modifiers modifiers;

   private final TypeReference type;

   private final boolean isVarArgs;

   private final Name name;

   /**
    * Constructs a new AST parameter node instance.
    *
    * @param modifiers the modifiers associated with the parameter.
    * @param type the type of the parameter.
    * @param isVarArgs whether the parameter is a variable arguments parameter.
    * @param name the formal parameter name.
    */
   public Parameter(Modifiers modifiers, TypeReference type, boolean isVarArgs, Name name) {
       super(TokenImage.builder().add(modifiers).add(type).add(name).build());
       this.modifiers = modifiers;
       this.type = type;
       this.isVarArgs = isVarArgs;
       this.name = name;
   }

   public Modifiers getModifiers() {
      return modifiers;
   }

   public TypeReference getType() {
      return type;
   }

   public boolean isVarArgs() {
      return isVarArgs;
   }

   public Name getName() {
      return name;
   }

   @Override
   public Class<?> getTypeDescriptor() {
      return type == null ? null : type.getTypeDescriptor();
   }

   @Override
   public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof Parameter)) return false;
      if (!super.equals(o)) return false;
      Parameter parameter = (Parameter) o;
      return isVarArgs() == parameter.isVarArgs() &&
             Objects.equals(getModifiers(), parameter.getModifiers()) &&
             Objects.equals(getType(), parameter.getType()) &&
             Objects.equals(getName(), parameter.getName());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getModifiers(), getType(), isVarArgs(), getName());
   }

   @Override
   public List<Node> getChildNodes() {
      return ListBuilder.<Node>builder()
                 .addNotNull(modifiers)
                 .addNotNull(type)
                 .addNotNull(name)
          .build();
   }

   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitParameter(this);
   }
}
