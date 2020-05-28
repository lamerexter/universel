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

package org.orthodox.universel.cst.type.reference;

import org.beanplanet.core.models.path.SimpleNamePath;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.type.declaration.TypeDeclaration;

import java.util.Collections;
import java.util.List;

/**
 * AST representation of a class or interface type.
 *
 * @author Gary Watson
 */
public final class ClassOrInterfaceType extends TypeReference {

    /**
     * The enclosing scope of the type, for example java.util.Map would be the scope in java.util.Map.Entry.
     */
    private ClassOrInterfaceType scope;

    private String name;

    private List<TypeReference> typeArgs;

    private TypeDeclaration typeDeclaration;

    public ClassOrInterfaceType(TokenImage tokenImage, ClassOrInterfaceType scope, String name, List<TypeReference> typeArgs) {
        super(tokenImage, new SimpleNamePath(Collections.singletonList(name)));
        this.scope = scope;
        this.name = name;
        this.typeArgs = typeArgs;
    }

    public ClassOrInterfaceType getScope() {
        return scope;
    }

    public List<TypeReference> getTypeArgs() {
        return typeArgs;
    }

    public String getRawTypeName() {
        if (scope == null) {
            return name;
        }

        return scope.getRawTypeName() + "." + name;
    }

    public TypeDeclaration getTypeDeclaration() {
        return typeDeclaration;
    }

    public void setTypeDeclaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }
}
