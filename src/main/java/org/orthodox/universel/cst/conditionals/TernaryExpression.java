/*
 *  MIT Licence:
 *
 *  Copyright (c) 2019 Orthodox Engineering Ltd
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
package org.orthodox.universel.cst.conditionals;

import org.orthodox.universel.cst.CompositeNode;
import org.orthodox.universel.cst.Expression;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.UniversalCodeVisitor;

import java.util.List;

import static org.beanplanet.core.util.ArrayUtil.asListOfNotNull;
import static org.orthodox.universel.cst.TokenImage.range;

public class TernaryExpression extends Expression implements CompositeNode {
   protected Node testExpression;

   protected Node lhsExpression;

   protected Node rhsExpression;

   public TernaryExpression(Node testExpression,
                            Node lhsExpression,
                            Node rhsExpression) {
      super(range(testExpression, lhsExpression, rhsExpression));
      this.testExpression = testExpression;
      this.lhsExpression = lhsExpression;
      this.rhsExpression = rhsExpression;
   }

   public Node getTestExpression() {
      return testExpression;
   }

   public void setTestExpression(Node testExpression) {
      this.testExpression = testExpression;
   }

   public Node getLhsExpression() {
      return lhsExpression;
   }

   public void setLhsExpression(Node lhsExpression) {
      this.lhsExpression = lhsExpression;
   }

   public Node getRhsExpression() {
      return rhsExpression;
   }

   public void setRhsExpression(Node rhsExpression) {
      this.rhsExpression = rhsExpression;
   }

   public Node accept(UniversalCodeVisitor visitor) {
      return visitor.visitTernaryExpression(this);
   }

   @Override
   public List<Node> getChildNodes() {
      return asListOfNotNull(testExpression, lhsExpression, rhsExpression);
   }
}
