package org.orthodox.universel.compiler;

import org.beanplanet.core.io.resource.Resource;

public class CompiledUnit {
    private Resource code;
    private String fullyQualifiedName;
    private String baseName;
    private long compilationTime;

    public CompiledUnit(Resource code, String fullyQualifiedName, String baseName, long compilationTime) {
        this.code = code;
        this.fullyQualifiedName = fullyQualifiedName;
        this.baseName = baseName;
        this.compilationTime = compilationTime;
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
