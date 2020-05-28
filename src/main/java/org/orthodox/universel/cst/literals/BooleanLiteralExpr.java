package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

/**
 * A boolean literal (<i>true</i> or <i>false</i>) on the Abstract Syntax Tree.
 */
public class BooleanLiteralExpr extends Expression {
    /**
     * Consructs a new boolean literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public BooleanLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public boolean getBooleanValue() {
        return Boolean.parseBoolean(getTokenImage().getImage().trim());
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitBooleanLiteral(this);
    }

    public Class<?> getTypeDescriptor() {
        return boolean.class;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ( !(o instanceof BooleanLiteralExpr) ) return false;
        return super.equals(o);
    }
}
