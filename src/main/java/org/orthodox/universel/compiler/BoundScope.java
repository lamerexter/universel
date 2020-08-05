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
import org.orthodox.universel.ast.navigation.NavigationAxisAndNodeTest;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.navigation.NavigationTransform;
import org.orthodox.universel.exec.navigation.NavigatorRegistry;

import java.util.Objects;

import static org.beanplanet.core.util.ObjectUtil.nvl;

public class BoundScope implements NameScope {
    public static final int SCRIPT_BINDING_LOCAL_OFFSET = 0;

    private final NavigatorRegistry navigatorRegistry;
    private final Class<?> bindingType;
    private final Node bindingAccessor;

    public BoundScope(final Class<?> bindingType,
                      final Node bindingAccessor,
                      final NavigatorRegistry navigatorRegistry) {
        this.bindingType = nvl(bindingType, Object.class);
        this.bindingAccessor = bindingAccessor;
        this.navigatorRegistry = navigatorRegistry;
    }

    public Class<?> getBindingType() {
        return bindingType;
    }

    public Node getBindingAccessor() {
        return bindingAccessor;
    }

    @Override
    public NavigationTransform navigate(Node source, final NavigationAxisAndNodeTest<?> step) {
        return navigatorRegistry.lookup(bindingType, step).stream()
                         .map(navigator -> navigator.navigationTransform(bindingType, source, step))
                         .filter(Objects::nonNull)
                         .filter(transformedNode -> !Objects.equals(transformedNode, step))
                         .map(target -> new NavigationTransform(getBindingAccessor(), target))
                         .findFirst()
                         .orElse(null);
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
