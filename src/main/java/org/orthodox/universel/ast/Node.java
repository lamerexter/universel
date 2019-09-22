/*
 *  MIT Licence:
 *
 *  Copyright (c) 2018 Orthodox Engineering Ltd
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *
 */
package org.orthodox.universel.ast;

/**
 * The superclass of all UEL Abstact Syntax Tree (AST) elements.
 *
 * @author Gary Watson
 */
public abstract class Node implements UniversalCodeVisitable {
    /**
     * The parent node.
     */
    private Node parent;

    /**
     * The parser token image backing this node.
     */
    private TokenImage tokenImage;


    /**
     * Constructs a new AST node instance, with no initial configuration.
     */
    public Node() {
    }

    /**
     * Constructs a new AST node instance.
     *
     * @param tokenImage the parser token image backing this node.
     */
    public Node(TokenImage tokenImage) {
        this.tokenImage = tokenImage;
    }

    public boolean accept(UniversalCodeVisitor visitor) {
        return true;
    }

    /**
     * Gets the parent node.
     *
     * @return the parent node, which may be null if this node does not have a parent.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets the parent node.
     *
     * @param parent the parent node, which may be null if this node does not have a parent.
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Sets the parent node.
     *
     * @param parent the parent node, which may be null if this node does not have a parent.
     * @return this instance for builder chaining.
     */
    public Node withParent(Node parent) {
        setParent(parent);
        return this;
    }

    /**
     * Gets the parser token image backing this node.
     *
     * @return the parser token image backing this node.
     */
    public TokenImage getTokenImage() {
        return tokenImage;
    }

    /**
     * Sets the parser token image backing this node.
     *
     * @param tokenImage the parser token image backing this node.
     */
    public void setTokenImage(TokenImage tokenImage) {
        this.tokenImage = tokenImage;
    }

    /**
     * Sets the parser token image backing this node.
     *
     * @param tokenImage the parser token image backing this node.
     * @return this instance for builder chaining.
     */
    public Node withTokenImage(TokenImage tokenImage) {
        this.tokenImage = tokenImage;
        return this;
    }

    /**
     * Returns the canonical (well-known form) of this node. Usually, this is in the shape of the original parsed form
     * of the tokens backing this node (i.e. token image). In this naive implementation, it is the parser token image
     * itself.
     *
     * @return the parser token image, or null if there is not one.
     */
    public String getCanonicalForm() {
        return getTokenImage() != null ? getTokenImage().getImage() : null;
    }

    @Override
    public String toString() {
        return getCanonicalForm();
    }
}
