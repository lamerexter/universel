package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * A 'big' integer literal on the Abstract Syntax Tree, as per {@link java.math.BigInteger}.
 */
public class BigIntegerLiteralExpr extends Expression {
    /**
     * Consructs a new integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public BigIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
