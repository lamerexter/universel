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

package org.orthodox.universel.ast;

import java.util.Map;
import java.util.TreeMap;

public enum Operator {
    // Alphabetical Order please
    AMPERSAND("&"),
    AMPERSAND_ASSIGN("&=", true),
    ASSIGN("=", true),
    BETWEEN("between"),
    CARAT("^"),
    CARAT_ASSIGN("^=", true),
    CONTAINS("contains"),
    ELVIS("?:", true),
    ENDS_WITH("ends-with"),
    EQUAL("=="),
    EXCLAMATION("!"),
    EXCLAMATION_EQUAL("!="),
    FORWARD_SLASH("/"),
    FORWARD_SLASH_ASSIGN("/=", true),
    GREATER_THAN(">"),
    GREATER_THAN_EQUAL(">="),
    IN("in"),
    INSTANCE_OF("instanceof"),
    LESS_GREATER_THAN("<>"),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    LOGICAL_OR("or", true),
    LOGICAL_AND("and", true),
    MINUS("-"),
    MINUS_ASSIGN("-=", true),
    NOT("not"),
    NOT_IN("not in"),
    PERCENT("%"),
    PERCENT_ASSIGN("%=", true),
    PIPE("|"),
    PIPE_ASSIGN("|=", true),
    PLUS("+"),
    PLUS_ASSIGN("+=", true),
    POST_INCREMENT("++"),
    POST_DECREMENT("--"),
    PRE_DECREMENT("--"),
    PRE_INCREMENT("++"),
    RANGE_INCLUSIVE(".."),
    RANGE_LEFT_LT_RIGHT_INCLUSIVE("<.."),
    RANGE_LEFT_LT_RIGHT_LT("<..<"),
    RANGE_LEFT_LT_RIGHT_LTE("<..<="),
    RANGE_LEFT_LT_RIGHT_GT("<..>"),
    RANGE_LEFT_LT_RIGHT_GTE("<..>="),
    RANGE_LEFT_LTE_RIGHT_GT("<=..>"),
    RANGE_LEFT_LTE_RIGHT_GTE("<=..>="),
    RANGE_LEFT_LTE_RIGHT_INCLUSIVE("<=.."),
    RANGE_LEFT_LTE_RIGHT_LT("<=..<"),
    RANGE_LEFT_LTE_RIGHT_LTE("<=..<="),
    RANGE_LEFT_GT_RIGHT_GT(">..>"),
    RANGE_LEFT_GT_RIGHT_GTE(">..>="),
    RANGE_LEFT_GT_RIGHT_INCLUSIVE(">.."),
    RANGE_LEFT_GT_RIGHT_LT(">..<"),
    RANGE_LEFT_GT_RIGHT_LTE(">..<="),
    RANGE_LEFT_GTE_RIGHT_GT(">=..>"),
    RANGE_LEFT_GTE_RIGHT_GTE(">=..>="),
    RANGE_LEFT_GTE_RIGHT_INCLUSIVE(">=.."),
    RANGE_LEFT_GTE_RIGHT_LT(">=..<"),
    RANGE_LEFT_GTE_RIGHT_LTE(">=..<="),
    RANGE_LEFT_INCLUSIVE_RIGHT_GT("..>"),
    RANGE_LEFT_INCLUSIVE_RIGHT_GTE("..>="),
    RANGE_LEFT_INCLUSIVE_RIGHT_LT("..<"),
    RANGE_LEFT_INCLUSIVE_RIGHT_LTE("..<="),
    SELECTION("[...]"),
    SHIFT_LEFT("<<"),
    SHIFT_LEFT_ASSIGN("<<=", true),
    SHIFT_RIGHT(">>"),
    SHIFT_RIGHT_ASSIGN(">>=", true),
    STAR("*"),
    STAR_ASSIGN("*=", true),
    STARTS_WITH("starts-with"),
    TREBLE_EQUAL("==="),
    TREBLE_SHIFT_RIGHT_ASSIGN(">>>=", true),
    TREBLE_SHIFT_RIGHT(">>>"),
    TILDA("~");

    private final String symbol;
    private final boolean lValue;

    private static final Map<String, Operator> symbolToOperator = new TreeMap<>();

    static {
        for (Operator op : Operator.values()) {
            symbolToOperator.put(op.symbol, op);
        }
    }

    Operator(String symbol) {
        this(symbol, false);
    }

    Operator(String symbol, boolean lValue) {
        this.symbol = symbol;
        this.lValue = lValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isLValue() {
        return lValue;
    }

    public static Operator forSymbol(String symbol) {
        return symbolToOperator.get(symbol);
    }
}
