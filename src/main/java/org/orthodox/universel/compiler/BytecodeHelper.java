package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.lang.conversion.SystemTypeConverter;
import org.beanplanet.core.lang.conversion.TypeConverter;
import org.beanplanet.core.models.path.NamePath;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.orthodox.universel.ast.Operator;
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Arrays.stream;
import static org.beanplanet.core.lang.TypeUtil.*;
import static org.objectweb.asm.Opcodes.*;
import static org.orthodox.universel.compiler.codegen.CodeGenUtil.descriptor;
import static org.orthodox.universel.compiler.codegen.CodeGenUtil.internalName;

/**
 * Utility for generating JVM bytecode instructions, using the ASM bytecode manipulation library.
 */
public class BytecodeHelper {
    public static final String CTOR_METHOD_NAME = "<init>";
    public static final Type[] EMPTY_TYPES = new Type[0];
    public static final Type OBJECT_TYPE = Type.getType(Object.class);
    public static final Type[] OBJECT_TYPE_ARRAY = new Type[] { OBJECT_TYPE };

    private static final Map<Class<?>, Integer> primitiveTypeToNewArrayAsmOpCode = new HashMap<>();


    private Deque<ClassWriter> cwStack = new LinkedList<>();
    private Deque<MethodVisitor> mvStack = new LinkedList<>();

    static {
        primitiveTypeToNewArrayAsmOpCode.put(boolean.class, T_BOOLEAN);
        primitiveTypeToNewArrayAsmOpCode.put(byte.class, T_BYTE);
        primitiveTypeToNewArrayAsmOpCode.put(char.class, T_CHAR);
        primitiveTypeToNewArrayAsmOpCode.put(double.class, T_DOUBLE);
        primitiveTypeToNewArrayAsmOpCode.put(float.class, T_FLOAT);
        primitiveTypeToNewArrayAsmOpCode.put(int.class, T_INT);
        primitiveTypeToNewArrayAsmOpCode.put(long.class, T_LONG);
        primitiveTypeToNewArrayAsmOpCode.put(short.class, T_SHORT);
    }

    public void emitLoadBooleanWrapperOperand(final boolean operand) {
        peekMethodVisitor().visitFieldInsn(GETSTATIC, internalName(Boolean.class), (operand ? "TRUE" : "FALSE"), descriptor(Boolean.class));
    }

    private enum ComputationalCategory {
        CATEGORY_1, CATEGORY_2;
    }

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
     * Emits a constant numeric value onto the evaluation stack.
     *
     * @param value the numeric constant value to emit.
     */
    public void emitLoadNumericOperand(Number value) {
        if ( value.getClass() == BigInteger.class ) {
            String className = Type.getType(BigInteger.class).getInternalName();
            peekMethodVisitor().visitTypeInsn(NEW, className);
            peekMethodVisitor().visitInsn(DUP);
            peekMethodVisitor().visitLdcInsn(value.toString());
            peekMethodVisitor().visitMethodInsn(INVOKESPECIAL, className, CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE,
                                                                                                                     Type.getType(String.class)), false);
        } else if ( value.getClass() == BigDecimal.class ) {
            String className = Type.getType(BigDecimal.class).getInternalName();
            peekMethodVisitor().visitTypeInsn(NEW, className);
            peekMethodVisitor().visitInsn(DUP);
            peekMethodVisitor().visitLdcInsn(value.toString());
            peekMethodVisitor().visitMethodInsn(INVOKESPECIAL, className, CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE,
                                                                                                                     Type.getType(String.class)), false);
        } else {
            peekMethodVisitor().visitLdcInsn(value);
        }
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
        if ( !isPrimitiveType(primitiveType) ) return;

        box(primitiveType);
    }

    /**
     * Emits instuctions to box the given primitive type.
     *
     * @param primitiveType the primitive type to be boxed.
     * @return the primtive wrapper type employed to box the given primitive type.
     */
    public Class<?> box(Class<?> primitiveType) {
        Class<?> primitiveWrapperType = getPrimitiveWrapperType(primitiveType);
        peekMethodVisitor().visitMethodInsn(INVOKESTATIC,
                                            Type.getInternalName(BoxingFunctions.class),
                                            "box",
                                            Type.getMethodDescriptor(Type.getType(primitiveWrapperType), Type.getType(primitiveType)),
                                            false
        );
        return primitiveWrapperType;
    }

    /**
     * Emits instructions to call s static method on a given class. The parameters must have already been evaluated and
     * reside on the operand stack prior to calling this method.
     *
     * @param method the static method for which the invocation code is to be generated.
     */
    public void emitInvokeStaticMethod(Method method) {
//        peekMethodVisitor().visitMethodInsn(INVOKESTATIC,
//                                            Type.getInternalName(method.getDeclaringClass()),
//                                            method.getName(),
//                                            Type.getMethodDescriptor(Type.getType(method.getReturnType()), typeArrayFor(method.getParameterTypes())),
//                                            false);
        emitInvokeStaticMethod(method.getDeclaringClass(), method.getReturnType(), method.getName(), method.getParameterTypes());
    }

    /**
     * Emits instructions to call s static method on a given class. The parameters must have already been evaluated and
     * reside on the operand stack prior to calling this method.
     *
     * @param declaringType the enclosing type within which the static method is declared.
     * @param returnType the return type of the method.
     * @param name the method name.
     * @param parameterTypes the types of the parameters.
     */
    public void emitInvokeStaticMethod(Class<?> declaringType,
                                       Class<?> returnType,
                                       String name,
                                       Class<?> ... parameterTypes) {
        peekMethodVisitor().visitMethodInsn(INVOKESTATIC,
                                            Type.getInternalName(declaringType),
                                            name,
                                            Type.getMethodDescriptor(Type.getType(returnType), typeArrayFor(parameterTypes)),
                                            false);
    }

    /**
     * Emits instructions to call s static method on a given class. The parameters must have already been evaluated and
     * reside on the operand stack prior to calling this method.
     *
     * @param declaringType the enclosing type within which the static method is declared.
     * @param returnType the return type of the method.
     * @param name the method name.
     * @param parameterTypes the types of the parameters.
     */
    public void emitInvokeStaticMethod(TypeReference declaringType,
                                       Class<?> returnType,
                                       String name,
                                       Class<?> ... parameterTypes) {
        peekMethodVisitor().visitMethodInsn(INVOKESTATIC,
                                            getInternalName(declaringType),
                                            name,
                                            getMethodDescriptor(returnType, parameterTypes),
                                            declaringType.isInterface());
    }

    public void convert(final Class<?> fromType, final Class<?> toType) {
        //--------------------------------------------------------------------------------------------------------------
        // Runtime type conversion, via TypeConverter, is reference-based so may need to convert a primitive
        //--------------------------------------------------------------------------------------------------------------
        if ( isPrimitiveType(fromType) ) box(fromType);

        //--------------------------------------------------------------------------------------------------------------
        // Prepare to call into the Type Converter subsystem to convert to the target type. This is Object-based, with
        // primitives needing to be wrapped accordingly.
        //--------------------------------------------------------------------------------------------------------------
        Class<?> toTypeWrapped = ensureNonPrimitiveType(toType);
        MethodVisitor mv = peekMethodVisitor();
        mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(SystemTypeConverter.class), "getInstance",
                           Type.getMethodDescriptor(Type.getType(TypeConverter.class), EMPTY_TYPES));
        mv.visitInsn(SWAP);
        mv.visitLdcInsn(Type.getType(toTypeWrapped));
        mv.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(TypeConverter.class), "convert",
                           Type.getMethodDescriptor(OBJECT_TYPE, OBJECT_TYPE, Type.getType(Class.class)));

        //--------------------------------------------------------------------------------------------------------------
        // We are left with the converted value on the operand stack, exiting the Type Conversions subsystem.
        // Unbox if the target type is primitive, or simply cast to the Object-ref target type.
        //--------------------------------------------------------------------------------------------------------------
        if ( isPrimitiveType(toType) ) {
            unbox(toType);
        } else {
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(toType));
        }
    }

    public Class<?> unbox(Class<?> fromType) {
        Class<?> toType = TypeUtil.primitiveTypeFor(fromType);

        peekMethodVisitor().visitMethodInsn(INVOKESTATIC, Type.getInternalName(BoxingFunctions.class), toType.getName() + "Unbox",
                           Type.getMethodDescriptor(Type.getType(toType), OBJECT_TYPE));

        return toType;
    }

    public void emitInstantiateType(Class<?> type) {
        String className = Type.getInternalName(type);
        peekMethodVisitor().visitTypeInsn(NEW, className);
        peekMethodVisitor().visitInsn(DUP);
        peekMethodVisitor().visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES), false);
    }

    /**
     * Duplicates the operand at the top of the evaluation stack. The operation code for the duplicate operation is derived
     * from the lookup table, <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.11.1">Actual and Computational types in the Java Virtual Machine.</a>.
     *
     * @param actualType the Actual type, as defined in the JVM.
     * @return the category of the corresponding computational type in the JVM.
     */
    public void emitDuplicate(final Class<?> actualType) {
        peekMethodVisitor().visitInsn(getCategoryForActualType(actualType) == ComputationalCategory.CATEGORY_1 ? DUP : DUP2);
    }

    public void emitInvokeInstanceMethod(Class<?> type, Class<?> returnType, String methodName, Class<?> ... paramTypes) {
        String className = Type.getInternalName(type);
        peekMethodVisitor().visitMethodInsn(
                type.isInterface() ? INVOKEINTERFACE : INVOKEVIRTUAL,
                className,
                methodName,
                Type.getMethodDescriptor(Type.getType(returnType), toTypes(paramTypes)),
                type.isInterface());
    }

    /**
     * Taken from the lookup table, <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.11.1">Actual and Computational types in the Java Virtual Machine.</a>.
     *
     * @param actualType the Actual type, as defined in the JVM.
     * @return the category of the corresponding computational type in the JVM.
     */
    private ComputationalCategory getCategoryForActualType(final Class<?> actualType) {
        return (actualType == long.class || actualType == double.class) ? ComputationalCategory.CATEGORY_2 : ComputationalCategory.CATEGORY_1;
    }

    /**
     * Emits instructions to call an instance method on a given class. The parameters must have already been evaluated and
     * reside on the operand stack prior to calling this method.
     *
     * @param declaringType the enclosing type within which the static method is declared.
     * @param returnType the return type of the method.
     * @param name the method name.
     * @param parameterTypes the types of the parameters.
     */
    public void emitInvokeInstanceMethod(TypeReference declaringType,
                                         Class<?> returnType,
                                         String name,
                                         Class<?> ... parameterTypes) {
        peekMethodVisitor().visitMethodInsn(declaringType.isInterface() ? INVOKEINTERFACE : INVOKEVIRTUAL,
                                            declaringType.getName().join("/"),
                                            name,
                                            Type.getMethodDescriptor(Type.getType(returnType), typeArrayFor(parameterTypes)),
                                            declaringType.isInterface());
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

    public ClassWriter generateClass(int classVersion, int modifiers, NamePath className, Class<?> superType, Class<?> ... interfaceTypes) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);

        cw.visit(classVersion, modifiers, className.join("/"), null, superType == null ? Type.getInternalName(Object.class) : Type.getInternalName(superType),
                 interfaceTypes == null ? null : stream(interfaceTypes).map(Type::getDescriptor).toArray(String[]::new));

        cwStack.push(cw);
        return cw;
    }

    public MethodVisitor generateMethod(int modifiers, String methodName, Class<?> returnType, Class<?> ... paramTypes) {
        MethodVisitor mv = peekClassWriter().visitMethod(modifiers, methodName, Type.getMethodDescriptor(Type.getType(returnType),
                                                                                                         paramTypes == null ? EMPTY_TYPES : stream(paramTypes).map(Type::getType).toArray(Type[]::new)), null, null);
        mv.visitCode();
        mvStack.push(mv);
        return mv;
    }

    public void emitLoadLocal(int paramPosition, Class<?> type) {
        if (type.isPrimitive()) {
            if (type == boolean.class
                || type == int.class
                || type == char.class
                || type == short.class
                || type == byte.class ) {
                peekMethodVisitor().visitVarInsn(ILOAD, paramPosition);
            } else if (type == double.class) {
                peekMethodVisitor().visitVarInsn(DLOAD, paramPosition);
            } else if (type == float.class) {
                peekMethodVisitor().visitVarInsn(FLOAD, paramPosition);
            } else if (type == long.class) {
                peekMethodVisitor().visitVarInsn(LLOAD, paramPosition);
            }
        } else {
            peekMethodVisitor().visitVarInsn(ALOAD, paramPosition);
        }
    }

    public void emitLoadLocal(int paramPosition, org.orthodox.universel.ast.Type type) {
        if (type.isPrimitiveType()) {
            if (type.getTypeClass() == boolean.class
                || type.getTypeClass() == int.class
                || type.getTypeClass() == char.class
                || type.getTypeClass() == short.class
                || type.getTypeClass() == byte.class ) {
                peekMethodVisitor().visitVarInsn(ILOAD, paramPosition);
            } else if (type.getTypeClass() == double.class) {
                peekMethodVisitor().visitVarInsn(DLOAD, paramPosition);
            } else if (type.getTypeClass() == float.class) {
                peekMethodVisitor().visitVarInsn(FLOAD, paramPosition);
            } else if (type.getTypeClass() == long.class) {
                peekMethodVisitor().visitVarInsn(LLOAD, paramPosition);
            }
        } else {
            peekMethodVisitor().visitVarInsn(ALOAD, paramPosition);
        }
    }

    public void emitStoreLocal(int paramPosition, org.orthodox.universel.ast.Type type) {
        if (type.isPrimitiveType()) {
            if (type.getTypeClass() == boolean.class
                || type.getTypeClass() == int.class
                || type.getTypeClass() == char.class
                || type.getTypeClass() == short.class
                || type.getTypeClass() == byte.class ) {
                peekMethodVisitor().visitVarInsn(ISTORE, paramPosition);
            } else if (type.getTypeClass() == double.class) {
                peekMethodVisitor().visitVarInsn(DSTORE, paramPosition);
            } else if (type.getTypeClass() == float.class) {
                peekMethodVisitor().visitVarInsn(FSTORE, paramPosition);
            } else if (type.getTypeClass() == long.class) {
                peekMethodVisitor().visitVarInsn(LSTORE, paramPosition);
            }
        } else {
            peekMethodVisitor().visitVarInsn(ASTORE, paramPosition);
        }
    }

    public void emitLoadType(Class<?> type) {
        if ( type.isPrimitive() ) {
            peekMethodVisitor().visitFieldInsn(GETSTATIC,
                                               Type.getInternalName(ensureNonPrimitiveType(type)),
                                               "TYPE",
                                               Type.getDescriptor(Class.class));
        } else {
            peekMethodVisitor().visitLdcInsn(Type.getType(type));
        }

    }

    public static final Type[] typeArrayFor(Class<?> ... types) {
        if (types == null) return null;
        if (types.length == 0) return EMPTY_TYPES;

        return Arrays.stream(types)
              .map(Type::getType)
              .toArray(Type[]::new);
    }

    public void emitLoadEnum(Operator operator) {
        peekMethodVisitor().visitFieldInsn(GETSTATIC, Type.getInternalName(operator.getClass()), operator.name(), Type.getDescriptor(operator.getClass()));
    }

    public void castIfNeeded(Class<?> sourceType, Class<?> targetType) {
        if (sourceType == targetType || targetType.isAssignableFrom(sourceType))
            return;

        // If both source and target are primitive then emit
        // inline JVM instructions for direct conversion (very efficient)
        if (isPrimitiveType(sourceType) && isPrimitiveType(targetType)) {
            PrimitiveCastingCodeGenerator.cast(peekMethodVisitor(), sourceType, targetType);
        } else if (isPrimitiveType(targetType)) {
            unbox(targetType);
        } else if (isPrimitiveType(sourceType) && isPrimitiveTypeWrapperClass(targetType)) {
            box(targetType);
        } else if (isPrimitiveType(sourceType) && targetType == Object.class) {
            box(sourceType);
        } else {
            convert(sourceType, targetType);
        }
    }

    public void convertIfNeeded(Class<?> sourceType, Class<?> targetType) {
        if (sourceType == targetType || targetType.isAssignableFrom(sourceType))
            return;

        // If both source and target are primitive then emit
        // inline JVM instructions for direct conversion (very efficient)
        if (isPrimitiveType(sourceType) && isPrimitiveType(targetType)) {
            PrimitiveCastingCodeGenerator.cast(peekMethodVisitor(), sourceType, targetType);
        } else if (isPrimitiveTypeWrapperClass(sourceType) && isPrimitiveType(targetType)) {
            unbox(targetType);
        } else if (isPrimitiveType(sourceType) && (isPrimitiveTypeWrapperClass(targetType) || targetType == Object.class)) {
            box(sourceType);
        } else {
            convert(sourceType, targetType);
        }
    }

    public ClassWriter popClassWriter() {
        return cwStack.pop();
    }

    public MethodVisitor popMethodVisitor() {
        return mvStack.pop();
    }

    public void autoboxIfNeeded(final Class<?> sourceType, final Class<?> targetType) {
        if (sourceType == targetType || targetType.isAssignableFrom(sourceType))
            return;

        // If both source and target are primitive then emit
        // inline JVM instructions for direct conversion (very efficient)
        if (isPrimitiveType(sourceType) && isPrimitiveType(targetType)) {
            PrimitiveCastingCodeGenerator.cast(peekMethodVisitor(), sourceType, targetType);
        } else if (isPrimitiveTypeWrapperClass(sourceType) && isPrimitiveType(targetType)) {
            unbox(targetType);
        } else if (isPrimitiveType(sourceType) && (isPrimitiveTypeWrapperClass(targetType) || targetType == Object.class)) {
            box(targetType);
        }
    }

    public final void emitReturn(Class<?> returnType) {
        if (returnType == void.class) {
            peekMethodVisitor().visitInsn(RETURN);
        } else if (returnType.isPrimitive()) {
            if (returnType == boolean.class) {
                peekMethodVisitor().visitInsn(IRETURN);
            } else if (returnType == byte.class) {
                peekMethodVisitor().visitInsn(IRETURN);
            } else if (returnType == char.class) {
                peekMethodVisitor().visitInsn(IRETURN);
            } else if (returnType == double.class) {
                peekMethodVisitor().visitInsn(DRETURN);
            } else if (returnType == float.class) {
                peekMethodVisitor().visitInsn(FRETURN);
            } else if (returnType == int.class) {
                peekMethodVisitor().visitInsn(IRETURN);
            } else if (returnType == long.class) {
                peekMethodVisitor().visitInsn(LRETURN);
            } else if (returnType == short.class) {
                peekMethodVisitor().visitInsn(IRETURN);
            }
        } else {
            peekMethodVisitor().visitInsn(ARETURN);
        }
    }

    public void emitCreateArray(final TypeReference type, int dimensions) {
        if ( dimensions == 1) {
            final TypeReference componentType = type.getComponentType();
            if ( componentType.isPrimitiveType() ) {
                peekMethodVisitor().visitIntInsn(NEWARRAY, primitiveTypeToNewArrayAsmOpCode.get(componentType.getTypeDescriptor()));
            } else {
                peekMethodVisitor().visitTypeInsn(ANEWARRAY, getInternalName(componentType));
            }
        } else {
            peekMethodVisitor().visitMultiANewArrayInsn(getInternalName(type), dimensions);
        }
    }

    private static String getInternalName(final org.orthodox.universel.ast.Type type) {
        return type.getTypeClass() != null ? Type.getInternalName(type.getTypeClass()) : type.getName().join("/");
    }

    private static String getDescriptor(final org.orthodox.universel.ast.Type type) {
        return Type.getDescriptor(type.getTypeClass());
    }

    private static String getMethodDescriptor(Class<?> returnType, Class<?> ... argTypes) {
        return Type.getMethodDescriptor(Type.getType(returnType), typeArrayFor(argTypes));
    }

    public void emitGetStaticField(final org.orthodox.universel.ast.Type declaringType, final org.orthodox.universel.ast.Type fieldType, final String fieldName) {
        peekMethodVisitor().visitFieldInsn(GETSTATIC, getInternalName(declaringType), fieldName, getDescriptor(fieldType));
    }

    public void emitGetNonStaticField(final org.orthodox.universel.ast.Type declaringType, final org.orthodox.universel.ast.Type fieldType, final String fieldName) {
        peekMethodVisitor().visitIntInsn(ALOAD, 0); // this
        peekMethodVisitor().visitFieldInsn(GETFIELD, getInternalName(declaringType), fieldName, getDescriptor(fieldType));
    }

    public void emitPutStaticField(final org.orthodox.universel.ast.Type declaringType, final org.orthodox.universel.ast.Type fieldType, final String fieldName) {
        peekMethodVisitor().visitFieldInsn(PUTSTATIC, getInternalName(declaringType), fieldName, getDescriptor(fieldType));
    }

    public void emitPutNonStaticField(final org.orthodox.universel.ast.Type declaringType, final org.orthodox.universel.ast.Type fieldType, final String fieldName) {
        peekMethodVisitor().visitFieldInsn(PUTFIELD, getInternalName(declaringType), fieldName, getDescriptor(fieldType));
    }

    public void emitSwap() {
        peekMethodVisitor().visitInsn(SWAP);
    }
}
