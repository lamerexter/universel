package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * An integer literal on the Abstract Syntax Tree.
 */
public class IntegerLiteralExpr extends Expression {
    /**
     * Consructs a new integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public IntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    /**
     * The canonical form of an integer literal is the literal itself.
     *
     * @return the integer literal.
     */
    public String getCanonicalForm() {
        return getTokenImage() != null ? getTokenImage().getImage() : null;
    }
}
