package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;

/**
 * A composite code generator which simply executes its component parts in sequence.
 */
public class CompositeMethodCodeGenerator implements MethodCodeGenerator {
   private MethodCodeGenerator[] parts;
   
   public CompositeMethodCodeGenerator(final MethodCodeGenerator ... parts) {
      this.parts = parts;
   }
   
   /**
    * Called to generate or augment the code of a method through the parts that make
    * up this composite.
    * 
    * @param mv the method visitor through which the code will be added.
    */
   public void generate(final MethodVisitor mv) {
      if (parts == null) return;
      
      for (MethodCodeGenerator part : parts) {
         part.generate(mv);
      }
   }
}
