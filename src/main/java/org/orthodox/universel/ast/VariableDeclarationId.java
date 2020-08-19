package org.orthodox.universel.ast;

public class VariableDeclarationId extends Node {
    /**
     * The name of the variable.
     */
    private final String name;

    /**
     * The number of array indices associated with the declaration.
     */
    private final int arrayCount;


    /**
     * Instantiates a new field declaration.
     *
     * @param startLine   the start line of the declaration.
     * @param startColumn the start column of the declaration.
     * @param endLine     the end line of the declaration.
     * @param endColumn   the end column of the declaration.
     * @param name        the name defined by this type declaration.
     * @param arrayCount  the number of dimensions, if this field declaration refers to an array type
     */
    public VariableDeclarationId(final int startLine,
                                 final int startColumn,
                                 final int endLine,
                                 final int endColumn,
                                 final String name,
                                 final int arrayCount
    ) {
        super(TokenImage.builder().startLine(startLine).startColumn(startColumn).endLine(endLine).endColumn(endColumn).build());
        this.name = name;
        this.arrayCount = arrayCount;
    }

    /**
     * Gets the name of the variable.
     *
     * @return the name of the variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of array indices associated with the declaration.
     *
     * @return the number of array indices associated with the declaration.
     */
    public int getArrayCount() {
        return arrayCount;
    }

    /**
     * Returns true if the type represented by this declaration represents an array.
     *
     * @return true if there are one or more array indices associated with this declaration, false otherwise.
     */
    public boolean isArray() {
        return arrayCount > 0;
    }
}
