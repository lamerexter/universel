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

package org.orthodox.universel.execute.objectcreation;

import java.util.Objects;

public class TestClass {
    private int intField;
    private Double doubleField;
    private long longField;
    private String stringField;

    public TestClass() {}

    public TestClass(int intField) {
        this.intField = intField;
    }

    public TestClass(double doubleField) {
        this.doubleField = doubleField;
    }

    public TestClass(long longField) {
        this.longField = longField;
    }

    public TestClass(String stringField) {
        this.stringField = stringField;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if ( !(other instanceof TestClass) ) return false;

        TestClass that = (TestClass)other;
        return Objects.equals(this.intField, that.intField)
                && Objects.equals(this.doubleField, that.doubleField)
                && Objects.equals(this.longField, that.longField)
                && Objects.equals(this.stringField, that.stringField);
    }

    public int hashCode() {
        return Objects.hash(intField, doubleField, longField, stringField);
    }
}
