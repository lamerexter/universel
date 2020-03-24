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
import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.annotation.Annotation;
import org.orthodox.universel.cst.collections.ListExpr;
import org.orthodox.universel.cst.collections.MapExpr;
import org.orthodox.universel.cst.collections.SetExpr;
import org.orthodox.universel.cst.conditionals.TernaryExpression;
import org.orthodox.universel.cst.literals.*;
import org.orthodox.universel.cst.types.PrimitiveType;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;
import org.orthodox.universel.symanticanalysis.conversion.TypeConversion;

import javax.lang.model.type.NullType;
import java.util.*;
import java.util.stream.Collectors;

import static org.beanplanet.core.util.StringUtil.asDelimitedString;
import static org.orthodox.universel.compiler.Messages.ConditionalExpression.AMBIGUOUS_TYPE;
import static org.orthodox.universel.compiler.Messages.MethodCall.TYPE_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.MethodCall.TYPE_NOT_FOUND;

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
    public Node visitAnnotation(Annotation node) {
        return node;
    }

    @Override
    public Node visitBetweenExpression(BetweenExpression node) {
        return node;
    }

    @Override
    public Node visitNumericLiteralExpression(NumericLiteral node) {
        Node numericLiteralNode = (Node)node;
        numericLiteralNode.setTypeDescriptor(node.getTypeDescriptor());
        return numericLiteralNode;
    }

    @Override
    public Node visitBinaryExpression(BinaryExpression node) {
        return node;
    }

    @Override
    public Node visitInExpression(InExpression node) {
        node.setTypeDescriptor(boolean.class);
        return node;
    }

    @Override
    public Node visitInstanceofExpression(InstanceofExpression node) {
        node.setTypeDescriptor(boolean.class);
        return node;
    }

    @Override
    public Node visitBooleanLiteral(BooleanLiteralExpr node) {
        node.setTypeDescriptor(boolean.class);
        return node;
    }

    @Override
    public Node visitImportDeclaration(ImportDecl node) {
        this.importDecl = node;
        return node;
    }

    @Override
    public Node visitList(ListExpr node) {
        node.setTypeDescriptor(List.class);
        return node;
    }

    @Override
    public Node visitMap(MapExpr node) {
        node.setTypeDescriptor(Map.class);
        return node;
    }

    @Override
    public Node visitMethodCall(MethodCall node) {
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

    @Override
    public Node visitName(Name node) {
        return node;
    }

    @Override
    public Node visitNullLiteral(NullLiteralExpr node) {
        node.setTypeDescriptor(NullType.class);
        return node;
    }

    @Override
    public Node visitNullTestExpression(NullTestExpression node) {
        node.setTypeDescriptor(boolean.class);
        return node;
    }

    @Override
    public Node visitReferenceType(ReferenceType node) {
        node.getReferredType().accept(this);

        Class<?> resolvedType = determineTypeOfReference(node);
        node.getReferredType().setTypeDescriptor(resolvedType);

        return node;
    }

    @Override
    public Node visitInterpolatedStringLiteral(InterpolatedStringLiteralExpr node) {
        node.setTypeDescriptor(String.class);
        return node;
    }

    @Override
    public Node visitScript(Script node) {
        if ( node.getImportDeclaration() != null ) {
            node.getImportDeclaration().accept(this);
        }

        Class<?> establishedType = null;
        for (Node child : node.getBodyElements()) {
            child.accept(this);
            establishedType = child.getTypeDescriptor();
        }

        if ( establishedType != null ) {
            node.setTypeDescriptor(establishedType);
        }

        return node;
    }

    @Override
    public Node visitSet(SetExpr node) {
        node.setTypeDescriptor(Set.class);
        return node;
    }

    @Override
    public Node visitStringLiteral(StringLiteralExpr node) {
        node.setTypeDescriptor(String.class);
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

    private Class<?> determineTypeOfReference(ReferenceType node) {
        //--------------------------------------------------------------------------------------------------------------
        // Find matching primitive types
        //--------------------------------------------------------------------------------------------------------------
        if ( node.getReferredType() instanceof PrimitiveType) {
            return node.getReferredType().getTypeDescriptor();
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
            context.getMessages().addError(TYPE_NOT_FOUND.withParameters(node.getReferredType().getName().getElement()));
        } else if ( matchingTypesImportedOnDemand.size() > 1
                    || matchingTypesImportedImplicitly.size() > 1 ) {
            context.getMessages().addError(TYPE_AMBIGUOUS.withParameters(
                    node.getReferredType().getName().getElement(),
                    matchingTypesImportedOnDemand.size(),
                    matchingTypesImportedOnDemand));
        }

        return null;
    }

    private List<Class<?>> findTypesMatchingImplicitImports(ReferenceType referenceType) {
        final NamePath typeReferenceName = referenceType.getReferredType().getName();
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

    private List<Class<?>> findTypesMatchingOnDemandImports(ReferenceType referenceType) {
        if ( importDecl == null ) return Collections.emptyList();

        final NamePath typeReferenceName = referenceType.getReferredType().getName();

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

    private List<Class<?>> findTypesMatchingExplicitImports(ReferenceType referenceType) {
        if ( importDecl == null ) return Collections.emptyList();

        final NamePath typeReferenceName = referenceType.getReferredType().getName();

        List<Class<?>> matchingTypes = new ArrayList<>();
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);

            if ( importStmt.isOnDemand() ) continue;

            String importedTypeFqn = asDelimitedString(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()), ".");

            Class<?> importedType = TypeUtil.loadClassOrNull(importedTypeFqn);
            if (importedType != null) matchingTypes.add(importedType);
        }

        return matchingTypes;
    }

    @Override
    public Node visitTypeReference(TypeReference node) {
        return node;
    }

    @Override
    public Node visitTypeConversion(TypeConversion node) {
        return node;
    }

    @Override
    public Node visitUnaryExpression(UnaryExpression node) {
        return node;
    }
}
