package org.orthodox.universel.parser.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.IntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class IntegerLiteralParserTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  12345";
        IntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(7));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private IntegerLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(IntegerLiteralExpr.class));
        return (IntegerLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0").getTokenImage().getImage(), equalTo("0"));
        assertThat(parse("1").getTokenImage().getImage(), equalTo("1"));
        assertThat(parse("1234").getTokenImage().getImage(), equalTo("1234"));
        assertThat(parse("1000__1000__1000").getTokenImage().getImage(), equalTo("1000__1000__1000"));
        assertThat(parse(Integer.toString(Integer.MAX_VALUE)).getTokenImage().getImage(), equalTo(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            assertThat(parse("-1").getTokenImage().getImage(), equalTo("1"));
        }, "Unary minus is handled by the parser");
    }
}
