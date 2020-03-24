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

package org.orthodox.universel.symanticanalysis.conversion;

import org.orthodox.universel.ast.AstVisitor;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;
import org.orthodox.universel.cst.literals.NumericLiteral;

import static org.beanplanet.core.lang.TypeUtil.primitiveTypeFor;

public class ConvertedNumericLiteral extends Expression implements NumericLiteral {
    private Number value;
    private NumericPrecision numericPrecision;

    public ConvertedNumericLiteral(TokenImage tokenImage, Number value, NumericPrecision numericPrecision) {
        super(tokenImage);
        this.value = value;
        this.numericPrecision = numericPrecision;
    }
    @Override
    public NumericPrecision getPrecision() {
        return numericPrecision;
    }

    @Override
    public Number getValue() {
        return value;
    }

    public Class<?> getTypeDescriptor() {
        return primitiveTypeFor(value.getClass());
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNumericLiteralExpression(this);
    }

    public Node accept(AstVisitor visitor) {
        return visitor.visitNumericLiteralExpression(this);
    }
}
