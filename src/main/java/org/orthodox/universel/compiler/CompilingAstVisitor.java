package org.orthodox.universel.compiler;

import org.beanplanet.core.io.resource.ByteArrayResource;
import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.core.util.IterableUtil;
import org.objectweb.asm.Type;
import org.objectweb.asm.*;
import org.orthodox.universel.UniversalException;
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.allocation.ArrayCreationExpression;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.ast.annotation.Annotation;
import org.orthodox.universel.ast.collections.ListExpr;
import org.orthodox.universel.ast.collections.MapEntryExpr;
import org.orthodox.universel.ast.collections.MapExpr;
import org.orthodox.universel.ast.collections.SetExpr;
import org.orthodox.universel.ast.conditionals.IfStatement;
import org.orthodox.universel.ast.conditionals.IfStatement.ElseIf;
import org.orthodox.universel.ast.conditionals.TernaryExpression;
import org.orthodox.universel.ast.functional.FunctionalInterfaceObject;
import org.orthodox.universel.ast.literals.*;
import org.orthodox.universel.ast.methods.LambdaFunction;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.ast.type.LoadTypeExpression;
import org.orthodox.universel.ast.type.declaration.ClassDeclaration;
import org.orthodox.universel.ast.type.declaration.FieldRead;
import org.orthodox.universel.ast.type.declaration.FieldWrite;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.compiler.CompilationContext.CompilingTypeInfo;
import org.orthodox.universel.exec.operators.binary.BinaryOperatorRegistry;
import org.orthodox.universel.exec.operators.binary.ConcurrentBinaryOperatorRegistry;
import org.orthodox.universel.exec.operators.binary.PackageScanBinaryOperatorLoader;
import org.orthodox.universel.symanticanalysis.JvmInstructionNode;
import org.orthodox.universel.symanticanalysis.ValueConsumingNode;
import org.orthodox.universel.symanticanalysis.conversion.BinaryExpressionOperatorMethodCall;
import org.orthodox.universel.symanticanalysis.conversion.BoxConversion;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import javax.lang.model.type.NullType;
import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.beanplanet.core.util.CollectionUtil.isNullOrEmpty;
import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.getInternalName;
import static org.orthodox.universel.StringEscapeUtil.unescapeUniversalCharacterEscapeSequences;
import static org.orthodox.universel.compiler.BytecodeHelper.CTOR_METHOD_NAME;
import static org.orthodox.universel.compiler.codegen.CodeGenUtil.descriptor;
import static org.orthodox.universel.compiler.codegen.CodeGenUtil.internalName;

public class CompilingAstVisitor extends UniversalVisitorAdapter {
    private static BinaryOperatorRegistry binaryOperatorRegistry;
    private CompilationContext compilationContext;

    private synchronized void checkLoaded() {
        if (binaryOperatorRegistry != null)
            return;

        PackageScanBinaryOperatorLoader loader = new PackageScanBinaryOperatorLoader();
        BinaryOperatorRegistry newBinaryOperatorRegistry = new ConcurrentBinaryOperatorRegistry();
        loader.load(newBinaryOperatorRegistry);
        binaryOperatorRegistry = newBinaryOperatorRegistry;
    }

    public CompilingAstVisitor(CompilationContext compilationContext) {
        this.compilationContext = compilationContext;
    }

    @Override
    public Node visitAnnotation(final Annotation node) {
        return node;
    }

    @Override
    public ArrayCreationExpression visitArrayCreationExpression(final ArrayCreationExpression node) {
        node.getDimensionExpressions().forEach(n -> n.accept(this));

        compilationContext.getBytecodeHelper().emitCreateArray(node.getType(), node.getDimensionExpressions().size());
        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());

        return node;
    }

//    @Override
//    public Node visitAssignment(final AssignmentExpression node) {
//        node.getRhsExpression().accept(this);
//        node.getLhsExpression().accept(this);
//
//        return node;
//    }


    @Override
    public Node visitBetweenExpression(final BetweenExpression node) {
        return node;
    }

    @Override
    public Node visitBinaryExpression(final BinaryExpression node) {
        checkLoaded();

        Class<?> lhsType;
        Class<?> rhsType;

        // Separately implemented, for now...
        if (Operator.ELVIS == node.getOperator() || Operator.INSTANCE_OF == node.getOperator()) {
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

        if (Operator.INSTANCE_OF == node.getOperator()) {
            rhsType = Class.class;
        }


        Method binaryOperatorMethod = binaryOperatorRegistry.lookup(node.getOperator(), lhsType, rhsType).orElseThrow(() -> new UniversalException("Unable to find applicable binary operator [" + node.getOperator() + "] method."));

        if (binaryOperatorMethod.getParameterTypes().length == 3 && Operator.class.isAssignableFrom(binaryOperatorMethod.getParameterTypes()[2])) {
            compilationContext.getBytecodeHelper().emitLoadEnum(node.getOperator());
        }
        compilationContext.getBytecodeHelper().emitInvokeStaticMethod(binaryOperatorMethod);
        compilationContext.getVirtualMachine().loadOperandOfType(binaryOperatorMethod.getReturnType());
        return node;
    }

    @Override
    public Node visitBinaryExpression(final BinaryExpressionOperatorMethodCall node) {
        node.getParameters().forEach(p -> p.accept(this));

        final Method operatorMethod = node.getOperatorMethod();
        if (operatorMethod.getParameterTypes().length == 3 && Operator.class.isAssignableFrom(operatorMethod.getParameterTypes()[2])) {
            compilationContext.getBytecodeHelper().emitLoadEnum(node.getOperator());
        }
        compilationContext.getBytecodeHelper().emitInvokeStaticMethod(operatorMethod);
        compilationContext.getVirtualMachine().loadOperandOfType(operatorMethod.getReturnType());

        return node;
    }

    @Override
    public Node visitBooleanLiteral(final BooleanLiteralExpr node) {
        boolean booleanValue = node.getBooleanValue();
        compilationContext.getVirtualMachine().loadOperandConstant(booleanValue);
        compilationContext.getBytecodeHelper().emitLoadBooleanOperand(booleanValue);
        return node;
    }

    @Override
    public Node visitBoxConversion(final BoxConversion node) {
        node.getSource().accept(this);
        compilationContext.getVirtualMachine().box();
        return node;
    }

    @Override
    public Node visitBoxExpression(final Box node) {
        node.getSource().accept(this);
        compilationContext.getVirtualMachine().box();
        return node;
    }

    @Override
    public Node visitClassDeclaration(ClassDeclaration node) {
        final NamePath pkgName = node.getPackageDeclaration() != null ? node.getPackageDeclaration().getName() : NamePath.EMPTY_NAME_PATH;
        final String classBasename = node.getName().getName();
        final NamePath fqClassName = pkgName.joinSingleton(classBasename);
        compilationContext.pushTypeInfo(new CompilingTypeInfo(fqClassName));

        // Generate the class
        ClassWriter cw = compilationContext.getBytecodeHelper().generateClass(V1_8, node.getModifiers().getModifiers(),
                                                                              fqClassName,
                                                                              node.getExtendsList().isEmpty() ? null : node.getExtendsList().get(0).getTypeDescriptor(),
                                                                              toTypesArray(node.getImplementsList())
                                                                              );
        // Generate a default constructor, for now...
        withinStack(node, n -> {
            MethodVisitor mv = compilationContext.getBytecodeHelper().generateMethod(ACC_PUBLIC, "<init>", void.class, null);
//            MethodVisitor mv = compilationContext.getBytecodeHelper().generateMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

            //----------------------------------------------------------------------------------------------------------
            // Initialise non-static fields. TODO: This should be done along side constructor chaining, but as we only
            //                                     have one non-arg constructor this will wait.
            //----------------------------------------------------------------------------------------------------------
            List<FieldDeclaration> applicableNonstaticInitialisations = node.getMembers()
                                                                         .stream()
                                                                         .filter(FieldDeclaration.class::isInstance)
                                                                         .map(FieldDeclaration.class::cast)
                                                                         .filter(f -> !Modifiers.isStatic(f.getModifiers().getModifiers()) && f.getVariableDeclarations().stream().anyMatch(v -> v.getInitialiser() != null))
                                                                         .collect(Collectors.toList());
            if ( !applicableNonstaticInitialisations.isEmpty() ) {
                applicableNonstaticInitialisations.forEach(f -> {
                    f.getVariableDeclarations()
                     .stream()
                     .filter(vd -> vd.getInitialiser() != null)
                     .forEach(vd -> {
                         mv.visitVarInsn(ALOAD, 0);
                         vd.getInitialiser().accept(this);

                         mv.visitFieldInsn(PUTFIELD, internalName(fqClassName), vd.getId().getName(), descriptor(f.getDeclarationType()));
                     });
                });
            };

            mv.visitInsn(RETURN);
            mv.visitMaxs(1,1);
            mv.visitEnd();

            return node;
        });

        //--------------------------------------------------------------------------------------------------------------
        // Generate Class Initialiser, if appropriate.
        //--------------------------------------------------------------------------------------------------------------
        withinStack(node, n -> {
            List<FieldDeclaration> applicableStaticInitialisations = node.getMembers()
                                                                         .stream()
                                                                         .filter(FieldDeclaration.class::isInstance)
                                                                         .map(FieldDeclaration.class::cast)
                                                                         .filter(f -> Modifiers.isStatic(f.getModifiers().getModifiers()) && f.getVariableDeclarations().stream().anyMatch(v -> v.getInitialiser() != null))
                                                                         .collect(Collectors.toList());
            if ( applicableStaticInitialisations.isEmpty() ) return node;

            MethodVisitor mv = compilationContext.getBytecodeHelper().generateMethod(ACC_STATIC, "<clinit>", void.class, null);

            applicableStaticInitialisations.forEach(f -> {
                f.getVariableDeclarations()
                 .stream()
                 .filter(vd -> vd.getInitialiser() != null)
                 .forEach(vd -> {
                     vd.getInitialiser().accept(this);

                     mv.visitFieldInsn(PUTSTATIC, internalName(fqClassName), vd.getId().getName(), descriptor(f.getDeclarationType()));
                 });
            });

            mv.visitInsn(RETURN);

            mv.visitEnd();
            mv.visitMaxs(0, 0);

            compilationContext.getBytecodeHelper().popMethodVisitor();


            return node;
        });

        // Generate the class members
        for (Node member : node.getMembers()) {
            member.accept(this);
        }

        cw.visitEnd();
        compilationContext.getBytecodeHelper().popClassWriter();

        compilationContext.addCompiledType(fqClassName.join("."), new ByteArrayResource(cw.toByteArray()));
        compilationContext.popTypeInfo();

        return node;
    }

    @Override
    public Node visitFieldAccess(final FieldRead node) {
        if ( node.isStatic()) {
            compilationContext.getBytecodeHelper().emitGetStaticField(node.getDeclaringType(), node.getFieldType(), node.getFieldName());
        } else {
            compilationContext.getBytecodeHelper().emitGetNonStaticField(node.getDeclaringType(), node.getFieldType(), node.getFieldName());
        }
        compilationContext.getVirtualMachine().loadOperandOfType(node.getFieldType());
        return node;
    }

    @Override
    public Node visitFieldAccess(final FieldWrite node) {
        if ( node.getFieldValue() != null ) {
            node.getFieldValue().accept(this);
            compilationContext.getVirtualMachine().loadOperandOfType(node.getFieldType());
        }
        compilationContext.getVirtualMachine().getBytecodeHelper().emitDuplicate();  // Push value of assignment onto operand stack

        if ( node.isStatic()) {
            compilationContext.getBytecodeHelper().emitPutStaticField(node.getDeclaringType(), node.getFieldType(), node.getFieldName());
        } else {
            compilationContext.getBytecodeHelper().peekMethodVisitor().visitIntInsn(ALOAD, 0); // this
            compilationContext.getBytecodeHelper().emitSwap();
            compilationContext.getBytecodeHelper().emitPutNonStaticField(node.getDeclaringType(), node.getFieldType(), node.getFieldName());
        }
        return node;
    }

    @Override
    public Node visitFieldDeclaration(final FieldDeclaration node) {
        final ClassWriter cw = compilationContext.getBytecodeHelper().peekClassWriter();

        for (VariableDeclaration v : node.getVariableDeclarations()) {
            cw.visitField(node.getModifiers().getModifiers(), v.getId().getName(), descriptor(node.getDeclarationType()), null, null);
        }

        return node;
    }

    @Override
    public Node visitFunctionalInterfaceObject(final FunctionalInterfaceObject node) {
        MethodDeclaration syntheticMethod = visitLambdaFunction(node.getTargetMethodPrototype());

        Type funcType = Type.getMethodType(Type.getType(node.getSourceFunctionReturnType()),
                                           node.getSourceFunctionParameters().stream().map(Type::getType).toArray(Type[]::new));
        Type targType = Type.getMethodType(Type.getType(node.getTargetMethodPrototype().getReturnType().getTypeDescriptor()),
                                           node.getTargetMethodPrototype().getParameters().getNodes().stream().map(Node::getTypeDescriptor).map(Type::getType).toArray(Type[]::new));
        MethodType methodType = MethodType.methodType(syntheticMethod.getReturnType().getTypeDescriptor(),
                                                      syntheticMethod.getParameters().getNodes().stream().map(Node::getTypeDescriptor).toArray(Class[]::new));
        Handle bsm = new Handle(H_INVOKESTATIC,
                                getInternalName(LambdaMetafactory.class),
                                "metafactory",
                                MethodType.methodType(CallSite.class,
                                                      MethodHandles.Lookup.class,
                                                      String.class,
                                                      MethodType.class,
                                                      MethodType.class,
                                                      MethodHandle.class,
                                                      MethodType.class).toMethodDescriptorString(),
                                false);
        String generatedClassName = compilationContext.peekTypeInfo().getFullyQualifiedTypeName().join("/");
        compilationContext.getBytecodeHelper()
                          .peekMethodVisitor()
                          .visitInvokeDynamicInsn(node.getSourceFunctionName(),
                                                  MethodType.methodType(node.getSourceFunctionType()).toMethodDescriptorString(),
                                                  bsm,
                                                  funcType,
                                                  new Handle(H_INVOKESTATIC,
                                                             generatedClassName,
                                                             syntheticMethod.getName(),
                                                             methodType.toMethodDescriptorString(),
                                                             false),
                                                  targType
                                                  );

        return node;
    }

    @Override
    public IfStatement visitIfStatement(final IfStatement node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        boolean hasElseIf = node.getElseIfExpressions() != null && !node.getElseIfExpressions().isEmpty();
        boolean hasElse = node.getElseExpression() != null;
        Label[] elseIfLabels = null;
        Label elseLabel = new Label();
        Label endLabel = new Label();
        if (hasElseIf) {
            elseIfLabels = new Label[node.getElseIfExpressions().size()];
            for (int n = 0; n < node.getElseIfExpressions().size(); n++) {
                elseIfLabels[n] = new Label();
            }
        }

        // If test condition
        node.getTestExpression().accept(this);
        compilationContext.getVirtualMachine().castIfNecessary(boolean.class);
        mv.visitJumpInsn(IFEQ, (hasElseIf ? elseIfLabels[0] : (hasElse ? elseLabel : endLabel)));
        compilationContext.getVirtualMachine().popOperand();

        // Then
        node.getThenExpression().accept(this);
        compilationContext.getVirtualMachine().convertOrAutoboxOperandIfNeeded(node.getTypeDescriptor());
        mv.visitJumpInsn(GOTO, endLabel);

        // Else-if...
        if (hasElseIf) {
            int numberOfElseIfs = node.getElseIfExpressions().size();
            for (int n = 0; n < numberOfElseIfs; n++) {
                boolean hasMoreElseIfs = (n + 1 < numberOfElseIfs);
                ElseIf elseIf = node.getElseIfExpressions().get(n);
                mv.visitLabel(elseIfLabels[n]);

                // Else-if test condition
                elseIf.getTestExpression().accept(this);
                mv.visitJumpInsn(IFEQ, (hasMoreElseIfs ? elseIfLabels[n + 1] : (hasElse ? elseLabel : endLabel)));
                compilationContext.getVirtualMachine().popOperand();

                // Else-if body
                elseIf.getBody().accept(this);
                compilationContext.getVirtualMachine().convertOrAutoboxOperandIfNeeded(node.getTypeDescriptor());
                mv.visitJumpInsn(GOTO, endLabel);
            }
        }

        // Else
        if (hasElse) {
            mv.visitLabel(elseLabel);

            node.getElseExpression().accept(this);
            compilationContext.getVirtualMachine().convertOrAutoboxOperandIfNeeded(node.getTypeDescriptor());
        }

        // End
        mv.visitLabel(endLabel);

        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());

        return node;
    }

    @Override
    public InternalNodeSequence visitInternalNodeSequence(InternalNodeSequence node) {
        for (int n = 0; n < node.getNodes().size(); n++) {
            Node child = node.getNodes().get(n);

            child.accept(this);
        }

        return node;
    }

    @Override
    public Node visitInExpression(final InExpression node) {
        return node;
    }

    @Override
    public Node visitImportDeclaration(final ImportDecl node) {
        compilationContext.pushNameScope(new TypeReferenceScope(compilationContext, node));

        return node;
    }

    @Override
    public Node visitJvmInstruction(JvmInstructionNode jvmInstructionNode) {
        if ( jvmInstructionNode.getSource() != null) {
            jvmInstructionNode.getSource().accept(this);
        }

        jvmInstructionNode.emit(compilationContext.getBytecodeHelper());

        if ( jvmInstructionNode.getSource() != null) {
            compilationContext.getVirtualMachine().loadOperandOfType(jvmInstructionNode.getTypeDescriptor());
        }

        return jvmInstructionNode;
    }

    @Override
    public MethodDeclaration visitLambdaFunction(final LambdaFunction node) {
        final String syntheticMethodName = compilationContext.peekTypeInfo().generateSyntheticMethodName(node.getNameHints());
        MethodDeclaration syntheticMethod = new MethodDeclaration(node.getModifiers(),
                                                                  node.getTypeParameters(),
                                                                  node.getDeclaringType(),
                                                                  node.getReturnType(),
                                                                  syntheticMethodName,
                                                                  node.getParameters(),
                                                                  node.getBody()
        );
        visitMethodDeclaration(syntheticMethod);

        return syntheticMethod;
    }

    @Override
    public Node visitList(final ListExpr node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        String className = getInternalName(ArrayList.class);
        mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);

        if (isNullOrEmpty(node.getElements())) {
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES));
        } else {
            compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getElements().size());
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE));
        }

        for (Node itemNode : node.getElements()) {
            mv.visitInsn(DUP);
            itemNode.accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitMethodInsn(INVOKEINTERFACE, getInternalName(List.class), "add", Type.getMethodDescriptor(Type.BOOLEAN_TYPE, BytecodeHelper.OBJECT_TYPE_ARRAY));
            mv.visitInsn(POP);
        }
        compilationContext.getVirtualMachine().loadOperandOfType(ArrayList.class);

        return node;
    }

    @Override
    public Node visitLoadLocal(final LoadLocal node) {
        if ( node.getType() != null ) {
            compilationContext.getBytecodeHelper().emitLoadLocal(true, node.getLocalIndex(), node.getType());
        } else {
            compilationContext.getBytecodeHelper().emitLoadLocal(true, node.getLocalIndex(), node.getTypeDescriptor());
        }
        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());

        return node;
    }

    @Override
    public Node visitLoadType(LoadTypeExpression node) {
        compilationContext.getBytecodeHelper().emitLoadType(node.getLoadType().getTypeClass());
        compilationContext.getVirtualMachine().loadOperandOfType(node.getLoadType().getTypeClass());

        return node;
    }


    @Override
    public Node visitMap(final MapExpr node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        String className = getInternalName(LinkedHashMap.class);
        mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES));

        for (MapEntryExpr mapEntryExpr : IterableUtil.nullSafe(node.getEntries())) {
            mv.visitInsn(DUP);
            mapEntryExpr.getKeyExpression().accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mapEntryExpr.getValueExpression().accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitMethodInsn(INVOKEINTERFACE, getInternalName(Map.class), "put", Type.getMethodDescriptor(BytecodeHelper.OBJECT_TYPE, BytecodeHelper.OBJECT_TYPE, BytecodeHelper.OBJECT_TYPE));
            mv.visitInsn(POP);
        }

        compilationContext.getVirtualMachine().loadOperandOfType(LinkedHashMap.class);

        return node;
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
        return withinStack(node, n -> {
            MethodVisitor mv = compilationContext
                                   .getBytecodeHelper()
                                   .generateMethod(node.getModifiers() == null ? Modifiers.PROTECTED : node
                                                                                                           .getModifiers()
                                                                                                           .getModifiers(),
                                                   node.getName(),
                                                   node.getReturnType().getTypeDescriptor(),
                                                   toTypesArray(node.getParameters())
                                   );
//        compilationContext.getBytecodeHelper().emitLoadNullOperand();

            node.getBody().accept(this);
//        mv.visitInsn(RETURN);

            mv.visitEnd();
            mv.visitMaxs(0, 0);

            compilationContext.getBytecodeHelper().popMethodVisitor();

            return node;
        });
    }

    @Override
    public Node visitMethodCall(final MethodCall node) {
//        compilationContext.generateCall(this, node);
        return node;
    }

    private <T  extends Node> T withinStack(T node, Function<T, Node> visitFunction) {
        int initialStackSize = compilationContext.getVirtualMachine().stackSize();
        try {
            return (T)visitFunction.apply(node);
        } finally {
            compilationContext.getVirtualMachine().popToStackSize(initialStackSize);
        }
    }

    @Override
    public Node visitMethodCall(final InstanceMethodCall node) {
        withinStack(node, n -> {
//        if (node.getInstanceProvider() != null) {
//            node.getInstanceProvider().accept(this);
//        }

            for (Node parameter : n.getParameters()) {
                parameter.accept(this);
            }

            if ( node.getDeclaringType() != null) {
                compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(n.getDeclaringType(), n.getTypeDescriptor(), n.getName(), n.getParameterClasses().stream().toArray(Class[]::new));
            } else {
                compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(n.getDeclaringClass(), n.getTypeDescriptor(), n.getName(), n.getParameterClasses().stream().toArray(Class[]::new));
            }

            return n;
        });

        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());

        return node;
    }

    @Override
    public Node visitMethodCall(final StaticMethodCall node) {
        withinStack(node, n -> {
            for (Node parameter : node.getParameters()) {
                parameter.accept(this);
            }

            if ( node.getDeclaringType() != null) {
                compilationContext.getBytecodeHelper().emitInvokeStaticMethod(node.getDeclaringType(), node.getTypeDescriptor(), node.getName(), node.getParameterClasses().stream().toArray(Class[]::new));
            } else {
                compilationContext.getBytecodeHelper().emitInvokeStaticMethod(node.getDeclaringClass(), node.getTypeDescriptor(), node.getName(), node.getParameterClasses().stream().toArray(Class[]::new));
            }

            return n;
        });

        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());

        return node;
    }

    @Override
    public Modifiers visitModifiers(final Modifiers node) {
        return node;
    }

    @Override
    public Node visitName(final QualifiedIdentifier node) {
        return node;
    }

    private MethodVisitor mv() {
        return compilationContext.getBytecodeHelper().peekMethodVisitor();
    }

    @Override
    public Name visitName(final Name node) {
        compilationContext.generateAccess(node.getName());
        return node;
    }

    @Override
    public NodeSequence<? extends Node> visitNodeSequence(final NodeSequence<? extends Node> node) {
//        int startingStackSize = compilationContext.getVirtualMachine().stackSize();
        for (int n = 0; n < node.getNodes().size(); n++) {
            Node child = node.getNodes().get(n);

            child.accept(this);
//            boolean shouldClear = !node.isAtomic();
//            if (shouldClear && n < node.getNodes().size() - 1 && compilationContext.getVirtualMachine().stackSize() > startingStackSize) {
//                compilationContext.getVirtualMachine().emitAndPopOperand();
//            }
        }

        return node;
    }

    @Override
    public Node visitNullLiteral(final NullLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(NullType.class);
        compilationContext.getBytecodeHelper().emitLoadNullOperand();
        return node;
    }

    @Override
    public Node visitNullTestExpression(final NullTestExpression node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();

        //--------------------------------------------------------------------------------------------------------------
        // If the null test has a test expression, evalulate it. Otherwise assume it relies on preceeding statements.
        //--------------------------------------------------------------------------------------------------------------
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
            compilationContext.getVirtualMachine().boxIfNeeded();
        } else {
//            compilationContext.getBytecodeHelper().emitDuplicate();
        }

        Label trueLabel = new Label();
        Label endLabel = new Label();
        mv.visitJumpInsn(node.isNot() ? IFNONNULL : IFNULL, trueLabel);
        mv.visitInsn(ICONST_0);
        mv.visitJumpInsn(GOTO, endLabel);
        mv.visitLabel(trueLabel);
        mv.visitInsn(ICONST_1);
        mv.visitLabel(endLabel);

        compilationContext.getVirtualMachine().loadOperandOfType(boolean.class);

        return node;
    }

    @Override
    public Node visitNumericLiteralExpression(final NumericLiteral node) {
        Class<?> fpClass = node.getTypeDescriptor();
        compilationContext.getVirtualMachine().loadOperandOfType(fpClass);
        compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getValue());

        return (Node) node;
    }

    @Override
    public Node visitObjectCreationExpression(ObjectCreationExpression node) {
        String internalTypename = internalName(node.getType());
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();

        //--------------------------------------------------------------------------------------------------------------
        // Instantiate a new instance, not yet initialised, with a instance reference as first parameter (see DUP).
        //--------------------------------------------------------------------------------------------------------------
        mv.visitTypeInsn(NEW, internalTypename);
        mv.visitInsn(DUP);

        //--------------------------------------------------------------------------------------------------------------
        // Evaluate parameters next
        //--------------------------------------------------------------------------------------------------------------
        evaluateArguments(node.getParameters());

        mv.visitMethodInsn(INVOKESPECIAL,
                           internalTypename,
                           CTOR_METHOD_NAME,
                           Type.getMethodDescriptor(Type.VOID_TYPE,
                                                    BytecodeHelper.typeArrayFor(toTypesArray(node.getParameters()))),
                           node.getType().isInterface());

        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());

        return node;
    }

    private Class<?>[] toTypesArray(NodeSequence<? extends Node> nodeList) {
        return nodeList == null || nodeList.getChildNodes() == null ? null : nodeList.getNodes().stream().map(Node::getTypeDescriptor).toArray(Class[]::new);
    }

    private Class<?>[] toTypesArray(List<? extends Node> nodeList) {
        return nodeList == null ? null : nodeList.stream().map(Node::getTypeDescriptor).toArray(Class[]::new);
    }

    private void evaluateArguments(final List<Node> arguments) {
        for (Node argument : IterableUtil.nullSafe(arguments)) {
            argument.accept(this);
        }
    }

    @Override
    public Node visitReturnStatement(final ReturnStatement node) {
        Class<?> returnType = node.getTypeDescriptor();

        if (node.getExpression() != null) {
            node.getExpression().accept(this);
            compilationContext.getVirtualMachine().autoboxIfNeeded(returnType);
        }

        compilationContext.getBytecodeHelper().emitReturn(returnType);

        return node;
    }

    @Override
    public Node visitInterpolatedStringLiteral(final InterpolatedStringLiteralExpr node) {
        compilationContext.getBytecodeHelper().emitInstantiateType(StringBuilder.class);

        for (Node expr : node.getParts()) {
//            compilationContext.getBytecodeHelper().emitDuplicate();
            expr.accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());

            compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(StringBuilder.class, StringBuilder.class, "append", Object.class);
//            compilationContext.getBytecodeHelper().emitPop();

            compilationContext.getVirtualMachine().popOperand();
        }

        compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(StringBuilder.class, String.class, "toString");
        compilationContext.getVirtualMachine().loadOperandOfType(String.class);

        return node;
    }


    @Override
    public Node visitScript(final Script node) {
        // Visit the import declaration section, if present
        if (node.getImportDeclaration() != null) {
            node.getImportDeclaration().accept(this);
        }

        // Visit script body elements
        for (Node child : node.getBody()) {
            child.accept(this);
        }
        return node;
    }

    @Override
    public Node visitSet(final SetExpr node) {
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        String className = getInternalName(LinkedHashSet.class);
        mv.visitTypeInsn(NEW, className);
        mv.visitInsn(DUP);

        if (isNullOrEmpty(node.getElements())) {
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, BytecodeHelper.EMPTY_TYPES));
        } else {
            compilationContext.getBytecodeHelper().emitLoadNumericOperand(node.getElements().size());
            mv.visitMethodInsn(INVOKESPECIAL, className, BytecodeHelper.CTOR_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE));
        }

        for (Node itemNode : node.getElements()) {
            mv.visitInsn(DUP);
            itemNode.accept(this);
            compilationContext.getBytecodeHelper().boxIfNeeded(compilationContext.getVirtualMachine().peekOperandStack());
            mv.visitMethodInsn(INVOKEINTERFACE, getInternalName(Set.class), "add", Type.getMethodDescriptor(Type.BOOLEAN_TYPE, BytecodeHelper.OBJECT_TYPE_ARRAY));
            mv.visitInsn(POP);
        }
        compilationContext.getVirtualMachine().loadOperandOfType(LinkedHashSet.class);

        return node;
    }

    @Override
    public Node visitStringLiteral(final StringLiteralExpr node) {
        compilationContext.getVirtualMachine().loadOperandOfType(String.class);
        compilationContext.getBytecodeHelper().emitLoadStringOperand(unescapeUniversalCharacterEscapeSequences(node.getUndelimitedTokenImage()));
        return node;
    }

    @Override
    public Node visitTernaryExpression(final TernaryExpression node) {
        return node;
    }

    @Override
    public Node visitTypeConversion(final TypeConversion node) {
        node.getSource().accept(this);

        compilationContext.getVirtualMachine().boxIfNeeded();
        compilationContext.getVirtualMachine().convert(node.getTargetType());

        return node;
    }

    @Override
    public TypeReference visitTypeReference(final TypeReference node) {
        compilationContext.getVirtualMachine().loadOperandOfType(node.getTypeDescriptor());
        compilationContext.getBytecodeHelper().emitLoadType(node.getTypeDescriptor());

        return node;
    }

    @Override
    public Node visitUnboxExpression(final Unbox node) {
        node.getSource().accept(this);
        compilationContext.getVirtualMachine().unbox();
        return node;
    }

    @Override
    public Node visitValueConsumingNode(ValueConsumingNode node) {
        int startingStackSize = compilationContext.getVirtualMachine().stackSize();
        if (node.getDelegate() != null) {
            node.getDelegate().accept(this);
        }
        compilationContext.getVirtualMachine().emitAndPopToStackSize(startingStackSize);

        return node;
    }
}