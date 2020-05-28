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

package org.orthodox.universel.symanticanalysis;

import org.beanplanet.core.collections.ListBuilder;
import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.core.models.path.SimpleNamePath;
import org.orthodox.universel.ast.InstanceMethodCall;
import org.orthodox.universel.ast.LoadLocal;
import org.orthodox.universel.ast.NodeSequence;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.declaration.ClassDeclaration;
import org.orthodox.universel.cst.type.declaration.TypeDeclaration;
import org.orthodox.universel.cst.type.declaration.TypeDeclarationReference;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReference;
import org.orthodox.universel.cst.type.reference.TypeNameReference;
import org.orthodox.universel.cst.type.reference.TypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.beanplanet.core.util.CollectionUtil.isEmptyOrNull;
import static org.orthodox.universel.ast.NodeSequence.emptyNodeSequence;
import static org.orthodox.universel.compiler.CompilationDefaults.SCRIPT_CLASS_NAME;
import static org.orthodox.universel.cst.Modifiers.*;

/**
 * This is where semantic analysis really begins. Looks at what the incoming script looks like and assembles a
 * canonical form consisting of the types declared or a surrogate type to house the members and executable body lines.
 */
public class ScriptAssembler extends AbstractSemanticAnalyser {
    private static AtomicLong generatedScriptIndex = new AtomicLong();

    @Override
    public Node visitScript(final Script script) {
        if ( allBodyElementsAreTypeDeclarations(script) ) return script;
//        if ( !isEmptyOrNull(script.getBodyElements()) &&
//             (allBodyElementsAreTypeDeclarations(script) || allBodyElementsAreTypeMembers(script)) ) return script;

        //--------------------------------------------------------------------------------------------------------------
        // There is one or more body elements that are not type declarations or type members (e.g. fields, methods etc.).
        // Assemble those body elements into a new class declaration.
        // First determine whether we need to generate instance methods or a purely static implementation.
        //--------------------------------------------------------------------------------------------------------------
        return shouldGenerateStaticImplementation(script) ? generateStaticImplementation(script) : generateNonStaticImplementation(script);
    }

    private boolean shouldGenerateStaticImplementation(final Script script) {
        return script.getBodyElements().stream()
                     .filter(MethodDeclaration.class::isInstance)
                     .map(MethodDeclaration.class::cast)
                     .map(MethodDeclaration::getModifiers)
                     .filter(Objects::nonNull)
                     .allMatch(Modifiers::isStatic);
    }

    private Node generateStaticImplementation(final Script script) {
        List<Node> methodBodyElements = findMethodBodyElements(script);
        final String generatedTypename = generateUniqueExecutionClassname();
        MethodDeclaration scriptBodyMethod = new MethodDeclaration(valueOf(PUBLIC | STATIC),
                                                                   null,
                                                                   new ResolvedTypeReference(script.getTokenImage(), Object.class),
                                                                   "main",
                                                                   getContext().getBindingType() == null
                                                                   ? emptyNodeSequence() : NodeSequence.<Parameter>builder()
                                                                                               .add(new Parameter(valueOf(PUBLIC | FINAL),
                                                                                                                  new ResolvedTypeReference(script.getTokenImage(),
                                                                                                                                            getContext().getBindingType()
                                                                                                                  ),
                                                                                                                  false,
                                                                                                                  new Name(script.getTokenImage(), "binding"))
                                                                                                   )
                                                                                               .build(),
                                                                   NodeSequence.<Node>builder().addAll(methodBodyElements).build()
        );
        ClassDeclaration scriptClass = new ClassDeclaration(valueOf(PUBLIC),
                                                            script.getPackageDeclaration(),
                                                            new Name(script.getTokenImage(), generatedTypename),
                                                            null,
                                                            NodeSequence.<Node>builder()
                                                                .addAll(findTypeDeclarations(script))
                                                                .addAll(findTypeMembers(script))
                                                                .add(scriptBodyMethod
                                                                    )
                                                                .build(),
                                                            null,
                                                            null
        );
        scriptBodyMethod.setDeclaringType(new TypeDeclarationReference(scriptClass));
        return new Script(script.getPackageDeclaration(),
                          script.getImportDeclaration(),
                          ListBuilder.<Node>builder()
                              .add(scriptClass)
                              .build()
        );
    }

    private String generateUniqueExecutionClassname() {
        return SCRIPT_CLASS_NAME + generatedScriptIndex.incrementAndGet();
    }

    private Node generateNonStaticImplementation(final Script script) {
        List<Node> methodBodyElements = findMethodBodyElements(script);
        final String generatedTypename = generateUniqueExecutionClassname();
        final NamePath fqGeneratedTypeName = script.getPackageDeclaration() == null ? new SimpleNamePath(generatedTypename) : script.getPackageDeclaration().getName().joinSingleton(generatedTypename);

        return new Script(script.getPackageDeclaration(),
                          script.getImportDeclaration(),
                          ListBuilder.<Node>builder()
                              .add(new ClassDeclaration(valueOf(PUBLIC),
                                                        script.getPackageDeclaration(),
                                                        new Name(script.getTokenImage(), generatedTypename),
                                                        null,
                                                        NodeSequence.<Node>builder()
                                                            .addAll(findTypeDeclarations(script))
                                                            .addAll(findTypeMembers(script))
                                                            .add(new MethodDeclaration(valueOf(PUBLIC),
                                                                                       null,
                                                                                       new ResolvedTypeReference(script.getTokenImage(), Object.class),
                                                                                       "execute",
                                                                                       getContext().getBindingType() == null
                                                                                       ? emptyNodeSequence() : NodeSequence.<Parameter>builder()
                                                                                                                   .add(new Parameter(valueOf(PUBLIC | FINAL),
                                                                                                                                      new ResolvedTypeReference(script.getTokenImage(),
                                                                                                                                                                getContext().getBindingType()
                                                                                                                                      ),
                                                                                                                                      false,
                                                                                                                                      new Name(script.getTokenImage(), "binding"))
                                                                                                                       )
                                                                                                                   .build(),
                                                                                       NodeSequence.<Node>builder().addAll(methodBodyElements).build()
                                                                 )
                                                                )
                                                            .add(new MethodDeclaration(valueOf(PUBLIC | STATIC),
                                                                                       null,
                                                                                       new ResolvedTypeReference(script.getTokenImage(), Object.class),
                                                                                       "main",
                                                                                       getContext().getBindingType() == null
                                                                                       ? emptyNodeSequence() : NodeSequence.<Parameter>builder()
                                                                                                                   .add(new Parameter(valueOf(PUBLIC | FINAL),
                                                                                                                                      new ResolvedTypeReference(script.getTokenImage(),
                                                                                                                                                                getContext().getBindingType()
                                                                                                                                      ),
                                                                                                                                      false,
                                                                                                                                      new Name(script.getTokenImage(), "binding"))
                                                                                                                       )
                                                                                                                   .build(),
                                                                                       NodeSequence.<Node>builder()
                                                                                           .add(new ObjectCreationExpression(script.getTokenImage(), new TypeNameReference(script.getTokenImage(), fqGeneratedTypeName)))
                                                                                           .add(new InstanceMethodCall(script.getTokenImage(),
                                                                                                                       new TypeNameReference(script.getTokenImage(), fqGeneratedTypeName),
                                                                                                                       new ResolvedTypeReference(script.getTokenImage(), Object.class),
                                                                                                                       "execute",
                                                                                                                       getContext().getBindingType() == null
                                                                                                                       ? emptyList() : ListBuilder.<TypeReference>builder()
                                                                                                                                           .add(new ResolvedTypeReference(script.getTokenImage(),
                                                                                                                                                                          getContext().getBindingType()
                                                                                                                                           ))
                                                                                                                                           .build(),
                                                                                                                       getContext().getBindingType() == null
                                                                                                                       ? emptyList() : singletonList(new LoadLocal(script.getTokenImage(),
                                                                                                                                                                   getContext().getBindingType(), 0)
                                                                                                                                                    )
                                                                                                )
                                                                                               )
                                                                                           .build()
                                                                 )
                                                                ).build(),
                                                        null, null
                                   )
                                  ).build()
        );
    }

    private List<Node> findTypeDeclarations(Script script) {
        return script.getBodyElements()
                     .stream()
                     .filter(this::isTypeDeclaration)
                     .collect(Collectors.toList());
    }

    private List<Node> findTypeMembers(Script script) {
        return script.getBodyElements()
                     .stream()
                     .filter(this::isTypeMember)
                     .collect(Collectors.toList());
    }

    private List<Node> findMethodBodyElements(Script script) {
        return script.getBodyElements()
                     .stream()
                     .filter(n -> !isTypeDeclaration(n) && !isTypeMember(n))
                     .collect(Collectors.toList());
    }

    private boolean allBodyElementsAreTypeDeclarations(final Script node) {
        return !node.getBodyElements().isEmpty() &&
               node.getBodyElements()
                   .stream()
                   .allMatch(this::isTypeDeclaration);
    }

    private boolean allBodyElementsAreTypeMembers(final Script node) {
        return node.getBodyElements()
                   .stream()
                   .allMatch(this::isTypeMember);
    }

    private boolean isTypeDeclaration(final Node node) {
        return node instanceof TypeDeclaration;
    }

    private boolean isTypeMember(final Node n) {
        return n instanceof MethodDeclaration;
    }
}
