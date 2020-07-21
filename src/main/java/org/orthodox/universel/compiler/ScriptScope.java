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
import org.orthodox.universel.ast.navigation.NavigationAxis;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NodeType;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.exec.navigation.NavigatorRegistry;

import java.util.Objects;

public class ScriptScope extends BoundScope {

    public static final int SCRIPT_BINDING_LOCAL_OFFSET = 0;

    public ScriptScope(final Class<?> bindingType, final NavigatorRegistry navigatorRegistry) {
        super(bindingType, navigatorRegistry);
    }

    @Override
    public NavigationStep<?> resolveInitial(final NavigationStep<?> initialStep) {
        Node transformedNode = super.navigate(initialStep);
        if ( Objects.equals(transformedNode, initialStep) ) return null;

        return new NavigationStep<>(initialStep.getTokenImage(), NavigationAxis.BINDING, new NodeType(initialStep.getTokenImage()));
    }

    @Override
    public Node navigate(final NavigationStep<?> step) {
        if ( !(step.getAxis().equals(NavigationAxis.BINDING.getCanonicalName()) && step.getNodeTest() instanceof NodeType) ) return null;

        return new LoadLocal(step.getTokenImage(), getBindingType(), SCRIPT_BINDING_LOCAL_OFFSET);
    }
}
