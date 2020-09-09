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

package org.orthodox.universel.compiler;

import org.beanplanet.messages.domain.Message;

import static org.beanplanet.messages.domain.Message.Builder;
import static org.beanplanet.messages.domain.Message.builder;

public interface Messages {
    interface ConditionalExpression {
        Builder AMBIGUOUS_TYPE = builder().code("uel.ternary.type.ambiguous").parameterisedMessage("The type of the conditional expression cannot be determined");
    }

    interface Constructor {
        Builder CONSTRUCTOR_AMBIGUOUS = builder().code("uel.constructor.ambiguous").parameterisedMessage("The call to constructor named \"{0}\" is ambiguous and matches {1} methods: {2}");
    }

    interface FieldDeclaration {
        Message VOID_TYPE = builder().code("uel.field.voidtype.illegal").parameterisedMessage("The void type may not be used in field declaraions").build();
    }

    interface MethodCall {
        Builder METHOD_NOT_FOUND = builder().code("uel.method.notfound").parameterisedMessage("Method named \"{0}\" not found");
        Builder METHOD_AMBIGUOUS = builder().code("uel.method.ambiguous").parameterisedMessage("T call to method named \"{0}\" is ambiguous and matches {1} methods: {2}");
    }

    interface MethodDeclaration {
        Builder MISSING_RETURN = builder().code("uel.method.missingreturn").parameterisedMessage("Missing return statement from method");
        Builder IMPLICIT_RETURN_TYPE_MISMATCH = builder().code("uel.method.implicitreturntypemismatch").parameterisedMessage("Implicitly returned type \"{0}\" cannot be converted to method return type \"{1}\"");
    }

    interface NAME {
        Builder NAME_NOT_RESOLVED = builder().code("uel.navigation.unresolvedname").parameterisedMessage("The name \"{0}\" could not be resolved");
    }

    interface OPERATOR {
        Message UNARY_NOTFOUND = builder().code("uel.operator.unary.notapplicable").parameterisedMessage("Unary \"{0}\" operator not applicable for the given expression type").build();
    }

    interface NavigationExpression {
        Builder UNRESOLVED_STEP = builder().code("uel.navigation.unresolvedstep").parameterisedMessage("The navigation step could not be resolved. Is there a navigator function available on the classpath for this type of navigation? ");
    }

    interface TYPE {
        Builder TYPE_NOT_FOUND = builder().code("uel.type.notfound").parameterisedMessage("Type named \"{0}\" not found");
        Builder TYPE_AMBIGUOUS = builder().code("uel.type.ambiguous").parameterisedMessage("The type reference \"{0}\" is ambiguous and matches {1} inferred types: {2}");
    }
}
