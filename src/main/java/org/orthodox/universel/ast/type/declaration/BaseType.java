/*
 *  MIT Licence:
 *
 *  Copyright (c) 2020 Orthodox Engineering Ltd
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

package org.orthodox.universel.ast.type.declaration;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.*;

import java.util.List;
import java.util.Objects;

/**
 * The supertype of all universel types.
 */
public class BaseType extends Node implements CompositeNode {
    /** The modifiers associated with this type declaration. */
    private final Modifiers modifiers;

    /** The package in which this type is declared. */
    private final PackageDeclaration packageDeclaration;

    /** The simple name of the type declared. */
    private final Name name;

    /** The type parameters associated with the declared type. */
    private final NodeSequence<TypeParameter> typeParameters;

    /** The members declared by the type. */
    private final NodeSequence<Node> members;


    /**
     * Constructs a type declaration.
     *
     * @param modifiers the modifiers associated with this type declaration.
     * @param packageDeclaration the package in which this type is declared.
     * @param name the simple name of the type declared.
     * @param typeParameters the type parameters associated with the declared type.
     * @param members the members declared by the type.
     */
    public BaseType(Modifiers modifiers,
                    PackageDeclaration packageDeclaration,
                    Name name,
                    NodeSequence<TypeParameter> typeParameters,
                    NodeSequence<Node> members) {
        super(TokenImage.builder()
                        .range(modifiers, packageDeclaration, name, typeParameters, members)
                        .build());
        this.modifiers = modifiers;
        this.packageDeclaration = packageDeclaration;
        this.name = name;
        this.typeParameters = typeParameters;
        this.members = members;
    }

    /**
     * Gets the modifiers associated with this type declaration.
     *
     * @return the modifiers associated with this type declaration.
     */
    public Modifiers getModifiers() {
        return modifiers;
    }

    /**
     * Gets the package in which this type is declared
     *
     * @return the package in which this type is declared
     */
    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }

    /**
     * Gets the simple name of the type declared.
     *
     * @return the simple name of the type declared.
     */
    public Name getName() {
        return name;
    }

    /**
     * Gets the type parameters associated with the declared type.
     *
     * @return the type parameters associated with the declared type.
     */
    public NodeSequence<TypeParameter> getTypeParameters() {
        return typeParameters;
    }

    /**
     * Gets the members declared by the type.
     *
     * @return the members declared by the type.
     */
    public NodeSequence<Node> getMembers() {
        return members;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseType)) return false;
        if (!super.equals(o)) return false;
        BaseType nodes = (BaseType) o;
        return Objects.equals(getModifiers(), nodes.getModifiers()) &&
               Objects.equals(getPackageDeclaration(), nodes.getPackageDeclaration()) &&
               Objects.equals(getName(), nodes.getName()) &&
               Objects.equals(getTypeParameters(), nodes.getTypeParameters()) &&
               Objects.equals(getMembers(), nodes.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                            getModifiers(),
                            getPackageDeclaration(),
                            getName(),
                            getTypeParameters(),
                            getMembers()
        );
    }

    /**
     * Gets a list of the children of this node.
     *
     * @return the children of this node, which may be empty but not null.
     */
    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                   .addNotNull(modifiers, packageDeclaration, name, typeParameters, members)
                   .build();
    }
}
