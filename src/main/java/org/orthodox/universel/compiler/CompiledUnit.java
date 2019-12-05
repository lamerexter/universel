package org.orthodox.universel.compiler;

import org.beanplanet.core.io.resource.Resource;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.Script;

public class CompiledUnit {
    private Node astNode;
    private Resource code;
    private String fullyQualifiedName;
    private String baseName;
    private long compilationTime;

    private final Messages messages;

    public CompiledUnit(Node astNode, Messages messages, Resource code, String fullyQualifiedName, String baseName, long compilationTime) {
        this.astNode = astNode;
        this.messages = messages;
        this.code = code;
        this.fullyQualifiedName = fullyQualifiedName;
        this.baseName = baseName;
        this.compilationTime = compilationTime;
    }

    public Node getAstNode() { return astNode; }

    public Messages getMessages() {
        return messages;
    }

    public Resource getCode() {
        return code;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public String getBaseName() {
        return baseName;
    }

    public long getCompilationTime() {
        return compilationTime;
    }
}
