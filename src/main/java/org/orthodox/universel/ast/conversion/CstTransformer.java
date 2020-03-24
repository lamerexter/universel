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

package org.orthodox.universel.ast.conversion;

import org.orthodox.universel.ast.AstVisitor;
import org.orthodox.universel.ast.AstVisitorAdapter;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     Converts the Concrete Syntax Tree (CST) to the Abstract Syntax Tree (AST).
 * </p>
 * <p>
 *     Delegates to a number of registered transformers, iterating over the CST until no further changes are
 *     made to the tree. That is, tends towards an AST through iterative application of a number of specific trabsformers.
 * </p>
 */
public class CstTransformer extends AstVisitorAdapter {
    private static final List<AstVisitor> cstTransformers = Arrays.asList(
//        new UnaryMinusLiteralTransformer()
    );

    @Override
    public Node visitScript(Script node) {
        List<Node> newBodyElements = new ArrayList<>(node.getBodyElements());
        for (int n=0; n < newBodyElements.size(); n++) {
            Node bodyElement = transform(newBodyElements.get(n));
            newBodyElements.set(n, bodyElement);
        }
        return new Script(node.getImportDeclaration(), newBodyElements);
    }

    private Node transform(Node node) {
        Node newNode = node;
        for (AstVisitor transformer : cstTransformers) {
            newNode = node.accept(transformer);
        }

        return newNode;
    }
}
