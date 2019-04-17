package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

class OctalLongLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new OctalLongLiteralExpr(new TokenImage(1, 2, 3, 4, "0765L")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "0771l");
        OctalLongLiteralExpr expr = new OctalLongLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }

    @Test
    public void canonicalForm() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "0771L");
        OctalLongLiteralExpr expr = new OctalLongLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getCanonicalForm(), equalTo("0771L"));
    }
}