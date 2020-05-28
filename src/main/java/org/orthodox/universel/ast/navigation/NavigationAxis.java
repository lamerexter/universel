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
package org.orthodox.universel.ast.navigation;

/**
 * The universal standard names of navigation axes. There will be many more user defined ones.
 */
public enum NavigationAxis {
    ANCESTOR("ancestor"),
    ANCESTOR_OR_SELF("ancestor-or-self"),
    ATTRIBUTE("attribute"),
    CHILD("child"),
    DEFAULT("default"),
    DESCENDANT("descendant"),
    DESCENDANT_OR_SELF("descendant-or-self"),
    FOLLOWING("following"),
    FOLLOWING_SIBLING("following-sibling"),
    PARENT("parent"),
    PRECEDING("preceding"),
    PRECEDING_SIBLING("preceding-sibling"),
    ROOT("root"),
    SELF("self"),
    THIS("this");

    private final String canonicalName;

    NavigationAxis(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    @Override
    public String toString() {
         return canonicalName;
    }
}
