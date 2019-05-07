package org.orthodox.universel.parse.literals;

import org.beanplanet.core.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.literals.BooleanLiteralExpr;
import org.orthodox.universel.ast.literals.IntegerLiteral;
import org.orthodox.universel.ast.literals.InterpolatedStringLiteralExpr;
import org.orthodox.universel.ast.literals.StringLiteralExpr;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class TripleQuoteInterpolatedStringLiteralParseTest {
    @Test
    public void singleLineUninterpolatedParsePosition() throws Exception{
        // When
        String input = "\"\"\"Hello World!\"\"\"";
        InterpolatedStringLiteralExpr expr = parse(input);

        // Then
        assertThat(expr.getTokenImage().getStartLine(), equalTo(1));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(1));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(1));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(18));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));

        // And
        assertThat(expr.getParts().size(), equalTo(1));
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage().getStartLine(), equalTo(1));
        assertThat(expr.getParts().get(0).getTokenImage().getStartColumn(), equalTo(4));
        assertThat(expr.getParts().get(0).getTokenImage().getEndLine(), equalTo(1));
        assertThat(expr.getParts().get(0).getTokenImage().getEndColumn(), equalTo(15));
        assertThat(expr.getParts().get(0).getTokenImage().getImage(), equalTo(StringUtil.trim(input, "\"")));

    }

    @Test
    public void multiLineUninterpolatedParsePosition() throws Exception{
        // When
        String input = "\n\"\"\"\n\nHello World!\r\n\"\"\"";

        // Then
        InterpolatedStringLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage(), equalTo(new TokenImage(2, 1, 5, 3, input.trim())));

        // And
        assertThat(expr.getParts().size(), equalTo(1));
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage(), equalTo(new TokenImage(2, 4, 4, 14, "\n\nHello World!\r\n")));
    }

    @Test
    public void singleLineInterpolatedParsePosition() throws Exception{
        // When
        String input = "\"\"\"Hello${ 1234 } World!\"\"\"\"";
        InterpolatedStringLiteralExpr expr = parse(input);

        // Then
        assertThat(expr.getTokenImage(), equalTo(new TokenImage(1, 1, 1, 28, "\"\"\"Hello${1234} World!\"\"\"\"")));

        // And
        assertThat(expr.getParts().size(), equalTo(3));
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage(), equalTo(new TokenImage(1, 4, 1, 8, "Hello")));

        // And
        assertThat(expr.getParts().get(1), instanceOf(IntegerLiteral.class));
        assertThat(expr.getParts().get(1).getTokenImage(), equalTo(new TokenImage(1, 12, 1, 15, "1234")));

        // And
        assertThat(expr.getParts().get(2), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(2).getTokenImage(), equalTo(new TokenImage(1, 18, 1, 25, " World!\"")));
    }

    @Test
    public void multiLineInterpolatedParsePosition() throws Exception{
        // When
        String input = "\"\"\"\n Hello${\n 1111 }World!${true\r\n}\"${3333}\"\"\"\"\"";
        InterpolatedStringLiteralExpr expr = parse(input);

        // Then
        assertThat(expr.getTokenImage(), equalTo(new TokenImage(1, 1, 4, 14, "\"\"\"\n Hello${1111}World!${true}\"${3333}\"\"\"\"\"")));
        assertThat(expr.getParts().size(), equalTo(7));

        // And
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage(), equalTo(new TokenImage(1, 4, 2, 6, "\n Hello")));

        // And
        assertThat(expr.getParts().get(1), instanceOf(IntegerLiteral.class));
        assertThat(expr.getParts().get(1).getTokenImage(), equalTo(new TokenImage(3, 2, 3, 5, "1111")));

        // And
        assertThat(expr.getParts().get(2), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(2).getTokenImage(), equalTo(new TokenImage(3, 8, 3, 13, "World!")));

        // And
        assertThat(expr.getParts().get(3), instanceOf(BooleanLiteralExpr.class));
        assertThat(expr.getParts().get(3).getTokenImage(), equalTo(new TokenImage(3, 16, 3, 19, "true")));

        // And
        assertThat(expr.getParts().get(4), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(4).getTokenImage(), equalTo(new TokenImage(4, 2, 4, 2, "\"")));

        // And
        assertThat(expr.getParts().get(5), instanceOf(IntegerLiteral.class));
        assertThat(expr.getParts().get(5).getTokenImage(), equalTo(new TokenImage(4, 5, 4, 8, "3333")));

        // And
        assertThat(expr.getParts().get(6), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(6).getTokenImage(), equalTo(new TokenImage(4, 10, 4, 11, "\"\"")));
    }

    private InterpolatedStringLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(InterpolatedStringLiteralExpr.class));
        return (InterpolatedStringLiteralExpr)literalExpr;
    }

    @Test
    public void emptyString() throws Exception{
        assertThat(parse("\"\"\"\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\"\"\""));
    }

    @Test
    public void singleCharacterString() throws Exception{
        assertThat(parse("\"\"\"w\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"w\"\"\""));
    }

    @Test
    public void singleCharacterMultilineString() throws Exception{
        assertThat(parse("\"\"\"\n\nw\r\n\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\n\nw\r\n\"\"\""));
    }

    @Test
    public void multiCharacterString() throws Exception{
        assertThat(parse("\"\"\"Hello World!\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"Hello World!\"\"\""));
    }

    @Test
    public void multiCharacterMultilineString() throws Exception{
        assertThat(parse("\"\"\"\nHello\r\nWorld!\n\n\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\nHello\r\nWorld!\n\n\"\"\""));
    }

    @Test
    public void escapeCharacters() throws Exception{
        assertThat(parse("\"\"\"\\n\\t\\b\\r\\f\\'\\\"\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\\n\\t\\b\\r\\f\\'\\\"\"\"\""));
        assertThat(parse("\"\"\"\\00\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\\00\"\"\""));
        assertThat(parse("\"\"\"\\377\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\\377\"\"\""));
    }

    @Test
    public void unicodeEscapeCharacters() throws Exception{
        assertThat(parse("\"\"\"\\u01bc\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\\u01bc\"\"\""));
        assertThat(parse("\"\"\"\\u01bcefg\\u1234\"\"\"").getTokenImage().getImage(), equalTo("\"\"\"\\u01bcefg\\u1234\"\"\""));
    }
}
