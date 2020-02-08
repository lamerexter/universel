package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;

/**
 * An hexadecimal integer literal on the Abstract Syntax Tree.
 */
public class HexadecimalIntegerLiteralExpr extends Expression {
    /**
     * Consructs a new hexadecimal integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public HexadecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public Class<?> getTypeDescriptor() {
        return long.class;
    }
}
