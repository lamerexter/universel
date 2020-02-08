package org.orthodox.universel.compiler;

import org.beanplanet.messages.domain.Messages;
import org.objectweb.asm.MethodVisitor;
import org.orthodox.universel.cst.MethodCall;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.ArrayDeque;
import java.util.Deque;

public class CompilationContext implements NameScope, MethodCallScope {
    private final MethodVisitor methodVisitor;
    private final VirtualMachine virtualMachine;
    private final BytecodeHelper bytecodeHelper;
    private final Deque<NameScope> scopes = new ArrayDeque<>();
    private final Deque<MethodCallScope> methodCallScopes = new ArrayDeque<>();

    private final Messages messages;

    public CompilationContext(final MethodVisitor methodVisitor,
                              final VirtualMachine virtualMachine,
                              final Messages messages) {
        this.methodVisitor = methodVisitor;
        this.virtualMachine = virtualMachine;
        this.bytecodeHelper = new BytecodeHelper(methodVisitor);
        this.messages = messages;
    }

    public Messages getMessages() {
        return messages;
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

    public void pushMethodCallScope(MethodCallScope scope) {
        methodCallScopes.push(scope);
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

    @Override
    public boolean canResolve(MethodCall methodCall) {
        return methodCallScopes.stream().anyMatch(s -> s.canResolve(methodCall));
    }

    @Override
    public void generateCall(UniversalCodeVisitor visitor,
                             MethodCall methodCall) {
        methodCallScopes.stream().filter(s -> s.canResolve(methodCall)).findFirst()
              .orElseThrow(() -> new RuntimeException("Cannot find applicable methos "+methodCall))
              .generateCall(visitor, methodCall);
    }
}
