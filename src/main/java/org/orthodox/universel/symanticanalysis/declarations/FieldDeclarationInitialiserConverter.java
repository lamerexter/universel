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

package org.orthodox.universel.symanticanalysis.declarations;

import org.orthodox.universel.ast.FieldDeclaration;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.VariableDeclaration;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.compiler.TransformationUtil;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.orthodox.universel.compiler.TransformationUtil.*;

/**
 * Converts field declaration initialiser expression values to the correct type for assignment.
 */
public class FieldDeclarationInitialiserConverter extends AbstractSemanticAnalyser implements SemanticAnalyser {
    @Override
    public Node visitFieldDeclaration(final FieldDeclaration node) {
        return updateFieldInitialiserExpressions(node);
    }

    private FieldDeclaration updateFieldInitialiserExpressions(final FieldDeclaration node) {
        if ( node.getDeclarationType() == null || node.getDeclarationType().getTypeDescriptor() == null || allFieldinitialisationsHaveCompatibleInitialisers(node) ) return node;

        List<VariableDeclaration> transformedVariableDeclarations = new ArrayList<>(node.getVariableDeclarations().size());
        for (VariableDeclaration vd : node.getVariableDeclarations()) {
            if (vd.getInitialiser() == null || vd.getInitialiser().getType() == null || initialiserIsAssigmentCompatible(node, vd.getInitialiser()) ) {
                transformedVariableDeclarations.add(vd);
                continue;
            }

            final Node transformedInitialiser = applyAppropriateinitialiserTransformation(node.getDeclarationType(), vd.getInitialiser());
            final VariableDeclaration transformedVd = new VariableDeclaration(vd.getId(),
                                                                              initialiserIsAssigmentCompatible(node, transformedInitialiser) ? transformedInitialiser : vd.getInitialiser());
            transformedVariableDeclarations.add(transformedVd);
        }

        boolean noTransformationChanges = Objects.equals(node.getVariableDeclarations(), transformedVariableDeclarations);
        return noTransformationChanges ? node : new FieldDeclaration(node.getModifiers(), node.getDeclarationType(), transformedVariableDeclarations);
    }

    private Node applyAppropriateinitialiserTransformation(final TypeReference declarationType, final Node initialiser) {
        Node transformedinitialiser = initialiser;
        if (autoboxCompatible(declarationType.getTypeClass(), transformedinitialiser.getTypeDescriptor())) {
            transformedinitialiser =  autoBoxIfNecessary(transformedinitialiser, declarationType.getTypeClass());
        }

        if ( promotionCompatible(declarationType.getTypeClass(), transformedinitialiser.getTypeDescriptor()) ) {
            transformedinitialiser =  promoteIfNecessary(transformedinitialiser, declarationType.getTypeClass());
        }

        return transformedinitialiser;
    }

    private boolean initialiserIsAssigmentCompatible(final FieldDeclaration lhs, final Node initialiser) {
        return lhs.getDeclarationType().isAssignableFrom(initialiser.getType());
    }

    private boolean allFieldinitialisationsHaveCompatibleInitialisers(final FieldDeclaration node) {
        return node.getVariableDeclarations().stream().filter(vd -> vd.getInitialiser() != null).allMatch(vd -> initialiserIsAssigmentCompatible(node, vd.getInitialiser()));
    }
}
