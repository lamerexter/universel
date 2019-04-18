package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

class HexadecimalFloatingPointLiteralExprTest {
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
        BinaryLiteralExpr expr = new BinaryLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }
}