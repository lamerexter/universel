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

package org.orthodox.universel.exec.operators.binary.impl.range.enumeration;

/**
 * Built-in JUEL function implementations for enumeration range related functionality.
 * 
 * @author Rob Jefferson
 */
//@FunctionSource
public class EnumRangeOperatorFunctions {
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY..DaysInWeek.SUNDAY or CardSuit.CLUBS..CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_INCLUSIVE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, rhs);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>..>DaysInWeek.SUNDAY or CardSuit.CLUBS>..>CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GT_RIGHT_GT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as >DaysInWeek.MONDAY..>=DaysInWeek.SUNDAY or CardSuit.CLUBS>..>=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GT_RIGHT_GTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>..DaysInWeek.SUNDAY or CardSuit.CLUBS>..CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GT_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN, rhs);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>..<DaysInWeek.SUNDAY or CardSuit.CLUBS>..<CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GT_RIGHT_LT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>..<=DaysInWeek.SUNDAY or CardSuit.CLUBS>..<=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GT_RIGHT_LTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>=..>DaysInWeek.SUNDAY or CardSuit.CLUBS<=..>CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GTE_RIGHT_GT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>=..>=DaysInWeek.SUNDAY or CardSuit.CLUBS>=..>=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GTE_RIGHT_GTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>=..DaysInWeek.SUNDAY or CardSuit.CLUBS>=..CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GTE_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN_EQUAL, rhs);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>=..<DaysInWeek.SUNDAY or CardSuit.CLUBS>=..<CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GTE_RIGHT_LT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY>=..<=DaysInWeek.SUNDAY or CardSuit.CLUBS>=..<=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_GTE_RIGHT_LTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY..>DaysInWeek.SUNDAY or CardSuit.CLUBS..>CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_INCLUSIVE_RIGHT_GT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY..>=DaysInWeek.SUNDAY or CardSuit.CLUBS..>=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_INCLUSIVE_RIGHT_GTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY..<DaysInWeek.SUNDAY or CardSuit.CLUBS..<CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_INCLUSIVE_RIGHT_LT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY..<=DaysInWeek.SUNDAY or CardSuit.CLUBS..<=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_INCLUSIVE_RIGHT_LTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<..>DaysInWeek.SUNDAY or CardSuit.CLUBS<..>CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LT_RIGHT_GT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<..>=DaysInWeek.SUNDAY or CardSuit.CLUBS<..>=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LT_RIGHT_GTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<..DaysInWeek.SUNDAY or CardSuit.CLUBS<..CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LT_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN, rhs);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<..<DaysInWeek.SUNDAY or CardSuit.CLUBS<..<CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LT_RIGHT_LT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<..<=DaysInWeek.SUNDAY or CardSuit.CLUBS<..<=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LT_RIGHT_LTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<=..>DaysInWeek.SUNDAY or CardSuit.CLUBS<=..>CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LTE_RIGHT_GT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<=..>=DaysInWeek.SUNDAY or CardSuit.CLUBS<=..>=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LTE_RIGHT_GTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<=..DaysInWeek.SUNDAY or CardSuit.CLUBS<=..CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LTE_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.RANGE_INCLUSIVE);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<=..<DaysInWeek.SUNDAY or CardSuit.CLUBS<=..<CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LTE_RIGHT_LT(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Enum range operator implentation for ranges such as DaysInWeek.MONDAY<=..<=DaysInWeek.SUNDAY or CardSuit.CLUBS<=..<=CardSuit.DIAMONDS", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final <E extends Enum<E>> EnumRange<E> operator_RANGE_LEFT_LTE_RIGHT_LTE(ExpressionEvaluationContext context, E lhs, E rhs) {
//      return new EnumRange<E>(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.LESS_THAN_EQUAL);
//   }
}
