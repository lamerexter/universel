package org.orthodox.universel.compiler;

import org.orthodox.universel.ast.Script;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.ast.UniversalCodeVisitor;
import org.orthodox.universel.ast.literals.BooleanLiteralExpr;
import org.orthodox.universel.ast.literals.DecimalFloatingPointLiteralExpr;
import org.orthodox.universel.ast.literals.DecimalIntegerLiteralExpr;
import org.orthodox.universel.ast.literals.StringLiteralExpr;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CompilingAstVisitor implements UniversalCodeVisitor {
    private CompilationContext compilationContext;

    public CompilingAstVisitor(CompilationContext compilationContext) {
        this.compilationContext = compilationContext;
    }

    @Override
    public boolean visitBooleanLiteral(BooleanLiteralExpr node) {
        boolean booleanValue = node.getBooleanValue();
        compilationContext.getVirtualMachine().loadOperandConstant(booleanValue);
        compilationContext.getBytecodeHelper().emitLoadBooleanOperand(booleanValue);
        return true;
    }

    @Override
    public boolean visitDecimalFloatingPointLiteral(DecimalFloatingPointLiteralExpr node) {
        Class<?> fpClass = node.getLiteralValueClass();

        if ( float.class == fpClass ) {
            float value = node.asFloat();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadFloatOperand(value);
        } else if ( double.class == fpClass ) {
            double value = node.asDouble();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadDoubleOperand(value);
        } else if ( BigDecimal.class == fpClass ) {
            String value = node.asBigDecimalString();
            compilationContext.getVirtualMachine().loadOperandOfType(BigDecimal.class);
            compilationContext.getBytecodeHelper().emitLoadBigDecimalOperand(value);
        }

        return true;
    }

    @Override
    public boolean visitDecimalIntegerLiteral(DecimalIntegerLiteralExpr node) {
        Class<?> integerClass = node.getLiteralValueClass();

        if ( int.class == integerClass ) {
            int value = node.asIntValue();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadIntegerOperand(value);
        } else if ( long.class == integerClass ) {
            long value = node.asLongValue();
            compilationContext.getVirtualMachine().loadOperandConstant(value);
            compilationContext.getBytecodeHelper().emitLoadIntegerOperand(value);
        } else if ( BigInteger.class == integerClass ) {
            String value = node.asBigIntegerString();
            compilationContext.getVirtualMachine().loadOperandOfType(BigInteger.class);
            compilationContext.getBytecodeHelper().emitLoadBigIntegerOperand(value);
        }

        return true;
    }


    @Override
    public boolean visitScript(Script node) {
        return false;
    }

    @Override
    public boolean visitStringLiteral(StringLiteralExpr node) {
        String value = node.getTokenImage().getImage().substring(1, node.getTokenImage().getImage().length()-1);
        compilationContext.getVirtualMachine().loadOperandOfType(String.class);
        compilationContext.getBytecodeHelper().emitLoadStringOperand(expandUniversalCharacterEscapeSequences(value));
        return true;
    }

    private String expandUniversalCharacterEscapeSequences(String input) {
        if (input == null) return null;

        StringBuilder s = new StringBuilder();
        boolean inEscape = false;
        for (int n=0; n < input.length(); n++) {
            if (!inEscape && input.charAt(n) == '\\') {
                inEscape = true;
                continue;
            }

            char ch = input.charAt(n);
            if ( inEscape ) {
                inEscape = false;
                switch ( ch )
                {
                    case 'n':   ch = '\n'; break;
                    case 'r':   ch = '\r'; break;
                    case 't':   ch = '\t'; break;
                    case 'b':   ch = '\b'; break;
                    case 'f':   ch = '\f'; break;
                    case '\\':  ch = '\\'; break;
                    case '\'':  ch = '\''; break;
                    case '\"':  ch = '\"'; break;
                    case '0' :
                    case '1' :
                    case '2' :
                    case '3' :
                    case '4' :
                    case '5' :
                    case '6' :
                    case '7' :
                        // Assume it's an octal character sequence (e.g. \17 or \377))
                        int chSeq = 0;
                        for (int i=0; i < 3; i++) {
                            chSeq = (chSeq << 3) | (ch - '0');

                            if (n+1 >= input.length()) break;

                            ch = input.charAt(n+1);
                            if ( !(ch >= '0' && ch <= '7') ) break;
                            n++;
                        }
                        ch = (char)chSeq;
                        break;
                }
            }
            s.append(ch);
        }
        return s.toString();
    }
}
