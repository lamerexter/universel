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

package org.orthodox.universel.symanticanalysis;

import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.core.models.path.SimpleNamePath;
import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.methods.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.reference.*;

import javax.lang.model.type.NullType;
import java.util.*;
import java.util.stream.Collectors;

import static org.beanplanet.core.util.StringUtil.asDelimitedString;
import static org.orthodox.universel.compiler.Messages.ConditionalExpression.AMBIGUOUS_TYPE;
import static org.orthodox.universel.compiler.Messages.TYPE.TYPE_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.TYPE.TYPE_NOT_FOUND;

/**
 * Iterates over the AST, performing a depth-first post-order traversal to establish the types on the AST. Essentially,
 * known types flow upwards from the leaves on the tree, forming a type tree from the ground up.
 *
 * <ul>
 * <li>{@link ReferenceType}
 * </ul>
 *
 * and resolving them to actiual types.
 */
public class ConditionalExpressionAnalyser extends UniversalVisitorAdapter implements SemanticAnalyser {
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
    public Node visitTernaryExpression(TernaryExpression node) {
        // The type of a conditional expression is the common type of its second and third arguments.
        // If both types are the same, then that is the established type of the conditional expression. If they are
        // convertible to the to each other, one gets chosen (usually the second argument) and the other gets converted,
        // by promotion, to the other.
        // Otherwise, if there is a common superclass of both, then that is the established type.
        // It is an error if the common established type cannot be determined.

        Class<?> establishedType = null;
        if ( node.getLhsExpression().getTypeDescriptor() == node.getRhsExpression().getTypeDescriptor()
            && node.getLhsExpression().getTypeDescriptor() != NullType.class ) {
            establishedType = node.getLhsExpression().getTypeDescriptor();
        } else {
            establishedType = commonTypeOf(node.getLhsExpression(), node.getRhsExpression());
        }

        if ( establishedType == null ) {
            context.getMessages().addError(AMBIGUOUS_TYPE.build());

        }
        return node;
    }

    private Class<?> commonTypeOf(Node lhsExpression, Node rhsExpression) {
//        Class<?> lhsType = lhsExpression.getTypeDescriptor();
//        Class<?> rhsType = lhsExpression.getTypeDescriptor();
//
//        lhsType = lhsType.getSuperclass();
//        while (lhsType != null) {
//            Class<?> superRhsType = rhsType;
//            while ()
//        }

        return null;
    }

    private Class<?> determineTypeOfReference(TypeReference node) {
        //--------------------------------------------------------------------------------------------------------------
        // Find matching primitive types
        //--------------------------------------------------------------------------------------------------------------
        if (node instanceof PrimitiveType || node instanceof VoidType) {
            return node.getTypeDescriptor();
        }

        //--------------------------------------------------------------------------------------------------------------
        // Find matching types imported from explicit imports.
        //--------------------------------------------------------------------------------------------------------------
        List<Class<?>> matchingTypesImportedExplicitly = findTypesMatchingExplicitImports(node);
        if ( matchingTypesImportedExplicitly.size() == 1 ) return matchingTypesImportedExplicitly.get(0);

        //--------------------------------------------------------------------------------------------------------------
        // Find matching types imported on demand.
        //--------------------------------------------------------------------------------------------------------------
        List<Class<?>> matchingTypesImportedOnDemand = findTypesMatchingOnDemandImports(node);
        if ( matchingTypesImportedOnDemand.size() == 1 ) return matchingTypesImportedOnDemand.get(0);

        //--------------------------------------------------------------------------------------------------------------
        // Find matching types imported from implicit imports.
        //--------------------------------------------------------------------------------------------------------------
        List<Class<?>> matchingTypesImportedImplicitly = findTypesMatchingImplicitImports(node);
        if ( matchingTypesImportedImplicitly.size() == 1 ) return matchingTypesImportedImplicitly.get(0);

        //--------------------------------------------------------------------------------------------------------------
        // Unable to find type or the reference. Add appropriate compilation errors.
        //--------------------------------------------------------------------------------------------------------------
        if ( matchingTypesImportedExplicitly.isEmpty()
             && matchingTypesImportedOnDemand.isEmpty()
             && matchingTypesImportedImplicitly.isEmpty() ) {
            context.getMessages().addError(TYPE_NOT_FOUND.withParameters(node.getName().getElement()));
        } else if ( matchingTypesImportedOnDemand.size() > 1
                    || matchingTypesImportedImplicitly.size() > 1 ) {
            context.getMessages().addError(TYPE_AMBIGUOUS.withParameters(
                    node.getName().getElement(),
                    matchingTypesImportedOnDemand.size(),
                    matchingTypesImportedOnDemand));
        }

        return null;
    }

    private List<Class<?>> findTypesMatchingImplicitImports(TypeReference referenceType) {
        final NamePath typeReferenceName = referenceType.getName();
        final String[][] implicitImports = { {"java", "lang"}, {"java", "util"}, {"java", "math"} };

        List<Class<?>> matchingTypes = new ArrayList<>();
        for (int n=implicitImports.length-1; n >= 0; n--) {
            String[] implicitImport = implicitImports[n];

            List<String> importElements = new ArrayList<>(Arrays.asList(implicitImport));
            importElements.addAll(typeReferenceName.getElements());

            String importedTypename = StringUtil.asDelimitedString(importElements, ".");

            Class<?> importedType = TypeUtil.loadClassOrNull(importedTypename);
            if (importedType != null) matchingTypes.add(importedType);
        }

        return matchingTypes;
    }

    private List<Class<?>> findTypesMatchingOnDemandImports(TypeReference referenceType) {
        if ( importDecl == null ) return Collections.emptyList();

        final NamePath typeReferenceName = referenceType.getName();

        List<Class<?>> matchingTypes = new ArrayList<>();
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            if ( !importStmt.isOnDemand() ) continue;

            String importedPackage = asDelimitedString(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()), ".");

            Class<?> importedType = TypeUtil.loadClassOrNull(importedPackage+"."+typeReferenceName.getElement());
            if (importedType != null) matchingTypes.add(importedType);
        }

        return matchingTypes;
    }

    private List<Class<?>> findTypesMatchingExplicitImports(TypeReference referenceType) {
        if ( importDecl == null ) return Collections.emptyList();

        final NamePath typeReferenceName = referenceType.getName();

        List<Class<?>> matchingTypes = new ArrayList<>();
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            if ( importStmt.isOnDemand() ) continue;

            NamePath importedTypeFqn = new SimpleNamePath(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()));
            if ( importStmt.isOnDemand() ) {
                importedTypeFqn = (NamePath)importedTypeFqn.join(typeReferenceName);
            } else if ( !typeReferenceName.getElement().equals(importedTypeFqn.getLastElement()) ) {
                continue;
            }

            Class<?> importedType = TypeUtil.loadClassOrNull(importedTypeFqn.join("."));
            if (importedType != null) matchingTypes.add(importedType);
        }

        return matchingTypes;
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(MethodDeclaration node) {
//        if (node.getReturnType().getTypeDescriptor() != null && node.getParameters().getNodes().stream().map(Node::getTypeDescriptor).allMatch(Objects::nonNull)) return node;

        TypeReference transformedReturnTypeReference;
        if ( node.getReturnType().getTypeDescriptor() != null ) {
            transformedReturnTypeReference = node.getReturnType();
        } else {
            Class<?> resolvedType = determineTypeOfReference(node.getReturnType());
            transformedReturnTypeReference = resolvedType == null ? node.getReturnType() : new ResolvedTypeReference(node.getTokenImage(),
                                                                                                                     resolvedType,
                                                                                                                     node.getReturnType().getName(),
                                                                                                                     node.getReturnType().getDimensions());

        }

        NodeSequence.NodeSequenceBuilder<Parameter> nodeSequenceBuilder = NodeSequence.<Parameter>builder().tokenImage(node.getParameters().getTokenImage());
        List<Parameter> transformedParametersList = new ArrayList<>(node.getParameters().size());
        for (Parameter parameter : node.getParameters().getNodes()) {
            TypeReference resolvedTypeReference;
            if ( parameter.getTypeDescriptor() != null ) {
                resolvedTypeReference = parameter.getType();
            } else {
                Class<?> resolvedType = determineTypeOfReference(parameter.getType());
                resolvedTypeReference = resolvedType == null ? parameter.getType() : new ResolvedTypeReference(node.getTokenImage(), resolvedType, parameter.getType().getName(), parameter.getType().getDimensions());
            }
            transformedParametersList.add(new Parameter(parameter.getModifiers(), resolvedTypeReference, parameter.isVarArgs(), parameter.getName()));
        }
        nodeSequenceBuilder.addAll(transformedParametersList);
        final NodeSequence<Parameter> transformedParameters = nodeSequenceBuilder.build();

        final NodeSequence<Node> transformedBody = super.transformNodeSequence(node.getBody());

        boolean noTransformationChanges = Objects.equals(transformedReturnTypeReference, node.getReturnType())
                                          && Objects.equals(transformedParameters, node.getParameters())
                                          && Objects.equals(transformedBody, node.getBody());
        return noTransformationChanges ? node : new MethodDeclaration(node.getModifiers(),
                                                                      node.getTypeParameters(),
                                                                      transformedReturnTypeReference,
                                                                      node.getName(),
                                                                      transformedParameters,
                                                                      transformedBody);
   }


    @Override
    public TypeReference visitTypeReference(TypeReference node) {
        if (node instanceof ResolvedTypeReference) return node;

        // TODO: Hack to make ScriptAssemblyTest work. A reference to the current type being compiled (i.e. the main class) cannot
        // possibly resolve to the class being compiled! This is an example of requiring the Class<?> to be available for
        // the current class being compiled. Chicken and egg...
        if (node instanceof TypeNameReference) return node;

        Class<?> resolvedType = determineTypeOfReference(node);
        return new ResolvedTypeReference(node.getTokenImage(), resolvedType, node.getName(), node.getDimensions());
    }
}
