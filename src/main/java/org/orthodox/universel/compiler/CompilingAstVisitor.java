package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.orthodox.universel.ast.Name;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Script;
import org.orthodox.universel.ast.UniversalCodeVisitor;
import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.collections.SetExpr;
import org.orthodox.universel.ast.literals.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.beanplanet.core.util.CollectionUtil.isEmptyOrNull;
import static org.beanplanet.core.util.CollectionUtil.isNullOrEmpty;
import static org.beanplanet.core.util.IterableUtil.nullSafe;
import static org.objectweb.asm.Opcodes.*;
import static org.orthodox.universel.StringEscapeUtil.unescapeUniversalCharacterEscapeSequences;

public class CompilingAstVisitor implements UniversalCodeVisitor {
    private CompilationContext compilationContext;

    public CompilingAstVisitor(CompilationContext compilationContext) {
        this.compilationContext = compilationContext;
    }

    @Override
    public boolean visitBooleanLiteral(BooleanLiteralExpr node) {
        boolean booleanValue = node.getBooleanValue();
        compilationContext.getVirtualMachine().loadOperandConstant(booleanValue);
        compilationContext.getBytecodeHelper().emitLoadBooleanOperand(booleanValue);
        return true;
    }

    @Override
    public boolean visitDecimalFloatingPointLiteral(DecimalFloatingPointLiteralExpr node) {
        Class<?> fpClass = node.getLiteralValueClass();

        if ( float.class == fpClass ) {
            float value = node.asFloat();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadFloatOperand(value);
        } else if ( double.class == fpClass ) {
            double value = node.asDouble();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadDoubleOperand(value);
        } else if ( BigDecimal.class == fpClass ) {
            String value = node.asBigDecimalString();
            compilationContext.getVirtualMachine().loadOperandOfType(BigDecimal.class);
            compilationContext.getBytecodeHelper().emitLoadBigDecimalOperand(value);
        }

        return true;
    }

    @Override
    public boolean visitDecimalIntegerLiteral(DecimalIntegerLiteralExpr node) {
        Class<?> integerClass = node.getLiteralValueClass();

        if ( int.class == integerClass ) {
            int value = node.asIntValue();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadIntegerOperand(value);
        } else if ( long.class == integerClass ) {
            long value = node.asLongValue();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadIntegerOperand(value);
        } else if ( BigInteger.class == integerClass ) {
            String value = node.asBigIntegerString();
            compilationContext.getVirtualMachine().loadOperandOfType(BigInteger.class);
            compilationContext.getBytecodeHelper().emitLoadBigIntegerOperand(value);
        }

        return true;
    }

    @Override
    public boolean visitList(ListExpr node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        String className = Type.getInternalName(ArrayList.class);
        mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);

        if ( isNullOrEmpty(node.getElements()) ) {
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES));
        } else {
            compilationContext.getBytecodeHelper().emitLoadIntegerOperand(node.getElements().size());
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE));
        }

        for (Node itemNode : node.getElements()) {
            mv.visitInsn(DUP);
            itemNode.accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(List.class), "add",
                               Type.getMethodDescriptor(Type.BOOLEAN_TYPE, BytecodeHelper.OBJECT_TYPE_ARRAY));
            mv.visitInsn(POP);
        }
        compilationContext.getVirtualMachine().loadOperandOfType(ArrayList.class);

        return true;
    }

    @Override
    public boolean visitName(Name node) {
        compilationContext.getNameScope().generateAccess(node.getName());
        return true;
    }

    @Override
    public boolean visitNullLiteral(NullLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(Object.class);
        compilationContext.getBytecodeHelper().emitLoadNullOperand();
        return true;
    }

    @Override
    public boolean visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node) {
        compilationContext.getBytecodeHelper().emitInstantiateType(StringBuilder.class);

        for (Node expr : nullSafe(node.getParts())) {
            compilationContext.getBytecodeHelper().emitDuplicate();
            expr.accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());

            compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(StringBuilder.class, "append", StringBuilder.class, Object.class);
            compilationContext.getBytecodeHelper().emitPop();

            compilationContext.getVirtualMachine().popOperand();
        }

        compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(StringBuilder.class, "toString", String.class);
        compilationContext.getVirtualMachine().loadOperandOfType(String.class);

        return true;
    }


    @Override
    public boolean visitScript(Script node) {
        for (Node child : nullSafe(node.getChildNodes())) {
            child.accept(this);
        }
        return false;
    }

    @Override
    public boolean visitSet(SetExpr node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        String className = Type.getInternalName(LinkedHashSet.class);
        mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);

        if ( isNullOrEmpty(node.getElements()) ) {
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES));
        } else {
            compilationContext.getBytecodeHelper().emitLoadIntegerOperand(node.getElements().size());
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, new Type[] { Type.INT_TYPE }));
        }

        for (Node itemNode : node.getElements()) {
            mv.visitInsn(DUP);
            itemNode.accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(Set.class), "add",
                               Type.getMethodDescriptor(Type.BOOLEAN_TYPE, BytecodeHelper.OBJECT_TYPE_ARRAY));
            mv.visitInsn(POP);
        }
        compilationContext.getVirtualMachine().loadOperandOfType(LinkedHashSet.class);

        return true;
    }

    @Override
    public boolean visitStringLiteral(StringLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(String.class);
        compilationContext.getBytecodeHelper().emitLoadStringOperand(unescapeUniversalCharacterEscapeSequences(node.getUndelimitedTokenImage()));
        return true;
    }
}
