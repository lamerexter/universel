package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.BinaryIntegerLiteralExpr;
import org.orthodox.universel.ast.literals.OctalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class BinaryIntegerLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0b1010";
        BinaryIntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(8));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private BinaryIntegerLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(BinaryIntegerLiteralExpr.class));
        return (BinaryIntegerLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0b0").getTokenImage().getImage(), equalTo("0b0"));
        assertThat(parse("0B1").getTokenImage().getImage(), equalTo("0B1"));
        assertThat(parse("0b11110000").getTokenImage().getImage(), equalTo("0b11110000"));
        assertThat(parse("0b1111__0000").getTokenImage().getImage(), equalTo("0b1111__0000"));
        assertThat(parse("0b0111_1111_1111_1111_1111_1111_1111_1111").getTokenImage().getImage(), equalTo("0b0111_1111_1111_1111_1111_1111_1111_1111"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        assertThat(parse("0b1111_1111_1111_1111_1111_1111_1111_1111").getTokenImage().getImage(), equalTo("0b1111_1111_1111_1111_1111_1111_1111_1111"));
        assertThat(parse("0b1000_0000_0000_0000_0000_0000_0000_0000").getTokenImage().getImage(), equalTo("0b1000_0000_0000_0000_0000_0000_0000_0000"));
    }
}
