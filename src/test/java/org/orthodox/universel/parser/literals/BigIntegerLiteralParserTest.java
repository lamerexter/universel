package org.orthodox.universel.parser.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.BigIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class BigIntegerLiteralParserTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  12345I";
        BigIntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(8));
        assertThat(expr.getTokenImage().getImage(), equalTo("12345I"));
    }

    private BigIntegerLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(BigIntegerLiteralExpr.class));
        return (BigIntegerLiteralExpr)literalExpr;
    }

    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0I").getTokenImage().getImage(), equalTo("0I"));
        assertThat(parse("1I").getTokenImage().getImage(), equalTo("1I"));
        assertThat(parse("1234I").getTokenImage().getImage(), equalTo("1234I"));
        assertThat(parse("1000__1000__1000I").getTokenImage().getImage(), equalTo("1000__1000__1000I"));
        assertThat(parse(Long.toString(Integer.MAX_VALUE)+"1000I").getTokenImage().getImage(), equalTo(Long.toString(Integer.MAX_VALUE)+"1000I"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            assertThat(parse("-1I").getTokenImage().getImage(), equalTo("1"));
        }, "Unary minus is handled by the parser");
    }
}
