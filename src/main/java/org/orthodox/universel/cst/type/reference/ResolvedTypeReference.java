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

import org.beanplanet.core.models.path.DelimitedNamePath;
import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.cst.TokenImage;

import static org.beanplanet.core.lang.TypeUtil.determineArrayDimensions;

/**
 * Represents a resolved referred to type, such as java.lang.String or com.acme.MyClass. The referenced type may be an existing
 * type (such as a compiled class) or a type currently being compiled.
 *
 * <p>The resolved type has come from an unresolved type name, resolved against existing types or types being compiled in the
 * current compilation unit.</p>
 */
public class ResolvedTypeReference extends TypeReference {
    /**
     * Constructs a new type AST resolved type reference instance.
     *
     * @param tokenImage the image representing the tresolved type.
     * @param typeDescriptor the name of the referenced type, which may be fully qualified.
     * @param name the name of the referenced type, which may be fully qualified.
     * @param dimensions the number of dimensions, if this is is an array type.
     */
    public ResolvedTypeReference(TokenImage tokenImage, Class<?> typeDescriptor, NamePath name, int dimensions) {
        super(tokenImage, typeDescriptor, name, dimensions);
    }

    /**
     * Constructs a new type AST resolved type reference instance.
     *
     * @param tokenImage the image representing the tresolved type.
     * @param typeDescriptor the name of the referenced type, which may be fully qualified.
     */
    public ResolvedTypeReference(TokenImage tokenImage, Class<?> typeDescriptor) {
        super(tokenImage, typeDescriptor, new DelimitedNamePath(typeDescriptor.getName(), "."), determineArrayDimensions(typeDescriptor));
    }
}
