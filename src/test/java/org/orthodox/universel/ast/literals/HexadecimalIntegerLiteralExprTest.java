package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

class HexadecimalIntegerLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new HexadecimalIntegerLiteralExpr(new TokenImage(1, 2, 3, 4, "0xa2b2")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "0Xf1e2d1");
        HexadecimalIntegerLiteralExpr expr = new HexadecimalIntegerLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }
}