package org.orthodox.universel.cst.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.cst.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

class DecimalIntegerLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new DecimalIntegerLiteralExpr(new TokenImage(1, 2, 3, 4, "1234I")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "8899I");
        DecimalIntegerLiteralExpr expr = new DecimalIntegerLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }
}