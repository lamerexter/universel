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

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.cst.TokenImage;

import java.util.Objects;

/**
 * AST representation of a wildcard type.
 * 
 * @author Gary Watson
 */
public final class WildcardType extends TypeReference {
   /** The extension referenced type which bounds this type. */
   private final TypeReference ext;

   /** The superclass referenced type which bounds this type. */
   private final TypeReference sup;

   public WildcardType(TokenImage tokenImage, TypeReference ext, TypeReference sup) {
       super(tokenImage);
       assert ext == null || sup == null;
       this.ext = ext;
       this.sup = sup;
   }

   /**
    * Gets the extension referenced type which bounds this type.he extension referenced type which bounds this type.
    * 
    * @return the extension referenced type which bounds this type.
    */
   public TypeReference getExtends() {
       return ext;
   }

   /**
    * Gets the superclass referenced type which bounds this type.
    * 
    * @return the superclass referenced type which bounds this type.
    */
   public TypeReference getSuper() {
       return sup;
   }

    /**
     * Gets the fully qualified name of the type including any package name prefix, such as <code>java.lang.String</code>
     *
     * @return the fully qualified name of the type.
     */
    @Override
    public NamePath getName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the arity of dimensions for an array type reference.
     *
     * @return the arity of dimensions for an array type reference. which may be zero if the type referred to is not an array.
     */
    @Override
    public int getDimensions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WildcardType)) return false;
        if (!super.equals(o)) return false;
        WildcardType that = (WildcardType) o;
        return Objects.equals(ext, that.ext) &&
               Objects.equals(sup, that.sup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ext, sup);
    }
}
