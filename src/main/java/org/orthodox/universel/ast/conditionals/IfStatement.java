/******************************************************************************* 
 * Copyright 2004-2009 BeanPlanet Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.orthodox.universel.ast.conditionals;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.Statement;
import org.orthodox.universel.cst.*;
import org.orthodox.universel.cst.type.reference.ResolvedTypeReferenceOld;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import static org.beanplanet.core.lang.TypeUtil.determineCommonSuperclass;
import static org.beanplanet.core.util.ArrayUtil.asListOfNotNull;
import static org.beanplanet.core.util.ObjectUtil.nvl;

/**
 * An Abstract Syntax Tree (AST) node representing the classic if...else-if...else statement block.
 */
public class IfStatement extends Statement implements CompositeNode {
    /**
     * The if test expression.
     */
    protected Node testExpression;
    /**
     * The then expression/block associated with this if statement.
     */
    protected Node thenExpression;
    /**
     * The else-if expressions associated with this if statement.
     */
    protected List<ElseIf> elseIfExpressions;
    /**
     * The else expression/block associated with this if statement.
     */
    protected Node elseExpression;

    @Override
    public List<Node> getChildNodes() {
        return ListBuilder.<Node>builder()
                          .addNotNull(testExpression)
                          .addNotNull(thenExpression)
                          .addAllNotNull(elseIfExpressions)
                          .addNotNull(elseExpression)
                .build();
    }

    public static class ElseIf extends Node implements CompositeNode {
        /**
         * The else-if test expression.
         */
        protected final Node testExpression;
        /**
         * The else-if body.
         */
        protected final Node body;

        /**
         * Constructs a new AST else-if statement node instance with start and end line information, test expression and body.
         *
         * @param tokenImage     the parser token image.
         * @param testExpression the test expression associated with the else-if statement.
         * @param body           the body associated with the else-if statement which will be a single node or block statement.
         */
        public ElseIf(final TokenImage tokenImage, final Node testExpression, final Node body) {
            super(tokenImage);
            this.testExpression = testExpression;
            this.body = body;
        }

        /**
         * Gets the else-if test expression.
         *
         * @return the else-if test expression.
         */
        public Node getTestExpression() {
            return testExpression;
        }

        /**
         * Gets the else-if body.
         *
         * @return the else-if body.
         */
        public Node getBody() {
            return body;
        }

        @Override
        public List<Node> getChildNodes() {
            return asListOfNotNull(testExpression, body);
        }

        @Override
        public Class<?> getTypeDescriptor() {
            return body == null ? null : body.getTypeDescriptor();
        }

       @Override
       public boolean equals(Object o) {
          if (this == o)
             return true;
          if (!(o instanceof ElseIf))
             return false;
          if (!super.equals(o))
             return false;
          ElseIf nodes = (ElseIf) o;
          return Objects.equals(getTestExpression(), nodes.getTestExpression()) && Objects.equals(getBody(), nodes.getBody());
       }

       @Override
       public int hashCode() {
          return Objects.hash(super.hashCode(), getTestExpression(), getBody());
       }
    }

    /**
     * Constructs a new AST if statement node instance with start and end line information, test expression, optional else-if statements and
     * optional else statement.
     *
     * @param tokenImage        the parser token image.
     * @param testExpression    the test expression associated with the if statement.
     * @param thenExpression    the then statement body associated with the if statement which will be a single node or node sequence.
     * @param elseIfExpressions the then statement body associated with the if statement which will be a single node or node sequence.
     * @param elseExpression    the else statement body associated with the if statement which will be a single node or node sequence.
     */
    public IfStatement(TokenImage tokenImage, Node testExpression, Node thenExpression, List<ElseIf> elseIfExpressions, Node elseExpression) {
        super(tokenImage);
        this.testExpression = testExpression;
        this.thenExpression = thenExpression;
        this.elseIfExpressions = elseIfExpressions;
        this.elseExpression = elseExpression;
    }

    /**
     * Gets the if test expression.
     *
     * @return the if test expression.
     */
    public Node getTestExpression() {
        return testExpression;
    }

    /**
     * Gets the then expression associated with this if statement.
     *
     * @return the then expression associated with this if statement.
     */
    public Node getThenExpression() {
        return thenExpression;
    }

    /**
     * Gets the else-if expressions associated with this if statement.
     *
     * @return the else-if expressions associated with this if statement, which may be null.
     */
    public List<ElseIf> getElseIfExpressions() {
        return elseIfExpressions;
    }

    /**
     * Gets the else expression associated with this if statement.
     *
     * @return the else expression associated with this if statement.
     */
    public Node getElseExpression() {
        return elseExpression;
    }

    @Override
    public Type getType() {
        return nvl(TypeInferenceUtil.determineCommonSuperclass(new LinkedHashSet<>(ListBuilder.<Type>builder()
                                                 .addNotNull(thenExpression == null ? null : thenExpression.getType())
                                                 .addNotNull(elseIfExpressions == null ? null : elseIfExpressions.stream()
                                                                                                                 .map(eif -> eif.getBody() == null ? null : eif.getBody().getType())
                                                                                                                 .toArray(Type[]::new))
                                                 .addNotNull(elseExpression == null ? null : elseExpression.getType())
                                                 .build())

        ), new ResolvedTypeReferenceOld(Object.class));
    }

    @Override
    public Class<?> getTypeDescriptor() {
        return nvl(determineCommonSuperclass(ListBuilder.<Class<?>>builder()
                                                     .addNotNull(thenExpression == null ? null : thenExpression.getTypeDescriptor())
                                                     .addNotNull(elseIfExpressions == null ? null : elseIfExpressions.stream()
                                                                                                                     .map(eif -> eif.getBody() == null ? null : eif.getBody().getTypeDescriptor())
                                                                                                                     .toArray(Class[]::new))
                                                     .addNotNull(elseExpression == null ? null : elseExpression.getTypeDescriptor())
                                                     .build()
                                                     .stream()
                                                     .toArray(Class[]::new)
        ), Object.class);
    }

    public Node accept(UniversalCodeVisitor visitor) {
        return visitor.visitIfStatement(this);
    }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (!(o instanceof IfStatement))
         return false;
      if (!super.equals(o))
         return false;
      IfStatement that = (IfStatement) o;
      return Objects.equals(getTestExpression(), that.getTestExpression()) && Objects.equals(getThenExpression(), that.getThenExpression()) && Objects.equals(getElseIfExpressions(), that.getElseIfExpressions()) && Objects.equals(getElseExpression(), that.getElseExpression());
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), getTestExpression(), getThenExpression(), getElseIfExpressions(), getElseExpression());
   }
}
