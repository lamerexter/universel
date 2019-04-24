package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * A null literal (<i>null</i>) on the Abstract Syntax Tree.
 */
public class NullLiteralExpr extends Expression implements IntegerLiteral {
    /**
     * Consructs a new null literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public NullLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}