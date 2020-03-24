package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.orthodox.universel.UniversalException;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapEntryExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;
import org.orthodox.universel.exec.operators.binary.BinaryOperatorRegistry;
import org.orthodox.universel.exec.operators.binary.ConcurrentBinaryOperatorRegistry;
import org.orthodox.universel.exec.operators.binary.PackageScanBinaryOperatorLoader;
import org.orthodox.universel.exec.operators.unary.UnaryFunctions;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;

import javax.lang.model.type.NullType;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static org.beanplanet.core.lang.TypeUtil.findMethod;
import static org.beanplanet.core.util.CollectionUtil.isNullOrEmpty;
import static org.beanplanet.core.util.IterableUtil.nullSafe;
import static org.objectweb.asm.Opcodes.*;
import static org.orthodox.universel.StringEscapeUtil.unescapeUniversalCharacterEscapeSequences;

public class CompilingAstVisitor extends UniversalVisitorAdapter {
    private static BinaryOperatorRegistry binaryOperatorRegistry;
    private CompilationContext compilationContext;

    private synchronized void checkLoaded() {
        if ( binaryOperatorRegistry != null ) return;

        PackageScanBinaryOperatorLoader loader = new PackageScanBinaryOperatorLoader();
        BinaryOperatorRegistry newBinaryOperatorRegistry = new ConcurrentBinaryOperatorRegistry();
        loader.load(newBinaryOperatorRegistry);
        binaryOperatorRegistry = newBinaryOperatorRegistry;
    }

    public CompilingAstVisitor(CompilationContext compilationContext) {
        this.compilationContext = compilationContext;
    }

    @Override
    public Node visitAnnotation(Annotation node) {
        return node;
    }

    @Override
    public Node visitBetweenExpression(BetweenExpression node) {
        return node;
    }

    @Override
    public Node visitBinaryExpression(BinaryExpression node) {
        checkLoaded();

        Class<?> lhsType;
        Class<?> rhsType;

        // Separately implemented, for now...
        if ( Operator.ELVIS == node.getOperator() || Operator.INSTANCE_OF == node.getOperator()) {
            node.getLhsExpression().accept(this);
            compilationContext.getVirtualMachine().boxIfNeeded();
            lhsType = compilationContext.getVirtualMachine().peekOperandStack();

            node.getRhsExpression().accept(this);
            compilationContext.getVirtualMachine().boxIfNeeded();
            rhsType = compilationContext.getVirtualMachine().peekOperandStack();
        } else {
            node.getLhsExpression().accept(this);
            lhsType = compilationContext.getVirtualMachine().peekOperandStack();

            node.getRhsExpression().accept(this);
            rhsType = compilationContext.getVirtualMachine().peekOperandStack();
        }

        if ( Operator.INSTANCE_OF == node.getOperator() ) {
            rhsType = Class.class;
        }


        Method binaryOperatorMethod = binaryOperatorRegistry.lookup(node.getOperator(), lhsType, rhsType)
                                                            .orElseThrow(() -> new UniversalException("Unable to find applicable binary operator [" + node.getOperator() + "] method."));

        if ( binaryOperatorMethod.getParameterTypes().length == 3 && Operator.class.isAssignableFrom(binaryOperatorMethod.getParameterTypes()[2]) ) {
            compilationContext.getBytecodeHelper().emitLoadEnum(node.getOperator());
        }
        compilationContext.getBytecodeHelper().emitInvokeStaticMethod(binaryOperatorMethod);
        compilationContext.getVirtualMachine().loadOperandOfType(binaryOperatorMethod.getReturnType());
        return node;
    }

    @Override
    public Node visitNumericLiteralExpression(NumericLiteral node) {
        Class<?> fpClass = node.getTypeDescriptor();
        compilationContext.getVirtualMachine().loadOperandOfType(fpClass);
        compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getValue());

        return (Node)node;
    }

    @Override
    public Node visitBooleanLiteral(BooleanLiteralExpr node) {
        boolean booleanValue = node.getBooleanValue();
        compilationContext.getVirtualMachine().loadOperandConstant(booleanValue);
        compilationContext.getBytecodeHelper().emitLoadBooleanOperand(booleanValue);
        return node;
    }

    @Override
    public Node visitInExpression(InExpression node) {
        return node;
    }

    @Override
    public Node visitImportDeclaration(ImportDecl node) {
        compilationContext.pushNameScope(new TypeReferenceScope(compilationContext, node));
        compilationContext.pushMethodCallScope(new StaticMethodCallGenerator(compilationContext, node));

        return node;
    }

    @Override
    public Node visitList(ListExpr node) {
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

        return node;
    }

    @Override
    public Node visitMap(MapExpr node) {
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

        return node;
    }

    @Override
    public Node visitMethodCall(MethodCall node) {
        compilationContext.generateCall(this, node);
        return node;
    }

    @Override
    public Node visitModifiers(Modifiers node) {
        return node;
    }

    @Override
    public Node visitName(QualifiedIdentifier node) {
        return node;
    }

    private MethodVisitor mv() {
        return compilationContext.getBytecodeHelper().peekMethodVisitor();
    }

    @Override
    public Node visitName(Name node) {
        compilationContext.generateAccess(node.getName());
        return node;
    }

    @Override
    public Node visitNullLiteral(NullLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(NullType.class);
        compilationContext.getBytecodeHelper().emitLoadNullOperand();
        return node;
    }

    @Override
    public Node visitNullTestExpression(NullTestExpression node) {
        return node;
    }

    public Node visitReferenceType(ReferenceType node) {
        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());
        compilationContext.getBytecodeHelper().emitLoadType(node.getTypeDescriptor());

        return node;
    }

    @Override
    public Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node) {
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

        return node;
    }


    @Override
    public Node visitScript(Script node) {
        // Visit the import declaration section, if present
        if (node.getImportDeclaration() != null) {
            node.getImportDeclaration().accept(this);
        }

        // Visit script body elements
        for (Node child : nullSafe(node.getBodyElements())) {
            child.accept(this);
        }
        return node;
    }

    @Override
    public Node visitSet(SetExpr node) {
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

        return node;
    }

    @Override
    public Node visitStringLiteral(StringLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(String.class);
        compilationContext.getBytecodeHelper().emitLoadStringOperand(unescapeUniversalCharacterEscapeSequences(node.getUndelimitedTokenImage()));
        return node;
    }

    @Override
    public Node visitTernaryExpression(TernaryExpression node) {
        return node;
    }

    @Override
    public Node visitTypeConversion(TypeConversion node) {
        node.getSource().accept(this);

        compilationContext.getVirtualMachine().boxIfNeeded();
        compilationContext.getVirtualMachine().convert(node.getTargetType());

        return node;
    }

    @Override
    public Node visitTypeReference(TypeReference node) {
        return node;
    }

    @Override
    public Node visitUnaryExpression(UnaryExpression node) {
        node.getExpression().accept(this);

        Class<?> unaryExprType = node.getTypeDescriptor();
        Optional<Method> unaryMinusFunction = findMethod(PUBLIC + STATIC, "unaryMinus", UnaryFunctions.class, unaryExprType, unaryExprType);

        if ( !unaryMinusFunction.isPresent() ) {
            compilationContext.getMessages().addError("uel.compiler.math.unary-minus.not-found", "Unable to find unary minus function for the given type: {0}", unaryExprType);
            return node;
        }

        compilationContext.getBytecodeHelper().emitInvokeStaticMethod(unaryMinusFunction.get());
        compilationContext.getVirtualMachine().loadOperandOfType(unaryMinusFunction.get().getReturnType());
        return node;
    }
}
