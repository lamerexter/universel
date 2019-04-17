package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.HexadecimalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class HexadecimalIntegerLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0xffee12";
        HexadecimalIntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(10));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private HexadecimalIntegerLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(HexadecimalIntegerLiteralExpr.class));
        return (HexadecimalIntegerLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0x0").getTokenImage().getImage(), equalTo("0x0"));
        assertThat(parse("0X1").getTokenImage().getImage(), equalTo("0X1"));
        assertThat(parse("0xffeedd").getTokenImage().getImage(), equalTo("0xffeedd"));
        assertThat(parse("0xff__ee__dd").getTokenImage().getImage(), equalTo("0xff__ee__dd"));
        assertThat(parse("0xffffffff").getTokenImage().getImage(), equalTo("0xffffffff"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-0xff");
        }, "Unary minus is handled by the parser");
    }
}
