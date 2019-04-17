package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

class BinaryLongLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new BinaryLongLiteralExpr(new TokenImage(1, 2, 3, 4, "0b1010L")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "0b1010L");
        BinaryLongLiteralExpr expr = new BinaryLongLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }
}