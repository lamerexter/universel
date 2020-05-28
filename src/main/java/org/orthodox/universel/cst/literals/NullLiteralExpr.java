package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import javax.lang.model.type.NullType;

/**
 * A null literal (<i>null</i>) on the Abstract Syntax Tree.
 */
public class NullLiteralExpr extends Expression {
    /**
     * Constructs a new null literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public NullLiteralExpr(TokenImage tokenImage) {
        super(tokenImage, NullType.class);
    }

    /**
     * Constructs a new null literal node from the given parser token image and known type information.
     *
     * @param tokenImage the parser token image.
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     */
    public NullLiteralExpr(TokenImage tokenImage, Class<?> typeDescriptor) {
        super(tokenImage, typeDescriptor);
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitNullLiteral(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof NullLiteralExpr) ) return false;
        return super.equals(o);
    }
}
