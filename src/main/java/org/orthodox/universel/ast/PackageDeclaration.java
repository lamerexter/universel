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

package org.orthodox.universel.ast;

import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.core.models.path.SimpleNamePath;
import org.orthodox.universel.cst.Name;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PackageDeclaration extends Node {
    private final NamePath name;

    public PackageDeclaration(TokenImage tokenImage, List<Name> nameElements) {
        super(tokenImage);
        this.name = new SimpleNamePath(nameElements.stream().map(Name::getName).collect(Collectors.toList()));
    }

    public NamePath getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PackageDeclaration)) return false;
        if (!super.equals(o)) return false;
        PackageDeclaration that = (PackageDeclaration) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public PackageDeclaration accept(UniversalCodeVisitor visitor) {
        return visitor.visitPackageDeclaration(this);
    }
}
