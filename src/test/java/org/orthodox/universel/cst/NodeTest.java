package org.orthodox.universel.cst;

import org.beanplanet.testing.utils.BeanTestSupport;
import org.junit.jupiter.api.Test;

class NodeTest {
    @Test
    public void propertiesAndToString() {
        new BeanTestSupport(new Node() {})
                .withMockitoValuesGenerator()
                .testProperties()
                .testBuilderProperties();
    }
}