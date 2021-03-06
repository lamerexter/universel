package org.orthodox.universel.parse.literals;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;
import org.orthodox.universel.ast.literals.BinaryIntegerLiteralExpr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BinaryIntegerLiteralParseTest {
    @Test
    public void parsePosition() throws Exception{
        // When
        String input = "\n  0b1010";
        BinaryIntegerLiteralExpr expr = parse(input);
        assertThat(expr.getTokenImage().getStartLine(), equalTo(2));
        assertThat(expr.getTokenImage().getStartColumn(), equalTo(3));
        assertThat(expr.getTokenImage().getEndLine(), equalTo(2));
        assertThat(expr.getTokenImage().getEndColumn(), equalTo(8));
        assertThat(expr.getTokenImage().getImage(), equalTo(input.trim()));
    }

    private BinaryIntegerLiteralExpr parse(String input) throws Exception {
        return Universal.parse(BinaryIntegerLiteralExpr.class, input);
    }


    @Test
    public void positiveIntegerLiterals() throws Exception{
        assertThat(parse("0b0").getTokenImage().getImage(), equalTo("0b0"));
        assertThat(parse("0B1").getTokenImage().getImage(), equalTo("0B1"));
        assertThat(parse("0b11110000").getTokenImage().getImage(), equalTo("0b11110000"));
        assertThat(parse("0b1111__0000").getTokenImage().getImage(), equalTo("0b1111__0000"));
        assertThat(parse("0b0111_1111_1111_1111_1111_1111_1111_1111").getTokenImage().getImage(), equalTo("0b0111_1111_1111_1111_1111_1111_1111_1111"));
    }

    @Test
    public void positiveLongLiterals() throws Exception{
        assertThat(parse("0b0L").getTokenImage().getImage(), equalTo("0b0L"));
        assertThat(parse("0B1l").getTokenImage().getImage(), equalTo("0B1l"));
        assertThat(parse("0b11110000L").getTokenImage().getImage(), equalTo("0b11110000L"));
        assertThat(parse("0b1111__0000L").getTokenImage().getImage(), equalTo("0b1111__0000L"));
        assertThat(parse("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L").getTokenImage().getImage(), equalTo("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"));
    }

    @Test
    public void positiveBigIntegerLiterals() throws Exception{
        assertThat(parse("0b0I").getTokenImage().getImage(), equalTo("0b0I"));
        assertThat(parse("0B1l").getTokenImage().getImage(), equalTo("0B1l"));
        assertThat(parse("0b11110000I").getTokenImage().getImage(), equalTo("0b11110000I"));
        assertThat(parse("0b1111__0000I").getTokenImage().getImage(), equalTo("0b1111__0000I"));
        assertThat(parse("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111I").getTokenImage().getImage(), equalTo("0b0111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111I"));
    }

    @Test()
    public void negativeIntegerLiterals() throws Exception {
        assertThat(parse("0b1111_1111_1111_1111_1111_1111_1111_1111").getTokenImage().getImage(), equalTo("0b1111_1111_1111_1111_1111_1111_1111_1111"));
        assertThat(parse("0b1000_0000_0000_0000_0000_0000_0000_0000").getTokenImage().getImage(), equalTo("0b1000_0000_0000_0000_0000_0000_0000_0000"));
    }

    @Test()
    public void negativeLongLiterals() throws Exception {
        assertThat(parse("0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L").getTokenImage().getImage(), equalTo("0b1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111_1111L"));
        assertThat(parse("0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000L").getTokenImage().getImage(), equalTo("0b1000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000L"));
    }
}
