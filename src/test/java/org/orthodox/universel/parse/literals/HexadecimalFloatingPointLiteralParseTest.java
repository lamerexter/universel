package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orthodox.universal.parser.TokenMgrException;
import org.orthodox.universal.parser.UniversalParser;
import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.literals.DecimalFloatingPointLiteralExpr;
import org.orthodox.universel.ast.literals.HexadecimalFloatingPointLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

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

    private HexadecimalFloatingPointLiteralExpr parse(String input) throws Exception {
        // Given
        UniversalParser parser = new UniversalParser(input);

        // When
        Expression literalExpr = parser.Literal();

        // Then
        assertThat(literalExpr, instanceOf(HexadecimalFloatingPointLiteralExpr.class));
        return (HexadecimalFloatingPointLiteralExpr)literalExpr;
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

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        Assertions.assertThrows(TokenMgrException.class, () -> {
            parse("-0x12.2P2");
        }, "Unary minus is handled by the parser");
    }
}
