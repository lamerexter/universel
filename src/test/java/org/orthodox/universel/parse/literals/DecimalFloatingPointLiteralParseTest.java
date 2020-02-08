package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universel.Universal;
import org.orthodox.universel.cst.Operator;
import org.orthodox.universel.cst.UnaryExpression;
import org.orthodox.universel.cst.literals.DecimalFloatingPointLiteralExpr;
import org.orthodox.universel.cst.literals.DecimalIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.orthodox.universel.cst.Operator.MINUS;

public class DecimalFloatingPointLiteralParseTest {
    @Test
    public void positiveParsePosition() throws Exception{
        // When
        String input = "\n  12345d";
        DecimalFloatingPointLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(8));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    @Test
    public void negativeParsePosition() throws Exception{
        // When
        String input = "\n  -  12345d";
        UnaryExpression expr = parseUnaryMinus(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(11));
        assertThat(input, endsWith(expr.getExpression().getTokenImage().getImage()));
    }

    private DecimalFloatingPointLiteralExpr parse(String input) throws Exception {
        return Universal.parse(DecimalFloatingPointLiteralExpr.class, input);
    }

    private UnaryExpression parseUnaryMinus(String input) throws Exception {
        UnaryExpression unaryExpression = Universal.parse(UnaryExpression.class, input);
        assertThat(unaryExpression.getOperator(), equalTo(MINUS));
        return unaryExpression;
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
        assertThat(parse(Double.toString(Double.MAX_VALUE)+"d").getTokenImage().getImage(), equalTo(Double.toString(Double.MAX_VALUE)+"d"));
    }

    @Test
    public void positiveBigLiterals() throws Exception{
        assertThat(parse("0.0D").getTokenImage().getImage(), equalTo("0.0D"));
        assertThat(parse(".1D").getTokenImage().getImage(), equalTo(".1D"));
        assertThat(parse("1.D").getTokenImage().getImage(), equalTo("1.D"));
        assertThat(parse("1234.5D").getTokenImage().getImage(), equalTo("1234.5D"));
        assertThat(parse("123e02D").getTokenImage().getImage(), equalTo("123e02D"));
        assertThat(parse("1000__1000__1000.25").getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parse(Double.toString(Double.MAX_VALUE)+"D").getTokenImage().getImage(), equalTo(Double.toString(Double.MAX_VALUE)+"D"));
    }

    @Test
    public void negativeUntypedLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0.0").getExpression().getTokenImage().getImage(), equalTo("0.0"));
        assertThat(parseUnaryMinus("-.1").getExpression().getTokenImage().getImage(), equalTo(".1"));
        assertThat(parseUnaryMinus("-1.").getExpression().getTokenImage().getImage(), equalTo("1."));
        assertThat(parseUnaryMinus("-1234.5").getExpression().getTokenImage().getImage(), equalTo("1234.5"));
        assertThat(parseUnaryMinus("-123e02").getExpression().getTokenImage().getImage(), equalTo("123e02"));
        assertThat(parseUnaryMinus("-1000__1000__1000.25").getExpression().getTokenImage().getImage(), equalTo("1000__1000__1000.25"));

        // Parses with negative exponent
        assertThat(parseUnaryMinus("-"+Double.toString(Double.MAX_VALUE)).getExpression().getTokenImage().getImage(), equalTo(Double.toString(Double.MAX_VALUE)));
    }

    @Test
    public void negativeFloatLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0.0f").getExpression().getTokenImage().getImage(), equalTo("0.0f"));
        assertThat(parseUnaryMinus("-.1f").getExpression().getTokenImage().getImage(), equalTo(".1f"));
        assertThat(parseUnaryMinus("-1.f").getExpression().getTokenImage().getImage(), equalTo("1.f"));
        assertThat(parseUnaryMinus("-1234.5f").getExpression().getTokenImage().getImage(), equalTo("1234.5f"));
        assertThat(parseUnaryMinus("-123e02f").getExpression().getTokenImage().getImage(), equalTo("123e02f"));
        assertThat(parseUnaryMinus("-1000__1000__1000.25").getExpression().getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parseUnaryMinus("-"+Float.toString(Float.MAX_VALUE)+"f").getExpression().getTokenImage().getImage(), equalTo(Float.toString(Float.MAX_VALUE)+"f"));
    }

    @Test
    public void negativeBigLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0.0D").getExpression().getTokenImage().getImage(), equalTo("0.0D"));
        assertThat(parseUnaryMinus("-.1D").getExpression().getTokenImage().getImage(), equalTo(".1D"));
        assertThat(parseUnaryMinus("-1.D").getExpression().getTokenImage().getImage(), equalTo("1.D"));
        assertThat(parseUnaryMinus("-1234.5D").getExpression().getTokenImage().getImage(), equalTo("1234.5D"));
        assertThat(parseUnaryMinus("-123e02D").getExpression().getTokenImage().getImage(), equalTo("123e02D"));
        assertThat(parseUnaryMinus("-1000__1000__1000.25").getExpression().getTokenImage().getImage(), equalTo("1000__1000__1000.25"));
        assertThat(parseUnaryMinus("-"+Double.toString(Double.MAX_VALUE)+"D").getExpression().getTokenImage().getImage(), equalTo(Double.toString(Double.MAX_VALUE)+"D"));
    }
}
