package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

/**
 * An octal literal on the Abstract Syntax Tree.
 */
public class OctalLongLiteralExpr extends Expression {
    /**
     * Consructs a new octal literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public OctalLongLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }
}