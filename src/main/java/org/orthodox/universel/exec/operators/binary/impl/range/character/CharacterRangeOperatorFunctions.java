package org.orthodox.universel.exec.operators.binary.impl.range.character;

/**
 * Built-in JUEL function implementations for character range related functionality.
 * 
 * @author Rob Jefferson
 */
//@FunctionSource
public class CharacterRangeOperatorFunctions {
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..'z' or 'A'..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_INCLUSIVE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..'z' or 'A'..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_INCLUSIVE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..>'z' or 'A'>..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_GT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..>'z' or 'A'>..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_GT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..>='z' or 'A'>..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_GTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..>='z' or 'A'>..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_GTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..'z' or 'A'>..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..'z' or 'A'>..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..<'z' or 'A'>..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_LT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..<'z' or 'A'>..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_LT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..<='z' or 'A'>..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_LTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>..<='z' or 'A'>..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GT_RIGHT_LTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..>'z' or 'A'<=..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_GT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..>'z' or 'A'<=..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_GT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..>='z' or 'A'>=..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_GTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..>='z' or 'A'>=..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_GTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..'z' or 'A'>=..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..'z' or 'A'>=..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..<'z' or 'A'>=..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_LT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..<'z' or 'A'>=..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_LT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..<='z' or 'A'>=..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_LTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'>=..<='z' or 'A'>=..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_GTE_RIGHT_LTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.GREATER_THAN_EQUAL, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..>'z' or 'A'..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_GT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..>'z' or 'A'..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_GT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..>='z' or 'A'..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_GTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..>='z' or 'A'..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_GTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..<'z' or 'A'..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_LT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..<'z' or 'A'..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_LT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..<='z' or 'A'..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_LTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'..<='z' or 'A'..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_INCLUSIVE_RIGHT_LTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..>'z' or 'A'<..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_GT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..>'z' or 'A'<..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_GT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..>='z' or 'A'<..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_GTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..>='z' or 'A'<..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_GTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..'z' or 'A'<..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..'z' or 'A'<..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..<'z' or 'A'<..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_LT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..<'z' or 'A'<..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_LT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..<='z' or 'A'<..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_LTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<..<='z' or 'A'<..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LT_RIGHT_LTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..>'z' or 'A'<=..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_GT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..>'z' or 'A'<=..>'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_GT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.GREATER_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..>='z' or 'A'<=..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_GTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..>='z' or 'A'<=..>='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_GTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.GREATER_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..'z' or 'A'<=..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.RANGE_INCLUSIVE);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..'z' or 'A'<=..'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_INCLUSIVE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.RANGE_INCLUSIVE);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..<'z' or 'A'<=..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_LT(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..<'z' or 'A'<=..<'Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_LT(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.LESS_THAN);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..<='z' or 'A'<=..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_LTE(ExpressionEvaluationContext context, Character lhs, Character rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   @Function(displayName = "Character range operator implentation for ranges such as 'a'<=..<='z' or 'A'<=..<='Z'", description = "Implements the range operator for any Java Enum type.", idempotent = true, deterministic = true)
//   public static final CharacterRange operator_RANGE_LEFT_LTE_RIGHT_LTE(ExpressionEvaluationContext context, String lhs, String rhs) {
//      checkBounds(lhs, rhs);
//      return new CharacterRange(context, lhs, Operator.LESS_THAN_EQUAL, rhs, Operator.LESS_THAN_EQUAL);
//   }
//
//   private static final void checkBounds(Object lhs, Object rhs) {
//      if (lhs == null || rhs == null) {
//         throw new UnsupportedOperationException("The left-hand-side and right-hand-side bounds of a range expression may not be null");
//      }
//   }
//
//   private static final void checkBounds(String lhs, String rhs) {
//      checkBounds((Object)lhs, rhs);
//
//      if (lhs.length() == 0 || rhs.length() == 0) {
//         throw new UnsupportedOperationException("The left-hand-side and right-hand-side bounds of a character range expression must be at least 1 character long");
//      }
//   }
}
