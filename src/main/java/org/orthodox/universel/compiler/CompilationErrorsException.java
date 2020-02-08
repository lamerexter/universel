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

package org.orthodox.universel.compiler;

import org.beanplanet.core.util.StringUtil;
import org.beanplanet.messages.domain.Message;
import org.beanplanet.messages.domain.Messages;
import org.orthodox.universel.UniversalException;

import java.io.File;

/**
 * A compilation exception, thrown when there are errors produced from compilation.
 */
public class CompilationErrorsException extends UniversalException {
    private Messages messages;

    public CompilationErrorsException(Messages messages) {
        super("Compilation errors: "+System.lineSeparator()+getErrorMessagesSummary(messages));
        this.messages = messages;
    }

    public static final String getErrorMessagesSummary(Messages messages) {
        StringBuilder s = new StringBuilder();
        int errorNum = 1;
        for (Message error : messages.getErrors()) {
            s.append(errorNum++).append(": ").append(error.getRenderedMessage()).append(System.lineSeparator());
        }

        return s.toString();
    }
}
