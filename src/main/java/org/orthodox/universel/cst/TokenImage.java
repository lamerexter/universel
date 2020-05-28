package org.orthodox.universel.cst;

import org.beanplanet.core.models.Builder;
import org.beanplanet.core.util.PropertyBasedToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.beanplanet.core.util.CollectionUtil.nullSafe;

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

    public static TokenImage range(final Node node1, final List<? extends Node> nodeList) {
        return builder().add(node1).range(nodeList).build();
    }

    public static TokenImage range(final Node node1, final Node node2, final List<? extends Node> nodeList) {
        return builder().add(node1).add(node2).range(nodeList).build();
    }

    public static TokenImage range(final Node ... nodeList) {
        return builder().range(nodeList).build();
    }

    public static TokenImage range(final List<? extends Node> nodeList) {
        return builder().range(nodeList).build();
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

    public static TokenImageBuilder builder() {
        return new TokenImageBuilder();
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

    public static class TokenImageBuilder implements Builder<TokenImage> {
        /** The line the token image of the node begins. */
        private Integer startLine;
        /** The column the token image of the node begins. */
        private Integer startColumn;
        /** The line the token image of the node ends. */
        private Integer endLine;
        /** The column the token image of the node ends. */
        private Integer endColumn;
        /** The source image. */
        private String image;

        /** Other token images that will be used to build the token image. */
        private List<TokenImage> tokenImages;

        public TokenImageBuilder startLine(int startLine) {
            this.startLine = startLine;
            return this;
        }

        public TokenImageBuilder startColumn(int startColumn) {
            this.startColumn = startColumn;
            return this;
        }

        public TokenImageBuilder endLine(int endLine) {
            this.endLine = endLine;
            return this;
        }

        public TokenImageBuilder endColumn(int endColumn) {
            this.endColumn = endColumn;
            return this;
        }

        public TokenImageBuilder image(String image) {
            this.image = image;
            return this;
        }

        public TokenImageBuilder add(Node child) {
            if ( child == null ) return this;
            return add(child.getTokenImage());
        }

        public TokenImageBuilder range(Node ... children) {
            if ( children == null ) return this;
            return range(stream(children).filter(Objects::nonNull).collect(Collectors.toList()));
        }

        @SuppressWarnings("unchecked")
        public TokenImageBuilder range(List<? extends Node> nodes) {
            if ( nodes != null ) {
                for (Node node : nodes) {
                    add(node.getTokenImage());
                }
            }

            return this;
        }

        public TokenImageBuilder add(TokenImage tokenImage) {
            if ( tokenImage == null ) return this;

            if ( this.tokenImages == null ) {
                this.tokenImages = new ArrayList<>();
            }

            this.tokenImages.add(tokenImage);

            return this;
        }

        public TokenImageBuilder range(TokenImage ... tokenImages) {
            if ( tokenImages != null ) {
                rangeTokens(asList(tokenImages));
            }
            return this;
        }

        public TokenImageBuilder rangeTokens(List<TokenImage> tokenImages) {
            if ( tokenImages != null ) {
                for (TokenImage tokenImage : tokenImages) {
                    add(tokenImage);
                }
            }
            return this;
        }

        public TokenImage build() {
            int startLine = this.startLine == null ? -1 : this.startLine;
            int startColumn = this.startColumn == null ? -1 : this.startColumn;
            int endLine = this.endLine == null ? -1 : this.endLine;
            int endColumn = this.endColumn == null ? -1 : this.endColumn;

            boolean first = true;
            for (TokenImage tokenImage : nullSafe(tokenImages)) {
                if (tokenImage == null) continue;

                startColumn = (first && startColumn == -1) || (tokenImage.getStartLine() < startLine || (tokenImage.getStartLine() == startLine && tokenImage.getStartColumn() < startColumn)) ? tokenImage.getStartColumn() : startColumn;
                startLine = (first && startLine == -1) || tokenImage.getStartLine() < startLine ? tokenImage.getStartLine() : startLine;
                endColumn = (first && endColumn == -1) || (tokenImage.getEndLine() > endLine || (tokenImage.getEndLine() == endLine && tokenImage.getEndColumn() > endColumn)) ? tokenImage.getEndColumn() : endColumn;
                endLine = (first && endLine == -1) || tokenImage.getEndLine() > endLine ? tokenImage.getEndLine() : endLine;
                first = false;
            }

            return new TokenImage(startLine, startColumn, endLine, endColumn, image);
        }
    }
}
