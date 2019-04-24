package org.orthodox.universel.ast;

/**
 * An interface implemented by classes which can be visited by a {@link UniversalCodeVisitor}.
 */
public interface UniversalCodeVisitable {
    /**
     * Called to accept a visitor.
     *
     * @param visitor the visitor visiting the node.
     * @return true if visitation is to continue after this visit, false if visitation is requested to stop after this visit.
     */
    boolean accept(UniversalCodeVisitor visitor);
}
