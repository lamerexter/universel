package org.orthodox.universel.compiler;

import org.beanplanet.core.lang.conversion.SystemTypeConverter;
import org.beanplanet.core.lang.conversion.TypeConverter;

/**
 * Autoboxing static utility functions.
 */
public class BoxingFunctions {
    private static final TypeConverter TYPE_CONVERTER = SystemTypeConverter.getInstance();

    public static boolean booleanUnbox(Object value) {
        // Allow null value for now and treat as false (done to allow unboxing test expressions like in the
        // case when <test> then ...
        if ( value == null ) return false;
        else if ( value == Boolean.TRUE ) return true;
        else if ( value == Boolean.FALSE ) return false;

        return TYPE_CONVERTER.convert(value, boolean.class);
    }

    public static byte byteUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, byte.class);
    }

    public static char charUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, char.class);
    }

    public static float floatUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, float.class);
    }

    public static double doubleUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, double.class);
    }

    public static int intUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, int.class);
    }

    public static long longUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, long.class);
    }

    public static short shortUnbox(Object value) {
        return TYPE_CONVERTER.convert(value, short.class);
    }

    public static Byte box(byte value) {
        return value;
    }

    public static Character box(char value) {
        return value;
    }

    public static Integer box(int value) {
        return value;
    }

    public static Long box(long value) {
        return value;
    }

    public static Short box(short value) {
        return value;
    }

    public static Boolean box(boolean value) {
        return value;
    }

    public static Float box(float value) {
        return value;
    }

    public static Double box(double value) {
        return value;
    }
}
