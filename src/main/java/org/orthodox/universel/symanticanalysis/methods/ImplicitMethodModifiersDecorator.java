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

package org.orthodox.universel.symanticanalysis.methods;

import org.orthodox.universel.ast.Modifiers;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;

/**
 * Looks at script and method bodies, adding return statements where necessary.
 */
public class ImplicitMethodModifiersDecorator extends AbstractSemanticAnalyser {
    public static final Modifiers DEFAULT_METHOD_MODIFIERS = new Modifiers(Modifiers.PUBLIC);

    @Override
    public MethodDeclaration visitMethodDeclaration(MethodDeclaration node) {
        if (node.getModifiers() != null) return node;

        return new MethodDeclaration(DEFAULT_METHOD_MODIFIERS,
                                     node.getTypeParameters(),
                                     node.getReturnType(),
                                     node.getName(),
                                     node.getParameters(),
                                     node.getBody()
        );
    }
}
