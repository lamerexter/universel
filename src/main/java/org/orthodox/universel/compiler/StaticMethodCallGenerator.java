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

import org.beanplanet.core.lang.Assert;
import org.beanplanet.core.lang.TypeUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.orthodox.universel.ast.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.beanplanet.core.util.IterableUtil.nullSafe;
import static org.beanplanet.core.util.StringUtil.asDelimitedString;

public class StaticMethodCallGenerator implements MethodCallScope {
    private CompilationContext compilationContext;
    private ImportDecl importDecl;

    public StaticMethodCallGenerator(CompilationContext compilationContext,
                                     ImportDecl importDecl) {
        this.compilationContext =compilationContext;
        this.importDecl = importDecl;
    }

    @Override
    public boolean canResolve(MethodCall methodCall) {
        return findMethodFor(methodCall) != null;
    }

    @Override
    public void generateCall(UniversalCodeVisitor visitor,
                             MethodCall methodCall) {
        Method matchingMethod = findMethodFor(methodCall);
        Assert.notNull(matchingMethod, "No such method found "+methodCall.getName());

        if (matchingMethod.getParameterCount() > 0) {
            for (int n=0; n < matchingMethod.getParameterCount(); n++) {
                Expression paramExpr = methodCall.getParameters().get(n);
                paramExpr.accept(visitor);
                compilationContext.getVirtualMachine().convertOrBoxOperandIfNeeded(matchingMethod.getParameterTypes()[n]);
            }
        }
        compilationContext.getBytecodeHelper().peekMethodVisitor().visitMethodInsn(Opcodes.INVOKESTATIC,
                                                                                   Type.getInternalName(matchingMethod.getDeclaringClass()),
                                                                                   methodCall.getName().getName(),
                                                                                   Type.getMethodDescriptor(Type.getType(matchingMethod.getReturnType()),
                                                                                                            BytecodeHelper.typeArrayFor(matchingMethod.getParameterTypes())),
                                                                                   false);
        if ( matchingMethod.getReturnType() != Void.class ) {
            compilationContext.getVirtualMachine().loadOperandOfType(matchingMethod.getReturnType());
        }
    }

    private Method findMethodFor(final MethodCall methodCall) {
        final String methodName = methodCall.getName().getName();

        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            String enclosingTypeName;
            if ( importStmt.isOnDemand() ) {
                enclosingTypeName = asDelimitedString(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()), ".");
            } else {
                if ( importStmt.getElements().isEmpty() || !importStmt.getElements().get(importStmt.getElements().size()-1).getName().equals(methodName)) continue;

                enclosingTypeName = asDelimitedString(importStmt.getElements().subList(0, importStmt.getElements().size()-1)
                                                                .stream().map(Name::getName).collect(Collectors.toList()), ".");
            }

            Class<?> enclosingType = TypeUtil.loadClassOrNull(enclosingTypeName);
            if (enclosingType == null) continue;

            Optional<Method> foundMethod = TypeUtil.streamMethods(enclosingType)
                                                   .filter(m -> Modifier.isStatic(m.getModifiers()))
                                                   .filter(m -> Modifier.isPublic(m.getModifiers()))
                                                   .filter(m -> m.getName().equals(methodName))
                                                   .filter(m -> m.getParameterCount() == methodCall.getParameters().size())
                                                   .findFirst();
            if (foundMethod.isPresent()) return foundMethod.get();
        }

        return null;
    }
}
