package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.cst.UnaryExpression;
import org.orthodox.universel.cst.literals.HexadecimalFloatingPointLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

public class HexadecimalFloatingPointLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0x12345.P01";
        HexadecimalFloatingPointLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(13));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    @Test
    public void negativeParsePosition() throws Exception{
        // When
        String input = "\n   - 0x12345.P01";
        UnaryExpression expr = parseUnaryMinus(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(4));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(16));
        assertThat(input, endsWith(expr.getExpression().getTokenImage().getImage()));
    }

    private HexadecimalFloatingPointLiteralExpr parse(String input) throws Exception {
        return Universal.parse(HexadecimalFloatingPointLiteralExpr.class, input);
    }

    private UnaryExpression parseUnaryMinus(String input) throws Exception {
        return Universal.parse(UnaryExpression.class, input);
    }

    @Test
    public void positiveUntypedLiterals() throws Exception{
        assertThat(parse("0x1p3").getTokenImage().getImage(), equalTo("0x1p3"));
        assertThat(parse("0X1p3").getTokenImage().getImage(), equalTo("0X1p3"));
        assertThat(parse("0X12.2P2").getTokenImage().getImage(), equalTo("0X12.2P2"));
    }

    @Test
    public void positiveFloatLiterals() throws Exception{
        assertThat(parse("0x1p3f").getTokenImage().getImage(), equalTo("0x1p3f"));
        assertThat(parse("0X1p3f").getTokenImage().getImage(), equalTo("0X1p3f"));
        assertThat(parse("0x12.2P2f").getTokenImage().getImage(), equalTo("0x12.2P2f"));
    }

    @Test
    public void positiveDoubleLiterals() throws Exception{
        assertThat(parse("0x1p3d").getTokenImage().getImage(), equalTo("0x1p3d"));
        assertThat(parse("0X1p3d").getTokenImage().getImage(), equalTo("0X1p3d"));
        assertThat(parse("0x12.2P2d").getTokenImage().getImage(), equalTo("0x12.2P2d"));
    }

    @Test
    public void positiveBigLiterals() throws Exception{
        assertThat(parse("0x1p3D").getTokenImage().getImage(), equalTo("0x1p3D"));
        assertThat(parse("0X1p3D").getTokenImage().getImage(), equalTo("0X1p3D"));
        assertThat(parse("0x1p3D").getTokenImage().getImage(), equalTo("0x1p3D"));
    }

    @Test
    public void negativeUntypedLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0x1p3").getExpression().getTokenImage().getImage(), equalTo("0x1p3"));
        assertThat(parseUnaryMinus("-0X1p3").getExpression().getTokenImage().getImage(), equalTo("0X1p3"));
        assertThat(parseUnaryMinus("-0X12.2P2").getExpression().getTokenImage().getImage(), equalTo("0X12.2P2"));
    }

    @Test
    public void negativeFloatLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0x1p3f").getExpression().getTokenImage().getImage(), equalTo("0x1p3f"));
        assertThat(parseUnaryMinus("-0X1p3f").getExpression().getTokenImage().getImage(), equalTo("0X1p3f"));
        assertThat(parseUnaryMinus("-0x12.2P2f").getExpression().getTokenImage().getImage(), equalTo("0x12.2P2f"));
    }

    @Test
    public void negativeDoubleLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0x1p3d").getExpression().getTokenImage().getImage(), equalTo("0x1p3d"));
        assertThat(parseUnaryMinus("-0X1p3d").getExpression().getTokenImage().getImage(), equalTo("0X1p3d"));
        assertThat(parseUnaryMinus("-0x12.2P2d").getExpression().getTokenImage().getImage(), equalTo("0x12.2P2d"));
    }

    @Test
    public void negativeBigLiterals() throws Exception{
        assertThat(parseUnaryMinus("-0x1p3D").getExpression().getTokenImage().getImage(), equalTo("0x1p3D"));
        assertThat(parseUnaryMinus("-0X1p3D").getExpression().getTokenImage().getImage(), equalTo("0X1p3D"));
        assertThat(parseUnaryMinus("-0x1p3D").getExpression().getTokenImage().getImage(), equalTo("0x1p3D"));
    }
}
