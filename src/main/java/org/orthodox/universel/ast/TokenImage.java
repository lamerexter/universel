package org.orthodox.universel.ast;

import org.beanplanet.core.util.PropertyBasedToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Represents the token, its position in the input stream and the string image of the token itself.
 */
public class TokenImage {
    /** The line the token image of this node begins. */
    private int startLine;
    /** The column the token image of this node begins. */
    private int startColumn;
    /** The line the token image of this node ends. */
    private int endLine;
    /** The column the token image of this node ends. */
    private int endColumn;

    /** The string image of the parsed token. */
    private String image;

    public static TokenImage range(Node lhs, List<Node> rhs) {
        if (lhs != null && rhs != null)
            return range(Stream.concat(singletonList(lhs).stream(), rhs.stream()).collect(Collectors.toList()));
        else if (lhs == null)
            return range(rhs);

        return range(Collections.singletonList(lhs));
    }

    public static TokenImage range(List<? extends Node> nodeList) {
        if ( nodeList == null) return null;

        boolean first = true;
        int startLine = -1, startColumn = -1, endLine = -1, endColumn = -1;
        for (Node node : nodeList) {
            if (node == null || node.getTokenImage() == null) continue;

            startLine   = first || node.getTokenImage().getStartLine() < startLine ? node.getTokenImage().getStartLine() : startLine;
            startColumn = first || node.getTokenImage().getStartColumn() < startColumn ? node.getTokenImage().getStartColumn() : startColumn;
            endLine     = first || node.getTokenImage().getEndLine() < endLine ? node.getTokenImage().getEndLine() : endLine;
            endColumn = first || node.getTokenImage().getEndColumn() < endColumn ? node.getTokenImage().getEndColumn() : endColumn;
            first = false;
        }

        return new TokenImage(startLine, startColumn, endLine, endColumn);
    }

    /**
     * Constructs a new token image, with no initial configuration.
     */
    public TokenImage() {}

    /**
     * Constructs a new token image, with image.
     *
     * @param image the parsed token image.
     */
    public TokenImage(String image) {
        this.image = image;
    }

    /**
     * Constructs a new token image, with parse position and image.
     *
     * @param startLine the line the token image of this node begins.
     * @param startColumn te column the token image of this node begins.
     * @param endLine the line the token image of this node ends.
     * @param endColumn the column the token image of this node ends.
     */
    public TokenImage(int startLine, int startColumn, int endLine, int endColumn) {
        this(startLine, startColumn, endLine, endColumn, null);
    }

    /**
     * Constructs a new token image, with parse position and image.
     *
     * @param startLine the line the token image of this node begins.
     * @param startColumn te column the token image of this node begins.
     * @param endLine the line the token image of this node ends.
     * @param endColumn the column the token image of this node ends.
     * @param image the parsed token image.
     */
    public TokenImage(int startLine, int startColumn, int endLine, int endColumn, String image) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
        this.image = image;
    }

    /**
     * Gets the line the token image of this node begins.
     *
     * @return the line the token image of this node begins.
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * Sets the line the token image of this node begins.
     *
     * @param startLine the line the token image of this node begins.
     */
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    /**
     * Sets the line the token image of this node begins.
     *
     * @param startLine the line the token image of this node begins.
     * @return this instance for builder chaining.
     */
    public TokenImage withStartLine(int startLine) {
        setStartLine(startLine);
        return this;
    }

    /**
     * Gets the column the token image of this node begins.
     *
     * @return the column the token image of this node begins.
     */
    public int getStartColumn() {
        return startColumn;
    }

    /**
     * Sets the column the token image of this node begins.
     *
     * @param startColumn the column the token image of this node begins.
     */
    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    /**
     * Sets the column the token image of this node begins.
     *
     * @param startColumn the column the token image of this node begins.
     * @return this instance for builder chaining.
     */
    public TokenImage withStartColumn(int startColumn) {
        setStartColumn(startColumn);
        return this;
    }

    /**
     * Gets the line the token image of this node ends.
     *
     * @return the line the token image of this node ends.
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * Sets the line the token image of this node ends.
     *
     * @param endLine the line the token image of this node ends.
     */
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    /**
     * Sets the line the token image of this node ends.
     *
     * @param endLine the line the token image of this node ends.
     * @return this instance for builder chaining.
     */
    public TokenImage withEndLine(int endLine) {
        setEndLine(endLine);
        return this;
    }

    /**
     * Gets the column the token image of this node ends.
     *
     * @return the column the token image of this node ends.
     */
    public int getEndColumn() {
        return endColumn;
    }

    /**
     * Sets the column the token image of this node ends.
     *
     * @param endColumn the column the token image of this node ends.
     */
    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    /**
     * Sets the column the token image of this node ends.
     *
     * @param endColumn the column the token image of this node ends.
     * @return this instance for builder chaining.
     */
    public TokenImage withEndColumn(int endColumn) {
        setEndColumn(endColumn);
        return this;
    }

    /**
     * Gets the string image of the parsed token.
     *
     * @return the parsed token image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the strinng image of the parsed token.
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets the string image of the parsed token.
     *
     * @param image the image to set
     * @return this instance for builder chaining.
     */
    public TokenImage withImage(String image) {
        setImage(image);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenImage)) return false;
        TokenImage that = (TokenImage) o;
        return getStartLine() == that.getStartLine() &&
                getStartColumn() == that.getStartColumn() &&
                getEndLine() == that.getEndLine() &&
                getEndColumn() == that.getEndColumn() &&
                Objects.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartLine(), getStartColumn(), getEndLine(), getEndColumn(), getImage());
    }

    /**
     * Returns a useful string representation of this instance.
     *
     * @return a string representing this instance.
     */
    public String toString() {
        return new PropertyBasedToStringBuilder(this).build();
    }
}
