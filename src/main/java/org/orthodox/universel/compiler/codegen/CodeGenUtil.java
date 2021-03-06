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

package org.orthodox.universel.compiler.codegen;

import org.beanplanet.core.models.path.NamePath;
import org.objectweb.asm.Type;
import org.orthodox.universel.ast.type.reference.TypeReference;

public class CodeGenUtil {
    public static final Type[] EMPTY_TYPES = {};
    public static String internalName(TypeReference typeReference) {
        return internalName(typeReference.getName());
    }

    public static String internalName(NamePath namePath) {
        return namePath.join("/");
    }

    public static String internalName(Class<?> type) {
        return Type.getInternalName(type);
    }

    public static String descriptor(TypeReference typeReference) {
        return descriptor(typeReference.getTypeDescriptor());
    }

    public static String descriptor(Class<?> type) {
        return Type.getDescriptor(type);
    }
}
