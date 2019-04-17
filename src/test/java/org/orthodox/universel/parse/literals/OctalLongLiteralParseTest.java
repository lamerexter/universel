package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.OctalLongLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class OctalLongLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0771L";
        OctalLongLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(7));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private OctalLongLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(OctalLongLiteralExpr.class));
        return (OctalLongLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("00L").getTokenImage().getImage(), equalTo("00L"));
        assertThat(parse("01l").getTokenImage().getImage(), equalTo("01l"));
        assertThat(parse("0127L").getTokenImage().getImage(), equalTo("0127L"));
        assertThat(parse("012__7L").getTokenImage().getImage(), equalTo("012__7L"));
        assertThat(parse("0777L").getTokenImage().getImage(), equalTo("0777L"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-012L");
        }, "Unary minus is handled by the parser");
    }
}
