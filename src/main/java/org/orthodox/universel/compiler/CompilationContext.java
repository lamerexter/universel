package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;

public class CompilationContext {
    private MethodVisitor methodVisitor;
    private VirtualMachine virtualMachine;
    private BytecodeHelper bytecodeHelper;

    public CompilationContext(MethodVisitor methodVisitor, VirtualMachine virtualMachine) {
        this.methodVisitor = methodVisitor;
        this.virtualMachine = virtualMachine;
        this.bytecodeHelper = new BytecodeHelper(methodVisitor);
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public BytecodeHelper getBytecodeHelper() {
        return bytecodeHelper;
    }
}
