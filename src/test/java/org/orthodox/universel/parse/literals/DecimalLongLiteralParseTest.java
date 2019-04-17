package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.DecimalLongLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class DecimalLongLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  12345L";
        DecimalLongLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(8));
        assertThat(expr.getTokenImage().getImage(), equalTo("12345L"));
    }

    private DecimalLongLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(DecimalLongLiteralExpr.class));
        return (DecimalLongLiteralExpr)literalExpr;
    }

    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0l").getTokenImage().getImage(), equalTo("0l"));
        assertThat(parse("1L").getTokenImage().getImage(), equalTo("1L"));
        assertThat(parse("1234L").getTokenImage().getImage(), equalTo("1234L"));
        assertThat(parse("1000__1000__1000L").getTokenImage().getImage(), equalTo("1000__1000__1000L"));
        assertThat(parse(Long.toString(Integer.MAX_VALUE)+"L").getTokenImage().getImage(), equalTo(Long.toString(Integer.MAX_VALUE)+"L"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-1L");
        }, "Unary minus is handled by the parser");
    }
}
