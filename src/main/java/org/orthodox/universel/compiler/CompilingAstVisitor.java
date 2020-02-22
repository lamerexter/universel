package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.TypeUtil;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.orthodox.universel.UniversalException;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapEntryExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.ElvisExpression;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;
import org.orthodox.universel.operations.UnaryFunctions;

import javax.lang.model.type.NullType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static org.beanplanet.core.lang.TypeUtil.findMethod;
import static org.beanplanet.core.util.CollectionUtil.isNullOrEmpty;
import static org.beanplanet.core.util.IterableUtil.nullSafe;
import static org.objectweb.asm.Opcodes.*;
import static org.orthodox.universel.StringEscapeUtil.unescapeUniversalCharacterEscapeSequences;

public class CompilingAstVisitor extends UniversalVisitorAdapter {
    private CompilationContext compilationContext;

    public CompilingAstVisitor(CompilationContext compilationContext) {
        this.compilationContext = compilationContext;
    }

    @Override
    public boolean visitAnnotation(Annotation node) {
        return false;
    }

    @Override
    public boolean visitBetweenExpression(BetweenExpression node) {
        return false;
    }

    @Override
    public boolean visitBinaryExpression(BinaryExpression node) {
        return false;
    }

    @Override
    public boolean visitNumericLiteralExpression(NumericLiteral node) {
        Class<?> fpClass = node.getTypeDescriptor();
        compilationContext.getVirtualMachine().loadOperandOfType(fpClass);
        compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getValue());

        return true;
    }

    @Override
    public boolean visitBooleanLiteral(BooleanLiteralExpr node) {
        boolean booleanValue = node.getBooleanValue();
        compilationContext.getVirtualMachine().loadOperandConstant(booleanValue);
        compilationContext.getBytecodeHelper().emitLoadBooleanOperand(booleanValue);
        return true;
    }

    @Override
    public boolean visitElvisExpression(ElvisExpression node) {
        return false;
    }

    @Override
    public boolean visitInExpression(InExpression node) {
        return false;
    }

    @Override
    public boolean visitInstanceofExpression(InstanceofExpression node) {
        node.getLhsExpression().accept(this);
        compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());

        node.getRhsExpression().accept(this);

        compilationContext.getBytecodeHelper().emitInvokeStaticMethod(findMethod(PUBLIC|STATIC,
                                                                                 "operator_instanceOf",
                                                                                 BinaryOperatorFunctions.class,
                                                                                 boolean.class, Object.class, Class.class)
                                                                              .orElseThrow(() -> new UniversalException("Unable to find instanceOf method in "+BinaryOperatorFunctions.class)));
        compilationContext.getVirtualMachine().loadOperandOfType(boolean.class);
        return false;
    }

    @Override
    public boolean visitImportDeclaration(ImportDecl node) {
        compilationContext.pushNameScope(new TypeReferenceScope(compilationContext, node));
        compilationContext.pushMethodCallScope(new StaticMethodCallGenerator(compilationContext, node));

        return false;
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
            compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getElements().size());
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
    public boolean visitMap(MapExpr node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        String className = Type.getInternalName(LinkedHashMap.class);
        mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES));

        for (MapEntryExpr mapEntryExpr : nullSafe(node.getEntries())) {
            mv.visitInsn(DUP);
            mapEntryExpr.getKeyExpression().accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mapEntryExpr.getValueExpression().accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(Map.class), "put",
                               Type.getMethodDescriptor(BytecodeHelper.OBJECT_TYPE, BytecodeHelper.OBJECT_TYPE, BytecodeHelper.OBJECT_TYPE));
            mv.visitInsn(POP);
        }

        compilationContext.getVirtualMachine().loadOperandOfType(LinkedHashMap.class);

        return true;
    }

    @Override
    public boolean visitMethodCall(MethodCall node) {
        compilationContext.generateCall(this, node);
        return true;
    }

    @Override
    public boolean visitModifiers(Modifiers node) {
        return false;
    }

    @Override
    public boolean visitName(QualifiedIdentifier node) {
        return false;
    }

    private MethodVisitor mv() {
        return compilationContext.getBytecodeHelper().peekMethodVisitor();
    }

    @Override
    public boolean visitName(Name node) {
        compilationContext.generateAccess(node.getName());
        return true;
    }

    @Override
    public boolean visitNullLiteral(NullLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(NullType.class);
        compilationContext.getBytecodeHelper().emitLoadNullOperand();
        return true;
    }

    @Override
    public boolean visitNullTestExpression(NullTestExpression node) {
        return false;
    }

    @Override
    public boolean visitRangeExpression(RangeExpression node) {
        return false;
    }

    public boolean visitReferenceType(ReferenceType node) {
        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());
        compilationContext.getBytecodeHelper().emitLoadType(node.getTypeDescriptor());

        return false;
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
        // Visit the import declaration section, if present
        if (node.getImportDeclaration() != null) {
            node.getImportDeclaration().accept(this);
        }

        // Visit script body elements
        for (Node child : nullSafe(node.getBodyElements())) {
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
            compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getElements().size());
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE));
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

    @Override
    public boolean visitTernaryExpression(TernaryExpression node) {
        return false;
    }

    @Override
    public boolean visitTypeReference(TypeReference node) {
        return false;
    }

    @Override
    public boolean visitUnaryExpression(UnaryExpression node) {
        node.getExpression().accept(this);

        Class<?> unaryExprType = node.getTypeDescriptor();
        Optional<Method> unaryMinusFunction = findMethod(PUBLIC + STATIC, "unaryMinus", UnaryFunctions.class, unaryExprType, unaryExprType);

        if ( !unaryMinusFunction.isPresent() ) {
            compilationContext.getMessages().addError("uel.compiler.math.unary-minus.not-found", "Unable to find unary minus function for the given type: {0}", unaryExprType);
            return true;
        }

        compilationContext.getBytecodeHelper().emitInvokeStaticMethod(unaryMinusFunction.get());
        compilationContext.getVirtualMachine().loadOperandOfType(unaryMinusFunction.get().getReturnType());
        return true;
    }
}
