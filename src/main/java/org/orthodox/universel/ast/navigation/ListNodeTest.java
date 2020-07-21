/******************************************************************************* 
 * Copyright 2004-2009 BeanPlanet Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.orthodox.universel.ast.navigation;

import org.orthodox.universel.ast.Expression;
import org.orthodox.universel.ast.TokenImage;

import java.util.List;

public class ListNodeTest extends Expression implements ReductionNodeTest {
    public ListNodeTest(TokenImage tokenImage) {
        super(tokenImage, List.class);
    }

    public final Class<?> getReductionType() {
        return List.class;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ListNodeTest))
            return false;
        if (!super.equals(o))
            return false;

        return true;
    }
}
