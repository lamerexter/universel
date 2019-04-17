package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * An integer literal on the Abstract Syntax Tree.
 */
public class DecimalIntegerLiteralExpr extends Expression {
    /**
     * Consructs a new integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public DecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
