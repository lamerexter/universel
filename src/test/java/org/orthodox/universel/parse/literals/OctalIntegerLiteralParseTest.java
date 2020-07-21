package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.Operator;
import org.orthodox.universel.ast.UnaryExpression;
import org.orthodox.universel.ast.literals.OctalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

public class OctalIntegerLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0771";
        OctalIntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(6));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    @Test
    public void negativeParsePosition() throws Exception{
        // When
        String input = "\n  -0771";
        UnaryExpression expr = parseUnaryMinus(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(7));
        assertThat(input, endsWith(expr.getExpression().getTokenImage().getImage()));
    }

    private OctalIntegerLiteralExpr parse(String input) throws Exception {
        return Universal.parse(OctalIntegerLiteralExpr.class, input);
    }

    private UnaryExpression parseUnaryMinus(String input) throws Exception {
        UnaryExpression unaryExpression = Universal.parse(UnaryExpression.class, input);
        assertThat(unaryExpression.getOperator(), equalTo(Operator.MINUS));
        return unaryExpression;
    }

    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("00").getTokenImage().getImage(), equalTo("00"));
        assertThat(parse("01").getTokenImage().getImage(), equalTo("01"));
        assertThat(parse("0127").getTokenImage().getImage(), equalTo("0127"));
        assertThat(parse("012__7").getTokenImage().getImage(), equalTo("012__7"));
        assertThat(parse("0777").getTokenImage().getImage(), equalTo("0777"));
    }

    @Test
    public void positiveLongLiterals() throws Exception{
        assertThat(parse("00L").getTokenImage().getImage(), equalTo("00L"));
        assertThat(parse("01l").getTokenImage().getImage(), equalTo("01l"));
        assertThat(parse("0127L").getTokenImage().getImage(), equalTo("0127L"));
        assertThat(parse("012__7L").getTokenImage().getImage(), equalTo("012__7L"));
        assertThat(parse("0777L").getTokenImage().getImage(), equalTo("0777L"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        assertThat(parseUnaryMinus("-00").getExpression().getTokenImage().getImage(), equalTo("00"));
        assertThat(parseUnaryMinus("-01").getExpression().getTokenImage().getImage(), equalTo("01"));
        assertThat(parseUnaryMinus("-0127").getExpression().getTokenImage().getImage(), equalTo("0127"));
        assertThat(parseUnaryMinus("-012__7").getExpression().getTokenImage().getImage(), equalTo("012__7"));
        assertThat(parseUnaryMinus("-0777").getExpression().getTokenImage().getImage(), equalTo("0777"));
    }

    @Test()
    public void negativeLongLiterals() throws Exception {
        assertThat(parseUnaryMinus("-00L").getExpression().getTokenImage().getImage(), equalTo("00L"));
        assertThat(parseUnaryMinus("-01l").getExpression().getTokenImage().getImage(), equalTo("01l"));
        assertThat(parseUnaryMinus("-0127L").getExpression().getTokenImage().getImage(), equalTo("0127L"));
        assertThat(parseUnaryMinus("-012__7L").getExpression().getTokenImage().getImage(), equalTo("012__7L"));
        assertThat(parseUnaryMinus("-0777L").getExpression().getTokenImage().getImage(), equalTo("0777L"));
    }

}
