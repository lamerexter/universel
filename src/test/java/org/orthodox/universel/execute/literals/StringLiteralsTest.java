package org.orthodox.universel.execute.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringLiteralsTest {
    @Test
    public void emptyString() throws Exception{
        assertThat(Universal.execute("''"), equalTo(""));
    }

    @Test
    public void singleCharacterString() throws Exception{
        assertThat(Universal.execute("'w'"), equalTo("w"));
    }

    @Test
    public void multiCharacterString() throws Exception{
        assertThat(Universal.execute("'Hello World!'"), equalTo("Hello World!"));
    }

    @Test
    public void escapeCharacters() throws Exception{
        assertThat(Universal.execute("'\\n\\t\\b\\r\\f\\\\\\'\\\"'"), equalTo("\n\t\b\r\f\\'\""));
    }

    @Test
    public void escapeStartCharacterAtEndOfString() throws Exception{
        assertThat(Universal.execute("'HelloWorld\\\\'"), equalTo("HelloWorld\\"));
    }

    @Test
    public void octal2DigitEscapeCharacters() throws Exception{
        assertThat("\00", equalTo("\00")); // Java example
        assertThat(Universal.execute("'\\00'"), equalTo("\00")); // Universal equivalent

        assertThat("\77", equalTo("\77")); // Java example
        assertThat(Universal.execute("'\\77'"), equalTo("\77")); // Universal equivalent

        assertThat("xxx\77yyy", equalTo("xxx\77yyy")); // Java example
        assertThat(Universal.execute("'xxx\\77yyy'"), equalTo("xxx\77yyy")); // Universal equivalent


        assertThat("xxx\1\77\12yyy", equalTo("xxx\1\77\12yyy")); // Java example
        assertThat(Universal.execute("'xxx\\1\\77\\12yyy'"), equalTo("xxx\1\77\12yyy")); // Universal equivalent
    }

    @Test
    public void octal3DigitEscapeCharacters() throws Exception{
        assertThat("\000", equalTo("\000")); // Java example
        assertThat(Universal.execute("'\\000'"), equalTo("\000")); // Universal equivalent

        assertThat("\377", equalTo("\377")); // Java example
        assertThat(Universal.execute("'\\377'"), equalTo("\377")); // Universal equivalent

        assertThat("xxx\555yyy", equalTo("xxx\555yyy")); // Java example
        assertThat(Universal.execute("'xxx\\355yyy'"), equalTo("xxx\355yyy")); // Universal equivalent


        assertThat("xxx\1\367\12yyy", equalTo("xxx\1\367\12yyy")); // Java example
        assertThat(Universal.execute("'xxx\\1\\367\\12yyy'"), equalTo("xxx\1\367\12yyy")); // Universal equivalent

        assertThat("xxx\1\3679\12yyy", equalTo("xxx\1\3679\12yyy")); // Java example
        assertThat(Universal.execute("'xxx\\1\\3679\\12yyy'"), equalTo("xxx\1\3679\12yyy")); // Universal equivalent
    }

    @Test
    public void unicodeEscapeCharacters() throws Exception{
        assertThat(Universal.execute("'\\"+"u0000'"), equalTo("\u0000")); // character 0

        assertThat(Universal.execute("'\\"+"u0024'"), equalTo("$")); // ASCII
        assertThat(Universal.execute("'\\"+"u00A3'"), equalTo("£")); // Extended ASCII

        assertThat(Universal.execute("'\\"+"u2264'"), equalTo("\u2264")); // Less than or equal to
    }
}
