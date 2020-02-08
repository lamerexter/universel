/*
 *  MIT Licence:
 *
 *  Copyright (c) 2018 Orthodox Engineering Ltd
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
package org.orthodox.universel.cst;

import org.orthodox.universel.ast.AstVisitor;

import java.util.List;

import static java.util.Arrays.asList;
import static org.orthodox.universel.cst.TokenImage.range;

/**
 * The top-level goal symbol of the Universel Expression Language, equivalent to Java's Compilation Unit - but so
 * much more.
 *
 * @author Gary Watson
 */
public class Script extends Node implements CompositeNode {
    private final ImportDecl importDeclaration;
    private final List<Node> bodyElements;

    public Script() {
        this.importDeclaration = null;
        this.bodyElements = null;

    }

    public Script(Node ... bodyElements) {
        super(range(asList(bodyElements)));
        this.importDeclaration = null;
        this.bodyElements = asList(bodyElements);
    }

    public Script(ImportDecl importDecl, List<Node> bodyElements) {
        super(range(importDecl, bodyElements));
        this.importDeclaration = importDecl;
        this.bodyElements = bodyElements;
    }

    public ImportDecl getImportDeclaration() {
        return importDeclaration;
    }

    public List<Node> getBodyElements() {
        return bodyElements;
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitScript(this);
    }

    public Node accept(AstVisitor visitor) {
        return visitor.visitScript(this);
    }

    @Override
    public List<Node> getChildNodes() {
        return getBodyElements();
    }
}
