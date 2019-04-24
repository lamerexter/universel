package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.UniversalCodeVisitor;

/**
 * A boolean literal (<i>true</i> or <i>false</i>) on the Abstract Syntax Tree.
 */
public class BooleanLiteralExpr extends Expression implements IntegerLiteral {
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
    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitBooleanLiteral(this);
    }
}