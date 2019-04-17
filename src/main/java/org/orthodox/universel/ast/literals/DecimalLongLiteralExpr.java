package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * An long integer literal on the Abstract Syntax Tree.
 */
public class DecimalLongLiteralExpr extends Expression {
    /**
     * Consructs a new long literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalLongLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
