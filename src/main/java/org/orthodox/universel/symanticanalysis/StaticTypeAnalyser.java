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
import org.orthodox.universel.cst.types.PrimitiveType;
import org.orthodox.universel.cst.types.ReferenceType;
import org.orthodox.universel.cst.types.TypeReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.beanplanet.core.util.StringUtil.asDelimitedString;
import static org.orthodox.universel.compiler.Messages.MethodCall.TYPE_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.MethodCall.TYPE_NOT_FOUND;

/**
 * Iterates over the AST, looking for instances of
 *
 * <ul>
 * <li>{@link org.orthodox.universel.cst.types.ReferenceType}
 * </ul>
 *
 * and resolving them to actiual types.
 */
public class StaticTypeAnalyser extends UniversalVisitorAdapter implements SemanticAnalyser {
    private SemanticAnalysisContext context;
    private ImportDecl importDecl;

    @Override
    public void performAnalysis(SemanticAnalysisContext context, Node from) {
        this.context = context;
        from.accept(this);
    }

    @Override
    public boolean visitImportDeclaration(ImportDecl node) {
        this.importDecl = node;
        return true;
    }

    @Override
    public boolean visitReferenceType(ReferenceType node) {
        node.getReferredType().accept(this);

        Class<?> resolvedType = determineTypeOfReference(node);
        node.getReferredType().setTypeDescriptor(resolvedType);

        return true;
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
    public  boolean visitTypeReference(TypeReference node) {
        return true;
    }
}
