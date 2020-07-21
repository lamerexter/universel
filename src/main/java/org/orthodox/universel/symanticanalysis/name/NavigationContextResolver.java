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

package org.orthodox.universel.symanticanalysis.name;

import org.beanplanet.core.collections.ListBuilder;
import org.orthodox.universel.ast.navigation.NavigationStep;
import org.orthodox.universel.ast.navigation.NavigationStream;
import org.orthodox.universel.ast.navigation.NodeType;
import org.orthodox.universel.ast.Node;
import org.orthodox.universel.ast.TokenImage;
import org.orthodox.universel.symanticanalysis.AbstractSemanticAnalyser;

import static org.orthodox.universel.ast.navigation.NavigationAxis.*;

public class NavigationContextResolver extends AbstractSemanticAnalyser {

    @Override
    public Node visitNavigationStream(NavigationStream node) {
        if (node.getChildNodes().isEmpty())
            return node;

        //--------------------------------------------------------------------------------------------------------------
        // Add a navigation context (self/current) to the navigation start, if there is not already a context in place.
        //--------------------------------------------------------------------------------------------------------------
        if ( node.getChildNodes().get(0) instanceof NavigationStep ) {
            NavigationStep<?> firstStep = (NavigationStep<?>)node.getChildNodes().get(0);
            if ( !SELF.getCanonicalName().equals(firstStep.getAxis()) && !(firstStep.getNodeTest() instanceof NodeType) ) {
                TokenImage firstStepTokenImage = node.getChildNodes().get(0).getTokenImage();
                return new NavigationStream(ListBuilder.<Node>builder()
                                                .add(new NavigationStep(firstStepTokenImage,
                                                                        SELF,
                                                                        new NodeType(firstStepTokenImage)
                                                ))
                                                .addAll(node.getChildNodes())
                                                .build());
            }
        }

        return node;
    }
}
