package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.StringLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class TripleQuotedStringLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  \r\n '''\nHello World!\r\nabc'''";
        StringLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(3));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(2));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(5));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(6));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private StringLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(StringLiteralExpr.class));
        return (StringLiteralExpr)literalExpr;
    }

    @Test
    public void emptyString() throws Exception{
        assertThat(parse("''''''").getTokenImage().getImage(), equalTo("''''''"));
    }

    @Test
    public void singleCharacterString() throws Exception{
        assertThat(parse("'''w'''").getTokenImage().getImage(), equalTo("'''w'''"));
    }

    @Test
    public void singleCharacterMultilineString() throws Exception{
        assertThat(parse("'''\n\nw\r\n'''").getTokenImage().getImage(), equalTo("'''\n\nw\r\n'''"));
    }

    @Test
    public void multiCharacterString() throws Exception{
        assertThat(parse("'''Hello World!'''").getTokenImage().getImage(), equalTo("'''Hello World!'''"));
    }

    @Test
    public void multiCharacterMultilineString() throws Exception{
        assertThat(parse("'''\nHello\r\nWorld!\n\n'''").getTokenImage().getImage(), equalTo("'''\nHello\r\nWorld!\n\n'''"));
    }

    @Test
    public void escapeCharacters() throws Exception{
        assertThat(parse("'''\\n\\t\\b\\r\\f\\'\\\"'''").getTokenImage().getImage(), equalTo("'''\\n\\t\\b\\r\\f\\'\\\"'''"));
        assertThat(parse("'''\\00'''").getTokenImage().getImage(), equalTo("'''\\00'''"));
        assertThat(parse("'''\\377'''").getTokenImage().getImage(), equalTo("'''\\377'''"));
    }

    @Test
    public void unicodeEscapeCharacters() throws Exception{
        assertThat(parse("'''\\u01bc'''").getTokenImage().getImage(), equalTo("'''\\u01bc'''"));
        assertThat(parse("'''\\u01bcefg\\u1234'''").getTokenImage().getImage(), equalTo("'''\\u01bcefg\\u1234'''"));
    }
}
