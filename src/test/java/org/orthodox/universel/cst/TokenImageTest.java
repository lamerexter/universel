package org.orthodox.universel.cst;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TokenImageTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new TokenImage())
                .testProperties()
                .testBuilderProperties()
                .testToString();
    }

    @Test
    public void constructor_tokenPositionAndImage() {
        // When
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "theTokenImage");

        // Then
        assertThat(tokenImage.getStartLine(), equalTo(1));
        assertThat(tokenImage.getStartColumn(), equalTo(2));
        assertThat(tokenImage.getEndLine(), equalTo(3));
        assertThat(tokenImage.getEndColumn(), equalTo(4));
        assertThat(tokenImage.getImage(), equalTo("theTokenImage"));
    }
}