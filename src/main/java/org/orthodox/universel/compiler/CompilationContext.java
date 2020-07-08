package org.orthodox.universel.compiler;

import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.core.models.NameValue;
import org.beanplanet.core.models.SimpleNameValue;
import org.beanplanet.core.models.path.NamePath;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.cst.ImportStmt;
import org.orthodox.universel.cst.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.beanplanet.core.util.IteratorUtil.asStream;

public class CompilationContext implements NameScope {
    private final Class<?> bindingType;
    private final VirtualMachine virtualMachine;
    private final Deque<NameValue<Resource>> compiledClassResources = new ArrayDeque<>();
    private final Deque<NameScope> scopes = new ArrayDeque<>();
    private final Deque<CompilingTypeInfo> compilingTypeInfos = new ArrayDeque<>();

    private final Messages messages;

    private static final List<ImportStmt> DEFAULT_IMPORTS = asList(
        new ImportStmt(true, "java", "lang"),
        new ImportStmt(true, "java", "util"),
        new ImportStmt(true, "java", "lang", "Math")
    );

    private final List<ImportStmt> getDefaultImports = DEFAULT_IMPORTS;

    public CompilationContext(final Class<?> bindingType,
                              final VirtualMachine virtualMachine,
                              final Messages messages) {
        this.bindingType = bindingType;
        this.virtualMachine = virtualMachine;
        this.messages = messages;
    }

    public Class<?> getBindingType() {
        return bindingType;
    }

    public Messages getMessages() {
        return messages;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public BytecodeHelper getBytecodeHelper() {
        return getVirtualMachine().getBytecodeHelper();
    }

    public void pushNameScope(NameScope scope) {
        scopes.push(scope);
    }

    public List<ImportStmt> getDefaultImports() {
        return getDefaultImports;
    }

    @Override
    public Node navigate(final NavigationStep step) {
        return asStream(scopes.descendingIterator())
                    .map(s -> s.navigate(step))
                    .filter(Objects::nonNull)
                    .filter(transformedNode -> !Objects.equals(transformedNode, step))
                    .findFirst().orElse(null);
    }

    @Override
    public boolean canResolve(String name) {
        return scopes.stream().anyMatch(s -> s.canResolve(name));
    }

    @Override
    public void generateAccess(String name) {
        scopes.stream().filter(s -> s.canResolve(name)).findFirst()
              .orElseThrow(() -> new RuntimeException("Cannot find symbol "+name))
              .generateAccess(name);
    }

    public void addCompiledType(final String fqClassName, Resource classBytes) {
        compiledClassResources.push(new SimpleNameValue<Resource>(fqClassName, classBytes));
    }

    public List<NameValue<Resource>> getCompiledClassResources() {
        return new ArrayList<>(compiledClassResources);
    }

    public void pushTypeInfo(CompilingTypeInfo typeInfo) {
       this.compilingTypeInfos.push(typeInfo);
    }

    public CompilingTypeInfo popTypeInfo() {
        return this.compilingTypeInfos.pop();
    }

    public CompilingTypeInfo peekTypeInfo() {
        return compilingTypeInfos.peek();
    }

    public static class CompilingTypeInfo {
        private Map<Object, AtomicInteger> generatedMethodNameSequences = new HashMap<>();
        private NamePath fqTypeName;

        public CompilingTypeInfo(NamePath fqTypeName) {
            this.fqTypeName = fqTypeName;
        }

        public NamePath getFullyQualifiedTypeName() {
            return fqTypeName;
        }

        public String generateSyntheticMethodName(NamePath nameHints) {
            AtomicInteger sequenceGenerator = generatedMethodNameSequences.computeIfAbsent(nameHints, k -> new AtomicInteger());
            return nameHints.joinSingleton(valueOf(sequenceGenerator.incrementAndGet())).join("$");
        }
    }
}
