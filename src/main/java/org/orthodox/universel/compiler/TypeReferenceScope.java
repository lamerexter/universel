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

import org.beanplanet.core.lang.Assert;
import org.beanplanet.core.lang.TypeNotFoundException;
import org.beanplanet.core.lang.TypeUtil;
import org.beanplanet.core.util.StringUtil;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.cst.ImportDecl;
import org.orthodox.universel.cst.ImportStmt;
import org.orthodox.universel.cst.Name;
import org.orthodox.universel.cst.Node;

import java.util.stream.Collectors;

/**
 * Resolves type names from the following sources:
 *
 * <ul>
 *     <li>Classpath, including import statements from the import declaration of a script</li>
 * </ul>
 */
public class TypeReferenceScope implements NameScope {
    private final CompilationContext compilationContext;
    private final ImportDecl importDecl;

    public TypeReferenceScope(CompilationContext compilationContext, ImportDecl importDecl) {
        this.compilationContext = compilationContext;
        this.importDecl = importDecl;
    }

    @Override
    public Node navigate(final NavigationStep step) {
        return null;
    }

    @Override
    public boolean canResolve(String name) {
        return resolve(name) != null;
    }

    @Override
    public void generateAccess(String name) {
        Class<?> resolvedType = resolve(name);
        Assert.notNull(resolvedType, "Unable to resolve symbol "+name);

        compilationContext.getBytecodeHelper().emitLoadType(resolvedType);
        compilationContext.getVirtualMachine().loadOperandOfType(resolvedType);
    }

    private Class<?> resolve(String name) {
        for (int n=importDecl.getImports().size()-1; n >= 0; n--) {
            ImportStmt importStmt = importDecl.getImports().get(n);
            if ( !importStmt.isOnDemand() ) {
                if ( importStmt.getElements().isEmpty() || !importStmt.getElements().get(importStmt.getElements().size()-1).getName().equals(name)) continue;
            }

            String fqTypename = StringUtil.asDelimitedString(importStmt.getElements().stream().map(Name::getName).collect(Collectors.toList()), ".");
            if ( importStmt.isOnDemand() ) {
                fqTypename += "."+name;
            }

            try {
                return TypeUtil.loadClass(fqTypename);
            } catch (TypeNotFoundException ignoreEx) {
            }
        }

        return null;
    }
}
