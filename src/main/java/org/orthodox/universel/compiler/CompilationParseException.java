package org.orthodox.universel.compiler;

import org.orthodox.universal.parser.ParseException;
import org.orthodox.universel.UniversalException;

/**
 * A compilation exception, thrown during parsing or compilation of a compilation unit.
 */
public class CompilationParseException extends UniversalException {
    public CompilationParseException(ParseException parseException) {
        super(parseException.getMessage());
    }
}
