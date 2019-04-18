package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.DecimalFloatingPointLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class DecimalFloatingPointLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  12345d";
        DecimalFloatingPointLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(8));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private DecimalFloatingPointLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(DecimalFloatingPointLiteralExpr.class));
        return (DecimalFloatingPointLiteralExpr)literalExpr;
    }


    @Test
    public void positiveUntypedLiterals() throws Exception{
        assertThat(parse("0.0").getTokenImage().getImage(), equalTo("0.0"));
        assertThat(parse(".1").getTokenImage().getImage(), equalTo(".1"));
        assertThat(parse("1.").getTokenImage().getImage(), equalTo("1."));
        assertThat(parse("1234.5").getTokenImage().getImage(), equalTo("1234.5"));
        assertThat(parse("123e02").getTokenImage().getImage(), equalTo("123e02"));
        assertThat(parse("1000__1000__1000.25").getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parse(Double.toString(Double.MAX_VALUE)).getTokenImage().getImage(), equalTo(Double.toString(Double.MAX_VALUE)));
    }

    @Test
    public void positiveFloatLiterals() throws Exception{
        assertThat(parse("0.0f").getTokenImage().getImage(), equalTo("0.0f"));
        assertThat(parse(".1f").getTokenImage().getImage(), equalTo(".1f"));
        assertThat(parse("1.f").getTokenImage().getImage(), equalTo("1.f"));
        assertThat(parse("1234.5f").getTokenImage().getImage(), equalTo("1234.5f"));
        assertThat(parse("123e02f").getTokenImage().getImage(), equalTo("123e02f"));
        assertThat(parse("1000__1000__1000.25").getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parse(Float.toString(Float.MAX_VALUE)+"f").getTokenImage().getImage(), equalTo(Float.toString(Float.MAX_VALUE)+"f"));
    }

    @Test
    public void positiveDoubleLiterals() throws Exception{
        assertThat(parse("0.0d").getTokenImage().getImage(), equalTo("0.0d"));
        assertThat(parse(".1d").getTokenImage().getImage(), equalTo(".1d"));
        assertThat(parse("1.d").getTokenImage().getImage(), equalTo("1.d"));
        assertThat(parse("1234.5d").getTokenImage().getImage(), equalTo("1234.5d"));
        assertThat(parse("123e02d").getTokenImage().getImage(), equalTo("123e02d"));
        assertThat(parse("1000__1000__1000.25").getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parse(Float.toString(Float.MAX_VALUE)+"d").getTokenImage().getImage(), equalTo(Float.toString(Float.MAX_VALUE)+"d"));
    }

    @Test
    public void positiveBigLiterals() throws Exception{
        assertThat(parse("0.0D").getTokenImage().getImage(), equalTo("0.0D"));
        assertThat(parse(".1D").getTokenImage().getImage(), equalTo(".1D"));
        assertThat(parse("1.D").getTokenImage().getImage(), equalTo("1.D"));
        assertThat(parse("1234.5D").getTokenImage().getImage(), equalTo("1234.5D"));
        assertThat(parse("123e02D").getTokenImage().getImage(), equalTo("123e02D"));
        assertThat(parse("1000__1000__1000.25").getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parse(Float.toString(Float.MAX_VALUE)+"D").getTokenImage().getImage(), equalTo(Float.toString(Float.MAX_VALUE)+"D"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-1.0");
        }, "Unary minus is handled by the parser");
    }
}
