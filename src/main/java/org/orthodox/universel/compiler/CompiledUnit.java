package org.orthodox.universel.compiler;

import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.models.NameValue;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universel.UniversalException;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.ParseTree;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CompiledUnit<B> {
    private Class<B> bindingType;
    private Node astNode;
    private List<NameValue<Resource>> compiledClassResources;
    private long compilationTimeMillis;

    private final Messages messages;

    private List<Class<?>> loadedClasses = null;

    private static final SimpleUniversalClassloader DEFAULT_CLASSLOADER = new SimpleUniversalClassloader();

    public CompiledUnit(Class<B> bindingType, Node astNode, Messages messages, List<NameValue<Resource>> compiledClassResources, long compilationTimeMillis) {
        this.bindingType = bindingType;
        this.astNode = astNode;
        this.messages = messages;
        this.compiledClassResources = compiledClassResources;
        this.compilationTimeMillis = compilationTimeMillis;
    }

    public Class<B> getBindingType() {
        return bindingType;
    }

    public Node getAstNode() { return astNode; }

    public <T> T getAstNode(Class<T> astNodeType) {
        return new ParseTree(astNode).preorderStream()
                                        .filter(n -> astNodeType.isAssignableFrom(n.getClass()))
                                        .map(n -> (T)n)
                                        .findFirst()
                                        .orElseThrow(() -> new UniversalException(format("An AST node of the given type [%s] was not found",
                                                                                         astNodeType
                                                                                         )));

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Node> T getAstNode(final Predicate<Node> filter) {
        return (T)new ParseTree(astNode).preorderParentUnawareStream()
                                     .filter(filter)
                                     .findFirst()
                                     .orElseThrow(() -> new UniversalException("An AST node matching the given predicate was not found"));

    }

    public Messages getMessages() {
        return messages;
    }

    public List<NameValue<Resource>> getCompiledClassResources() {
        return compiledClassResources;
    }

    public long getCompilationTimeMillis() {
        return compilationTimeMillis;
    }

    public boolean hasErrors() {
        return getMessages().hasErrors();
    }

    public List<Class<?>> getCompiledClasses() {
        if (loadedClasses == null) {
            loadedClasses = compiledClassResources
                                .stream()
                                .map(nv -> DEFAULT_CLASSLOADER.defineClass(nv.getName(), nv.getValue().readFullyAsBytes()))
                                .collect(Collectors.toList());
        }
        return loadedClasses;
    }

    private static class SimpleUniversalClassloader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

}
