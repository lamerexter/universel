package org.orthodox.universel.ast.literals;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.UniversalCodeVisitor;

import java.util.Collections;
import java.util.List;

/**
 * An interpolated string literal on the Abstract Syntax Tree.
 */
public class InterpolatedStringLiteralExpr extends Expression {
    /** The parts which comprise this interpolated string. */
    private List<Node> parts;

    /**
     * Consructs an interpolated string literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public InterpolatedStringLiteralExpr(TokenImage tokenImage, List<Node> parts) {
        super(tokenImage);
        this.parts = parts;
    }

    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    public boolean accept(UniversalCodeVisitor visitor) {
        return visitor.visitInterpolatedStringLiteral(this);
    }

    /**
     * Gets the parts which comprise this interpolated string.
     *
     * @return the parts which comprise this interpolated string, which may be empty;
     */
    public List<Node> getParts() {
        return parts == null ? Collections.emptyList() : parts;
    }
}
