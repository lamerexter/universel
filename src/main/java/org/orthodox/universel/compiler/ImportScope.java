/*
 *  MIT Licence:
 *
 *  Copyright (c) 2020 Orthodox Engineering Ltd
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

import org.beanplanet.core.collections.ListBuilder;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.core.models.path.SimpleNamePath;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universel.ast.StaticMethodCall;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationAxis;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.LoadTypeExpression;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;

import javax.lang.model.type.NullType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static org.beanplanet.core.lang.TypeUtil.ensureNonPrimitiveType;
import static org.beanplanet.core.util.CollectionUtil.isEmptyOrNull;
import static org.beanplanet.core.util.StringUtil.asDelimitedString;
import static org.orthodox.universel.compiler.Messages.Constructor.CONSTRUCTOR_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.MethodCall.METHOD_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.MethodCall.METHOD_NOT_FOUND;
import static org.orthodox.universel.compiler.Messages.TYPE.TYPE_AMBIGUOUS;

public class ImportScope implements Scope {
    private final List<ImportStmt> importStatements;
    private final Messages compilationMessages;

    public ImportScope(
        final ImportDecl importDecl,
        final Messages compilationMessages) {
        this(importDecl.getImports(), compilationMessages);
    }

    public ImportScope(
        final List<ImportStmt> importStatements,
        final Messages compilationMessages) {
        this.importStatements = importStatements;
        this.compilationMessages = compilationMessages;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Node navigate(final NavigationStep<?> step) {
        if ( isEmptyOrNull(importStatements) ) return step;

        if ( !step.getAxis().equals(NavigationAxis.DEFAULT.getCanonicalName()) ) {
            return step;
        }

        if ( step.getNodeTest() instanceof MethodCall ) {
            return resolveMethodOrConstructorCallFromImports((NavigationStep<MethodCall>)step);
        }

        if ( step.getNodeTest() instanceof NameTest) {
            return resolveNameReference((NavigationStep<NameTest>)step);
        }

        return step;
    }

    private Node resolveNameReference(final NavigationStep<NameTest> step) {
        final List<Class<?>> typesFromNotOnDemandImports = resolveTypeNameFromImports(step, importStatements.stream().filter(ImportStmt::isNotOnDemand).collect(Collectors.toList()));
        if (typesFromNotOnDemandImports.size() == 1) return new LoadTypeExpression(step.getTokenImage(), new ResolvedTypeReferenceOld(step.getTokenImage(), typesFromNotOnDemandImports.get(0)));
//        if (typesFromNotOnDemandImports.size() == 1) return new ResolvedTypeReferenceOld(step.getTokenImage(), typesFromNotOnDemandImports.get(0));

        final List<Class<?>> typesFromOnDemandImports = resolveTypeNameFromImports(step, importStatements.stream().filter(ImportStmt::isOnDemand).collect(Collectors.toList()));
        if (typesFromOnDemandImports.size() == 1)  return new LoadTypeExpression(step.getTokenImage(), new ResolvedTypeReferenceOld(step.getTokenImage(), typesFromOnDemandImports.get(0)));;
//        if (typesFromOnDemandImports.size() == 1) return new ResolvedTypeReferenceOld(step.getTokenImage(), typesFromOnDemandImports.get(0));

        //--------------------------------------------------------------------------------------------------------------
        // Determine if name can be ambiguously resolved to two or more types from the imports.
        //--------------------------------------------------------------------------------------------------------------
        if ( typesFromNotOnDemandImports.size() > 1 ) {
            compilationMessages.addError(TYPE_AMBIGUOUS
                                             .relatedObject(step)
                                             .withParameters(
                                                 step.getNodeTest().getName(),
                                                 typesFromNotOnDemandImports.size(),
                                                 typesFromNotOnDemandImports));
        } else if ( typesFromOnDemandImports.size() > 1) {
            compilationMessages.addError(TYPE_AMBIGUOUS
                                             .relatedObject(step)
                                             .withParameters(
                                                 step.getNodeTest().getName(),
                                                 typesFromOnDemandImports.size(),
                                                 typesFromOnDemandImports));
        }

        return step;
    }

    private List<Class<?>> resolveTypeNameFromImports(final NavigationStep<NameTest> step, final List<ImportStmt> importStatements) {
        final String name = step.getNodeTest().getName();

        List<Class<?>> matchingNameTransformations = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

            NamePath typeName = new SimpleNamePath(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()));
            if ( importStmt.isOnDemand() ) {
                typeName = typeName.joinSingleton(name);
            } else if ( !Objects.equals(name, typeName.getLastElement()) ) {
                continue;
            }

            Class<?> referredType = TypeUtil.loadClassOrNull(typeName.join("."));
            if (referredType == null) continue;

            matchingNameTransformations.add(referredType);
        }

        return matchingNameTransformations;
    }

    public Node resolveMethodCall(final NavigationStep<MethodCall> step) {
        final MethodCall methodCall = step.getNodeTest();

        //--------------------------------------------------------------------------------------------------------------
        // Collect the callable methods + constructors (in that order), which match the method call semantics.
        // Methods take precedence here because the source code has used the "methodCall(...)" quick syntax, as opposes to
        // the "new constructorName(..,)", if a new object instance was intended.
        //--------------------------------------------------------------------------------------------------------------
        // Methods
        //--------------------------------------------------------------------------------------------------------------
//        final List<Node> resolvedMethodCallTransforms = resolveMethodOrConstructorCallFromImports(step);

        //--------------------------------------------------------------------------------------------------------------
        // Code bellow is DEPRECATED. All resolution should be positive here (or none at all) and any error messages
        // separated out into a latter phase (to avoid adding more than once!).
        //--------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------------------------------
        // Collect the callable methods + constructors (in that order), which match the method call semantics.
        // Methods take precedence here because the source code has used the "methodCall(...)" quick syntax, as opposes to
        // the "new constructorName(..,)", if a new object instance was intended.
        //--------------------------------------------------------------------------------------------------------------
        // Methods
        //--------------------------------------------------------------------------------------------------------------
        List<Method> matchingExplicitlyImportedMethods = findMatchingMethodsFromExplicitImports(methodCall);
        if (matchingExplicitlyImportedMethods.size() == 1) {
            return new StaticMethodCall(matchingExplicitlyImportedMethods.get(0).getReturnType(),
                                        step.getTokenImage(),
                                        matchingExplicitlyImportedMethods.get(0).getDeclaringClass(),
                                        matchingExplicitlyImportedMethods.get(0).getName(),
                                        autoBoxParametersIfNecessary(matchingExplicitlyImportedMethods.get(0).getParameterTypes(), methodCall.getParameters())
            );
        }

        List<Method> matchingImportOnDemandNethods = findMatchingMethodsFromOnDemandImports(methodCall);
        if (matchingImportOnDemandNethods.size() == 1) {
            return new StaticMethodCall(matchingImportOnDemandNethods.get(0).getReturnType(),
                                        step.getTokenImage(),
                                        matchingImportOnDemandNethods.get(0).getDeclaringClass(),
                                        matchingImportOnDemandNethods.get(0).getName(),
                                        autoBoxParametersIfNecessary(matchingImportOnDemandNethods.get(0).getParameterTypes(), methodCall.getParameters())
            );
        }

        //--------------------------------------------------------------------------------------------------------------
        // Constructors
        //--------------------------------------------------------------------------------------------------------------
        List<Constructor<?>> matchingImplicitlyImportedConsructors = findMatchingConstructorsFromImplicitImports(methodCall);
        if (matchingImplicitlyImportedConsructors.size() == 1) {
            return new ObjectCreationExpression(step.getTokenImage(),
                                                new ResolvedTypeReferenceOld(step.getTokenImage(),
                                                                             matchingImplicitlyImportedConsructors.get(0).getDeclaringClass()),
                                                autoBoxParametersIfNecessary(matchingImplicitlyImportedConsructors.get(0).getParameterTypes(), methodCall.getParameters()));
        }

        List<Constructor<?>> matchingExplicitlyImportedConsructors = findMatchingConstructorsFromExplicitImports(methodCall);
        if (matchingExplicitlyImportedConsructors.size() == 1) {
            return new ObjectCreationExpression(step.getTokenImage(),
                                                new ResolvedTypeReferenceOld(step.getTokenImage(),
                                                                             matchingExplicitlyImportedConsructors.get(0).getDeclaringClass()),
                                                autoBoxParametersIfNecessary(matchingExplicitlyImportedConsructors.get(0).getParameterTypes(), methodCall.getParameters()));
        }

        //--------------------------------------------------------------------------------------------------------------
        // Unable to find methods or new instance creation matching the call. Add appropriate compilation errors.
        //--------------------------------------------------------------------------------------------------------------
        if ( matchingExplicitlyImportedMethods.isEmpty()
             && matchingImportOnDemandNethods.isEmpty()
             && matchingImplicitlyImportedConsructors.isEmpty()
             && matchingExplicitlyImportedConsructors.isEmpty() ) {
            compilationMessages.addError(METHOD_NOT_FOUND.relatedObject(methodCall).withParameters(methodCall.getName().getName()));
        } else if ( matchingExplicitlyImportedMethods.size() > 1
                    || matchingExplicitlyImportedConsructors.size() > 1 ) {
            compilationMessages.addError(METHOD_AMBIGUOUS
                                               .relatedObject(methodCall)
                                               .withParameters(
                                                   methodCall.getName().getName(),
                                                   matchingExplicitlyImportedMethods.size(),
                                                   matchingExplicitlyImportedMethods));
        } else if ( matchingImportOnDemandNethods.size() > 1) {
            compilationMessages.addError(METHOD_AMBIGUOUS
                                               .relatedObject(methodCall)
                                               .withParameters(
                                                   methodCall.getName().getName(),
                                                   matchingImportOnDemandNethods.size(),
                                                   matchingImportOnDemandNethods));
        }

        return step;
    }

    private Node resolveMethodOrConstructorCallFromImports(final NavigationStep<MethodCall> step) {
        Node transformedMethodOrConstructorCallFromImports = resolveMethodCallFromImports(step);
        if ( transformedMethodOrConstructorCallFromImports != null) return transformedMethodOrConstructorCallFromImports;

        transformedMethodOrConstructorCallFromImports = resolveConsructorCallFromImports(step);

        return transformedMethodOrConstructorCallFromImports == null ? step : transformedMethodOrConstructorCallFromImports;
    }

    private Node resolveMethodCallFromImports(final NavigationStep<MethodCall> step) {
        final List<Method> callableMethodsFromNotOnDemandImports = findMatchingMethodsFromImports(step.getNodeTest(), importStatements.stream().filter(ImportStmt::isNotOnDemand).collect(Collectors.toList()));
        if (callableMethodsFromNotOnDemandImports.size() == 1) {
            return new StaticMethodCall(callableMethodsFromNotOnDemandImports.get(0).getReturnType(),
                                        step.getTokenImage(),
                                        callableMethodsFromNotOnDemandImports.get(0).getDeclaringClass(),
                                        callableMethodsFromNotOnDemandImports.get(0).getName(),
                                        autoBoxParametersIfNecessary(callableMethodsFromNotOnDemandImports.get(0).getParameterTypes(), step.getNodeTest().getParameters())
            );
        }

        final List<Method> callableMethodsFromOnDemandImports = findMatchingMethodsFromImports(step.getNodeTest(), importStatements.stream().filter(ImportStmt::isOnDemand).collect(Collectors.toList()));
        if (callableMethodsFromOnDemandImports.size() == 1) {
            return new StaticMethodCall(callableMethodsFromOnDemandImports.get(0).getReturnType(),
                                        step.getTokenImage(),
                                        callableMethodsFromOnDemandImports.get(0).getDeclaringClass(),
                                        callableMethodsFromOnDemandImports.get(0).getName(),
                                        autoBoxParametersIfNecessary(callableMethodsFromOnDemandImports.get(0).getParameterTypes(), step.getNodeTest().getParameters())
            );
        }

        //--------------------------------------------------------------------------------------------------------------
        // Determine if method call can be ambiguously resolved to two or more methods from the imports.
        //--------------------------------------------------------------------------------------------------------------
        if ( callableMethodsFromNotOnDemandImports.size() > 1 ) {
            compilationMessages.addError(METHOD_AMBIGUOUS
                                             .relatedObject(step)
                                             .withParameters(
                                                 step.getNodeTest().getName(),
                                                 callableMethodsFromNotOnDemandImports.size(),
                                                 callableMethodsFromNotOnDemandImports));
        } else if ( callableMethodsFromOnDemandImports.size() > 1) {
            compilationMessages.addError(METHOD_AMBIGUOUS
                                             .relatedObject(step)
                                             .withParameters(
                                                 step.getNodeTest().getName(),
                                                 callableMethodsFromOnDemandImports.size(),
                                                 callableMethodsFromOnDemandImports));
        }

        return null;
    }

    private List<Method> findMatchingMethodsFromImports(final MethodCall methodCall, final List<ImportStmt> importStatements) {
        final String methodName = methodCall.getName().getName();
        List<Method> matchingMethods = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);
            SimpleNamePath enclosingTypeName = new SimpleNamePath(importStmt.getElements().stream()
                                                                            .map(Name::getName)
                                                                            .collect(Collectors.toList()));
            if ( !importStmt.isOnDemand() ) {
                if ( !Objects.equals(enclosingTypeName.getLastElement(), methodName) ) continue;
                enclosingTypeName = enclosingTypeName.parentPath();
            }

            Class<?> enclosingType = TypeUtil.loadClassOrNull(enclosingTypeName.join("."));
            if (enclosingType == null) continue;

            collectCallableMethods(methodCall, enclosingType, matchingMethods);
        }

        return matchingMethods;
    }

    private Node resolveConsructorCallFromImports(final NavigationStep<MethodCall> step) {
        List<Constructor<?>> callableConstructorsFromImports = findMatchingConstructorsFromImports(step.getNodeTest());
        if (callableConstructorsFromImports.size() == 1) {
            return new ObjectCreationExpression(step.getTokenImage(),
                                                new ResolvedTypeReferenceOld(step.getTokenImage(),
                                                                             callableConstructorsFromImports.get(0).getDeclaringClass()),
                                                autoBoxParametersIfNecessary(callableConstructorsFromImports.get(0).getParameterTypes(), step.getNodeTest().getParameters()));
        }

        //--------------------------------------------------------------------------------------------------------------
        // Determine if method call can be ambiguously resolved to two or more methods from the imports.
        //--------------------------------------------------------------------------------------------------------------
        if ( callableConstructorsFromImports.size() > 1 ) {
            compilationMessages.addError(CONSTRUCTOR_AMBIGUOUS
                                             .relatedObject(step)
                                             .withParameters(
                                                 step.getNodeTest().getName(),
                                                 callableConstructorsFromImports.size(),
                                                 callableConstructorsFromImports));
        }

        return null;
    }

    private List<Constructor<?>> findMatchingConstructorsFromImports(final MethodCall methodCall) {
        final String unqualifiedTypeName = methodCall.getName().getName();

        List<Constructor<?>> matchingConstructors = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

            NamePath enclosingTypeName = new SimpleNamePath(importStmt.getElements().stream()
                                                                      .map(Name::getName)
                                                                      .collect(Collectors.toList()));
            if ( importStmt.isOnDemand() ) {
                enclosingTypeName = enclosingTypeName.joinSingleton(unqualifiedTypeName);
            } else if ( !Objects.equals(enclosingTypeName.getLastElement(), methodCall.getName().getName()) ) {
                continue;
            }

            Class<?> enclosingType = TypeUtil.loadClassOrNull(enclosingTypeName.join("."));
            if (enclosingType == null) continue;

            collectCallableConstructors(methodCall, enclosingType, matchingConstructors);
        }

        return matchingConstructors;
    }

    private List<Node> autoBoxParametersIfNecessary(final Class<?>[] toTypes, final List<Node> fromTypes) {
        return IntStream.range(0, toTypes.length)
            .mapToObj(i -> TransformationUtil.autoBoxOrPromoteIfNecessary(fromTypes.get(i), toTypes[i]))
            .collect(Collectors.toList());
    }

    private List<Method> findMatchingMethodsFromExplicitImports(final MethodCall methodCall) {
        final String methodName = methodCall.getName().getName();

        List<Method> matchingMethods = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

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

    private List<Constructor<?>> findMatchingConstructorsFromImplicitImports(final MethodCall methodCall) {
        final String unqualifiedTypeName = methodCall.getName().getName();

        List<Constructor<?>> matchingConstructors = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

            String fqTypeName = asDelimitedString(
                ListBuilder.<String>builder()
                    .addAll(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()))
                    .add(unqualifiedTypeName)
                    .build(),
                "."
            );

            Class<?> enclosingType = TypeUtil.loadClassOrNull(fqTypeName);
            if (enclosingType == null) continue;

            collectCallableConstructors(methodCall, enclosingType, matchingConstructors);
        }

        return matchingConstructors;
    }

    private List<Constructor<?>> findMatchingConstructorsFromExplicitImports(final MethodCall methodCall) {
        final String unqualifiedTypeName = methodCall.getName().getName();

        List<Constructor<?>> matchingConstructors = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

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
                .filter(m -> methodCallParametersMatch(m, methodCall))
                .forEach(colllection::add);
    }

    private void collectCallableConstructors(MethodCall methodCall,
                                             Class<?> enclosingType,
                                             Collection<Constructor<?>> colllection) {
        TypeUtil.streamConstructors(enclosingType)
                .filter(c -> isPublic(c.getModifiers()))
                .filter(c -> c.getParameterCount() == methodCall.getParameters().size())
                .filter(m -> methodCallParametersMatch(m, methodCall))
                .forEach(colllection::add);
    }

    private List<Method> findMatchingMethodsFromOnDemandImports(final MethodCall methodCall) {
        List<Method> matchingMethods = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

            if ( !importStmt.isOnDemand() ) continue;

            String enclosingTypeName = asDelimitedString(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()), ".");

            Class<?> enclosingType = TypeUtil.loadClassOrNull(enclosingTypeName);
            if (enclosingType == null) continue;

            collectCallableMethods(methodCall, enclosingType, matchingMethods);
        }

        return matchingMethods;
    }

    private boolean methodCallParametersMatch(Executable method, MethodCall methodCall) {

        for (int n=0; n < method.getParameterCount(); n++) {
            Class<?> callParamType = methodCall.getParameters().get(n).getTypeDescriptor();
            Class<?> methodParamType = method.getParameterTypes()[n];

            if ( callParamType == null ||
                 methodParamType == null ||
                 !(NullType.class == callParamType ||
                   methodParamType.isAssignableFrom(callParamType) ||
                   boxTypeCompatible(callParamType, methodParamType)) ) return false;
        }

        return true;
    }

    private boolean boxTypeCompatible(Class<?> type1, Class<?> type2) {
        return ensureNonPrimitiveType(type1).equals(ensureNonPrimitiveType(type2));
    }

    @Override
    public Type resolveType(final NamePath name) {
        final List<Type> typesFromNotOnDemandImports = resolveType(name, importStatements.stream().filter(ImportStmt::isNotOnDemand).collect(Collectors.toList()));
        if (typesFromNotOnDemandImports.size() == 1) return typesFromNotOnDemandImports.get(0);

        final List<Type> typesFromOnDemandImports = resolveType(name, importStatements.stream().filter(ImportStmt::isOnDemand).collect(Collectors.toList()));
        if (typesFromOnDemandImports.size() == 1) return typesFromOnDemandImports.get(0);

        return null;
    }

    private List<Type> resolveType(final NamePath names, final List<ImportStmt> importStatements) {
        final String name = names.getLastElement();  // TODO: What about fqtn? Possibly handled by navigation path...

        List<Type> matchingTypes = new ArrayList<>();
        for (int n=importStatements.size()-1; n >= 0; n--) {
            ImportStmt importStmt = importStatements.get(n);

            NamePath typeName = new SimpleNamePath(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()));
            if ( importStmt.isOnDemand() ) {
                typeName = typeName.joinSingleton(name);
            } else if ( !Objects.equals(name, typeName.getLastElement()) ) {
                continue;
            }

            Class<?> referredType = TypeUtil.loadClassOrNull(typeName.join("."));
            if (referredType == null) continue;

            matchingTypes.add(new ResolvedTypeReferenceOld(referredType));
        }

        return matchingTypes;
    }

}
