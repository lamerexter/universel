package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * A binary integer literal on the Abstract Syntax Tree, as per {@link java.math.BigInteger}.
 */
public class BinaryLiteralExpr extends Expression implements IntegerLiteral {
    /**
     * Consructs a new binary integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public BinaryLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
