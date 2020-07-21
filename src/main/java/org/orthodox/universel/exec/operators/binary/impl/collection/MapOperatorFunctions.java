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

package org.orthodox.universel.exec.operators.binary.impl.collection;

import org.orthodox.universel.exec.operators.binary.BinaryOperator;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static org.orthodox.universel.ast.Operator.CONTAINS;
import static org.orthodox.universel.ast.Operator.IN;

@BinaryOperator
public class MapOperatorFunctions {
    @BinaryOperator(CONTAINS)
    public static <K, V> boolean containsMap(final Map<K, V> lhs, final Map<K, V> rhs) {
        return rhs.entrySet().stream().allMatch(e -> Objects.equals(lhs.get(e.getKey()), e.getValue()));
    }

    @BinaryOperator(CONTAINS)
    public static <K, V> boolean containsCollectionOfKeys(final Map<K, V> lhs, final Collection<K> rhs) {
        return rhs.stream().allMatch(lhs::containsKey);
    }

    @BinaryOperator(CONTAINS)
    public static <K, V> boolean containsSingleKey(final Map<K, V> lhs, final K rhs) {
        return lhs.containsKey(rhs);
    }

    @BinaryOperator(IN)
    public static <K, V> boolean mapInMap(final Map<K, V> lhs, final Map<K, V> rhs) {
        return lhs.entrySet().stream().allMatch(e -> Objects.equals(rhs.get(e.getKey()), e.getValue()));
    }

    @BinaryOperator(IN)
    public static <K, V> boolean collectionOfKeysInMap(final Collection<K> lhs, final Map<K, V> rhs) {
        return lhs.stream().allMatch(rhs::containsKey);
    }

    @BinaryOperator(IN)
    public static <K, V> boolean singleKeyInMap(final K lhs, final Map<K, V> rhs) {
        return rhs.containsKey(lhs);
    }
}
