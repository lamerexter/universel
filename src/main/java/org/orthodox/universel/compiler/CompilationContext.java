package org.orthodox.universel.compiler;

import org.beanplanet.core.collections.DoublyLinkedListImpl;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayDeque;
import java.util.Deque;

public class CompilationContext implements NameScope {
    private MethodVisitor methodVisitor;
    private VirtualMachine virtualMachine;
    private BytecodeHelper bytecodeHelper;
    private Deque<NameScope> scopes = new ArrayDeque<>();

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

    public void pushNameScope(NameScope scope) {
        scopes.push(scope);
    }

    @Override
    public boolean canResolve(String name) {
        return scopes.stream().anyMatch(s -> s.canResolve(name));
    }

    @Override
    public void generateAccess(String name) {
        scopes.stream().filter(s -> s.canResolve(name)).findFirst()
              .orElseThrow(() -> new RuntimeException("Cannot find symbol "+name))
              .generateAccess(name);
    }
}
