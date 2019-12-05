package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.conversion.SystemTypeConverter;
import org.beanplanet.core.lang.conversion.TypeConverter;

/**
 * Autoboxing static utility functions.
 */
public class BoxingFunctions {
    private static final TypeConverter TYPE_CONVERTER = SystemTypeConverter.getInstance();

    public static final boolean booleanUnbox(Object value) {
        // Allow null value for now and treat as false (done to allow unboxing test expressions like in the
        // case when <test> then ...
        return value != null && TYPE_CONVERTER.convert(value, boolean.class);
    }

    public static final byte byteUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, byte.class);
    }

    public static final char charUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, char.class);
    }

    public static final float floatUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, float.class);
    }

    public static final double doubleUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, double.class);
    }

    public static final int intUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, int.class);
    }

    public static final long longUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, long.class);
    }

    public static final short shortUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, short.class);
    }

    public static final Integer box(int value) {
        return value;
    }

    public static final Long box(long value) {
        return value;
    }

    public static final Boolean box(boolean value) {
        return value;
    }

    public static final Float box(float value) {
        return value;
    }

    public static final Double box(double value) {
        return value;
    }
}
