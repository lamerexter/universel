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

import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.models.path.DelimitedNamePath;
import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;

import static org.beanplanet.core.lang.TypeUtil.determineArrayBaseComponentType;
import static org.beanplanet.core.lang.TypeUtil.determineArrayDimensions;

/**
 * Represents a resolved referred to type, such as java.lang.String or com.acme.MyClass. The referenced type may be an existing
 * type (such as a compiled class) or a type currently being compiled.
 *
 * <p>The resolved type has come from an unresolved type name, resolved against existing types or types being compiled in the
 * current compilation unit.</p>
 */
public class ResolvedTypeReferenceOld extends TypeReference {
    private final Class<?> typeDescriptor;

    /**
     * Constructs a new type AST resolved type reference instance.
     *
     * @param tokenImage the image representing the resolved type.
     * @param typeDescriptor the name of the referenced type, which may be fully qualified.
     */
    public ResolvedTypeReferenceOld(TokenImage tokenImage, Class<?> typeDescriptor) {
        super(tokenImage);
        this.typeDescriptor = typeDescriptor;
    }

    /**
     * Constructs a new type AST resolved type reference instance.
     *
     * @param typeDescriptor the name of the referenced type, which may be fully qualified.
     */
    public ResolvedTypeReferenceOld(Class<?> typeDescriptor) {
        this(null, typeDescriptor);
    }

    /**
     * Constructs a new type AST resolved type reference instance from the given node.
     *
     * @param node the node whose type will be the referred type.
     */
    public ResolvedTypeReferenceOld(Node node) {
        this(node.getTokenImage(), node.getTypeDescriptor());
    }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        return new DelimitedNamePath(determineArrayBaseComponentType(getTypeDescriptor()).getName(), ".");
    }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    @Override
    public int getDimensions() {
        return determineArrayDimensions(getTypeDescriptor());
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return typeDescriptor;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ResolvedTypeReferenceOld)) return false;
        if (!super.equals(o)) return false;
        ResolvedTypeReferenceOld that = (ResolvedTypeReferenceOld) o;
        return this.getTypeDescriptor() == that.getTypeDescriptor();
    }
}
