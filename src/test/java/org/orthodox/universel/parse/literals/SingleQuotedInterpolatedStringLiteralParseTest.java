package org.orthodox.universel.parse.literals;

import org.beanplanet.core.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.Universal;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.literals.BooleanLiteralExpr;
import org.orthodox.universel.cst.literals.IntegerLiteral;
import org.orthodox.universel.cst.literals.InterpolatedStringLiteralExpr;
import org.orthodox.universel.cst.literals.StringLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class SingleQuotedInterpolatedStringLiteralParseTest {
    private InterpolatedStringLiteralExpr parse(String input) {
        return Universal.parse(InterpolatedStringLiteralExpr.class, input);
    }

    @Test
    public void emptyString() {
        assertThat(parse("\"\"").getTokenImage().getImage(), equalTo("\"\""));
    }

    @Test
    public void singleCharacterString() {
        assertThat(parse("\"w\"").getTokenImage().getImage(), equalTo("\"w\""));
    }

    @Test
    public void multiCharacterString() {
        assertThat(parse("\"Hello World!\"").getTokenImage().getImage(), equalTo("\"Hello World!\""));
    }

    @Test
    public void escapeCharacters() {
        assertThat(parse("\"\\n\\t\\b\\r\\f\\'\\\"\"").getTokenImage().getImage(), equalTo("\"\\n\\t\\b\\r\\f\\'\\\"\""));
        assertThat(parse("\"\\00\"").getTokenImage().getImage(), equalTo("\"\\00\""));
        assertThat(parse("\"\\377\"").getTokenImage().getImage(), equalTo("\"\\377\""));
    }

    @Test
    public void unicodeEscapeCharacters() {
        assertThat(parse("\"\\u01bc\"").getTokenImage().getImage(), equalTo("\"\\u01bc\""));
        assertThat(parse("\"\\u01bcefg\\u1234\"").getTokenImage().getImage(), equalTo("\"\\u01bcefg\\u1234\""));
    }

    @Test
    void containingSingleDoubleQuote() {
        // When
        String input = "\"\\\"\"";
        InterpolatedStringLiteralExpr expr = parse(input);

        // Then
        assertThat(expr.getTokenImage(), equalTo(new TokenImage(1, 1, 1, 4, "\"\\\"\"")));

        // And
        assertThat(expr.getParts().size(), equalTo(1));
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage(), equalTo(new TokenImage(1, 2, 1, 3, "\\\"")));
    }

    @Test
    void uninterpolatedParsePosition() {
        // When
        String input = "  \"Hello World!\"";
        InterpolatedStringLiteralExpr expr = parse(input);

        // Then
        assertThat(expr.getTokenImage(), equalTo(new TokenImage(1, 3, 1, 16, "\"Hello World!\"")));

        // And
        assertThat(expr.getParts().size(), equalTo(1));
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage(), equalTo(new TokenImage(1, 4, 1, 15, StringUtil.trim(input.trim(), "\""))));
    }

    @Test
    void interpolatedParsePosition() {
        // When
        String input = "\"Hello${ 1234 } World!\"";
        InterpolatedStringLiteralExpr expr = parse(input);

        // Then
        assertThat(expr.getTokenImage(), equalTo(new TokenImage(1, 1, 1, 23, "\"Hello${1234} World!\"")));

        // And
        assertThat(expr.getParts().size(), equalTo(3));
        assertThat(expr.getParts().get(0), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(0).getTokenImage(), equalTo(new TokenImage(1, 2, 1, 6, "Hello")));

        // And
        assertThat(expr.getParts().get(1), instanceOf(IntegerLiteral.class));
        assertThat(expr.getParts().get(1).getTokenImage(), equalTo(new TokenImage(1, 10, 1, 13, "1234")));

        // And
        assertThat(expr.getParts().get(2), instanceOf(StringLiteralExpr.class));
        assertThat(expr.getParts().get(2).getTokenImage(), equalTo(new TokenImage(1, 16, 1, 22, " World!")));
    }
}
