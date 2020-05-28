package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.TypeUtil;
import org.objectweb.asm.MethodVisitor;

import java.util.Stack;

public class VirtualMachine {
    private BytecodeHelper bch;
    private Stack<Class<?>> operandStack = new Stack<>();

    public boolean operandStackIsEmpty() {
        return operandStack.isEmpty();
    }

    public Class<?> peekOperandStack() {
        return operandStack.peek();
    }

    public Class<?> peekOperandStack(int topOffset) {
        return operandStack.get(operandStack.size()-1-topOffset);
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

    public BytecodeHelper getBytecodeHelper() {
        return bch;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> popOperand() {
        return (Class<T>)operandStack.pop();
    }

    public void clearOperand() {
        operandStack.clear();
    }

    public int stackSize() {
        return operandStack.size();
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> emitAndPopOperand() {
        this.bch.emitPop();
        return (Class<T>)operandStack.pop();
    }

    public void emitAndPopToStackSize(int requiredStackSize) {
        while (operandStack.size() > requiredStackSize) {
            emitAndPopOperand();
        }
    }

    public void boxIfNeeded() {
        if ( TypeUtil.isPrimitiveType(peekOperandStack()) ) {
            box();
        }
    }

    public void convertOrAutoboxOperandIfNeeded(Class<?> toType) {
        bch.convertIfNeeded(peekOperandStack(), toType);

//        Class<?> operandType = peekOperandStack();
//        if ( toType.isAssignableFrom(operandType) ) return; // Compatible
//
//        if (TypeUtil.isPrimitiveType(operandType) && TypeUtil.isPrimitiveTypeOrWrapperClass(toType) )
//            box();
//        else if (TypeUtil.isPrimitiveTypeOrWrapperClass(operandType) && TypeUtil.isPrimitiveType(toType) )
//            unbox();
//        else {
//            if ( TypeUtil.isPrimitiveType(operandType) ) box(); // Box it first as conversion is reference-based!
//            convert(toType);
//        }
    }

    public void convert(Class<?> toType) {
        bch.convert(peekOperandStack(), toType);
        replaceTopOperand(toType);
    }

    public void unbox() {
        replaceTopOperand(bch.unbox(peekOperandStack()));
    }

    public void box() {
        replaceTopOperand(bch.box(peekOperandStack()));
    }

    private void replaceTopOperand(Class<?> operandType) {
        operandStack.pop();
        operandStack.push(operandType);
    }

    public void castIfNecessary(Class<?> targetType) {
        // TODO: Merge cast/convert/convertOrBoxOperandIfNeeded logic as all the same!
        bch.castIfNeeded(peekOperandStack(), targetType);
    }

    public void popToStackSize(int requiredStackSize) {
        while (operandStack.size() > requiredStackSize) {
            operandStack.pop();
        }
    }

    public void autoboxIfNeeded(Class<?> toType) {
        bch.autoboxIfNeeded(peekOperandStack(), toType);
        replaceTopOperand(toType);
    }
}
