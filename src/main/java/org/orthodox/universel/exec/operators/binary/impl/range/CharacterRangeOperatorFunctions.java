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

package org.orthodox.universel.exec.operators.binary.impl.range;

import org.orthodox.universel.cst.Operator;
import org.orthodox.universel.exec.operators.binary.BinaryOperator;
import org.orthodox.universel.exec.operators.range.CharacterRange;

import java.util.Collection;

import static org.orthodox.universel.cst.Operator.*;

/**
 * Specific operator functions for {@link org.orthodox.universel.exec.operators.range.CharacterRange} whoch deal
 * leniently with non-character operands, such as Strings where the first character is taken (e.g. 'A' in 'A'..'Z').
 */
@BinaryOperator
public class CharacterRangeOperatorFunctions {
    @BinaryOperator(CONTAINS)
    public static boolean containsSingleValue(final CharacterRange lhs, final CharSequence rhs) {
        String strRhs = String.valueOf(rhs);
        if ( strRhs == null ) return false;

        for (int n=0; n < strRhs.length(); n++) {
            if ( !lhs.contains(strRhs.charAt(n)) ) return false;
        }

        return true;
    }

    @BinaryOperator(CONTAINS)
    public static boolean containsCollectionOfCharacterSequences(final CharacterRange lhs, final Collection<?> rhs) {
        return lhs != null
               && rhs != null
               && rhs.stream().allMatch(r -> containsSingleValue(lhs, String.valueOf(r)));
    }

    @BinaryOperator(CONTAINS)
    public static  boolean containsCharacterRange(final CharacterRange lhs, final CharacterRange rhs) {
        return lhs.containsAll(rhs);
    }

    @BinaryOperator(IN)
    public static boolean inSingleValueInCharacterRange(final CharSequence lhs, final CharacterRange rhs) {
        String strLhs = String.valueOf(lhs);
        if ( strLhs == null ) return false;

        for (int n=0; n < strLhs.length(); n++) {
            if ( !rhs.contains(strLhs.charAt(n)) ) return false;
        }

        return true;
    }

    @BinaryOperator(IN)
    public static boolean inCharacterRangeInCharacterRange(final CharacterRange lhs, final CharacterRange rhs) {
        return rhs.containsAll(lhs);
    }

    @BinaryOperator(IN)
    public static boolean inCollectionOfCharacterSequences(final Collection<?> lhs, final CharacterRange rhs) {
        return rhs != null
               && lhs != null
               && lhs.stream().allMatch(l -> inSingleValueInCharacterRange(String.valueOf(l), rhs));
    }


    @BinaryOperator({
            RANGE_INCLUSIVE,
            RANGE_LEFT_LT_RIGHT_INCLUSIVE, RANGE_LEFT_LT_RIGHT_LT, RANGE_LEFT_LT_RIGHT_LTE, RANGE_LEFT_LT_RIGHT_GT, RANGE_LEFT_LT_RIGHT_GTE,
            RANGE_LEFT_LTE_RIGHT_GT, RANGE_LEFT_LTE_RIGHT_GTE, RANGE_LEFT_LTE_RIGHT_INCLUSIVE, RANGE_LEFT_LTE_RIGHT_LT, RANGE_LEFT_LTE_RIGHT_LTE,
            RANGE_LEFT_GT_RIGHT_GT, RANGE_LEFT_GT_RIGHT_GTE, RANGE_LEFT_GT_RIGHT_INCLUSIVE, RANGE_LEFT_GT_RIGHT_LT, RANGE_LEFT_GT_RIGHT_LTE,
            RANGE_LEFT_GTE_RIGHT_GT, RANGE_LEFT_GTE_RIGHT_GTE, RANGE_LEFT_GTE_RIGHT_INCLUSIVE, RANGE_LEFT_GTE_RIGHT_LT, RANGE_LEFT_GTE_RIGHT_LTE,
            RANGE_LEFT_INCLUSIVE_RIGHT_GT, RANGE_LEFT_INCLUSIVE_RIGHT_GTE, RANGE_LEFT_INCLUSIVE_RIGHT_LT, RANGE_LEFT_INCLUSIVE_RIGHT_LTE
    })
    public static CharacterRange stringRange(String lhs, String rhs, Operator operator) {
        return characterRange(lhs.charAt(0), rhs.charAt(0), operator);
    }

    @BinaryOperator({
            RANGE_INCLUSIVE,
            RANGE_LEFT_LT_RIGHT_INCLUSIVE, RANGE_LEFT_LT_RIGHT_LT, RANGE_LEFT_LT_RIGHT_LTE, RANGE_LEFT_LT_RIGHT_GT, RANGE_LEFT_LT_RIGHT_GTE,
            RANGE_LEFT_LTE_RIGHT_GT, RANGE_LEFT_LTE_RIGHT_GTE, RANGE_LEFT_LTE_RIGHT_INCLUSIVE, RANGE_LEFT_LTE_RIGHT_LT, RANGE_LEFT_LTE_RIGHT_LTE,
            RANGE_LEFT_GT_RIGHT_GT, RANGE_LEFT_GT_RIGHT_GTE, RANGE_LEFT_GT_RIGHT_INCLUSIVE, RANGE_LEFT_GT_RIGHT_LT, RANGE_LEFT_GT_RIGHT_LTE,
            RANGE_LEFT_GTE_RIGHT_GT, RANGE_LEFT_GTE_RIGHT_GTE, RANGE_LEFT_GTE_RIGHT_INCLUSIVE, RANGE_LEFT_GTE_RIGHT_LT, RANGE_LEFT_GTE_RIGHT_LTE,
            RANGE_LEFT_INCLUSIVE_RIGHT_GT, RANGE_LEFT_INCLUSIVE_RIGHT_GTE, RANGE_LEFT_INCLUSIVE_RIGHT_LT, RANGE_LEFT_INCLUSIVE_RIGHT_LTE
    })
    public static CharacterRange characterRange(Character lhs, Character rhs, Operator operator) {
        return new CharacterRange(operator, lhs, rhs);
    }
}
