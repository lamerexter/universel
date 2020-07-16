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
import org.orthodox.universel.cst.literals.NullLiteralExpr;
import org.orthodox.universel.cst.methods.MethodDeclaration;
import org.orthodox.universel.cst.type.Parameter;
import org.orthodox.universel.cst.type.declaration.ClassDeclaration;
import org.orthodox.universel.cst.type.declaration.TypeDeclaration;
import org.orthodox.universel.cst.type.declaration.TypeDeclarationReference;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.cst.type.reference.TypeNameReference;
import org.orthodox.universel.cst.type.reference.TypeReference;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.orthodox.universel.ast.NodeSequence.emptyNodeSequence;
import static org.orthodox.universel.compiler.CompilationDefaults.SCRIPT_CLASS_NAME;
import static org.orthodox.universel.cst.Modifiers.*;

/**
 * This is where semantic analysis really begins. Looks at what the incoming script looks like and assembles a
 * canonical form consisting of the types declared or a surrogate type to house the members and executable body lines.
 */
public class ScriptAssembler extends AbstractSemanticAnalyser {
    public static final Modifiers MAIN_MODIFIERS = valueOf(PUBLIC | STATIC);
    public static final String MAIN_NAME = "main";
    public static final String MAIN_BINDING_PARAM_NAME = "binding";
    private static AtomicLong generatedScriptIndex = new AtomicLong();
    private static final List<Node> EMPTY_SCRIPT_SYNTHETIC_ELEMENTS = singletonList(new NullLiteralExpr());

    @Override
    public Node visitScript(final Script script) {
        if ( scriptAlreadyAssembled(script) ) return script;
        if ( scriptMainMethodMissingReturnType(script) ) return addMainMethodReturnType(script);
        if ( scriptMissingResultType(script) ) return addScriptResultType(script);

        //--------------------------------------------------------------------------------------------------------------
        // There is one or more body elements that are not type declarations or type members (e.g. fields, methods etc.).
        // Assemble those body elements into a new class declaration.
        // First determine whether we need to generate instance methods or a purely static implementation.
        //--------------------------------------------------------------------------------------------------------------
        return shouldGenerateStaticImplementation(script) ? generateStaticImplementation(script) : generateNonStaticImplementation(script);
    }

    private Node addMainMethodReturnType(final Script script) {
        return super.visitScript(script);
    }

    private boolean scriptMainMethodMissingReturnType(final Script script) {
        final MethodDeclaration scriptMainMethod = findScriptMainMethod(script);
        return scriptMainMethod != null && scriptMainMethod.getReturnType() == null;
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
        if ( !isScriptMain(node) ) return node;
        if ( node.getReturnType() != null || node.getBody().getType() == null) return node;

        return new MethodDeclaration(node.getModifiers(),
                                     node.getTypeParameters(),
                                     node.getDeclaringType(),
                                     (TypeReference)node.getBody().getType(),
                                     node.getName(),
                                     node.getParameters(),
                                     node.getBody());
    }

    private Node addScriptResultType(final Script script) {
        MethodDeclaration scriptMainMethod = findScriptMainMethod(script);
        if ( scriptMainMethod == null ) return script;
        if ( scriptMainMethod.getBody().getType() == null ) return script;

        return new Script(scriptMainMethod.getBody().getType(), script.getPackageDeclaration(), script.getImportDeclaration(), script.getBody());
    }

    private MethodDeclaration findScriptMainMethod(final Script script) {
        return script.getBody()
                     .stream()
                     .filter(TypeDeclaration.class::isInstance)
                     .map(TypeDeclaration.class::cast)
                     .flatMap(td -> td.getMembers().stream()
                                      .filter(MethodDeclaration.class::isInstance)
                                      .map(MethodDeclaration.class::cast)
                                      .filter(this::isScriptMain))
                     .map(MethodDeclaration.class::cast)
                     .findFirst().orElse(null);
    }

    private boolean scriptMissingResultType(final Script script) {
        return findScriptMainMethod(script) != null && script.getType() == null;
    }

    private boolean scriptAlreadyAssembled(final Script script) {
        return !scriptMissingResultType(script)
               && findScriptMainMethod(script) != null;
    }

    private boolean isScriptMain(final MethodDeclaration md) {
        return "main".equals(md.getName())
               && Objects.equals(md.getModifiers(), MAIN_MODIFIERS)
               && (md.getParameters().isEmpty()
                   || (md.getParameters().size() == 1 && MAIN_BINDING_PARAM_NAME.equals(md.getParameters().getNodes().get(0).getName().getName()))
               );
    }

    private boolean shouldGenerateStaticImplementation(final Script script) {
        return script.getBody().stream()
                     .filter(MethodDeclaration.class::isInstance)
                     .map(MethodDeclaration.class::cast)
                     .map(MethodDeclaration::getModifiers)
                     .filter(Objects::nonNull)
                     .allMatch(Modifiers::isStatic);
    }

    private Node generateStaticImplementation(final Script script) {
        List<Node> methodBodyElements = findMethodBodyElements(script);
        if (methodBodyElements.isEmpty()) {
            methodBodyElements = EMPTY_SCRIPT_SYNTHETIC_ELEMENTS;
        }

        final String generatedTypename = generateUniqueExecutionClassname();
        Type scriptResult = InternalNodeSequence.builder().add(methodBodyElements.toArray(new Node[methodBodyElements.size()])).build().getType();
        if ( scriptResult == null ) {
            System.out.println("HERE");
        }

        MethodDeclaration scriptBodyMethod = new MethodDeclaration(MAIN_MODIFIERS,
                                                                   null,
                                                                   (TypeReference) scriptResult,
                                                                   MAIN_NAME,
                                                                   getContext().getBindingType() == null
                                                                   ? emptyNodeSequence() : NodeSequence.<Parameter>builder()
                                                                                               .add(new Parameter(valueOf(FINAL),
                                                                                                                  new ResolvedTypeReferenceOld(script.getTokenImage(),
                                                                                                                                               getContext().getBindingType()
                                                                                                                  ),
                                                                                                                  false,
                                                                                                                  new Name(script.getTokenImage(), MAIN_BINDING_PARAM_NAME)
                                                                                                    )
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
                                                                .addNotNull(scriptBodyMethod)
                                                                .build(),
                                                            null,
                                                            null
        );

        scriptBodyMethod.setDeclaringType(new TypeDeclarationReference(scriptClass));

        return new Script(scriptResult,
                          script.getPackageDeclaration(),
                          script.getImportDeclaration(),
                          NodeSequence.builder().add(scriptClass).build()
        );
    }

    private String generateUniqueExecutionClassname() {
        return SCRIPT_CLASS_NAME + generatedScriptIndex.incrementAndGet();
    }

    private Node generateNonStaticImplementation(final Script script) {
        List<Node> methodBodyElements = findMethodBodyElements(script);
        final String generatedTypename = generateUniqueExecutionClassname();
        final NamePath fqGeneratedTypeName = script.getPackageDeclaration() == null ? new SimpleNamePath(generatedTypename) : script.getPackageDeclaration().getName().joinSingleton(generatedTypename);
        final Type returnType = script.getType() != null ? script.getType() : new ResolvedTypeReferenceOld(script.getTokenImage(), Object.class);

        return new Script(returnType,
                          script.getPackageDeclaration(),
                          script.getImportDeclaration(),
                          NodeSequence.builder()
                              .add(new ClassDeclaration(valueOf(PUBLIC),
                                                        script.getPackageDeclaration(),
                                                        new Name(script.getTokenImage(), generatedTypename),
                                                        null,
                                                        NodeSequence.<Node>builder()
                                                            .addAll(findTypeDeclarations(script))
                                                            .addAll(findTypeMembers(script))
                                                            .add(new MethodDeclaration(valueOf(PUBLIC),
                                                                                       null,
                                                                                       (TypeReference)returnType,
                                                                                       "execute",
                                                                                       getContext().getBindingType() == null
                                                                                       ? emptyNodeSequence() : NodeSequence.<Parameter>builder()
                                                                                                                   .add(new Parameter(valueOf(FINAL),
                                                                                                                                      new ResolvedTypeReferenceOld(script.getTokenImage(),
                                                                                                                                                                   getContext().getBindingType()
                                                                                                                                      ),
                                                                                                                                      false,
                                                                                                                                      new Name(script.getTokenImage(), MAIN_BINDING_PARAM_NAME))
                                                                                                                       )
                                                                                                                   .build(),
                                                                                       NodeSequence.<Node>builder().addAll(methodBodyElements).build()
                                                                 )
                                                                )
                                                            .add(new MethodDeclaration(MAIN_MODIFIERS,
                                                                                       null,
                                                                                       new ResolvedTypeReferenceOld(script.getTokenImage(), Object.class),
                                                                                       "main",
                                                                                       getContext().getBindingType() == null
                                                                                       ? emptyNodeSequence() : NodeSequence.<Parameter>builder()
                                                                                                                   .add(new Parameter(valueOf(FINAL),
                                                                                                                                      new ResolvedTypeReferenceOld(script.getTokenImage(),
                                                                                                                                                                   getContext().getBindingType()
                                                                                                                                      ),
                                                                                                                                      false,
                                                                                                                                      new Name(script.getTokenImage(), MAIN_BINDING_PARAM_NAME))
                                                                                                                       )
                                                                                                                   .build(),
                                                                                       NodeSequence.<Node>builder()
                                                                                           .add(new ObjectCreationExpression(script.getTokenImage(), new TypeNameReference(script.getTokenImage(), fqGeneratedTypeName)))
                                                                                           .add(new InstanceMethodCall(script.getTokenImage(),
                                                                                                                       new TypeNameReference(script.getTokenImage(), fqGeneratedTypeName),
                                                                                                                       new ResolvedTypeReferenceOld(script.getTokenImage(), Object.class),
                                                                                                                       "execute",
                                                                                                                       getContext().getBindingType() == null
                                                                                                                       ? emptyList() : ListBuilder.<TypeReference>builder()
                                                                                                                                           .add(new ResolvedTypeReferenceOld(script.getTokenImage(),
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
        return script.getBody()
                     .stream()
                     .filter(this::isTypeDeclaration)
                     .collect(Collectors.toList());
    }

    private List<Node> findTypeMembers(Script script) {
        return script.getBody()
                     .stream()
                     .filter(this::isTypeMember)
                     .collect(Collectors.toList());
    }

    private List<Node> findMethodBodyElements(Script script) {
        return script.getBody()
                     .stream()
                     .filter(n -> !isTypeDeclaration(n) && !isTypeMember(n))
                     .collect(Collectors.toList());
    }

    private boolean allBodyElementsAreTypeDeclarations(final Script node) {
        return !node.getBody().isEmpty() &&
               node.getBody()
                   .stream()
                   .allMatch(this::isTypeDeclaration);
    }

    private boolean allBodyElementsAreTypeMembers(final Script node) {
        return node.getBody()
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
