package org.orthodox.universel.ast.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.ast.TokenImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;

class LongLiteralExprTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new LongLiteralExpr(new TokenImage(1, 2, 3, 4, "1234L")))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_tokenImage() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "8899L");
        LongLiteralExpr expr = new LongLiteralExpr(tokenImage);

        // Then
        assertThat(expr.getTokenImage(), sameInstance(tokenImage));
    }

    @Test
    public void canonicalForm() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "8899L");
        LongLiteralExpr expr = new LongLiteralExpr(tokenImage);

        // Then
        assertThat(new LongLiteralExpr(new TokenImage(1, 2, 3, 4, "112233l")).getCanonicalForm(), equalTo("112233l"));
    }
}