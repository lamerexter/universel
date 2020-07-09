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

package org.orthodox.universel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A standard JavaBean with readable amd writable properties (accessors) for all of the runtime standard
 * types: primitives, primitive wrappers, string, BigDecimal, BigInteger and other object types, class and then Collection and Array
 * versions/
 */
public class BeanWithProperties {
    private BigDecimal bigDecimalProperty;
    private BigInteger bigIntegerProperty;
    private boolean booleanProperty;
    private byte byteProperty;
    private char charProperty;
    private double doubleProperty;
    private float floatProperty;
    private int intProperty;
    private long longProperty;
    private BeanWithProperties referenceProperty;
    private short shortProperty;
    private String stringProperty;
    private Class<?> typeProperty;

    private Boolean booleanWrapperProperty;
    private Byte byteWrapperProperty;
    private Character charWrapperProperty;
    private Double doubleWrapperProperty;
    private Float floatWrapperProperty;
    private Integer intWrapperProperty;
    private Long longWrapperProperty;
    private Short shortWrapperProperty;
    private String stringWrapperProperty;

    private List<Boolean> booleanWrapperListProperty;
    private List<BigDecimal> bigDecimalListProperty;
    private List<BigInteger> bigIntegerListProperty;
    private List<Byte> byteWrapperListProperty;
    private List<Character> charWrapperListProperty;
    private List<Class<?>> typeListProperty;
    private List<Double> doubleWrapperListProperty;
    private List<Float> floatWrapperListProperty;
    private List<Integer> intWrapperListProperty;
    private List<Long> longWrapperListProperty;
    private List<Short> shortWrapperListProperty;
    private List<String> stringListProperty;
    private List<BeanWithProperties> referenceListProperty;

    private BigDecimal[] bigDecimalArrayProperty;
    private BigInteger[] bigIntegerArrayProperty;
    private boolean[] booleanArrayProperty;
    private byte[] byteArrayProperty;
    private char[] charArrayProperty;
    private double[] doubleArrayProperty;
    private float[] floatArrayProperty;
    private int[] intArrayProperty;
    private long[] longArrayProperty;
    private BeanWithProperties[] referenceArrayProperty;
    private short[] shortArrayProperty;
    private String[] stringArrayProperty;
    private Class<?>[] typeArrayProperty;

    private Boolean[] booleanWrapperArrayProperty;
    private Byte[] byteWrapperArrayProperty;
    private Character[] charWrapperArrayProperty;
    private Double[] doubleWrapperArrayProperty;
    private Float[] floatWrapperArrayProperty;
    private Integer[] intWrapperArrayProperty;
    private Long[] longWrapperArrayProperty;
    private Short[] shortWrapperArrayProperty;

    public BeanWithProperties() {}

    public BeanWithProperties(int intProperty) {
        this.intProperty = intProperty;
    }

    public BeanWithProperties(Long longProperty) {
        this.longProperty = longProperty;
    }

    public BeanWithProperties(float floatProperty) {
        this.floatProperty = floatProperty;
    }

    public BigDecimal getBigDecimalProperty() {
        return bigDecimalProperty;
    }

    public void setBigDecimalProperty(BigDecimal bigDecimalProperty) {
        this.bigDecimalProperty = bigDecimalProperty;
    }

    public BeanWithProperties withBigDecimalProperty(BigDecimal bigDecimalProperty) {
        setBigDecimalProperty(bigDecimalProperty);
        return this;
    }

    public BigInteger getBigIntegerProperty() {
        return bigIntegerProperty;
    }

    public void setBigIntegerProperty(BigInteger bigIntegerProperty) {
        this.bigIntegerProperty = bigIntegerProperty;
    }

    public BeanWithProperties withBigIntegerProperty(BigInteger bigIntegerProperty) {
        setBigIntegerProperty(bigIntegerProperty);
        return this;
    }

    public boolean isBooleanProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty = booleanProperty;
    }

    public BeanWithProperties withBooleanProperty(boolean booleanProperty) {
        setBooleanProperty(booleanProperty);
        return this;
    }

    public byte getByteProperty() {
        return byteProperty;
    }

    public void setByteProperty(byte byteProperty) {
        this.byteProperty = byteProperty;
    }

    public BeanWithProperties withByteProperty(byte byteProperty) {
        setByteProperty(byteProperty);
        return this;
    }

    public char getCharProperty() {
        return charProperty;
    }

    public void setCharProperty(char charProperty) {
        this.charProperty = charProperty;
    }

    public BeanWithProperties withCharProperty(char charProperty) {
        setCharProperty(charProperty);
        return this;
    }

    public Class<?> getTypeProperty() {
        return typeProperty;
    }

    public void setTypeProperty(Class<?> typeProperty) {
        this.typeProperty = typeProperty;
    }

    public BeanWithProperties withTypeProperty(Class<?> typeProperty) {
        setTypeProperty(typeProperty);
        return this;
    }

    public double getDoubleProperty() {
        return doubleProperty;
    }

    public void setDoubleProperty(double doubleProperty) {
        this.doubleProperty = doubleProperty;
    }

    public BeanWithProperties withDoubleProperty(double doubleProperty) {
        setDoubleProperty(doubleProperty);
        return this;
    }

    public float getFloatProperty() {
        return floatProperty;
    }

    public void setFloatProperty(float floatProperty) {
        this.floatProperty = floatProperty;
    }

    public BeanWithProperties withFloatProperty(float floatProperty) {
        setFloatProperty(floatProperty);
        return this;
    }

    public int getIntProperty() {
        return intProperty;
    }

    public void setIntProperty(int intProperty) {
        this.intProperty = intProperty;
    }

    public BeanWithProperties withIntProperty(int intProperty) {
        setIntProperty(intProperty);
        return this;
    }

    public long getLongProperty() {
        return longProperty;
    }

    public void setLongProperty(long longProperty) {
        this.longProperty = longProperty;
    }

    public BeanWithProperties withLongProperty(long longProperty) {
        setLongProperty(longProperty);
        return this;
    }

    public short getShortProperty() {
        return shortProperty;
    }

    public void setShortProperty(short shortProperty) {
        this.shortProperty = shortProperty;
    }

    public BeanWithProperties withShortProperty(short shortProperty) {
        setShortProperty(shortProperty);
        return this;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public BeanWithProperties withStringProperty(String stringProperty) {
        setStringProperty(stringProperty);
        return this;
    }

    public BeanWithProperties getReferenceProperty() {
        return referenceProperty;
    }

    public void setReferenceProperty(BeanWithProperties referenceProperty) {
        this.referenceProperty = referenceProperty;
    }

    public BeanWithProperties withReferenceProperty(BeanWithProperties referenceProperty) {
        this.referenceProperty = referenceProperty;
        return this;
    }

    public Boolean getBooleanWrapperProperty() {
        return booleanWrapperProperty;
    }

    public void setBooleanWrapperProperty(Boolean booleanWrapperProperty) {
        this.booleanWrapperProperty = booleanWrapperProperty;
    }

    public BeanWithProperties withBooleanWrapperProperty(Boolean booleanWrapperProperty) {
        setBooleanWrapperProperty(booleanWrapperProperty);
        return this;
    }

    public Byte getByteWrapperProperty() {
        return byteWrapperProperty;
    }

    public void setByteWrapperProperty(Byte byteWrapperProperty) {
        this.byteWrapperProperty = byteWrapperProperty;
    }

    public BeanWithProperties withByteWrapperProperty(Byte byteWrapperProperty) {
        setByteWrapperProperty(byteWrapperProperty);
        return this;
    }

    public Character getCharWrapperProperty() {
        return charWrapperProperty;
    }

    public void setCharWrapperProperty(Character charWrapperProperty) {
        this.charWrapperProperty = charWrapperProperty;
    }

    public BeanWithProperties withCharWrapperProperty(Character charWrapperProperty) {
        setCharWrapperProperty(charWrapperProperty);
        return this;
    }

    public Double getDoubleWrapperProperty() {
        return doubleWrapperProperty;
    }

    public void setDoubleWrapperProperty(Double doubleWrapperProperty) {
        this.doubleWrapperProperty = doubleWrapperProperty;
    }

    public BeanWithProperties withDoubleWrapperProperty(Double doubleWrapperProperty) {
        setDoubleWrapperProperty(doubleWrapperProperty);
        return this;
    }

    public Float getFloatWrapperProperty() {
        return floatWrapperProperty;
    }

    public void setFloatWrapperProperty(Float floatWrapperProperty) {
        this.floatWrapperProperty = floatWrapperProperty;
    }

    public BeanWithProperties withFloatWrapperProperty(Float floatWrapperProperty) {
        setFloatWrapperProperty(floatWrapperProperty);
        return this;
    }

    public Integer getIntWrapperProperty() {
        return intWrapperProperty;
    }

    public void setIntWrapperProperty(Integer intWrapperProperty) {
        this.intWrapperProperty = intWrapperProperty;
    }

    public BeanWithProperties withIntWrapperProperty(Integer intWrapperProperty) {
        setIntWrapperProperty(intWrapperProperty);
        return this;
    }

    public Long getLongWrapperProperty() {
        return longWrapperProperty;
    }

    public void setLongWrapperProperty(Long longWrapperProperty) {
        this.longWrapperProperty = longWrapperProperty;
    }

    public BeanWithProperties withLongWrapperProperty(Long longWrapperProperty) {
        setLongWrapperProperty(longWrapperProperty);
        return this;
    }

    public Short getShortWrapperProperty() {
        return shortWrapperProperty;
    }

    public void setShortWrapperProperty(Short shortWrapperProperty) {
        this.shortWrapperProperty = shortWrapperProperty;
    }

    public BeanWithProperties withShortWrapperProperty(Short shortWrapperProperty) {
        setShortWrapperProperty(shortWrapperProperty);
        return this;
    }

    public String getStringWrapperProperty() {
        return stringWrapperProperty;
    }

    public void setStringWrapperProperty(String stringWrapperProperty) {
        this.stringWrapperProperty = stringWrapperProperty;
    }

    public List<Boolean> getBooleanWrapperListProperty() {
        return booleanWrapperListProperty;
    }

    public void setBooleanWrapperListProperty(List<Boolean> booleanWrapperListProperty) {
        this.booleanWrapperListProperty = booleanWrapperListProperty;
    }

    public List<BigDecimal> getBigDecimalListProperty() {
        return bigDecimalListProperty;
    }

    public void setBigDecimalListProperty(List<BigDecimal> bigDecimalListProperty) {
        this.bigDecimalListProperty = bigDecimalListProperty;
    }

    public List<BigInteger> getBigIntegerListProperty() {
        return bigIntegerListProperty;
    }

    public void setBigIntegerListProperty(List<BigInteger> bigIntegerListProperty) {
        this.bigIntegerListProperty = bigIntegerListProperty;
    }

    public List<Byte> getByteWrapperListProperty() {
        return byteWrapperListProperty;
    }

    public void setByteWrapperListProperty(List<Byte> byteWrapperListProperty) {
        this.byteWrapperListProperty = byteWrapperListProperty;
    }

    public List<Character> getCharWrapperListProperty() {
        return charWrapperListProperty;
    }

    public void setCharWrapperListProperty(List<Character> charWrapperListProperty) {
        this.charWrapperListProperty = charWrapperListProperty;
    }

    public List<Class<?>> getTypeListProperty() {
        return typeListProperty;
    }

    public void setTypeListProperty(List<Class<?>> typeListProperty) {
        this.typeListProperty = typeListProperty;
    }

    public List<Double> getDoubleWrapperListProperty() {
        return doubleWrapperListProperty;
    }

    public void setDoubleWrapperListProperty(List<Double> doubleWrapperListProperty) {
        this.doubleWrapperListProperty = doubleWrapperListProperty;
    }

    public List<Float> getFloatWrapperListProperty() {
        return floatWrapperListProperty;
    }

    public void setFloatWrapperListProperty(List<Float> floatWrapperListProperty) {
        this.floatWrapperListProperty = floatWrapperListProperty;
    }

    public List<Integer> getIntWrapperListProperty() {
        return intWrapperListProperty;
    }

    public void setIntWrapperListProperty(List<Integer> intWrapperListProperty) {
        this.intWrapperListProperty = intWrapperListProperty;
    }

    public List<Long> getLongWrapperListProperty() {
        return longWrapperListProperty;
    }

    public void setLongWrapperListProperty(List<Long> longWrapperListProperty) {
        this.longWrapperListProperty = longWrapperListProperty;
    }

    public List<Short> getShortWrapperListProperty() {
        return shortWrapperListProperty;
    }

    public void setShortWrapperListProperty(List<Short> shortWrapperListProperty) {
        this.shortWrapperListProperty = shortWrapperListProperty;
    }

    public List<String> getStringListProperty() {
        return stringListProperty;
    }

    public void setStringListProperty(List<String> stringListProperty) {
        this.stringListProperty = stringListProperty;
    }

    public List<BeanWithProperties> getReferenceListProperty() {
        return referenceListProperty;
    }

    public void setReferenceListProperty(List<BeanWithProperties> referenceListProperty) {
        this.referenceListProperty = referenceListProperty;
    }

    public BeanWithProperties withReferenceListProperty(List<BeanWithProperties> referenceListProperty) {
        setReferenceListProperty(referenceListProperty);
        return this;
    }

    public boolean[] getBooleanArrayProperty() {
        return booleanArrayProperty;
    }

    public void setBooleanArrayProperty(boolean[] booleanArrayProperty) {
        this.booleanArrayProperty = booleanArrayProperty;
    }

    public BigDecimal[] getBigDecimalArrayProperty() {
        return bigDecimalArrayProperty;
    }

    public void setBigDecimalArrayProperty(BigDecimal[] bigDecimalArrayProperty) {
        this.bigDecimalArrayProperty = bigDecimalArrayProperty;
    }

    public BigInteger[] getBigIntegerArrayProperty() {
        return bigIntegerArrayProperty;
    }

    public void setBigIntegerArrayProperty(BigInteger[] bigIntegerArrayProperty) {
        this.bigIntegerArrayProperty = bigIntegerArrayProperty;
    }

    public byte[] getByteArrayProperty() {
        return byteArrayProperty;
    }

    public void setByteArrayProperty(byte[] byteArrayProperty) {
        this.byteArrayProperty = byteArrayProperty;
    }

    public char[] getCharArrayProperty() {
        return charArrayProperty;
    }

    public void setCharArrayProperty(char[] charArrayProperty) {
        this.charArrayProperty = charArrayProperty;
    }

    public Class<?>[] getTypeArrayProperty() {
        return typeArrayProperty;
    }

    public void setTypeArrayProperty(Class<?>[] typeArrayProperty) {
        this.typeArrayProperty = typeArrayProperty;
    }

    public double[] getDoubleArrayProperty() {
        return doubleArrayProperty;
    }

    public void setDoubleArrayProperty(double[] doubleArrayProperty) {
        this.doubleArrayProperty = doubleArrayProperty;
    }

    public float[] getFloatArrayProperty() {
        return floatArrayProperty;
    }

    public void setFloatArrayProperty(float[] floatArrayProperty) {
        this.floatArrayProperty = floatArrayProperty;
    }

    public int[] getIntArrayProperty() {
        return intArrayProperty;
    }

    public void setIntArrayProperty(int[] intArrayProperty) {
        this.intArrayProperty = intArrayProperty;
    }

    public long[] getLongArrayProperty() {
        return longArrayProperty;
    }

    public void setLongArrayProperty(long[] longArrayProperty) {
        this.longArrayProperty = longArrayProperty;
    }

    public short[] getShortArrayProperty() {
        return shortArrayProperty;
    }

    public void setShortArrayProperty(short[] shortArrayProperty) {
        this.shortArrayProperty = shortArrayProperty;
    }

    public String[] getStringArrayProperty() {
        return stringArrayProperty;
    }

    public void setStringArrayProperty(String[] stringArrayProperty) {
        this.stringArrayProperty = stringArrayProperty;
    }

    public BeanWithProperties[] getReferenceArrayProperty() {
        return referenceArrayProperty;
    }

    public void setReferenceArrayProperty(BeanWithProperties[] referenceArrayProperty) {
        this.referenceArrayProperty = referenceArrayProperty;
    }

    public BeanWithProperties withReferenceArrayProperty(BeanWithProperties[] referenceArrayProperty) {
        setReferenceArrayProperty(referenceArrayProperty);
        return this;
    }

    public Boolean[] getBooleanWrapperArrayProperty() {
        return booleanWrapperArrayProperty;
    }

    public void setBooleanWrapperArrayProperty(Boolean[] booleanWrapperArrayProperty) {
        this.booleanWrapperArrayProperty = booleanWrapperArrayProperty;
    }

    public Byte[] getByteWrapperArrayProperty() {
        return byteWrapperArrayProperty;
    }

    public void setByteWrapperArrayProperty(Byte[] byteWrapperArrayProperty) {
        this.byteWrapperArrayProperty = byteWrapperArrayProperty;
    }

    public Character[] getCharWrapperArrayProperty() {
        return charWrapperArrayProperty;
    }

    public void setCharWrapperArrayProperty(Character[] charWrapperArrayProperty) {
        this.charWrapperArrayProperty = charWrapperArrayProperty;
    }

    public Double[] getDoubleWrapperArrayProperty() {
        return doubleWrapperArrayProperty;
    }

    public void setDoubleWrapperArrayProperty(Double[] doubleWrapperArrayProperty) {
        this.doubleWrapperArrayProperty = doubleWrapperArrayProperty;
    }

    public Float[] getFloatWrapperArrayProperty() {
        return floatWrapperArrayProperty;
    }

    public void setFloatWrapperArrayProperty(Float[] floatWrapperArrayProperty) {
        this.floatWrapperArrayProperty = floatWrapperArrayProperty;
    }

    public Integer[] getIntWrapperArrayProperty() {
        return intWrapperArrayProperty;
    }

    public void setIntWrapperArrayProperty(Integer[] intWrapperArrayProperty) {
        this.intWrapperArrayProperty = intWrapperArrayProperty;
    }

    public Long[] getLongWrapperArrayProperty() {
        return longWrapperArrayProperty;
    }

    public void setLongWrapperArrayProperty(Long[] longWrapperArrayProperty) {
        this.longWrapperArrayProperty = longWrapperArrayProperty;
    }

    public Short[] getShortWrapperArrayProperty() {
        return shortWrapperArrayProperty;
    }

    public void setShortWrapperArrayProperty(Short[] shortWrapperArrayProperty) {
        this.shortWrapperArrayProperty = shortWrapperArrayProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BeanWithProperties))
            return false;
        BeanWithProperties that = (BeanWithProperties) o;
        return isBooleanProperty() == that.isBooleanProperty() && getByteProperty() == that.getByteProperty() && getCharProperty() == that.getCharProperty() && Double.compare(that.getDoubleProperty(), getDoubleProperty()) == 0 && Float.compare(that.getFloatProperty(), getFloatProperty()) == 0 && getIntProperty() == that.getIntProperty() && getLongProperty() == that.getLongProperty() && getShortProperty() == that.getShortProperty() && Objects.equals(getBigDecimalProperty(), that.getBigDecimalProperty()) && Objects.equals(getBigIntegerProperty(), that.getBigIntegerProperty()) && Objects.equals(getTypeProperty(), that.getTypeProperty()) && Objects.equals(getStringProperty(), that.getStringProperty()) && Objects.equals(getReferenceProperty(), that.getReferenceProperty()) && Objects.equals(getBooleanWrapperProperty(), that.getBooleanWrapperProperty()) && Objects.equals(getByteWrapperProperty(), that.getByteWrapperProperty()) && Objects.equals(getCharWrapperProperty(), that.getCharWrapperProperty()) && Objects.equals(getDoubleWrapperProperty(), that.getDoubleWrapperProperty()) && Objects.equals(getFloatWrapperProperty(), that.getFloatWrapperProperty()) && Objects.equals(getIntWrapperProperty(), that.getIntWrapperProperty()) && Objects.equals(getLongWrapperProperty(), that.getLongWrapperProperty()) && Objects.equals(getShortWrapperProperty(), that.getShortWrapperProperty()) && Objects.equals(getStringWrapperProperty(), that.getStringWrapperProperty()) && Objects.equals(getBooleanWrapperListProperty(), that.getBooleanWrapperListProperty()) && Objects.equals(getBigDecimalListProperty(), that.getBigDecimalListProperty()) && Objects.equals(getBigIntegerListProperty(), that.getBigIntegerListProperty()) && Objects.equals(getByteWrapperListProperty(), that.getByteWrapperListProperty()) && Objects.equals(getCharWrapperListProperty(), that.getCharWrapperListProperty()) && Objects.equals(getTypeListProperty(), that.getTypeListProperty()) && Objects.equals(getDoubleWrapperListProperty(), that.getDoubleWrapperListProperty()) && Objects.equals(getFloatWrapperListProperty(), that.getFloatWrapperListProperty()) && Objects.equals(getIntWrapperListProperty(), that.getIntWrapperListProperty()) && Objects.equals(getLongWrapperListProperty(), that.getLongWrapperListProperty()) && Objects.equals(getShortWrapperListProperty(), that.getShortWrapperListProperty()) && Objects.equals(getStringListProperty(), that.getStringListProperty()) && Objects.equals(getReferenceListProperty(), that.getReferenceListProperty()) && Arrays.equals(getBooleanArrayProperty(), that.getBooleanArrayProperty()) && Arrays.equals(getBigDecimalArrayProperty(), that.getBigDecimalArrayProperty()) && Arrays.equals(getBigIntegerArrayProperty(), that.getBigIntegerArrayProperty()) && Arrays.equals(getByteArrayProperty(), that.getByteArrayProperty()) && Arrays.equals(getCharArrayProperty(), that.getCharArrayProperty()) && Arrays.equals(getTypeArrayProperty(), that.getTypeArrayProperty()) && Arrays.equals(getDoubleArrayProperty(), that.getDoubleArrayProperty()) && Arrays.equals(getFloatArrayProperty(), that.getFloatArrayProperty()) && Arrays.equals(getIntArrayProperty(), that.getIntArrayProperty()) && Arrays.equals(getLongArrayProperty(), that.getLongArrayProperty()) && Arrays.equals(getShortArrayProperty(), that.getShortArrayProperty()) && Arrays.equals(getStringArrayProperty(), that.getStringArrayProperty()) && Arrays.equals(getReferenceArrayProperty(), that.getReferenceArrayProperty()) && Arrays.equals(getBooleanWrapperArrayProperty(), that.getBooleanWrapperArrayProperty()) && Arrays.equals(getByteWrapperArrayProperty(), that.getByteWrapperArrayProperty()) && Arrays.equals(getCharWrapperArrayProperty(), that.getCharWrapperArrayProperty()) && Arrays.equals(getDoubleWrapperArrayProperty(), that.getDoubleWrapperArrayProperty()) && Arrays.equals(getFloatWrapperArrayProperty(), that.getFloatWrapperArrayProperty()) && Arrays.equals(getIntWrapperArrayProperty(), that.getIntWrapperArrayProperty()) && Arrays.equals(getLongWrapperArrayProperty(), that.getLongWrapperArrayProperty()) && Arrays.equals(getShortWrapperArrayProperty(), that.getShortWrapperArrayProperty());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isBooleanProperty(), getBigDecimalProperty(), getBigIntegerProperty(), getByteProperty(), getCharProperty(), getTypeProperty(), getDoubleProperty(), getFloatProperty(), getIntProperty(), getLongProperty(), getShortProperty(), getStringProperty(), getReferenceProperty(), getBooleanWrapperProperty(), getByteWrapperProperty(), getCharWrapperProperty(), getDoubleWrapperProperty(), getFloatWrapperProperty(), getIntWrapperProperty(), getLongWrapperProperty(), getShortWrapperProperty(), getStringWrapperProperty(), getBooleanWrapperListProperty(), getBigDecimalListProperty(), getBigIntegerListProperty(), getByteWrapperListProperty(), getCharWrapperListProperty(), getTypeListProperty(), getDoubleWrapperListProperty(), getFloatWrapperListProperty(), getIntWrapperListProperty(), getLongWrapperListProperty(), getShortWrapperListProperty(), getStringListProperty(), getReferenceListProperty());
        result = 31 * result + Arrays.hashCode(getBooleanArrayProperty());
        result = 31 * result + Arrays.hashCode(getBigDecimalArrayProperty());
        result = 31 * result + Arrays.hashCode(getBigIntegerArrayProperty());
        result = 31 * result + Arrays.hashCode(getByteArrayProperty());
        result = 31 * result + Arrays.hashCode(getCharArrayProperty());
        result = 31 * result + Arrays.hashCode(getTypeArrayProperty());
        result = 31 * result + Arrays.hashCode(getDoubleArrayProperty());
        result = 31 * result + Arrays.hashCode(getFloatArrayProperty());
        result = 31 * result + Arrays.hashCode(getIntArrayProperty());
        result = 31 * result + Arrays.hashCode(getLongArrayProperty());
        result = 31 * result + Arrays.hashCode(getShortArrayProperty());
        result = 31 * result + Arrays.hashCode(getStringArrayProperty());
        result = 31 * result + Arrays.hashCode(getReferenceArrayProperty());
        result = 31 * result + Arrays.hashCode(getBooleanWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getByteWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getCharWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getDoubleWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getFloatWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getIntWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getLongWrapperArrayProperty());
        result = 31 * result + Arrays.hashCode(getShortWrapperArrayProperty());
        return result;
    }
}
