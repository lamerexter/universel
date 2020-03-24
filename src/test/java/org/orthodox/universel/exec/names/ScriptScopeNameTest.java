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

package org.orthodox.universel.exec.names;

import org.junit.jupiter.api.Test;
import org.orthodox.universel.Universal;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScriptScopeNameTest {
    @Test
    public void nameNotFound() {
        assertThat(Universal.execute("theVariable"), nullValue());
    }

    @Test
    public void nameFound() {
        Map<String, Object> binding = new HashMap<>();
        binding.put("booleanValue", true);
        binding.put("byteValue", (byte)64);
        binding.put("charValue", 'Z');
        binding.put("floatValue", 128f);
        binding.put("doubleValue", (char)512d);
        binding.put("intValue", 4096);
        binding.put("longValue", 8192L);
        binding.put("shortValue", (short)32767);
        binding.put("stringValue", "HelloWorld");

        binding.forEach((key, value) -> assertThat(Universal.execute(key, binding), equalTo(binding.get(key))));
    }

    @Test
    public void interpolationOfVariable() {
        Map<String, Object> binding = new HashMap<>();
        binding.put("personName", "Mr An Ming");
        assertThat(Universal.execute("\"Hello my friend, ${personName}!\"", binding), equalTo("Hello my friend, Mr An Ming!"));
    }

}
