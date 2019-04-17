package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.HexadecimalLongLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class HexadecimalLongLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0xffee12L";
        HexadecimalLongLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(11));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private HexadecimalLongLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(HexadecimalLongLiteralExpr.class));
        return (HexadecimalLongLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0x0L").getTokenImage().getImage(), equalTo("0x0L"));
        assertThat(parse("0X1l").getTokenImage().getImage(), equalTo("0X1l"));
        assertThat(parse("0xffeeddL").getTokenImage().getImage(), equalTo("0xffeeddL"));
        assertThat(parse("0xff__ee__ddL").getTokenImage().getImage(), equalTo("0xff__ee__ddL"));
        assertThat(parse("0xffffffffL").getTokenImage().getImage(), equalTo("0xffffffffL"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-0xffL");
        }, "Unary minus is handled by the parser");
    }
}
