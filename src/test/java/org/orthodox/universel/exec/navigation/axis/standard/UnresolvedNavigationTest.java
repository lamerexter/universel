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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.Script;
import org.orthodox.universel.BeanWithProperties;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.orthodox.universel.Universal.compile;
import static org.orthodox.universel.Universal.execute;
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
        CompiledUnit compiledUnit = compile("a", (Class<?>)null);
        assertThat(compiledUnit.hasErrors(), is(true));

        Script script = (Script) compiledUnit.getAstNode();
        NavigationStream ns = (NavigationStream)script.getBody().getNodes().get(0);

        assertThat(compiledUnit.getMessages().hasErrorWithCode(UNRESOLVED_STEP.getCode()), is(true));
        assertThat(compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject() instanceof Node, is(true));
        assertThat(((Node)compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject()).getTokenImage(), equalTo(ns.getSteps().get(0).getTokenImage()));
    }

    @Test
    void nullBinding_nestedStepsNameAreUnresolved() {
        CompiledUnit compiledUnit = compile("a\\b\\c", (Class<?>)null);
        assertThat(compiledUnit.hasErrors(), is(true));

        Script script = (Script) compiledUnit.getAstNode();
        NavigationStream ns = (NavigationStream)script.getBody().getChildNodes().get(0);

        assertThat(compiledUnit.getMessages().hasErrorWithCode(UNRESOLVED_STEP.getCode()), is(true));
        assertThat(compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject() instanceof Node, is(true));
        assertThat(((Node)compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject()).getTokenImage(), equalTo(ns.getSteps().get(0).getTokenImage()));
    }

    @Test
    void invalidInnermostStepName_unresolved() {
        CompiledUnit compiledUnit = compile("referenceProperty\\referenceProperty\\invalidName", binding.getClass());
        assertThat(compiledUnit.hasErrors(), is(true));

        Script script = (Script) compiledUnit.getAstNode();
        NavigationStream ns = (NavigationStream)script.getBody().getChildNodes().get(0);

        assertThat(compiledUnit.getMessages().hasErrorWithCode(UNRESOLVED_STEP.getCode()), is(true));
        assertThat(compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject() instanceof Node, is(true));
        assertThat(((Node)compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject()).getTokenImage(), equalTo(ns.getSteps().get(ns.getSteps().size()-1).getTokenImage()));
    }

    @Test
    void invalidIntermediateStepName_unresolved() {
        CompiledUnit<?> compiledUnit = compile("referenceProperty\\invalidName\\bigDecimalProperty", binding.getClass());
        assertThat(compiledUnit.hasErrors(), is(true));

        Script script = (Script) compiledUnit.getAstNode();
        NavigationStream ns = (NavigationStream)script.getBody().getChildNodes().get(0);

        assertThat(compiledUnit.getMessages().hasErrorWithCode(UNRESOLVED_STEP.getCode()), is(true));
        assertThat(compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject() instanceof Node, is(true));
        assertThat(((Node)compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject()).getTokenImage(), equalTo(ns.getSteps().get(ns.getSteps().size()-2).getTokenImage()));
    }

    @Test
    void invalidOutermostStepName_unresolved() {
        CompiledUnit compiledUnit = compile("invalidName\\referenceProperty\\bigDecimalProperty", binding.getClass());
        assertThat(compiledUnit.hasErrors(), is(true));

        Script script = (Script) compiledUnit.getAstNode();
        NavigationStream ns = (NavigationStream)script.getBody().getChildNodes().get(0);

        assertThat(compiledUnit.getMessages().hasErrorWithCode(UNRESOLVED_STEP.getCode()), is(true));
        assertThat(compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject() instanceof Node, is(true));
        assertThat(((Node)compiledUnit.getMessages().findErrorWithCode(UNRESOLVED_STEP.getCode()).get().getRelatedObject()).getTokenImage(), equalTo(ns.getSteps().get(ns.getSteps().size()-3).getTokenImage()));
    }
}
