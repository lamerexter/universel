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

package org.orthodox.universel.workbench;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.Universal;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.ParseTree;

import java.util.List;
import java.util.stream.Collectors;

public class UniversalWorkbench extends Application implements Logger {
    public void start(Stage stage) {
        final BorderPane mainPanel = new BorderPane();
        mainPanel.setPadding(new Insets(15));
        mainPanel.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #547AAB, #A0B5D1)");

        final TreeView<Node> commandNavView = new TreeView<>();
        commandNavView.setCellFactory(new Callback<TreeView<Node>, TreeCell<Node>>() {
            @Override
            public TreeCell<Node> call(TreeView<Node> param) {
                return new TextFieldTreeCell<>(new StringConverter<Node>() {
                    @Override
                    public String toString(Node node) {
                        return node.getClass().getSimpleName();
                    }

                    @Override
                    public Node fromString(String string) {
                        return null;
                    }
                });
            }
        });
        commandNavView.setStyle("rgba(255, 255, 255, 1)");
        commandNavView.setPadding(new Insets(10));

        BorderPane scriptPanel = new BorderPane();
        TextArea scriptTextArea = new TextArea();
        scriptPanel.setPadding(new Insets(0, 0, 0, 10));
        scriptPanel.setCenter(scriptTextArea);

        Button parseScriptButton = new Button("Parse");
        Button compileScriptButton = new Button("Compile");
        Button executeScriptButton = new Button("Execute");
        executeScriptButton.setOnAction(e -> {
            CompiledUnit compiled = Universal.compile(scriptTextArea.getText());
            commandNavView.setRoot(createAbstractSyntaxTree(new ParseTree(compiled.getAstNode())));
        });
        HBox scriptActionsPanel = new HBox(parseScriptButton, compileScriptButton, executeScriptButton);
        scriptActionsPanel.setPadding(new Insets(15));
        scriptActionsPanel.setSpacing(10);
        scriptPanel.setBottom(scriptActionsPanel);

        mainPanel.setLeft(commandNavView);
        mainPanel.setCenter(scriptPanel);

        // Create the Scene
        Scene scene = new Scene(mainPanel);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("Universal Workbench");

        // Display the Stage
        stage.show();
    }

    @SuppressWarnings("unchecked")
    public static TreeItem<Node> createAbstractSyntaxTree(ParseTree parseTree) {
        TreeItem<Node> fsRoot = new TreeItem<>(parseTree.getRoot());
        List<TreeItem<Node>> parseTreeToplevelItems = parseTree.getChildren(parseTree.getRoot()).stream()
                                                     .map(f -> createParseTreeNodeItem(parseTree, f))
                                                     .collect(Collectors.toList());
        fsRoot.getChildren().addAll((List)parseTreeToplevelItems);
        fsRoot.setExpanded(true);
        return fsRoot;
    }

    private static TreeItem<Node> createParseTreeNodeItem(ParseTree parseTree, Node f) {
        TreeItem<Node> nodeTreeItem = new TreeItem<>(f);
        nodeTreeItem.setExpanded(true);
        parseTree.getChildren(f).stream().forEach(c -> nodeTreeItem.getChildren().add(createParseTreeNodeItem(parseTree, c)));
        return nodeTreeItem;
    }

}
