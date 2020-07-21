/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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

package org.orthodox.universel.compiler;

import org.orthodox.universel.ast.LoadLocal;
import org.orthodox.universel.ast.navigation.NameTest;
import org.orthodox.universel.ast.navigation.NavigationAxis;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.methods.MethodDeclaration;

import static java.util.stream.IntStream.range;

public class MethodScope implements NameScope {

    public static final int SCRIPT_BINDING_LOCAL_OFFSET = 0;

    private final MethodDeclaration methodDeclaration;

    public MethodScope(final MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    @Override
    public Node navigate(final NavigationStep<?> step) {
        if ( methodDeclaration.getParameters().isEmpty() ||
             !step.getAxis().equals(NavigationAxis.DEFAULT.getCanonicalName()) ||
             !(step.getNodeTest() instanceof NameTest) ) return step;

        NameTest nameTest = (NameTest)step.getNodeTest();
        final String name = nameTest.getName();
        return range(0, methodDeclaration.getParameters().size())
            .filter(i -> methodDeclaration.getParameters().getNodes().get(i).getName().getName().equals(name))
            .mapToObj(i -> (Node)new LoadLocal(step.getTokenImage(), methodDeclaration.getParameters().getNodes().get(i).getTypeDescriptor(), i+(methodDeclaration.getModifiers().isStatic() ? 0 : 1)))
            .findFirst().orElse(step);
    }

    @Override
    public boolean canResolve(final String name) {
        return false;
    }

    @Override
    public void generateAccess(final String name) {

    }
}
