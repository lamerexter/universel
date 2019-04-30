package org.orthodox.universel.compiler;

import java.util.Stack;

public class VirtualMachine {
    private Stack<Class<?>> operandStack = new Stack<>();

    public Class<?> peekOperandStack() {
        return operandStack.peek();
    }

    public void loadOperandConstant(boolean operand) {
        loadOperandOfType(boolean.class);
    }

    public void loadOperandConstant(int operand) {
        loadOperandOfType(int.class);
    }

    public void loadOperandConstant(long operand) {
        loadOperandOfType(long.class);
    }

    public void loadOperandConstant(float operand) {
        loadOperandOfType(float.class);
    }

    public void loadOperandConstant(double operand) {
        loadOperandOfType(double.class);
    }

    public void loadOperandOfType(Class<?> operandType) {
        operandStack.push(operandType);
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> popOperand() {
        return (Class<T>)operandStack.pop();
    }
}
