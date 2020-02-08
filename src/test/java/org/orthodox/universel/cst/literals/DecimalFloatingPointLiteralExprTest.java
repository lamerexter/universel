package org.orthodox.universel.cst.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.cst.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.orthodox.universel.cst.literals.NumericLiteral.NumericPrecision.*;

class DecimalFloatingPointLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new DecimalFloatingPointLiteralExpr(new TokenImage(1, 2, 3, 4, "1.25")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "125.678");
        DecimalFloatingPointLiteralExpr expr = new DecimalFloatingPointLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }

    @Test
    public void getPrecision() {
        assertThat(new DecimalFloatingPointLiteralExpr(new TokenImage("1.5f")).getPrecision(), equalTo(STANDARD));
        assertThat(new DecimalFloatingPointLiteralExpr(new TokenImage("1.5")).getPrecision(), equalTo(LONG));
        assertThat(new DecimalFloatingPointLiteralExpr(new TokenImage("1.5d")).getPrecision(), equalTo(LONG));
        assertThat(new DecimalFloatingPointLiteralExpr(new TokenImage("1.5D")).getPrecision(), equalTo(ARBITRARY));
    }
}