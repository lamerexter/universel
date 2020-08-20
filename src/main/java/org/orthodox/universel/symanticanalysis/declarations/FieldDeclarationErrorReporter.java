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

package org.orthodox.universel.symanticanalysis.declarations;

import org.beanplanet.messages.domain.Message;
import org.orthodox.universel.ast.FieldDeclaration;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.type.reference.ReferenceType;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.compiler.Messages;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;
import org.orthodox.universel.symanticanalysis.ResolvedTypeReference;
import org.orthodox.universel.symanticanalysis.SemanticAnalyser;

import java.util.Objects;

/**
 * Iterates over the AST, performing a depth-first post-order traversal to establish the types on the AST. Essentially,
 * known types flow upwards from the leaves on the tree, forming a type tree from the ground up.
 *
 * <p>This implementation is extremely lenient in that it ignores types which cannot be resolved and leaves them
 * for possible future resolution or error reporting.</p>
 *
 * <ul>
 * <li>{@link ReferenceType}
 * </ul>
 */
public class FieldDeclarationErrorReporter extends AbstractSemanticAnalyser implements SemanticAnalyser {

    @Override
    public Node visitFieldDeclaration(final FieldDeclaration node) {
        if (node.getDeclarationType().getTypeDescriptor() == void.class || node.getDeclarationType().getTypeDescriptor() == Void.class) {
            getContext().addError(Message.builder().message(Messages.FieldDeclaration.VOID_TYPE)
                                      .withRelatedObject(node.getDeclarationType()));

        }

        return node;
    }
}
