package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.BinaryLiteralExpr;
import org.orthodox.universel.ast.literals.BooleanLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class BooleanLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  true";
        BooleanLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(6));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private BooleanLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(BooleanLiteralExpr.class));
        return (BooleanLiteralExpr)literalExpr;
    }


    @Test
    public void trueFalseLiterals() throws Exception{
        assertThat(parse("true").getTokenImage().getImage(), equalTo("true"));
        assertThat(parse("false").getTokenImage().getImage(), equalTo("false"));
    }
}
