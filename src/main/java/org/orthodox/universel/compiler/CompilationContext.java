package org.orthodox.universel.compiler;

import org.beanplanet.messages.domain.Messages;
import org.beanplanet.messages.domain.MessagesImpl;
import org.objectweb.asm.MethodVisitor;
import org.orthodox.universel.ast.MethodCall;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.beanplanet.messages.domain.MessagesImpl.messages;

public class CompilationContext implements NameScope, MethodCallScope {
    private MethodVisitor methodVisitor;
    private VirtualMachine virtualMachine;
    private BytecodeHelper bytecodeHelper;
    private Deque<NameScope> scopes = new ArrayDeque<>();
    private Deque<MethodCallScope> methodCallScopes = new ArrayDeque<>();

    private Messages messages = messages();

    public CompilationContext(MethodVisitor methodVisitor, VirtualMachine virtualMachine) {
        this.methodVisitor = methodVisitor;
        this.virtualMachine = virtualMachine;
        this.bytecodeHelper = new BytecodeHelper(methodVisitor);
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
