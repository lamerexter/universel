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

import java.util.Collections;

import static org.beanplanet.core.util.StringUtil.initCap;


/**
 * AST representation of a primitive type.
 * 
 * @author Gary Watson
 */
public final class PrimitiveType extends TypeReference {

   public enum Primitive {
       Boolean(boolean.class), Byte(byte.class), Char(char.class), Double(double.class), Float(float.class), Int(int.class), Long(long.class), Short(short.class);

       private final Class<?> typeDescriptor;
       
       Primitive(Class<?> primitiveClass) {
          this.typeDescriptor = primitiveClass;
       }
       
       public static Primitive valueOfTypeName(String typeName) {
          return Primitive.valueOf(initCap(typeName, true));
       }
       
       public Class<?> getTypeDescriptor() {
          return typeDescriptor;
       }
   }

   private final Primitive primitive;

   public PrimitiveType(TokenImage tokenImage, Primitive primitive) {
       super(tokenImage, primitive.getTypeDescriptor(), new SimpleNamePath(Collections.singletonList(primitive.getTypeDescriptor().getSimpleName())));
       this.primitive = primitive;
//       this.typeDescriptor = primitive.getTypeDescriptor();
   }

    /**
     * Whether this type reference is to a primitive type.
     *
     * @return true if the type referred to is a primitive type, false otherwise.
     */
    @Override
    public boolean isPrimitiveType() {
        return true;
    }

    public Primitive getPrimitive() {
       return primitive;
   }

    @Override
    public Class<?> getTypeDescriptor() {
        return getPrimitive().getTypeDescriptor();
    }
}
