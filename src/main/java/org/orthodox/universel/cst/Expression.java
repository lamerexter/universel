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

    /**
     * Constructs an expression from the given parser token image and type.
     *
     * @param tokenImage the parser token image backing this node.
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     */
    public Expression(TokenImage tokenImage, Class<?> typeDescriptor) {
        super(tokenImage, typeDescriptor);
    }

}
