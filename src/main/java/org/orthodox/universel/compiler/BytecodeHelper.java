package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.TypeUtil;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.objectweb.asm.Opcodes.*;

/**
 * Utility for generating JVM bytecode instructions, using the ASM bytecode manipulation library.
 */
public class BytecodeHelper {
    public static final String CTOR_METHOD_NAME = "<init>";

    private MethodVisitor methodVisitor;

    public BytecodeHelper(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    /**
     * Emits a constant boolean value onto the evaluation stack.
     *
     * @param value the boolean constant value to emit.
     */
    public void emitLoadBooleanOperand(boolean value) {
        methodVisitor.visitInsn(value ? ICONST_1 : ICONST_0);
    }

    /**
     * Emits a constant integer value onto the evaluation stack.
     *
     * @param value the integer constant value to emit.
     */
    public void emitLoadIntegerOperand(int value) {
        if (value >=0 && value <= 5)
            methodVisitor.visitInsn(ICONST_0+value);
        else
            methodVisitor.visitLdcInsn(value);
    }

    /**
     * Emits a constant long value onto the evaluation stack.
     *
     * @param value the long constant value to emit.
     */
    public void emitLoadIntegerOperand(long value) {
        if (value >=0L && value <= 1L)
            methodVisitor.visitInsn(LCONST_0+(int)value);
        else
            methodVisitor.visitLdcInsn(value);
    }

    /**
     * Emits a constant float value onto the evaluation stack.
     *
     * @param value the float constant value to emit.
     */
    public void emitLoadFloatOperand(float value) {
        if (value >=0f && value <= 2f)
            methodVisitor.visitInsn(FCONST_0+(int)value);
        else
            methodVisitor.visitLdcInsn(value);
    }

    /**
     * Emits a constant double value onto the evaluation stack.
     *
     * @param value the double constant value to emit.
     */
    public void emitLoadDoubleOperand(double value) {
        if (value >=0d && value <= 1d)
            methodVisitor.visitInsn(DCONST_0+(int)value);
        else
            methodVisitor.visitLdcInsn(value);
    }

    /**
     * Emits a constant {@link java.math.BigInteger} value onto the evaluation stack.
     *
     * @param value the literal string of the {@link java.math.BigInteger} constant value to emit.
     */
    public void emitLoadBigIntegerOperand(String value) {
        String className = Type.getType(BigInteger.class).getInternalName();
        methodVisitor.visitTypeInsn(NEW, className);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn(value);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, className, CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE,
                Type.getType(String.class)), false);
    }

    /**
     * Emits a constant {@link java.math.BigDecimal} value onto the evaluation stack.
     *
     * @param value the literal string of the {@link java.math.BigDecimal} constant value to emit.
     */
    public void emitLoadBigDecimalOperand(String value) {
        String className = Type.getType(BigDecimal.class).getInternalName();
        methodVisitor.visitTypeInsn(NEW, className);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn(value);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, className, CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE,
                Type.getType(String.class)), false);
    }

    /**
     * Emits a load constant {@link String} value onto the evaluation stack.
     *
     * @param value the literal string of the {@link java.math.BigDecimal} constant value to emit.
     */
    public void emitLoadStringOperand(String value) {
        methodVisitor.visitLdcInsn(value);
    }

    /**
     * Emits instuctions to box the the primitive type, if indeed it is a primitive type or no-op otherwise.
     *
     * @param primitiveType the primitive type to be boxed.
     */
    public void boxIfNeeded(Class<?> primitiveType) {
        if ( !TypeUtil.isPrimitiveType(primitiveType) ) return;

        box(primitiveType);
    }

    /**
     * Emits instuctions to box the given primitive type.
     *
     * @param primitiveType the primitive type to be boxed.
     */
    public void box(Class<?> primitiveType) {
        methodVisitor.visitMethodInsn(INVOKESTATIC,
                Type.getInternalName(BoxingFunctions.class),
                "box",
                Type.getMethodDescriptor(Type.getType(TypeUtil.getPrimitiveWrapperType(primitiveType)), Type.getType(primitiveType)),
                false
        );
    }
}
