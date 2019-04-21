package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * A boolean literal (<i>true</i> or <i>false</i>) on the Abstract Syntax Tree.
 */
public class BooleanLiteralExpr extends Expression implements IntegerLiteral {
    /**
     * Consructs a new boolean literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public BooleanLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}
