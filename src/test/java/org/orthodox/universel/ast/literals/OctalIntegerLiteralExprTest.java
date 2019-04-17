package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

class OctalIntegerLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new OctalIntegerLiteralExpr(new TokenImage(1, 2, 3, 4, "0765")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "0771");
        OctalIntegerLiteralExpr expr = new OctalIntegerLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }

    @Test
    public void canonicalForm() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "0771");
        OctalIntegerLiteralExpr expr = new OctalIntegerLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getCanonicalForm(), equalTo("0771"));
    }
}