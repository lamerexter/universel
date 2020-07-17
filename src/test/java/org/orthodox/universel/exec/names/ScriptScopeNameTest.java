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

import org.beanplanet.core.beans.JavaBean;
import org.beanplanet.messages.domain.Message;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.orthodox.universel.BeanWithProperties;
import org.orthodox.universel.compiler.CompilationErrorsException;
import org.orthodox.universel.compiler.Messages;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.orthodox.universel.Universal.execute;
import static org.orthodox.universel.compiler.Messages.NAME.NAME_NOT_RESOLVED;

public class ScriptScopeNameTest {
    @Test
    public void nameNotFound() {
        final String nameThatWillNotResolve = "nameThatWillNotResolve";

        CompilationErrorsException ex = assertThrows(CompilationErrorsException.class, () -> assertThat(execute(nameThatWillNotResolve), nullValue()));

        Optional<Message> notFoundMessage = ex.getMessages().findErrorWithCode(NAME_NOT_RESOLVED.getCode());
        assertThat(notFoundMessage.isPresent(), is(true));
        assertThat(notFoundMessage.get().getMessageParameters(), equalTo(new String[]{ nameThatWillNotResolve }));
        assertThat(notFoundMessage.get().getRelatedObject(), notNullValue());
    }

    @Test
    public void nameFound() {
        // Given
        BeanWithProperties referencePropertyValue = new BeanWithProperties();
        BeanWithProperties binding = new JavaBean<>(new BeanWithProperties())
            .with("booleanProperty", true)
            .with("bigDecimalProperty", new BigDecimal("123.456"))
            .with("bigIntegerProperty", new BigInteger("1234567890"))
            .with("byteProperty", (byte)64)
            .with("charProperty", 'Z')
            .with("doubleProperty", 512d)
            .with("floatProperty", 128f)
            .with("intProperty", 123)
            .with("longProperty", 123456L)
            .with("shortProperty", (short)32767)
            .with("stringProperty", "Hello World!")
            .with("referenceProperty", referencePropertyValue)
            .with("typeProperty", Number.class)
            .getBean();

        // Then
        assertThat(execute("booleanProperty", binding), equalTo(binding.isBooleanProperty()));
        assertThat(execute("bigDecimalProperty", binding), equalTo(binding.getBigDecimalProperty()));
        assertThat(execute("bigIntegerProperty", binding), equalTo(binding.getBigIntegerProperty()));
        assertThat(execute("byteProperty", binding), equalTo(binding.getByteProperty()));
        assertThat(execute("charProperty", binding), equalTo(binding.getCharProperty()));
        assertThat(execute("doubleProperty", binding), equalTo(binding.getDoubleProperty()));
        assertThat(execute("floatProperty", binding), equalTo(binding.getFloatProperty()));
        assertThat(execute("intProperty", binding), equalTo(binding.getIntProperty()));
        assertThat(execute("longProperty", binding), equalTo(binding.getLongProperty()));
        assertThat(execute("shortProperty", binding), equalTo(binding.getShortProperty()));
        assertThat(execute("stringProperty", binding), equalTo(binding.getStringProperty()));
        assertThat(execute("referenceProperty", binding), equalTo(binding.getReferenceProperty()));
        assertThat(execute("typeProperty", binding), equalTo(binding.getTypeProperty()));
    }

    @Test
    public void interpolationOfBindVariableProperty() {
        // Given
        BeanWithProperties binding = new JavaBean<>(new BeanWithProperties())
                                         .with("stringProperty", "Mr An Ming")
                                         .getBean();

        // Then
        assertThat(execute("\"Hello my friend, ${stringProperty}!\"", binding), equalTo("Hello my friend, Mr An Ming!"));
    }

}
