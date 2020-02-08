/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

import java.util.List;

/**
 * Represents a type: such as a class, interface or enum type. The type may be an existing type (such as a compiled class) or
 * a type currently under compilation.
 *
 * @author Gary Watson.
 */
public interface Type {
   /**
    * Returns the superclass of this type.
    *
    * @return the superclass of this type. If this type represents an interface, primitive type, Object or void then null is returned. If this
    * type represents an array type then Object is returned.
    */
   public Type getSuperclass();

   /**
    * Returns the fully qualified name of the type, such as <code>a.b.c.D</code>.
    *
    * @return the fully qualified type name, including package prefix, delimited by dot (.).
    */
   public String getFullyQualifiedName();

   /**
    * Returns the fields this type declares.
    *
    * @return a list of the fields declared by this type.
    */
   public List<Field> getDeclaredFields();

   /**
    * Returns whether this type represents an array.
    *
    * @return true if this type is an array type, false otherwise.
    */
   public boolean isArray();

   /**
    * Returns the component type of this array type. Note that the component type may itself be an array in the
    * event this array type represents a multi-dimensional array.
    *
    * @return the component type of this array type or null if this type does not represent an array type.
    */
   public Type getComponentType();
}
