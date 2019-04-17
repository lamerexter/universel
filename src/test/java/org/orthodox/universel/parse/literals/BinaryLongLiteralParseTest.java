package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.BinaryIntegerLiteralExpr;
import org.orthodox.universel.ast.literals.BinaryLongLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class BinaryLongLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0b1010L";
        BinaryLongLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(9));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private BinaryLongLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(BinaryLongLiteralExpr.class));
        return (BinaryLongLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0b0L").getTokenImage().getImage(), equalTo("0b0L"));
        assertThat(parse("0B1l").getTokenImage().getImage(), equalTo("0B1l"));
        assertThat(parse("0b11110000L").getTokenImage().getImage(), equalTo("0b11110000L"));
        assertThat(parse("0b1111__0000L").getTokenImage().getImage(), equalTo("0b1111__0000L"));
        assertThat(parse("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L").getTokenImage().getImage(), equalTo("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        assertThat(parse("0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L").getTokenImage().getImage(), equalTo("0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"));
        assertThat(parse("0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000L").getTokenImage().getImage(), equalTo("0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000L"));
    }
}
