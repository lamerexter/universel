package org.orthodox.universel.compiler;

import java.util.Stack;

public class VirtualMachine {
    private Stack<Class<?>> operandStack = new Stack<>();

    public Class<?> peekOperandStack() {
        return operandStack.peek();
    }

    public void loadOperandConstant(boolean operand) {
        operandStack.push(boolean.class);
    }

    public void loadOperandConstant(int operand) {
        operandStack.push(int.class);
    }

    public void loadOperandConstant(long operand) {
        operandStack.push(long.class);
    }

    public void loadOperandConstant(float operand) {
        operandStack.push(float.class);
    }

    public void loadOperandConstant(double operand) {
        operandStack.push(double.class);
    }
}
