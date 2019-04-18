package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * A decimal integer literal on the Abstract Syntax Tree.
 */
public class DecimalIntegerLiteralExpr extends Expression implements IntegerLiteral {
    /**
     * Consructs a new decimal integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
