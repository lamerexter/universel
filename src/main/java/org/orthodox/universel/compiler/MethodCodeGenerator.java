package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;

/**
 * Generates complete or additional code to the body of a method.
 */
public interface MethodCodeGenerator {
   /**
    * Called to generate or augment the code of a method.
    * 
    * @param mv the method visitor through which the code will be added.
    */
   void generate(MethodVisitor mv);
}
