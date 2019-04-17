package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * An hexadecimal literal on the Abstract Syntax Tree.
 */
public class HexadecimalLongLiteralExpr extends Expression {
    /**
     * Consructs a new hexadecimal literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public HexadecimalLongLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
