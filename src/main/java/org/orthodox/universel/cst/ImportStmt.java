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

package org.orthodox.universel.cst;

import java.util.Collections;
import java.util.List;

/**
 * An import statement on the Abstract Syntax Tree, of the form:
 * <ul>
 * <li><code>import com.acme.services.MyService</code></li>
 * <li><code>import com.acme.services.*</code></li>
 * <li><code>import com.acme.services.MyService.myStaticMethod</code></li>
 * <li><code>import com.acme.services.MyService.*</code></li>
 * </ul>
 */
public class ImportStmt extends Expression {
    /** The name elements of this import statement. */
    private List<Name> elements;

    /** Whether this import was an on-demand import. */
    private boolean isOnDemand;

    /**
     * Consructs set expression from the given parser token image.
     *
     * @param tokenImage the parser token image.
     * @param elements the element expressions of this set expression, which may be empty.
     */
    public ImportStmt(TokenImage tokenImage, List<Name> elements, boolean isOnDemand) {
        super(tokenImage);
        this.elements = elements;
        this.isOnDemand = isOnDemand;
    }

    /**
     * Gets the name elements of this import statement.
     *
     * @return the the name elements of this import statement.
     */
    public List<Name> getElements() {
        return elements == null ? Collections.emptyList() : elements;
    }

    /**
     * Whether this import was an on-demand import.
     *
     * @return true is this import is an on-demand import, false otherwise.
     */
    public boolean isOnDemand() {
        return isOnDemand;
    }
}
