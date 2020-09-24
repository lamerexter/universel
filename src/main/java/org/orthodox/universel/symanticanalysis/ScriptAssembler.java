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
import org.orthodox.universel.ast.*;
import org.orthodox.universel.ast.allocation.ObjectCreationExpression;
import org.orthodox.universel.ast.literals.NullLiteralExpr;
import org.orthodox.universel.ast.methods.MethodDeclaration;
import org.orthodox.universel.ast.type.Parameter;
import org.orthodox.universel.ast.type.declaration.ClassDeclaration;
import org.orthodox.universel.ast.type.declaration.TypeDeclaration;
import org.orthodox.universel.ast.type.declaration.TypeDeclarationReference;
import org.orthodox.universel.ast.type.reference.ResolvedTypeReferenceOld;
import org.orthodox.universel.ast.type.reference.TypeNameReference;
import org.orthodox.universel.ast.type.reference.TypeReference;
import org.orthodox.universel.symanticanalysis.name.InternalNodeSequence;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.orthodox.universel.ast.Modifiers.*;
import static org.orthodox.universel.ast.NodeSequence.emptyNodeSequence;
import static org.orthodox.universel.compiler.CompilationDefaults.*;

/**
 * This is where semantic analysis really begins. Looks at what the incoming script looks like and assembles a
 * canonical form consisting of the types declared or a surrogate type to house the members and executable body lines.
 */
public class ScriptAssembler extends AbstractSemanticAnalyser {
    private static AtomicLong generatedScriptIndex = new AtomicLong();
    private static final List<Node> EMPTY_SCRIPT_SYNTHETIC_ELEMENTS = singletonList(new NullLiteralExpr());
    private static final Set<Class<?>> STATIC_IMPL_IF_ALL_MEMBERS_STATIC = new HashSet(
        asList(MethodDeclaration.class, FieldDeclaration.class)
    );

    private boolean withinMainMethod;
    private TypeReference executionMethodReturnType;

    @Override
    public Node visitScript(final Script script) {
        //--------------------------------------------------------------------------------------------------------------
        // Stop if the script is fully assembled: ClassDeclaration { main()  [execute()] }
        //--------------------------------------------------------------------------------------------------------------
        if ( scriptAlreadyAssembled(script) ) return script;

        //--------------------------------------------------------------------------------------------------------------
        // If we're midway through assembly, decorate additional sections when they become available.
        //--------------------------------------------------------------------------------------------------------------
        if ( scriptExecutionMethodMissingReturnType(script) ) return addExecutionMethodReturnType(script);
        if ( scriptMainMethodMissingReturnType(script) ) return addMainMethodReturnType(script);
        if ( scriptMissingResultType(script) ) return addScriptResultType(script);

        //--------------------------------------------------------------------------------------------------------------
        // Create a new assembly with members and executable method(s) housed in a new class declaration.
        // First determine whether we need to generate instance methods or a purely static implementation.
        //--------------------------------------------------------------------------------------------------------------
        return shouldGenerateStaticImplementation(script) ? generateStaticImplementation(script) : generateNonStaticImplementation(script);
    }

    private Node addExecutionMethodReturnType(final Script script) {
        Script transformedScript = (Script)super.visitScript(script);
        if ( !scriptExecutionMethodMissingReturnType(transformedScript) ) {
            MethodDeclaration executionMethod = findScriptExecuteMethod(transformedScript);
            executionMethodReturnType = executionMethod.getReturnType();
            transformedScript = (Script)super.visitScript(transformedScript);
        }

        return transformedScript;
    }

    private Node addMainMethodReturnType(final Script script) {
        return super.visitScript(script);
    }

    private boolean scriptMainMethodMissingReturnType(final Script script) {
        final MethodDeclaration scriptMainMethod = findScriptMainMethod(script);
        return scriptMainMethod != null
               && scriptMainMethod.getReturnType() == null;

    }

    private boolean scriptExecutionMethodMissingReturnType(final Script script) {
        final MethodDeclaration scriptExecuteMethod = findScriptExecuteMethod(script);
        return scriptExecuteMethod != null
               && scriptExecuteMethod.getReturnType() == null;
    }

    @Override
    public MethodDeclaration visitMethodDeclaration(final MethodDeclaration node) {
        withinMainMethod = isScriptMain(node);
        try {
            if ( !(withinMainMethod || isScriptExecute(node)) ) return super.visitMethodDeclaration(node);

            if ( node.getReturnType() != null || node.getBody().getType() == null) return super.visitMethodDeclaration(node);

            return new MethodDeclaration(node.getModifiers(),
                                         node.getTypeParameters(),
                                         node.getDeclaringType(),
                                         (TypeReference)node.getBody().getType(),
                                         node.getName(),
                                         node.getParameters(),
                                         node.getBody());
        } finally {
            withinMainMethod = false;
        }
    }

    @Override
    public Node visitMethodCall(final InstanceMethodCall node) {
        if (!withinMainMethod || !SCRIPT_EXECUTE_METHOD_NAME.equals(node.getName()) || executionMethodReturnType == null || node.getReturnType() != null) return node;

        return new InstanceMethodCall(node.getTokenImage(),
                                      node.getDeclaringType(),
                                      executionMethodReturnType,
                                      node.getName(),
                                      node.getParameterTypes(),
                                      node.getParameters());
    }


    private Node addScriptResultType(final Script script) {
        MethodDeclaration scriptMainMethod = findScriptMainMethod(script);
        if ( scriptMainMethod == null ) return script;
        if ( scriptMainMethod.getBody().getType() == null ) return script;

        return new Script(scriptMainMethod.getBody().getType(), script.getPackageDeclaration(), script.getImportDeclaration(), script.getBody());
    }

    private MethodDeclaration findScriptMainMethod(final Script script) {
        return findScriptMethod(script, this::isScriptMain);
    }

    private MethodDeclaration findScriptExecuteMethod(final Script script) {
        return findScriptMethod(script, this::isScriptExecute);
    }

    private MethodDeclaration findScriptMethod(final Script script, Predicate<MethodDeclaration> methodFilter) {
        return script.getBody()
                     .stream()
                     .filter(TypeDeclaration.class::isInstance)
                     .map(TypeDeclaration.class::cast)
                     .flatMap(td -> td.getMembers().stream()
                                      .filter(MethodDeclaration.class::isInstance)
                                      .map(MethodDeclaration.class::cast)
                                      .filter(methodFilter))
                     .map(MethodDeclaration.class::cast)
                     .findFirst().orElse(null);
    }

    private boolean scriptMissingResultType(final Script script) {
        return findScriptMainMethod(script) != null && script.getType() == null;
    }

    private boolean scriptAlreadyAssembled(final Script script) {
        MethodDeclaration scriptMainMethod = findScriptMainMethod(script);
        MethodDeclaration scriptExecuteMethod = findScriptExecuteMethod(script);
        return !scriptMissingResultType(script)
               && scriptMainMethod != null
               && !scriptMainMethodMissingReturnType(script)
               && (scriptExecuteMethod == null || !scriptExecutionMethodMissingReturnType(script));
    }

    private boolean isScriptMain(final MethodDeclaration md) {
        return SCRIPT_MAIN_METHOD_NAME.equals(md.getName())
               && Objects.equals(md.getModifiers(), SCRIPT_MAIN_METHOD_MODIFIERS)
               && (md.getParameters().isEmpty()
                   || (md.getParameters().size() == 1 && MAIN_BINDING_PARAM_NAME.equals(md.getParameters().getNodes().get(0).getName().getName()))
               );
    }

    private boolean isScriptExecute(final MethodDeclaration md) {
        return SCRIPT_EXECUTE_METHOD_NAME.equals(md.getName())
               && Objects.equals(md.getModifiers(), SCRIPT_EXECUTE_METHOD_MODIFIERS)
               && (md.getParameters().isEmpty()
                   || (md.getParameters().size() == 1 && MAIN_BINDING_PARAM_NAME.equals(md.getParameters().getNodes().get(0).getName().getName()))
               );
    }

    private boolean shouldGenerateStaticImplementation(final Script script) {
        return script.getBody().stream()
                     .filter(m -> STATIC_IMPL_IF_ALL_MEMBERS_STATIC.contains(m.getClass()))
                     .map(Declaration.class::cast)
                     .map(Declaration::getModifiers)
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

        MethodDeclaration scriptMainMethod = new MethodDeclaration(SCRIPT_MAIN_METHOD_MODIFIERS,
                                                                   null,
                                                                   (TypeReference) scriptResult,
                                                                   SCRIPT_MAIN_METHOD_NAME,
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
                                                                .addNotNull(scriptMainMethod)
                                                                .build(),
                                                            null,
                                                            null
        );

        scriptMainMethod.setDeclaringType(new TypeDeclarationReference(scriptClass));

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
        if (methodBodyElements.isEmpty()) {
            methodBodyElements = EMPTY_SCRIPT_SYNTHETIC_ELEMENTS;
        }

        final String generatedTypename = generateUniqueExecutionClassname();
        Type scriptResult = InternalNodeSequence.builder().add(methodBodyElements.toArray(new Node[methodBodyElements.size()])).build().getType();

        final NamePath fqGeneratedTypeName = script.getPackageDeclaration() == null ? new SimpleNamePath(generatedTypename) : script.getPackageDeclaration().getName().joinSingleton(generatedTypename);

        MethodDeclaration scriptExecuteMethod = new MethodDeclaration(valueOf(PUBLIC),
                                                                      null,
                                                                      (TypeReference) scriptResult,
                                                                      SCRIPT_EXECUTE_METHOD_NAME,
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
        MethodDeclaration scriptMainMethod = new MethodDeclaration(SCRIPT_MAIN_METHOD_MODIFIERS,
                                                                   null,
                                                                   (TypeReference) scriptResult,
                                                                   SCRIPT_MAIN_METHOD_NAME,
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
                                                                   NodeSequence.<Node>builder()
                                                                       .add(new ObjectCreationExpression(script.getTokenImage(), new TypeNameReference(script.getTokenImage(), fqGeneratedTypeName)))
                                                                       .add(new InstanceMethodCall(script.getTokenImage(),
                                                                                                   new TypeNameReference(script.getTokenImage(), fqGeneratedTypeName),
                                                                                                   (TypeReference)scriptResult,
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
        );


        return new Script(scriptResult,
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
                                                            .add(scriptExecuteMethod)
                                                            .add(scriptMainMethod)
                                                            .build(),
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

    private boolean isTypeDeclaration(final Node node) {
        return node instanceof TypeDeclaration;
    }

    private boolean isTypeMember(final Node n) {
        return n instanceof MethodDeclaration
            || n instanceof FieldDeclaration;
    }
}
