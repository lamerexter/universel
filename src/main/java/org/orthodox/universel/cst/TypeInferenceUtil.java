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

package org.orthodox.universel.cst;

import org.beanplanet.core.lang.TypeUtil;
import org.orthodox.universel.UniversalException;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.beanplanet.core.lang.TypeUtil.determineCommonSuperclass;
import static org.beanplanet.core.util.ObjectUtil.nvl;

public class TypeInferenceUtil {
    public static Type resolveType(java.lang.reflect.Type fromType) {
        if ( fromType instanceof Class ) {
            return new ResolvedTypeReferenceOld(((Class<?>)fromType));
        } else if ( fromType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)fromType;
            return new ParameterisedTypeImpl(resolveType(pt.getRawType()), resolveTypes(pt.getActualTypeArguments()));
        } else if ( fromType instanceof java.lang.reflect.WildcardType) {
            java.lang.reflect.WildcardType wt = (java.lang.reflect.WildcardType)fromType;
            return new WildcardTypeImpl(resolveTypes(wt.getUpperBounds()), resolveTypes(wt.getLowerBounds()));
        } else if ( fromType instanceof GenericArrayType ) {
            GenericArrayType gat = (GenericArrayType)fromType;
            return new ArrayTypeImpl(resolveType(gat.getGenericComponentType()));
        }

        throw new UniversalException(format("Unknown type found [%s] when resolving type information", fromType.getClass().getName()));
    }

    private static List<Type> resolveTypes(java.lang.reflect.Type[] fromTypes) {
        return stream(fromTypes)
                   .map(TypeInferenceUtil::resolveType)
                   .collect(Collectors.toList());
    }

    public static Type determineCommonSuperclass(Set<Type> types) {
        // Base common type on real class types, for now.
        final Class<?>[] actualClassTypes = types.stream().map(Type::getTypeClass).toArray(Class[]::new);
        final Class<?> commonClass = TypeUtil.determineCommonSuperclass(actualClassTypes);
        return commonClass == null ? null : types.stream()
                    .filter(t -> Objects.equals(t.getTypeClass(), commonClass))
                    .findFirst()
                    .orElse(new ResolvedTypeReferenceOld(commonClass));
    }
}
