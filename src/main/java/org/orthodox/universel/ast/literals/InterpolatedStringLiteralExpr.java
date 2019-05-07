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
    /** The delimiter of this string. */
    private String delimeter = "";

    /** The parts which comprise this interpolated string. */
    private List<Node> parts;

    /**
     * Consructs an interpolated string literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     * @param delimiter the delimiter of this string
     */
    public InterpolatedStringLiteralExpr(TokenImage tokenImage, List<Node> parts, String delimiter) {
        super(tokenImage);
        this.parts = parts;
        this.delimeter = delimiter;
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

    /**
     * Gets the delimiter of this string, for example single or triple single/double quotes (i.e. <code>"simple string with ${ 'interpolation' }"</code> or
     * <code>"""multi-line string with ${ 'interpolation}"""</code>).
     * @return
     */
    public String getDelimeter() {
        return delimeter;
    }

    public String getUndelimitedTokenImage() {
        return getTokenImage() == null ? null : getTokenImage().getImage().substring(getDelimeter().length(), getTokenImage().getImage().length()-getDelimeter().length());
    }
}
