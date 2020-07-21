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
import org.orthodox.universel.ast.type.reference.TypeReference;

import java.util.List;
import java.util.Objects;

public class WildcardTypeImpl extends TypeReference implements WildcardType {
    public static final SimpleNamePath WILDCARD_TYPE_NAME = new SimpleNamePath("?");
    private final List<Type> upperBounds;
    private final List<Type> lowerBounds;

    public WildcardTypeImpl(final List<Type> upperBounds, final List<Type> lowerBounds) {
        super(null);
        this.upperBounds = upperBounds;
        this.lowerBounds = lowerBounds;
    }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        return WILDCARD_TYPE_NAME;
    }

    /**
     * Returns a list of {@code Type} objects representing the  upper
     * bound(s) of this type variable.  If no upper bound is
     * explicitly declared, the upper bound is {@code Object}.
     *
     * @return a list of Types representing the upper bound(s) of this type variable, which may be empty.
     */
    @Override
    public List<Type> getUpperBounds() {
        return upperBounds;
    }

    /**
     * Returns a list of {@code Type} objects representing the
     * lower bound(s) of this type variable.  If no lower bound is
     * explicitly declared, the lower bound is the type of {@code null}.
     * In this case, a zero length list is returned.
     *
     * @return list of Types representing the lower bound(s) of this type variable, which may be empty.
     */
    @Override
    public List<Type> getLowerBounds() {
        return lowerBounds;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WildcardTypeImpl)) return false;
        if (!super.equals(o)) return false;
        WildcardTypeImpl that = (WildcardTypeImpl) o;
        return Objects.equals(getUpperBounds(), that.getUpperBounds()) &&
               Objects.equals(getLowerBounds(), that.getLowerBounds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUpperBounds(), getLowerBounds());
    }
}
