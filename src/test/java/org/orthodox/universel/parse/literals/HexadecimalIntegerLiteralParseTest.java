package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.Operator;
import org.orthodox.universel.ast.UnaryExpression;
import org.orthodox.universel.ast.literals.HexadecimalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

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

    @Test
    public void negativeParsePosition() throws Exception{
        // When
        String input = "\n  -0xffee12";
        UnaryExpression expr = parseUnaryMinus(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(11));
        assertThat(input, endsWith(expr.getExpression().getTokenImage().getImage()));
    }

    private HexadecimalIntegerLiteralExpr parse(String input) {
        return Universal.parse(HexadecimalIntegerLiteralExpr.class, input);
    }

    private UnaryExpression parseUnaryMinus(String input) {
        UnaryExpression unaryExpression = Universal.parse(UnaryExpression.class, input);
        assertThat(unaryExpression.getOperator(), equalTo(Operator.MINUS));
        return unaryExpression;
    }

    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0x0").getTokenImage().getImage(), equalTo("0x0"));
        assertThat(parse("0X1").getTokenImage().getImage(), equalTo("0X1"));
        assertThat(parse("0xffeedd").getTokenImage().getImage(), equalTo("0xffeedd"));
        assertThat(parse("0xff__ee__dd").getTokenImage().getImage(), equalTo("0xff__ee__dd"));
        assertThat(parse("0xffffffff").getTokenImage().getImage(), equalTo("0xffffffff"));
    }

    @Test
    public void positiveLongLiterals() {
        assertThat(parse("0x0L").getTokenImage().getImage(), equalTo("0x0L"));
        assertThat(parse("0X1l").getTokenImage().getImage(), equalTo("0X1l"));
        assertThat(parse("0xffeeddL").getTokenImage().getImage(), equalTo("0xffeeddL"));
        assertThat(parse("0xff__ee__ddL").getTokenImage().getImage(), equalTo("0xff__ee__ddL"));
        assertThat(parse("0xffffffffL").getTokenImage().getImage(), equalTo("0xffffffffL"));
    }

    @Test()
    public void negativeIntegerLiterals() {
        assertThat(parseUnaryMinus("-0x0").getExpression().getTokenImage().getImage(), equalTo("0x0"));
        assertThat(parseUnaryMinus("-0X1").getExpression().getTokenImage().getImage(), equalTo("0X1"));
        assertThat(parseUnaryMinus("-0xffeedd").getExpression().getTokenImage().getImage(), equalTo("0xffeedd"));
        assertThat(parseUnaryMinus("-0xff__ee__dd").getExpression().getTokenImage().getImage(), equalTo("0xff__ee__dd"));
        assertThat(parseUnaryMinus("-0xffffffff").getExpression().getTokenImage().getImage(), equalTo("0xffffffff"));
    }


    @Test()
    public void negativeLongLiterals() {
        assertThat(parseUnaryMinus("-0x0L").getExpression().getTokenImage().getImage(), equalTo("0x0L"));
        assertThat(parseUnaryMinus("-0X1l").getExpression().getTokenImage().getImage(), equalTo("0X1l"));
        assertThat(parseUnaryMinus("-0xffeeddL").getExpression().getTokenImage().getImage(), equalTo("0xffeeddL"));
        assertThat(parseUnaryMinus("-0xff__ee__ddL").getExpression().getTokenImage().getImage(), equalTo("0xff__ee__ddL"));
        assertThat(parseUnaryMinus("-0xffffffffL").getExpression().getTokenImage().getImage(), equalTo("0xffffffffL"));
    }
}
