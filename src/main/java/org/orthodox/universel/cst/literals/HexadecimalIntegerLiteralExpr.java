package org.orthodox.universel.cst.literals;

import org.orthodox.universel.cst.TokenImage;

import java.math.BigInteger;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static org.beanplanet.core.util.StringUtil.rTrim;

/**
 * An hexadecimal integer literal on the Abstract Syntax Tree.
 */
public class HexadecimalIntegerLiteralExpr extends IntegerLiteral {
    /**
     * Consructs a new hexadecimal integer literal node from the given parser token image.
     *
     * @param tokenImage the parser token image.
     */
    public HexadecimalIntegerLiteralExpr(TokenImage tokenImage) {
        super(tokenImage);
    }

    @Override
    public int asIntValue() {
        return parseInt(getTokenImage().getImage().substring(2), 16);
    }

    @Override
    public long asLongValue() {
        return parseLong(rTrim(getTokenImage().getImage().substring(2), "l", false), 16);
    }

    @Override
    public BigInteger asBigInteger() {
        return new BigInteger(rTrim(getTokenImage().getImage().substring(2), "I", false));
    }
}
