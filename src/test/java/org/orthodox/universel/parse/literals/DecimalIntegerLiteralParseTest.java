package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.UnaryExpression;
import org.orthodox.universel.ast.literals.DecimalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

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

    @Test
    public void negativeParsePosition() throws Exception{
        // When
        String input = "\n  -  12345";
        UnaryExpression expr = parseUnaryMinus(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(10));
        assertThat(input, endsWith(expr.getExpression().getTokenImage().getImage()));
    }

    private DecimalIntegerLiteralExpr parse(String input) throws Exception {
        return Universal.parse(DecimalIntegerLiteralExpr.class, input);
    }

    private UnaryExpression parseUnaryMinus(String input) throws Exception {
        return Universal.parse(UnaryExpression.class, input);
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
        assertThat(parseUnaryMinus("-0").getExpression().getTokenImage().getImage(), equalTo("0"));
        assertThat(parseUnaryMinus("-1").getExpression().getTokenImage().getImage(), equalTo("1"));
        assertThat(parseUnaryMinus("-1234").getExpression().getTokenImage().getImage(), equalTo("1234"));
        assertThat(parseUnaryMinus("-1000__1000__1000").getExpression().getTokenImage().getImage(), equalTo("1000__1000__1000"));
        assertThat(parseUnaryMinus("-"+Integer.toString(Integer.MAX_VALUE)).getExpression().getTokenImage().getImage(), equalTo(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test()
    public void negativeLongLiterals() throws Exception {
        assertThat(parseUnaryMinus("-0l").getExpression().getTokenImage().getImage(), equalTo("0l"));
        assertThat(parseUnaryMinus("-1L").getExpression().getTokenImage().getImage(), equalTo("1L"));
        assertThat(parseUnaryMinus("-1234L").getExpression().getTokenImage().getImage(), equalTo("1234L"));
        assertThat(parseUnaryMinus("-1000__1000__1000L").getExpression().getTokenImage().getImage(), equalTo("1000__1000__1000L"));
        assertThat(parseUnaryMinus("-"+Long.toString(Integer.MAX_VALUE)+"L").getExpression().getTokenImage().getImage(), equalTo(Long.toString(Integer.MAX_VALUE)+"L"));
    }

    @Test()
    public void negativeBigLiterals() throws Exception {
        assertThat(parseUnaryMinus("-0I").getExpression().getTokenImage().getImage(), equalTo("0I"));
        assertThat(parseUnaryMinus("-1I").getExpression().getTokenImage().getImage(), equalTo("1I"));
        assertThat(parseUnaryMinus("-1234I").getExpression().getTokenImage().getImage(), equalTo("1234I"));
        assertThat(parseUnaryMinus("-1000__1000__1000I").getExpression().getTokenImage().getImage(), equalTo("1000__1000__1000I"));
        assertThat(parseUnaryMinus("-"+Long.toString(Integer.MAX_VALUE)+"I").getExpression().getTokenImage().getImage(), equalTo(Long.toString(Integer.MAX_VALUE)+"I"));
    }
}
