package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.DecimalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class DecimalIntegerLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  12345";
        DecimalIntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(7));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private DecimalIntegerLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(DecimalIntegerLiteralExpr.class));
        return (DecimalIntegerLiteralExpr)literalExpr;
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0").getTokenImage().getImage(), equalTo("0"));
        assertThat(parse("1").getTokenImage().getImage(), equalTo("1"));
        assertThat(parse("1234").getTokenImage().getImage(), equalTo("1234"));
        assertThat(parse("1000__1000__1000").getTokenImage().getImage(), equalTo("1000__1000__1000"));
        assertThat(parse(Integer.toString(Integer.MAX_VALUE)).getTokenImage().getImage(), equalTo(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test
    public void positiveLongLiterals() throws Exception{
        assertThat(parse("0l").getTokenImage().getImage(), equalTo("0l"));
        assertThat(parse("1L").getTokenImage().getImage(), equalTo("1L"));
        assertThat(parse("1234L").getTokenImage().getImage(), equalTo("1234L"));
        assertThat(parse("1000__1000__1000L").getTokenImage().getImage(), equalTo("1000__1000__1000L"));
        assertThat(parse(Long.toString(Integer.MAX_VALUE)+"L").getTokenImage().getImage(), equalTo(Long.toString(Integer.MAX_VALUE)+"L"));
    }

    @Test
    public void positiveBigLiterals() throws Exception{
        assertThat(parse("0I").getTokenImage().getImage(), equalTo("0I"));
        assertThat(parse("1I").getTokenImage().getImage(), equalTo("1I"));
        assertThat(parse("1234I").getTokenImage().getImage(), equalTo("1234I"));
        assertThat(parse("1000__1000__1000I").getTokenImage().getImage(), equalTo("1000__1000__1000I"));
        assertThat(parse(Long.toString(Integer.MAX_VALUE)+"I").getTokenImage().getImage(), equalTo(Long.toString(Integer.MAX_VALUE)+"I"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-1");
        }, "Unary minus is handled by the parser");
    }

    @Test()
    public void negativeLongLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-1L");
        }, "Unary minus is handled by the parser");
    }

    @Test()
    public void negativeBigLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-1I");
        }, "Unary minus is handled by the parser");
    }
}
