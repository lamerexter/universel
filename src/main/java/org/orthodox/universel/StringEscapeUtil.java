package org.orthodox.universel;

import org.beanplanet.core.UncheckedException;
import org.beanplanet.core.io.IoException;
import org.beanplanet.core.lang.Assert;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class StringEscapeUtil {
    public static String unescapeUniversalCharacterEscapeSequences(String input) {
        StringWriter sw = new StringWriter(input.length());
        unescapeUniversalCharacterEscapeSequences(sw, input);
        return sw.toString();
    }

    public static void unescapeUniversalCharacterEscapeSequences(Writer out, String input) {
        Assert.notNull(out, "The writer may not be null");
        if (input == null) return;

        try {
            boolean inEscape = false;
            StringBuilder unicodeSeq = null;
            int inputLength = input.length();
            for (int n=0; n < inputLength; n++) {
                char ch = input.charAt(n);

                if ( inEscape ) {
                    inEscape = false;

                    switch ( ch )
                    {
                        case 'n':   ch ='\n'; break;
                        case 'r':   ch ='\r'; break;
                        case 't':   ch ='\t'; break;
                        case 'b':   ch ='\b'; break;
                        case 'f':   ch ='\f'; break;
                        case '\\':  ch ='\\'; break;
                        case '\'':  ch ='\''; break;
                        case '\"':  ch ='"'; break;
                        case 'u':   {
                            // It's a unicode escape sequence. TODO: supplementary characters?
                            if (unicodeSeq == null) unicodeSeq = new StringBuilder(4);
                            else unicodeSeq.setLength(0);

                            for (int i=0; i < 4; i++) {
                                if (n+1 >= input.length()) break;

                                unicodeSeq.append(input.charAt(++n));
                            }

                            if (unicodeSeq.length() == 4) {
                                try {
                                    ch = (char)Integer.parseInt(unicodeSeq.toString(), 16);
                                } catch (NumberFormatException nfe) {
                                    throw new UncheckedException("Unable to parse unicode value in universal string: " + unicodeSeq, nfe);
                                }
                            } else {
                                throw new UncheckedException("Incomplete unicode sequence detected in universal string: " +unicodeSeq);
                            }
                            break;
                        }

                        case '0' :
                        case '1' :
                        case '2' :
                        case '3' :
                        case '4' :
                        case '5' :
                        case '6' :
                        case '7' : {
                            // It's an octal character sequence (e.g. \17 or \377))
                            int chSeq = 0;
                            for (int i=0; i < 3; i++) {
                                chSeq = (chSeq << 3) | (ch - '0');

                                if (n+1 >= input.length()) break;

                                ch = input.charAt(n+1);
                                if ( !(ch >= '0' && ch <= '7') ) break;
                                n++;
                            }
                            ch = (char)chSeq;

                        }
                     }
                } else if (ch == '\\') {
                    inEscape = true;
                    continue;
                }
                out.write(ch);
            }

            // Deal with the situation where the last character was a slash. Just output it.
            if ( inEscape ) {
                out.write('\\');
            }
        } catch (IOException e) {
            throw new IoException("I/O error during unescape of universal string: ", e);
        }
    }
}
