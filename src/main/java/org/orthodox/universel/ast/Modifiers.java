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

package org.orthodox.universel.ast;

import org.beanplanet.core.util.PropertyBasedToStringBuilder;
import org.orthodox.universel.ast.annotation.Annotation;

import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * AST representation of standard Java-like modifiers, as defined in
 * {@link Modifier} and may also include {@link Annotation} instances.
 *
 * @author Gary Watson
 */
public final class Modifiers extends Node {
    // Bit representations of the Java modifiers
    public static final int ABSTRACT     = 0x00000400;
    public static final int ANNOTATION   = 0x00002000;
    public static final int BRIDGE       = 0x00000040;
    public static final int ENUM         = 0x00004000;
    public static final int FINAL        = 0x00000010;
    public static final int INTERFACE    = 0x00000200;
    public static final int NATIVE       = 0x00000100;
    public static final int PRIVATE      = 0x00000002;
    public static final int PROTECTED    = 0x00000004;
    public static final int PUBLIC       = 0x00000001;
    public static final int STATIC       = 0x00000008;
    public static final int STRICTFP     = 0x00000800;
    public static final int SYNCHRONIZED = 0x00000020;
    public static final int SYNTHETIC    = 0x00001000;
    public static final int TRANSIENT    = 0x00000080;
    public static final int VARARGS      = 0x00000080;
    public static final int VOLATILE     = 0x00000040;

    /**
     * The bitwise basic modifiers associated with this modifiers set.
     */
    private int modifiers;


    private static final Map<Integer, String> ALL_MODIFIERS = new LinkedHashMap<Integer, String>();

    static {
        ALL_MODIFIERS.put(ABSTRACT, "abstract");
        ALL_MODIFIERS.put(FINAL, "final");
        ALL_MODIFIERS.put(INTERFACE, "interface");
        ALL_MODIFIERS.put(NATIVE, "native");
        ALL_MODIFIERS.put(PRIVATE, "private");
        ALL_MODIFIERS.put(PROTECTED, "protected");
        ALL_MODIFIERS.put(PUBLIC, "public");
        ALL_MODIFIERS.put(STATIC, "static");
        ALL_MODIFIERS.put(STRICTFP, "strictfp");
        ALL_MODIFIERS.put(SYNCHRONIZED, "synchronized");
        ALL_MODIFIERS.put(SYNTHETIC, "synthetic");
        ALL_MODIFIERS.put(TRANSIENT, "transient");
        ALL_MODIFIERS.put(VOLATILE, "volatile");
    }

    /**
     * Constructs an empty (zero) set of modifiers.
     */
    public Modifiers() {
    }

    /**
     * Creates a set of modifiers, initialised to the specified bitwise set of
     * modifiers.
     *
     * @param modifiers the bitwise modifiers used to initialise this instance.
     */
    public Modifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * Creates a set of modifiers, initialised to the specified bitwise set of
     * modifiers and annotations.
     *
     * @param tokenImage  the image representing this cst annotation.
     * @param modifiers   the bitwise modifiers used to initialise this instance.
     */
    public Modifiers(TokenImage tokenImage, int modifiers) {
        super(tokenImage);
        this.modifiers = modifiers;
    }

    /**
     * Returns the bitwise representation of this set of modifiers.
     *
     * @return the bitset of modifiers.
     */
    public int getModifiers() {
        return modifiers;
    }

    /**
     * Sets the bitwise representation of this set of modifiers.
     *
     * @param modifiers the bitset of modifiers used to initialise this modifier set.
     */
    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public boolean isAbstract() {
        return isAbstract(modifiers);
    }

    public boolean isFinal() {
        return isFinal(modifiers);
    }

    public boolean isNative() {
        return isNative(modifiers);
    }

    public boolean isProtected() {
        return isProtected(modifiers);
    }

    public boolean isPrivate() {
        return isPrivate(modifiers);
    }

    public boolean isPublic() {
        return isPublic(modifiers);
    }

    public boolean isStatic() {
        return isStatic(modifiers);
    }

    public boolean isStrictfp() {
        return isStrictfp(modifiers);
    }

    public boolean isSynchronized() {
        return isSynchronized(modifiers);
    }

    public boolean isSynthetic() {
        return isSynthetic(modifiers);
    }

    public boolean isTransient() {
        return isTransient(modifiers);
    }

    public boolean isVolatile() {
        return isVolatile(modifiers);
    }

    public static boolean isAbstract(int modifiers) {
        return (modifiers & ABSTRACT) != 0;
    }

    public static boolean isFinal(int modifiers) {
        return (modifiers & FINAL) != 0;
    }

    public static boolean isNative(int modifiers) {
        return (modifiers & NATIVE) != 0;
    }

    public static boolean isProtected(int modifiers) {
        return (modifiers & PROTECTED) != 0;
    }

    public static boolean isPrivate(int modifiers) {
        return (modifiers & PRIVATE) != 0;
    }

    public static boolean isPublic(int modifiers) {
        return (modifiers & PUBLIC) != 0;
    }

    public static boolean isStatic(int modifiers) {
        return (modifiers & STATIC) != 0;
    }

    public static boolean isStrictfp(int modifiers) {
        return (modifiers & STRICTFP) != 0;
    }

    public static boolean isSynchronized(int modifiers) {
        return (modifiers & SYNCHRONIZED) != 0;
    }

    public static boolean isSynthetic(int modifiers) {
        return (modifiers & SYNTHETIC) != 0;
    }

    public static boolean isTransient(int modifiers) {
        return (modifiers & TRANSIENT) != 0;
    }

    public static boolean isVolatile(int modifiers) {
        return (modifiers & VOLATILE) != 0;
    }

    public void add(int modifier) {
        modifiers = addModifier(modifiers, modifier);
    }

    public void remove(int modifier) {
        modifiers = removeModifier(modifiers, modifier);
    }

    public boolean contains(int modifier) {
        return (modifiers & modifier) == modifier;
    }

    public static int addModifier(int modifiers, int mod) {
        return modifiers | mod;
    }

    public static int removeModifier(int modifiers, int mod) {
        return modifiers & ~mod;
    }

    public static Modifiers valueOf(int modifiers) {
        return new Modifiers(modifiers);
    }

    public Modifiers accept(UniversalCodeVisitor visitor) {
        return visitor.visitModifiers(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Modifiers)) return false;
        if (!super.equals(o)) return false;
        Modifiers nodes = (Modifiers) o;
        return getModifiers() == nodes.getModifiers();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getModifiers());
    }

    @Override
    public String toString() {
        return new PropertyBasedToStringBuilder(this).build();
    }
}
