package org.orthodox.universel.compiler;

import java.util.Stack;

public class VirtualMachine {
    private Stack<Class<?>> operandStack = new Stack<>();

    public void loadOperandConstant(boolean operand) {
        operandStack.push(boolean.class);
    }
}
