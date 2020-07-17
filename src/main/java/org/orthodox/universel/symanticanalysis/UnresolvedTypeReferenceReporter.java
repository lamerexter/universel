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

import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.compiler.Messages;
import org.orthodox.universel.cst.ArrayTypeImpl;
import org.orthodox.universel.cst.type.reference.ReferenceType;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Objects;

import static org.orthodox.universel.compiler.Messages.NAME.NAME_NOT_RESOLVED;

/**
 * Iterates over the AST, performing a depth-first post-order traversal to establish the types on the AST. Essentially,
 * known types flow upwards from the leaves on the tree, forming a type tree from the ground up.
 *
 * <p>This implementation is extremely lenient in that it ignores types which cannot be resolved and leaves them
 * for possible future resolution or error reporting.</p>
 *
 * <ul>
 * <li>{@link ReferenceType}
 * </ul>
 */
public class UnresolvedTypeReferenceReporter extends AbstractSemanticAnalyser implements SemanticAnalyser {

    @Override
    public TypeReference visitTypeReference(TypeReference typeReference) {
        //--------------------------------------------------------------------------------------------------------------
        // If already resolved, simply ignore it.
        //--------------------------------------------------------------------------------------------------------------
        if (typeReference instanceof ResolvedTypeReference) return typeReference;

        //--------------------------------------------------------------------------------------------------------------
        // Not resolved so report it.
        //--------------------------------------------------------------------------------------------------------------
        getContext().addError(Messages.TYPE.TYPE_NOT_FOUND
                                  .relatedObject(typeReference)
                                  .withParameters(typeReference.getName().join(".")));

        return typeReference;
    }
}
