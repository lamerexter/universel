package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.TypeUtil;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static java.util.Arrays.stream;
import static org.objectweb.asm.Opcodes.*;

/**
 * Utility for generating JVM bytecode instructions, using the ASM bytecode manipulation library.
 */
public class BytecodeHelper {
    public static final String CTOR_METHOD_NAME = "<init>";
    public static final Type[] EMPTY_TYPES = new Type[0];
    public static final Type OBJECT_TYPE = Type.getType(Object.class);
    public static final Type[] OBJECT_TYPE_ARRAY = new Type[] { OBJECT_TYPE };


    private Queue<ClassWriter> cwStack = new LinkedList<>();
    private Queue<MethodVisitor> mvStack = new LinkedList<>();

    public BytecodeHelper() {}

    public BytecodeHelper(MethodVisitor methodVisitor) {
        mvStack.add(methodVisitor);
    }

    /**
     * Emits a constant boolean value onto the evaluation stack.
     *
     * @param value the boolean constant value to emit.
     */
    public void emitLoadBooleanOperand(boolean value) {
        peekMethodVisitor().visitInsn(value ? ICONST_1 : ICONST_0);
    }

    /**
     * Emits a constant integer value onto the evaluation stack.
     *
     * @param value the integer constant value to emit.
     */
    public void emitLoadIntegerOperand(int value) {
        if (value >=0 && value <= 5)
            peekMethodVisitor().visitInsn(ICONST_0+value);
        else
            peekMethodVisitor().visitLdcInsn(value);
    }

    /**
     * Emits a constant long value onto the evaluation stack.
     *
     * @param value the long constant value to emit.
     */
    public void emitLoadIntegerOperand(long value) {
        if (value >=0L && value <= 1L)
            peekMethodVisitor().visitInsn(LCONST_0+(int)value);
        else
            peekMethodVisitor().visitLdcInsn(value);
    }

    /**
     * Emits a constant float value onto the evaluation stack.
     *
     * @param value the float constant value to emit.
     */
    public void emitLoadFloatOperand(float value) {
        if (value >=0f && value <= 2f)
            peekMethodVisitor().visitInsn(FCONST_0+(int)value);
        else
            peekMethodVisitor().visitLdcInsn(value);
    }

    /**
     * Emits a constant double value onto the evaluation stack.
     *
     * @param value the double constant value to emit.
     */
    public void emitLoadDoubleOperand(double value) {
        if (value >=0d && value <= 1d)
            peekMethodVisitor().visitInsn(DCONST_0+(int)value);
        else
            peekMethodVisitor().visitLdcInsn(value);
    }

    /**
     * Emits a constant {@link java.math.BigInteger} value onto the evaluation stack.
     *
     * @param value the literal string of the {@link java.math.BigInteger} constant value to emit.
     */
    public void emitLoadBigIntegerOperand(String value) {
        String className = Type.getType(BigInteger.class).getInternalName();
        peekMethodVisitor().visitTypeInsn(NEW, className);
        peekMethodVisitor().visitInsn(DUP);
        peekMethodVisitor().visitLdcInsn(value);
        peekMethodVisitor().visitMethodInsn(INVOKESPECIAL, className, CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE,
                Type.getType(String.class)), false);
    }

    /**
     * Emits a constant {@link java.math.BigDecimal} value onto the evaluation stack.
     *
     * @param value the literal string of the {@link java.math.BigDecimal} constant value to emit.
     */
    public void emitLoadBigDecimalOperand(String value) {
        String className = Type.getType(BigDecimal.class).getInternalName();
        peekMethodVisitor().visitTypeInsn(NEW, className);
        peekMethodVisitor().visitInsn(DUP);
        peekMethodVisitor().visitLdcInsn(value);
        peekMethodVisitor().visitMethodInsn(INVOKESPECIAL, className, CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE,
                Type.getType(String.class)), false);
    }

    /**
     * Emits a load constant {@link String} value onto the evaluation stack.
     *
     * @param value the literal string of the {@link java.math.BigDecimal} constant value to emit.
     */
    public void emitLoadStringOperand(String value) {
        peekMethodVisitor().visitLdcInsn(value);
    }

    /**
     * Emits a load null constant instruction.
     */
    public void emitLoadNullOperand() {
        peekMethodVisitor().visitInsn(ACONST_NULL);
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
        peekMethodVisitor().visitMethodInsn(INVOKESTATIC,
                Type.getInternalName(BoxingFunctions.class),
                "box",
                Type.getMethodDescriptor(Type.getType(TypeUtil.getPrimitiveWrapperType(primitiveType)), Type.getType(primitiveType)),
                false
        );
    }

    public void emitInstantiateType(Class<?> type) {
        String className = Type.getInternalName(type);
        peekMethodVisitor().visitTypeInsn(NEW, className);
        peekMethodVisitor().visitInsn(DUP);
        peekMethodVisitor().visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES), false);
    }

    public void emitDuplicate() {
        peekMethodVisitor().visitInsn(DUP);
    }

    public void emitInvokeInstanceMethod(Class<?> type, String methodName, Class<?> returnType, Class<?> ... paramTypes) {
        String className = Type.getInternalName(type);
        peekMethodVisitor().visitMethodInsn(
                type.isInterface() ? INVOKEINTERFACE : INVOKEVIRTUAL,
                className,
                methodName,
                Type.getMethodDescriptor(Type.getType(returnType), toTypes(paramTypes)),
                type.isInterface());
    }


    private static Type[] toTypes(Class<?>[] paramTypes) {
        return paramTypes == null ? null : stream(paramTypes).map(Type::getType).toArray(Type[]::new);
    }

    public void emitPop() {
        peekMethodVisitor().visitInsn(POP);
    }

    public ClassWriter peekClassWriter() { return cwStack.peek(); }
    public MethodVisitor peekMethodVisitor() {
        return mvStack.peek();
    }

    public ClassWriter generateClass(int classVersion, int modifiers, String className, Class<?> superTyoe, Class<?> ... interfaceTypes) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);

        cw.visit(classVersion, modifiers, className, null, Type.getInternalName(superTyoe),
                 interfaceTypes == null ? null : stream(interfaceTypes).map(Type::getDescriptor).toArray(String[]::new));

        cwStack.add(cw);
        return cw;
    }

    public MethodVisitor generateMethod(int modifiers, String methodName, Class<?> returnType, Class<?> ... paramTypes) {
        MethodVisitor mv = peekClassWriter().visitMethod(modifiers, methodName, Type.getMethodDescriptor(Type.getType(returnType),
                                                                                                         paramTypes == null ? null : stream(paramTypes).map(Type::getType).toArray(Type[]::new)), null, null);
        mv.visitCode();
        mvStack.add(mv);
        return mv;
    }

    public void emitLoadLocal(boolean staticMethod, int paramPosition, Class<?> type) {
        int varPosition = staticMethod ? paramPosition : paramPosition+1;
        if (type.isPrimitive()) {
            if (type == boolean.class
                || type == int.class
                || type == char.class
                || type == short.class
                || type == byte.class ) {
                peekMethodVisitor().visitVarInsn(ILOAD, varPosition);
            } else if (type == double.class) {
                peekMethodVisitor().visitVarInsn(DLOAD, varPosition);
            } else if (type == float.class) {
                peekMethodVisitor().visitVarInsn(FLOAD, varPosition);
            } else if (type == long.class) {
                peekMethodVisitor().visitVarInsn(LLOAD, varPosition);
            }
        } else {
            peekMethodVisitor().visitVarInsn(ALOAD, varPosition);
        }
    }
}
