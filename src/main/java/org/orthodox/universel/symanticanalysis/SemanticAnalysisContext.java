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

package org.orthodox.universel.symanticanalysis;

import org.beanplanet.messages.domain.Message;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universel.compiler.Scope;
import org.orthodox.universel.cst.ImportStmt;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.exec.navigation.NavigatorRegistry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SemanticAnalysisContext {
    private final List<ImportStmt> defaultImports;

    private final Class<?> bindingType;
    private final Messages messages;
    private final NavigatorRegistry navigatorRegistry;
    private final Deque<Scope> scopes = new ArrayDeque<>();

    public SemanticAnalysisContext(List<ImportStmt> defaultImports,
                                   Class<?> bindingType,
                                   Messages message,
                                   NavigatorRegistry navigatorRegistry) {
        this.defaultImports = defaultImports;
        this.bindingType = bindingType;
        this.messages = message;
        this.navigatorRegistry = navigatorRegistry;
    }

    public List<ImportStmt> getDefaultImports() {
        return defaultImports;
    }

    public Class<?> getBindingType() {
        return bindingType;
    }

    public Messages getMessages() {
        return messages;
    }

    public Messages addError(Message message) {
        return addError(message, m -> Objects.equals(m, message));
    }

    public Messages addError(Message message, Predicate<Message> noMatchingErrorCondition) {
        if ( getMessages().getErrors().stream().noneMatch(noMatchingErrorCondition) ) {
            return getMessages().addError(message);
        }

        return getMessages();
    }

    public NavigatorRegistry getNavigatorRegistry() {
        return navigatorRegistry;
    }

    public void pushScope(Scope scope) {
        scopes.push(scope);
    }

    public Scope popScope() {
        return scopes.pop();
    }

    public Stream<Scope> scopes() {
        return scopes.stream();
    }
}
