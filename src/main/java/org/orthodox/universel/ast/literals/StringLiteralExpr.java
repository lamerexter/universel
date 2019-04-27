package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.UniversalCodeVisitor;

/**
 * A string literal on the Abstract Syntax Tree.
 */
public class StringLiteralExpr extends Expression {
    /**
     * Consructs a new string literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public StringLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitStringLiteral(this);
    }
}
