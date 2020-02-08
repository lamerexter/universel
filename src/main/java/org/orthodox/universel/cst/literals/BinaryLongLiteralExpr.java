package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;

/**
 * A binary integer literal on the Abstract Syntax Tree.
 */
public class BinaryLongLiteralExpr extends Expression {
    /**
     * Constructs a new binary integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public BinaryLongLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public Class<?> getTypeDescriptor() {
        return long.class;
    }
}
