package org.orthodox.universel.execute.literals;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TripleQuotedStringLiteralsTest {
    @Test
    public void emptyString() {
        assertThat(Universal.execute("''''''"), equalTo(""));
    }

    @Test
    public void singleCharacterString() {
        assertThat(Universal.execute("'''w'''"), equalTo("w"));
    }

    @Test
    public void singleCharacterMultilineString() {
        assertThat(Universal.execute("'''\n\nw\r\n'''"), Matchers.equalTo("\n\nw\r\n"));
    }

    @Test
    public void multiCharacterString() {
        assertThat(Universal.execute("'Hello World!'"), equalTo("Hello World!"));
    }

    @Test
    public void multiCharacterMultilineString() {
        assertThat(Universal.execute("'''\nHello\r\nWorld!\n\n'''"), Matchers.equalTo("\nHello\r\nWorld!\n\n"));
    }

    @Test
    public void escapeCharacters() {
        assertThat(Universal.execute("'''\\n\\t\\b\\r\\f\\\\\\'\\\"'''"), equalTo("\n\t\b\r\f\\'\""));
    }

    @Test
    public void escapeStartCharacterAtEndOfString() {
        assertThat(Universal.execute("'''HelloWorld\\\\'''"), equalTo("HelloWorld\\"));
    }

    @Test
    public void octal2DigitEscapeCharacters() {
        assertThat("\00", equalTo("\00")); // Java example
        assertThat(Universal.execute("'''\\00'''"), equalTo("\00")); // Universal equivalent

        assertThat("\77", equalTo("\77")); // Java example
        assertThat(Universal.execute("'''\\77'''"), equalTo("\77")); // Universal equivalent

        assertThat("xxx\77yyy", equalTo("xxx\77yyy")); // Java example
        assertThat(Universal.execute("'''xxx\\77yyy'''"), equalTo("xxx\77yyy")); // Universal equivalent


        assertThat("xxx\1\77\12yyy", equalTo("xxx\1\77\12yyy")); // Java example
        assertThat(Universal.execute("'''xxx\\1\\77\\12yyy'''"), equalTo("xxx\1\77\12yyy")); // Universal equivalent
    }

    @Test
    public void octal3DigitEscapeCharacters() {
        assertThat("\000", equalTo("\000")); // Java example
        assertThat(Universal.execute("'''\\000'''"), equalTo("\000")); // Universal equivalent

        assertThat("\377", equalTo("\377")); // Java example
        assertThat(Universal.execute("'''\\377'''"), equalTo("\377")); // Universal equivalent

        assertThat("xxx\555yyy", equalTo("xxx\555yyy")); // Java example
        assertThat(Universal.execute("'''xxx\\355yyy'''"), equalTo("xxx\355yyy")); // Universal equivalent


        assertThat("xxx\1\367\12yyy", equalTo("xxx\1\367\12yyy")); // Java example
        assertThat(Universal.execute("'''xxx\\1\\367\\12yyy'''"), equalTo("xxx\1\367\12yyy")); // Universal equivalent

        assertThat("xxx\1\3679\12yyy", equalTo("xxx\1\3679\12yyy")); // Java example
        assertThat(Universal.execute("'''xxx\\1\\3679\\12yyy'''"), equalTo("xxx\1\3679\12yyy")); // Universal equivalent
    }

    @Test
    public void unicodeEscapeCharacters() {
        assertThat(Universal.execute("'''\\"+"u0000'''"), equalTo("\u0000")); // character 0

        assertThat(Universal.execute("'''\\"+"u0024'''"), equalTo("$")); // ASCII
        assertThat(Universal.execute("'''\\"+"u00A3'''"), equalTo("£")); // Extended ASCII

        assertThat(Universal.execute("'''\\"+"u2264'''"), equalTo("\u2264")); // Less than or equal to

        assertThat(Universal.execute("'''Hello\\"+"u2264World!'''"), equalTo("Hello\u2264World!")); // Less than or equal to
        assertThat(Universal.execute("'''Hello\\"+"u2264\\"+"u00A3World!'''"), equalTo("Hello\u2264\u00A3World!")); // Less than or equal to
    }
}
