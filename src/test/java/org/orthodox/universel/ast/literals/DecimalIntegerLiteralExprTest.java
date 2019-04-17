package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "8899");
        DecimalIntegerLiteralExpr expr = new DecimalIntegerLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }

    @Test
    public void canonicalForm() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "8899");
        DecimalIntegerLiteralExpr expr = new DecimalIntegerLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getCanonicalForm(), equalTo("8899"));
    }
}