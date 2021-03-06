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

import org.beanplanet.core.models.path.NamePath;
import org.orthodox.universel.ast.navigation.NavigationAxisAndNodeTest;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Type;
import org.orthodox.universel.ast.navigation.NavigationTransform;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

public interface Scope {
    default NavigationAxisAndNodeTest<?> resolveInitial(NavigationAxisAndNodeTest<?> initialStep) { return null; }

    default Node assign(Node rValue, NavigationAxisAndNodeTest<?> step) {
        return null;
    }

    default Node navigate(NavigationAxisAndNodeTest<?> step) { return step; }
    default NavigationTransform navigate(Node source, NavigationAxisAndNodeTest<?> step) {
        final Node target = navigate(step);
        return target != null && !Objects.equals(step, target) ? new NavigationTransform(null, target) : null;
    }
    default List<Type> resolveType(NamePath name) {
        return emptyList();
    }
}
