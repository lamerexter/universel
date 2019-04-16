package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * An long integer literal on the Abstract Syntax Tree.
 */
public class LongLiteralExpr extends Expression {
    /**
     * Consructs a new long literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public LongLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
