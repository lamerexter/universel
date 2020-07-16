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
package org.orthodox.universel.cst;

import org.orthodox.universel.ast.AstVisitor;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Objects;

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

//    protected Class<?> typeDescriptor;

    private Type type;


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
        this(tokenImage, (Class<?>)null);
    }

    /**
     * Constructs the node.
     *
     * @param tokenImage the parser token image backing this node.
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     */
    public Node(TokenImage tokenImage, Class<?> typeDescriptor) {
        this(null, tokenImage, typeDescriptor);
    }

    /**
     * Constructs the node.
     *
     * @param tokenImage the parser token image backing this node.
     * @param type  the type of the node, or null if unknown at this time.
     */
    public Node(TokenImage tokenImage, Type type) {
        this(null, tokenImage, type);
    }

    /**
     * All args constructor.
     *
     * @param parent the parent node, which may be null to indicate none.
     * @param tokenImage the parser token image backing this node.
     * @param typeDescriptor the type of the node, or null if unknown at this time.
     */
    public Node(Node parent, TokenImage tokenImage, Class<?> typeDescriptor) {
        this.parent = parent;
        this.tokenImage = tokenImage;
//        this.typeDescriptor = typeDescriptor;
        this.type = typeDescriptor == null ? null : new ResolvedTypeReferenceOld(tokenImage, typeDescriptor);
    }

    /**
     * All args constructor.
     *
     * @param parent the parent node, which may be null to indicate none.
     * @param tokenImage the parser token image backing this node.
     * @param type the type of the node, or null if unknown at this time.
     */
    public Node(Node parent, TokenImage tokenImage, Type type) {
        this.parent = parent;
        this.tokenImage = tokenImage;
//        this.typeDescriptor = null;
        this.type = type;
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return this;
    }

    public Node accept(AstVisitor visitor) {
        return this;
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

    public Class<?> getTypeDescriptor() {
//        return typeDescriptor;
        return getType() == null ? null : getType().getTypeClass();
    }

    /**
     * Returns the established static type of the node.
     *
     * @return the node's type, or null if the type cannot be determined at this time.
     */
    public Type getType() {
        return type;
    }

    public void setType(final TypeReference type) {
        this.type = type;
    }

//    public void setTypeDescriptor(Class<?> typeDescriptor) {
//        this.typeDescriptor = typeDescriptor;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Node))
            return false;
        Node node = (Node) o;
        return Objects.equals(parent, node.parent)
               && Objects.equals(tokenImage, node.tokenImage)
               && Objects.equals(type, node.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, tokenImage, type);
    }
}
