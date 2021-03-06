package org.orthodox.universel.exec.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SingleQuoteInterpolatedStringLiteralsTest {
    @Test
    public void emptyString() {
        assertThat(Universal.execute("\"\""), equalTo(""));
    }

    @Test
    public void singleCharacterString() {
        assertThat(Universal.execute("\"w\""), equalTo("w"));
    }

    @Test
    public void multiCharacterString() {
        assertThat(Universal.execute("\"Hello World!\""), equalTo("Hello World!"));
    }

    @Test
    public void escapeCharacters() {
        assertThat(Universal.execute("\"\\n\\t\\b\\r\\f\\\\\\'\\\"\""), equalTo("\n\t\b\r\f\\'\""));
    }

    @Test
    public void escapeStartCharacterAtEndOfString() {
        assertThat(Universal.execute("\"HelloWorld\\\\\""), equalTo("HelloWorld\\"));
    }

    @Test
    public void octal2DigitEscapeCharacters() {
        assertThat("\00", equalTo("\00")); // Java example
        assertThat(Universal.execute("\"\\00\""), equalTo("\00")); // Universal equivalent

        assertThat("\77", equalTo("\77")); // Java example
        assertThat(Universal.execute("\"\\77\""), equalTo("\77")); // Universal equivalent

        assertThat("xxx\77yyy", equalTo("xxx\77yyy")); // Java example
        assertThat(Universal.execute("\"xxx\\77yyy\""), equalTo("xxx\77yyy")); // Universal equivalent


        assertThat("xxx\1\77\12yyy", equalTo("xxx\1\77\12yyy")); // Java example
        assertThat(Universal.execute("\"xxx\\1\\77\\12yyy\""), equalTo("xxx\1\77\12yyy")); // Universal equivalent
    }

    @Test
    public void octal3DigitEscapeCharacters() {
        assertThat("\000", equalTo("\000")); // Java example
        assertThat(Universal.execute("\"\\000\""), equalTo("\000")); // Universal equivalent

        assertThat("\377", equalTo("\377")); // Java example
        assertThat(Universal.execute("\"\\377\""), equalTo("\377")); // Universal equivalent

        assertThat("xxx\555yyy", equalTo("xxx\555yyy")); // Java example
        assertThat(Universal.execute("\"xxx\\355yyy\""), equalTo("xxx\355yyy")); // Universal equivalent


        assertThat("xxx\1\367\12yyy", equalTo("xxx\1\367\12yyy")); // Java example
        assertThat(Universal.execute("\"xxx\\1\\367\\12yyy\""), equalTo("xxx\1\367\12yyy")); // Universal equivalent

        assertThat("xxx\1\3679\12yyy", equalTo("xxx\1\3679\12yyy")); // Java example
        assertThat(Universal.execute("\"xxx\\1\\3679\\12yyy\""), equalTo("xxx\1\3679\12yyy")); // Universal equivalent
    }

    @Test
    public void unicodeEscapeCharacters() {
        assertThat(Universal.execute("\"\\"+"u0000\""), equalTo("\u0000")); // character 0

        assertThat(Universal.execute("\"\\"+"u0024\""), equalTo("$")); // ASCII
        assertThat(Universal.execute("\"\\"+"u00A3\""), equalTo("£")); // Extended ASCII

        assertThat(Universal.execute("\"\\"+"u2264\""), equalTo("\u2264")); // Less than or equal to

        assertThat(Universal.execute("\"Hello\\"+"u2264World!\""), equalTo("Hello\u2264World!")); // Less than or equal to
        assertThat(Universal.execute("\"Hello\\"+"u2264\\"+"u00A3World!\""), equalTo("Hello\u2264\u00A3World!")); // Less than or equal to
    }

    @Test
    public void emptyInterpolation() {
        assertThat(Universal.execute("\"${}\""), equalTo(""));
        assertThat(Universal.execute("\"${  }\""), equalTo(""));
    }

    @Test
    public void interpolationOfLiterals() {
        assertThat(Universal.execute("\"${ true }\""), equalTo("true"));
        assertThat(Universal.execute("\"${ false }\""), equalTo("false"));

        assertThat(Universal.execute("\"${ 'Hello World!' }\""), equalTo("Hello World!"));

        assertThat(Universal.execute("\"${ 1234 }\""), equalTo("1234"));
        assertThat(Universal.execute("\"${ 1234L }\""), equalTo("1234"));
        assertThat(Universal.execute("\"${ 1234L }\""), equalTo("1234"));

        assertThat(Universal.execute("\"${ 1234.5f }\""), equalTo("1234.5"));
        assertThat(Universal.execute("\"${ 1234.5d }\""), equalTo("1234.5"));
        assertThat(Universal.execute("\"${ 1234D }\""), equalTo("1234"));
    }

    public static String staticMethodCall() {
        return "This is the expected result";
    }

    @Test
    public void interpolationOfMethodCall() {
        assertThat(Universal.execute("import "+getClass().getName()+".staticMethodCall \"${ staticMethodCall() }\""), equalTo(staticMethodCall()));
    }

    @Test
    public void nestedStringInterpolation() {
        assertThat(Universal.execute("\"${ \"Hello World!\" }\""), equalTo("Hello World!"));

        assertThat(Universal.execute("\"${ \"${'Hello World!'}\" }\""), equalTo("Hello World!"));
        assertThat(Universal.execute("\"${ \"${ 'Hello World!'    }\" }\""), equalTo("Hello World!"));

        assertThat(Universal.execute("\"${ \"${ \" ${ \"Hello World!\" } \" }\" }\""), equalTo(" Hello World! "));
        assertThat(Universal.execute("\"${\"${\"${1234}\"}\"}\""), equalTo("1234"));
    }

    @Test
    public void unescapedUninterpolatedDollar() {
        assertThat(Universal.execute("\"I owe you $100.50 for the fish!\""), equalTo("I owe you $100.50 for the fish!"));
        assertThat(Universal.execute("\"I owe you $s for the fish!\""), equalTo("I owe you $s for the fish!"));
    }

    @Test
    public void escapedUninterpolatedDollar() {
        assertThat(Universal.execute("\"I owe you \\$100.50 for the fish!\""), equalTo("I owe you $100.50 for the fish!"));
    }

    @Test
    public void escapedUninterpolatedDollarBrace() {
        assertThat(Universal.execute("\"I owe you \\${100.50} for the fish!\""), equalTo("I owe you ${100.50} for the fish!"));
    }
}
