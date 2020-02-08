package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;

/**
 * An octal literal on the Abstract Syntax Tree.
 */
public class OctalIntegerLiteralExpr extends Expression {
    /**
     * Consructs a new octal literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public OctalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    public Class<?> getTypeDescriptor() {
        return long.class;
    }
}
