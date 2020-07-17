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

import org.beanplanet.core.util.CollectionUtil;
import org.orthodox.universel.cst.ArrayTypeImpl;
import org.orthodox.universel.cst.Type;
import org.orthodox.universel.cst.type.reference.ReferenceType;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.orthodox.universel.compiler.Messages.MethodCall.METHOD_AMBIGUOUS;
import static org.orthodox.universel.compiler.Messages.TYPE.TYPE_AMBIGUOUS;

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
public class TypeReferenceResolver extends AbstractSemanticAnalyser implements SemanticAnalyser {
    private final boolean reportErrors;

    public TypeReferenceResolver() {
        this(false);
    }

    public TypeReferenceResolver(boolean reportErrors) {
        this.reportErrors = reportErrors;
    }
    @Override
    public TypeReference visitTypeReference(TypeReference typeReference) {
        //--------------------------------------------------------------------------------------------------------------
        // If already resolved, simply return it.
        //--------------------------------------------------------------------------------------------------------------
        if (typeReference instanceof ResolvedTypeReference) return typeReference;

        //--------------------------------------------------------------------------------------------------------------
        // If already resolvable (existing class), simply wrap and return it.
        //--------------------------------------------------------------------------------------------------------------
        if ( alreadyResolvable(typeReference) ) {
            return new ResolvedTypeReference(typeReference);
        }

        //--------------------------------------------------------------------------------------------------------------
        // Assume it is a relative type and attempt to resolve it from imports.
        //--------------------------------------------------------------------------------------------------------------
        List<ResolvedTypeReference> resolved = resolveTypeNameInScope(typeReference);
        if ( resolved.size() == 1 ) {
            return resolved.get(0);
        } else if ( resolved.size() > 1 && reportErrors) {
            getContext().getMessages().addError(TYPE_AMBIGUOUS
                                             .relatedObject(typeReference)
                                             .withParameters(
                                                 typeReference.getName().join("."),
                                                 resolved.size(),
                                                 resolved.stream().map(t -> t.getName().join(".")).collect(Collectors.toList())));
        }

        return typeReference;
    }

    private List<ResolvedTypeReference> resolveTypeNameInScope(final TypeReference typeReference) {
        List<Type> resolvedTypes = getContext().scopes()
                               .map(s -> s.resolveType(typeReference.getName()))
                               .filter(CollectionUtil::isNotNullOrEmpty)
                               .findFirst().orElse(Collections.emptyList());

        return resolvedTypes.stream()
                            .map(t -> typeReference.isArray() ? new ArrayTypeImpl(t, typeReference.getDimensions()) : t)
                            .map(t -> new ResolvedTypeReference(typeReference.getTokenImage(), t))
                            .collect(Collectors.toList());
    }

    private boolean alreadyResolvable(final TypeReference type) {
        return type.getTypeDescriptor() != null;
    }
}
