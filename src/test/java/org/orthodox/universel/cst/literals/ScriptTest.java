package org.orthodox.universel.cst.literals;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.cst.Script;
import org.orthodox.universel.cst.TokenImage;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ScriptTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new Script(new BooleanLiteralExpr(new TokenImage("true"))))
                .withMockitoValuesGenerator()
                .testToString()
                .testProperties()
                .testBuilderProperties();
    }

    @Test
    public void consructor_noArgs() {
        assertThat(new Script().getBodyElements(), nullValue());
    }

    @Test
    public void consructor_childElements() {
        // Given
        TokenImage tokenImage = new TokenImage(1, 2, 3, 4, "Hello World");
        StringLiteralExpr expr = new StringLiteralExpr(tokenImage);
        Script script = new Script(expr);

        // Then
        assertThat(script.getBodyElements(), equalTo(asList(expr)));
    }

    @Test
    public void accept() {
        // Given
        UniversalCodeVisitor visitor = mock(UniversalCodeVisitor.class);
        Script script = new Script();

        // When
        script.accept(visitor);

        // Then
        verify(visitor).visitScript(script);
    }
}