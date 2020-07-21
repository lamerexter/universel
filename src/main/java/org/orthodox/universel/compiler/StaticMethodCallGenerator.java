/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *
 */

package org.orthodox.universel.compiler;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.orthodox.universel.ast.ImportDecl;
import org.orthodox.universel.ast.MethodCall;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import static org.beanplanet.core.lang.TypeUtil.ensureNonPrimitiveType;
import static org.objectweb.asm.Opcodes.*;
import static org.orthodox.universel.compiler.BytecodeHelper.CTOR_METHOD_NAME;

public class StaticMethodCallGenerator { //}
    private CompilationContext compilationContext;
    private ImportDecl importDecl;

    public StaticMethodCallGenerator(CompilationContext compilationContext,
                                     ImportDecl importDecl) {
        this.compilationContext =compilationContext;
        this.importDecl = importDecl;
    }

//    @Override
    public boolean canResolve(MethodCall methodCall) {
        return true;
    }

//    @Override
    public void generateCall(UniversalCodeVisitor visitor,
                             MethodCall methodCall) {
//        if ( methodCall.getExecutable() == null ) return;
//
//        if ( methodCall.getExecutable().getClass() == Method.class) {
//            generateForStaticMethodCall(visitor, methodCall, (Method)methodCall.getExecutable());
//        } else {
//            generateForNewObjectCreation(visitor, methodCall, (Constructor)methodCall.getExecutable());
//        }
    }

    public void generateForStaticMethodCall(UniversalCodeVisitor visitor,
                                            MethodCall methodCall,
                                            Method method) {
        //--------------------------------------------------------------------------------------------------------------
        // Generate for any parameters first, in order to ascertain the types of parameters
        //--------------------------------------------------------------------------------------------------------------
        evaluateCallParameters(visitor, methodCall, method);
        compilationContext.getBytecodeHelper().peekMethodVisitor().visitMethodInsn(Opcodes.INVOKESTATIC,
                                                                                   Type.getInternalName(method.getDeclaringClass()),
                                                                                   methodCall.getName().getName(),
                                                                                   Type.getMethodDescriptor(Type.getType(method.getReturnType()),
                                                                                                            BytecodeHelper.typeArrayFor(method.getParameterTypes())),
                                                                                   false);
        compilationContext.getVirtualMachine().loadOperandOfType(method.getReturnType());
    }

    private void evaluateCallParameters(UniversalCodeVisitor visitor,
                                        MethodCall methodCall,
                                        Executable executable) {
        if (executable.getParameterCount() > 0) {
            for (int n=0; n < executable.getParameterCount(); n++) {
                Node paramExpr = methodCall.getParameters().get(n);
                paramExpr.accept(visitor);
                compilationContext.getVirtualMachine().convertOrAutoboxOperandIfNeeded(executable.getParameterTypes()[n]);
            }

            methodCall.getParameters().forEach( i -> compilationContext.getVirtualMachine().popOperand() );
        }
    }

    private boolean boxTypeCompatible(Class<?> type1, Class<?> type2) {
        return ensureNonPrimitiveType(type1).equals(ensureNonPrimitiveType(type2));
    }

    public void generateForNewObjectCreation(UniversalCodeVisitor visitor,
                                             MethodCall methodCall,
                                             Constructor<?> constructor) {
        String internalTypename = Type.getInternalName(constructor.getDeclaringClass());
        MethodVisitor mv = compilationContext.getBytecodeHelper().peekMethodVisitor();
        mv.visitTypeInsn(NEW, internalTypename);
        mv.visitInsn(DUP);

        //--------------------------------------------------------------------------------------------------------------
        // Generate for any parameters first, in order to ascertain the types of parameters
        //--------------------------------------------------------------------------------------------------------------
        evaluateCallParameters(visitor, methodCall, constructor);

        mv.visitMethodInsn(INVOKESPECIAL,
                           internalTypename,
                           CTOR_METHOD_NAME,
                           Type.getMethodDescriptor(Type.VOID_TYPE,
                                                    BytecodeHelper.typeArrayFor(constructor.getParameterTypes())),
                           false);

        compilationContext.getVirtualMachine().loadOperandOfType(constructor.getDeclaringClass());
    }
}
