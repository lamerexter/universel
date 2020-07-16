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

package org.orthodox.universel.symanticanalysis.name;

import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.ast.conditionals.IfStatement;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Type;
import org.orthodox.universel.cst.literals.NullLiteralExpr;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;

import java.util.List;
import java.util.stream.Collectors;

public class IfStatementImplicitResultValueResolver extends AbstractSemanticAnalyser {

    @Override
    public IfStatement visitIfStatement(final IfStatement node) {
        final IfStatement transformedIfStatement = super.visitIfStatement(node);

        //--------------------------------------------------------------------------------------------------------------
        // Deal with empty then/else-if/else parts (i.e. {} was specified) where result must be null.
        //--------------------------------------------------------------------------------------------------------------
        Node transformedThen = transformEmptyOrNullStatementOrBlock(transformedIfStatement, transformedIfStatement.getThenExpression());
        List<IfStatement.ElseIf> transformedElseIfs = transformedIfStatement.getElseIfExpressions() == null ? null
                                                                                                            : transformedIfStatement.getElseIfExpressions()
                .stream()
                .map(eif -> new IfStatement.ElseIf(eif.getTokenImage(),
                                                   eif.getTestExpression(),
                                                   transformEmptyOrNullStatementOrBlock(transformedIfStatement, eif.getBody()))
                )
                .collect(Collectors.toList());
        Node transformedElse = transformEmptyOrNullStatementOrBlock(transformedIfStatement, transformedIfStatement.getElseExpression());

        return new IfStatement(transformedIfStatement.getTokenImage(),
                               transformedIfStatement.getTestExpression(),
                               transformedThen, transformedElseIfs, transformedElse);
    }

    private Node transformEmptyOrNullStatementOrBlock(final IfStatement enclosingStatement, final Node statementOrBlock) {
        Node transformedNode = statementOrBlock;
        if ((transformedNode instanceof NodeSequence && ((NodeSequence)transformedNode).isEmpty())
            || transformedNode == null) {
            transformedNode = new NullLiteralExpr(enclosingStatement.getTokenImage(), enclosingStatement.getType());
        }

        return transformedNode;
    }
}
