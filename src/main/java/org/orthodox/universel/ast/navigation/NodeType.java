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

import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.TokenImage;

import java.util.Objects;

/**
 * The 'node' node type, directly specified as dot (.). For example, along the 'self' axis this would be specified as
 * example <code>self::.</code>
 */
public class NodeType extends Node implements NodeTest {
    public NodeType(TokenImage tokenImage) {
        super(tokenImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getName());
    }

    @Override
    public boolean equals(final Object obj) {
        return obj == this || obj instanceof NodeType;
    }
}
