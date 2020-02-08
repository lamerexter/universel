package org.orthodox.universel.cst;

/**
 * The superclass of all expressions on the Abstract Syntax Tree.
 */
public class Expression extends Node {
    /**
     * Constructs an expression from the given parser token image.
     *
     * @param tokenImage the parser token image backing this expression.
     */
    public Expression(TokenImage tokenImage) {
        super(tokenImage);
    }
}
