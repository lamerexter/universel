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

import org.orthodox.universel.ast.AssignmentExpression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationAxisAndNodeTest;
import org.orthodox.universel.ast.navigation.NavigationExpression;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.type.declaration.FieldWrite;

import java.util.Objects;

import static org.orthodox.universel.compiler.TransformationUtil.autoBoxOrPromoteIfNecessary;
import static org.orthodox.universel.compiler.TransformationUtil.isAssignableFrom;

public class AssignmentAnalyser extends AbstractSemanticAnalyser implements SemanticAnalyser {
    @Override
    public Node visitAssignment(AssignmentExpression node) {
        if (isSimpleAssignment(node)) return simpleNameAssignment(node);

        return node;
    }

    private boolean isSimpleAssignment(final AssignmentExpression node) {
        if (!(node.getLhsExpression() instanceof NavigationExpression)) return false;

        NavigationExpression lhsExpression = (NavigationExpression) node.getLhsExpression();
        if (!(lhsExpression.getSteps().size() == 1)) return false;
        if (!(lhsExpression.getSteps().get(0) instanceof NavigationStep)) return false;

        if (!(((NavigationStep) lhsExpression.getSteps().get(0)).getNavigationAxisAndNodeTest().getNodeTest() instanceof NameTest)) return false;

        return true;
    }

    @SuppressWarnings("unchecked")
    private Node simpleNameAssignment(final AssignmentExpression node) {
        NavigationStep<NameTest> nameTestStep = ((NavigationStep<NameTest>) ((NavigationStep) ((NavigationExpression) node.getLhsExpression()).getChildNodes().get(0)));

        return assignTransform(node, node.getRhsExpression(), nameTestStep.getNavigationAxisAndNodeTest());
    }

    private Node assignTransform(final AssignmentExpression assignmentExpression, final Node rValue, final NavigationAxisAndNodeTest<?> navigationAxisAndNodeTest) {
        return getContext().scopes()
                           .map(s -> s.assign(rValue, navigationAxisAndNodeTest))
                           .filter(Objects::nonNull)
                           .findFirst().orElse(assignmentExpression);
    }


    /**
     * TODO: Is this the right place for this? A field write operation was as a consequence of assignment, but the RHS type was not known at the time - but perhaps
     * now it is...
     *
     * @param node the field write (assignment) operation as a consequence of an assignment
     * @return the field write operation, with the field value possibly autoboxed or promoted in accordance with the field declared type.
     */
    @Override
    public Node visitFieldAccess(final FieldWrite node) {
        if (node.getFieldValue() == null || isAssignableFrom(node.getFieldType(), node.getFieldValue().getType())) return node;

        if (node.getFieldValue() == null
            || node.getFieldValue().getTypeDescriptor() == null
            || node.getFieldType() == null
            || node.getFieldType().getTypeClass() == null) return node;

        Node transformedRhs = autoBoxOrPromoteIfNecessary(node.getFieldValue(), node.getFieldType().getTypeClass());
        boolean noTransformationChanges = Objects.equals(transformedRhs, node.getFieldValue());
        return noTransformationChanges ? node : new FieldWrite(node.getTokenImage(),
                                                               node.isStatic(),
                                                               node.getDeclaringType(),
                                                               node.getFieldType(),
                                                               node.getFieldName(),
                                                               transformedRhs
        );
    }
}
