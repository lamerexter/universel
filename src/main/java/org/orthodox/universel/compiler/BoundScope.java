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

import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.exec.navigation.NavigatorRegistry;

import java.util.Objects;

import static org.beanplanet.core.util.ObjectUtil.nvl;

public class BoundScope implements NameScope {
    private final NavigatorRegistry navigatorRegistry;
    private final Class<?> bindingType;

    public BoundScope(final Class<?> bindingType, final NavigatorRegistry navigatorRegistry) {
        this.bindingType = nvl(bindingType, Object.class);
        this.navigatorRegistry = navigatorRegistry;
    }

    public Class<?> getBindingType() {
        return bindingType;
    }

    @Override
    public Node navigate(final NavigationStep<?> step) {
        return navigatorRegistry.lookup(bindingType, step).stream()
                         .map(navigator -> navigator.navigationTransform(bindingType, step))
                         .filter(Objects::nonNull)
                         .filter(transformedNode -> !Objects.equals(transformedNode, step))
                         .findFirst()
                         .orElse(step);
    }

    @Override
    public boolean canResolve(String name) {
        return true; // Will always attempt to resolve the name
    }

    @Override
    public void generateAccess(String name) {
//            throw new UnsupportedOperationException();
//        compilationContext.getBytecodeHelper().emitLoadLocal(true, 0, Map.class);
//        compilationContext.getBytecodeHelper().emitLoadStringOperand(name);
//        compilationContext.getBytecodeHelper().emitInvokeInstanceMethod(Map.class, "get", Object.class, Object.class);
//        compilationContext.getVirtualMachine().loadOperandOfType(Object.class);
    }
}
