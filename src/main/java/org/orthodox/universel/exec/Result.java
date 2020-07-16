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

package org.orthodox.universel.exec;

import org.beanplanet.core.lang.conversion.TypeConverter;
import org.beanplanet.core.models.TypeConvertingValue;
import org.orthodox.universel.cst.Type;

import java.util.Objects;

/**
 * Universal extension of the value type, adding the universal type so that callers and tooling can access and
 * subsequently use the exact result type.
 */
public class Result extends TypeConvertingValue implements TypedValue {
    private Type valueType;

    public Result(final TypeConverter typeConverter, final Type type, final Object value) {
        super(typeConverter, value);
        this.valueType = type;
    }

    public Result(final TypeConverter typeConverter, final Type type, final Object value, final Object defaultValue) {
        super(typeConverter, value, defaultValue);
        this.valueType = type;
    }

    @Override
    public Type getValueType() {
        return valueType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        if (!super.equals(o)) return false;
        Result result = (Result) o;
        return Objects.equals(getValueType(), result.getValueType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValueType());
    }
}
