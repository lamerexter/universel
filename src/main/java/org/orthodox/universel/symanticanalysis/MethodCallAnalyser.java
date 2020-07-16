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

package org.orthodox.universel.symanticanalysis;

import org.beanplanet.core.lang.TypeUtil;
import org.orthodox.universel.cst.*;

import javax.lang.model.type.NullType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static org.beanplanet.core.lang.TypeUtil.ensureNonPrimitiveType;
import static org.beanplanet.core.util.StringUtil.asDelimitedString;

public class MethodCallAnalyser extends UniversalVisitorAdapter implements SemanticAnalyser {
    private SemanticAnalysisContext context;
    private ImportDecl importDecl;

    @Override
    public Node performAnalysis(SemanticAnalysisContext context, Node from) {
        this.context = context;
        return from.accept(this);
    }

    @Override
    public Node visitImportDeclaration(ImportDecl node) {
        this.importDecl = node;
        return node;
    }

    @Override
    public Node visitMethodCall(MethodCall node) {
        visitChildren(node.getParameters());
        resolveMethodCalls(node);
        return node;
    }

    private void visitChildren(List<? extends Node> children) {
        for (Node child : children) {
            child.accept(this);
        }
    }

    public void resolveMethodCalls(MethodCall methodCall) {
//        //--------------------------------------------------------------------------------------------------------------
//        // Collect the callable methods + constructors (in that order), which match the method call semantics.
//        // Methods take precedence here because the source code has used the "methodCall(...)" quick syntax, as opposes to
//        // the "new constructorName(..,)", if a new object instance was intended.
//        //--------------------------------------------------------------------------------------------------------------
//        // Methods
//        //--------------------------------------------------------------------------------------------------------------
//        List<Method> matchingExplicitlyImportedMethods = findMatchingMethodsFromExplicitImports(methodCall);
//        if (matchingExplicitlyImportedMethods.size() == 1) {
//            methodCall.setExecutable(matchingExplicitlyImportedMethods.get(0));
//            return;
//        }
//
//        List<Method> matchingImportOnDemandNethods = findMatchingMethodsFromOnDemandImports(methodCall);
//        if (matchingImportOnDemandNethods.size() == 1) {
//            methodCall.setExecutable(matchingImportOnDemandNethods.get(0));
//            return;
//        }
//
//        //--------------------------------------------------------------------------------------------------------------
//        // Constructors
//        //--------------------------------------------------------------------------------------------------------------
//        List<Constructor> matchingExplicitlyImporteConsructors = findMatchingConstructorsFromExplicitImports(methodCall);
//        if (matchingExplicitlyImporteConsructors.size() == 1) {
//            methodCall.setExecutable(matchingExplicitlyImporteConsructors.get(0));
//            return;
//        }
//
//        //--------------------------------------------------------------------------------------------------------------
//        // Unable to find methods or new instance creation matching the call. Add appropriate compilation errors.
//        //--------------------------------------------------------------------------------------------------------------
//        if ( matchingExplicitlyImportedMethods.isEmpty()
//             && matchingImportOnDemandNethods.isEmpty()
//             && matchingExplicitlyImporteConsructors.isEmpty() ) {
//            context.getMessages().addError(METHOD_NOT_FOUND.relatedObject(methodCall).withParameters(methodCall.getName().getName()));
//        } else if ( matchingExplicitlyImportedMethods.size() > 1
//                    || matchingExplicitlyImporteConsructors.size() > 1 ) {
//            context.getMessages().addError(METHOD_AMBIGUOUS
//                                                   .relatedObject(methodCall)
//                                                   .withParameters(
//                    methodCall.getName().getName(),
//                    matchingExplicitlyImportedMethods.size(),
//                    matchingExplicitlyImportedMethods));
//        } if ( matchingImportOnDemandNethods.size() > 1) {
//            context.getMessages().addError(METHOD_AMBIGUOUS
//                                                   .relatedObject(methodCall)
//                                                   .withParameters(
//                    methodCall.getName().getName(),
//                    matchingImportOnDemandNethods.size(),
//                    matchingImportOnDemandNethods));
//        }
    }

    private List<Method> findMatchingMethodsFromExplicitImports(final MethodCall methodCall) {
        final String methodName = methodCall.getName().getName();

        List<Method> matchingMethods = new ArrayList<>();
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            if ( importStmt.isOnDemand() ) continue;
            if ( importStmt.getElements().isEmpty() || !importStmt.getElements().get(importStmt.getElements().size()-1).getName().equals(methodName)) continue;

            String enclosingTypeName = asDelimitedString(importStmt.getElements().subList(0, importStmt.getElements().size()-1)
                                                                   .stream().map(Name::getName).collect(Collectors.toList()), ".");

            Class<?> enclosingType = TypeUtil.loadClassOrNull(enclosingTypeName);
            if (enclosingType == null) continue;

            collectCallableMethods(methodCall, enclosingType, matchingMethods);
        }

        return matchingMethods;
    }

    private List<Constructor> findMatchingConstructorsFromExplicitImports(final MethodCall methodCall) {
        final String unqualifiedTypeName = methodCall.getName().getName();

        List<Constructor> matchingConstructors = new ArrayList<>();
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            if ( importStmt.isOnDemand() ) continue;
            if ( importStmt.getElements().isEmpty() || !importStmt.getElements().get(importStmt.getElements().size()-1).getName().equals(unqualifiedTypeName)) continue;

            String fqTypeName = asDelimitedString(importStmt.getElements()
                                                            .stream()
                                                            .map(Name::getName).collect(Collectors.toList()),
                                                  ".");

            Class<?> enclosingType = TypeUtil.loadClassOrNull(fqTypeName);
            if (enclosingType == null) continue;

            collectCallableConstructors(methodCall, enclosingType, matchingConstructors);
        }

        return matchingConstructors;
    }

    private void collectCallableMethods(MethodCall methodCall,
                                        Class<?> enclosingType,
                                        Collection<Method> colllection) {
        final String methodName = methodCall.getName().getName();

        TypeUtil.streamMethods(enclosingType)
                .filter(m -> isStatic(m.getModifiers()))
                .filter(m -> isPublic(m.getModifiers()))
                .filter(m -> m.getName().equals(methodName))
                .filter(m -> m.getParameterCount() == methodCall.getParameters().size())
                .filter(m -> methodCallParemetersMatch(m, methodCall))
                .forEach(colllection::add);
    }

    private void collectCallableConstructors(MethodCall methodCall,
                                             Class<?> enclosingType,
                                             Collection<Constructor> colllection) {
        TypeUtil.streamConstructors(enclosingType)
                .filter(c -> isPublic(c.getModifiers()))
                .filter(c -> c.getParameterCount() == methodCall.getParameters().size())
                .filter(m -> methodCallParemetersMatch(m, methodCall))
                .forEach(colllection::add);
    }

    private List<Method> findMatchingMethodsFromOnDemandImports(final MethodCall methodCall) {
        List<Method> matchingMethods = new ArrayList<>();
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            if ( !importStmt.isOnDemand() ) continue;

            String enclosingTypeName = asDelimitedString(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()), ".");

            Class<?> enclosingType = TypeUtil.loadClassOrNull(enclosingTypeName);
            if (enclosingType == null) continue;

            collectCallableMethods(methodCall, enclosingType, matchingMethods);
        }

        return matchingMethods;
    }

    private boolean methodCallParemetersMatch(Executable method, MethodCall methodCall) {

        for (int n=0; n < method.getParameterCount(); n++) {
            Class<?> callParamType = methodCall.getParameters().get(n).getTypeDescriptor();
            Class<?> methodParamType = method.getParameterTypes()[n];

            if ( !(NullType.class == callParamType || methodParamType.isAssignableFrom(callParamType)
                   || boxTypeCompatible(callParamType, methodParamType)) ) return false;
        }

        return true;
    }

    private boolean boxTypeCompatible(Class<?> type1, Class<?> type2) {
        return ensureNonPrimitiveType(type1).equals(ensureNonPrimitiveType(type2));
    }
}
