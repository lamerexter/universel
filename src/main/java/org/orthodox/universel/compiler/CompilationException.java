package org.orthodox.universel.compiler;

import org.orthodox.universal.parser.ParseException;

/**
 * A compilation exception, thrown during parsing or compilation of a compilation unit.
 */
public class CompilationException extends RuntimeException {
    public CompilationException(ParseException parseException) {
        super(parseException.getMessage());
    }
}
