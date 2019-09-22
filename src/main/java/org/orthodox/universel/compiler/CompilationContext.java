package org.orthodox.universel.compiler;

import org.beanplanet.core.collections.DoublyLinkedListImpl;
import org.objectweb.asm.MethodVisitor;

import java.util.Deque;

public class CompilationContext {
    private MethodVisitor methodVisitor;
    private VirtualMachine virtualMachine;
    private BytecodeHelper bytecodeHelper;
    private Deque<NameScope> scopes = new DoublyLinkedListImpl<>();

    public CompilationContext(NameScope nameScope, MethodVisitor methodVisitor, VirtualMachine virtualMachine) {
        this.scopes.add(nameScope);
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

    public NameScope getNameScope() {
        return scopes.peek();
    }
}
