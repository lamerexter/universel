package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;

/**
 * Utility for generating JVM bytecode instructions, using the ASM bytecode manipulation library.
 */
public class BytecodeHelper {
    private MethodVisitor methodVisitor;

    public BytecodeHelper(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    /**
     * Emits a constant boolean value onto the evaluation stack.
     *
     * @param value the boolean constant value to emit.
     */
    public void emitLoadBooleanOperand(boolean value) {
        methodVisitor.visitInsn(value ? ICONST_1 : ICONST_0);
    }
}
