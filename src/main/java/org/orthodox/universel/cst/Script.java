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

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.AstVisitor;
import org.orthodox.universel.ast.PackageDeclaration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static org.beanplanet.core.util.ArrayUtil.asListOfNotNull;
import static org.orthodox.universel.cst.TokenImage.range;

/**
 * The top-level goal symbol of the Universel Expression Language, equivalent to Java's Compilation Unit - but so
 * much more.
 *
 * @author Gary Watson
 */
public class Script extends Node implements CompositeNode {
    private final PackageDeclaration packageDeclaration;
    private final ImportDecl importDeclaration;
    private final List<Node> bodyElements;

    public Script(Node ... bodyElements) {
        this(null, null, asList(bodyElements));
    }

    public Script(ImportDecl importDecl, List<Node> bodyElements) {
        this(null, importDecl, bodyElements);
    }

    public Script(PackageDeclaration packageDeclaration, ImportDecl importDecl, List<Node> bodyElements) {
        super(range(packageDeclaration, importDecl, bodyElements));
        this.packageDeclaration = packageDeclaration;
        this.importDeclaration = importDecl;
        this.bodyElements = bodyElements;
    }

    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }

    public ImportDecl getImportDeclaration() {
        return importDeclaration;
    }

    public List<Node> getBodyElements() {
        return bodyElements == null ? Collections.emptyList() : bodyElements;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitScript(this);
    }

    public Node accept(AstVisitor visitor) {
        return visitor.visitScript(this);
    }

    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder().addNotNull(packageDeclaration).addNotNull(importDeclaration).addAllNotNull(getBodyElements()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Script))
            return false;
        if (!super.equals(o))
            return false;
        Script that = (Script) o;
        return Objects.equals(packageDeclaration, that.packageDeclaration)
               && Objects.equals(importDeclaration, that.importDeclaration)
               && Objects.equals(bodyElements, that.bodyElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), packageDeclaration, importDeclaration, bodyElements);
    }
}
