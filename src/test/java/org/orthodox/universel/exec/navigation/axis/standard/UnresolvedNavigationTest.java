/*
 *  MIT Licence:
 *
 *  Copyright (c) 2020 Orthodox Engineering Ltd
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software,
 *  and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 *  KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *  PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *  CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *
 */

package org.orthodox.universel.exec.navigation.axis.standard;

import org.beanplanet.messages.domain.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.compiler.Messages;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.ParseTree;
import org.orthodox.universel.cst.Script;
import org.orthodox.universel.BeanWithProperties;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.compiler.Messages.NAME.NAME_NOT_RESOLVED;
import static org.orthodox.universel.compiler.Messages.NavigationExpression.UNRESOLVED_STEP;

public class UnresolvedNavigationTest {
    private BeanWithProperties binding;
    private BeanWithProperties firstStep;
    private BeanWithProperties secondStep;

    @BeforeEach
    void setUp() {
        binding = new BeanWithProperties();
        firstStep = new BeanWithProperties();
        secondStep = new BeanWithProperties();
        binding.setReferenceProperty(firstStep);
        firstStep.setReferenceProperty(secondStep);

        firstStep.setBigDecimalProperty(BigDecimal.ONE);
        secondStep.setBigDecimalProperty(BigDecimal.TEN);
    }

    @Test
    void nullBinding_singleStepNameAreUnresolved() {
        assertNavigationStreamContainsUnresolvedName("a", null, "a", 0);
    }

    @Test
    void nullBinding_nestedStepsNameAreUnresolved() {
        assertNavigationStreamContainsUnresolvedName("a\\b\\c", null, "a", 0);
        assertNavigationStreamContainsUnresolvedName("a\\b\\c", null, "b", 1);
        assertNavigationStreamContainsUnresolvedName("a\\b\\c", null, "c", 2);
    }

    @Test
    void invalidInnermostStepName_unresolved() {
        assertNavigationStreamContainsUnresolvedName("referenceProperty\\referenceProperty\\invalidName", binding.getClass(), "invalidName", 2);
    }

    @Test
    void invalidIntermediateStepName_unresolved() {
        assertNavigationStreamContainsUnresolvedName("referenceProperty\\invalidName\\bigDecimalProperty", binding.getClass(), "invalidName", 1);
    }

    @Test
    void invalidOutermostStepName_unresolved() {
        assertNavigationStreamContainsUnresolvedName("invalidName\\referenceProperty\\bigDecimalProperty", binding.getClass(), "invalidName", 0);
    }

    private void assertNavigationStreamContainsUnresolvedName(String script, Class<?> bindingType, String name, int stepIndex) {
        CompiledUnit compiledUnit = compile(script, bindingType);
        assertThat(compiledUnit.hasErrors(), is(true));

        Script scriptNode = (Script) compiledUnit.getAstNode();
        NavigationStream ns = new ParseTree(scriptNode).preorderParentUnawareStream()
                                                   .filter(NavigationStream.class::isInstance)
                                                   .map(NavigationStream.class::cast).findFirst().orElseGet(() -> fail("Unresolved navigation stream not found"));

        Optional<Message> notFoundMessage = compiledUnit.getMessages().findError(e -> e.getCode().equals(NAME_NOT_RESOLVED.getCode()) && asList(e.getMessageParameters()).contains(name));
        assertThat(notFoundMessage.isPresent(), is(true));
        assertThat(notFoundMessage.get().getRelatedObject() instanceof Node, is(true));
        assertThat(((Node)notFoundMessage.get().getRelatedObject()).getTokenImage(), equalTo(ns.getSteps().get(stepIndex).getTokenImage()));
    }
}
