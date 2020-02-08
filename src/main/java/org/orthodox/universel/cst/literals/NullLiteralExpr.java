package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import javax.lang.model.type.NullType;

/**
 * A null literal (<i>null</i>) on the Abstract Syntax Tree.
 */
public class NullLiteralExpr extends Expression implements IntegerLiteral {
    /**
     * Consructs a new null literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public NullLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitNullLiteral(this);
    }

    public Class<?> getTypeDescriptor() {
        return NullType.class;
    }
}
