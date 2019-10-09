package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.TypeUtil;

import java.util.Stack;

public class VirtualMachine {
    private BytecodeHelper bch;
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

    public VirtualMachine(BytecodeHelper bytecodeHelper) {
        this.bch = bytecodeHelper;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> popOperand() {
        return (Class<T>)operandStack.pop();
    }

    public void convertOrBoxOperandIfNeeded(Class<?> toType) {
        Class<?> operandType = peekOperandStack();
        if ( toType.isAssignableFrom(operandType) ) return; // Compatible

        if (TypeUtil.isPrimitiveType(operandType) && TypeUtil.isPrimitiveTypeOrWrapperClass(toType) )
            box();
        else if (TypeUtil.isPrimitiveTypeOrWrapperClass(operandType) && TypeUtil.isPrimitiveType(toType) )
            unbox();
        else
            convert(toType);
    }

    private void convert(Class<?> toType) {
        throw new UnsupportedOperationException();
    }

    private void unbox() {
        throw new UnsupportedOperationException();
    }

    private void box() {
        replaceTopOperand(bch.box(peekOperandStack()));
    }

    private void replaceTopOperand(Class<?> operandType) {
        operandStack.pop();
        operandStack.push(operandType);
    }
}
