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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.beanplanet.core.io.resource.UrlResource;
import org.beanplanet.core.logging.Logger;
import org.orthodox.universel.Universal;
import org.orthodox.universel.compiler.CompiledUnit;
import org.orthodox.universel.cst.Node;
import org.orthodox.universel.cst.ParseTree;
import org.orthodox.universel.cst.Script;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.orthodox.universel.Universal.execute;

public class UniversalWorkbench extends Application implements Logger {
    private Scene mainScene;

    public void start(Stage stage) {

        // Create the Scene
        Parent mainPanel = execute(Parent.class, new UrlResource(getClass().getResource("workbench.uni")));

        Scene scene = new Scene(mainPanel);
        scene.setRoot(mainPanel());
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("Universal Workbench");

        mainScene = scene;

        // Display the Stage
        stage.show();
    }

    public Parent mainPanel() {
        final SplitPane mainPanel = new SplitPane();
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
        commandNavView.setPadding(new Insets(5));

        final SplitPane scriptResultsPanel = new SplitPane();
        scriptResultsPanel.setPadding(new Insets(0, 0, 0, 10));
//        scriptResultsPanel.setStyle("-fx-background-color: rgba(100, 100, 100, 0.5);");
        scriptResultsPanel.setStyle("-fx-background-color: transparent;");
        BorderPane scriptPanel = new BorderPane();
        final TextArea scriptTextArea = new TextArea();
        scriptPanel.setCenter(scriptTextArea);

        Button bindingsButton = new Button("Bindings ...");
        VBox scriptActionsVBox = new VBox(bindingsButton);
        scriptPanel.setRight(scriptActionsVBox);
        scriptActionsVBox.setPadding(new Insets(0, 0, 0, 10));

        bindingsButton.setOnAction(event -> {
            editBindings();
        });

        BorderPane resultsPanel = new BorderPane();
        final TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);
        resultsPanel.setCenter(resultTextArea);

        Button parseScriptButton = new Button("Parse");
        parseScriptButton.setOnAction(e -> {
            Script script = Universal.parse(scriptTextArea.getText());
            commandNavView.setRoot(createAbstractSyntaxTree(new ParseTree(script)));
        });
        Button compileScriptButton = new Button("Compile");
        Button executeScriptButton = new Button("Execute");
        executeScriptButton.setOnAction(e -> {
            CompiledUnit compiled = Universal.compile(scriptTextArea.getText());

            Map<String, Object> binding = createBinding();
            Object result = execute(scriptTextArea.getText(), binding);
            commandNavView.setRoot(createAbstractSyntaxTree(new ParseTree(compiled.getAstNode())));
            resultTextArea.setText(String.valueOf(result));

        });
        compileScriptButton.setDisable(true);
        HBox scriptActionsPanel = new HBox(parseScriptButton, compileScriptButton, executeScriptButton);
        scriptActionsPanel.setPadding(new Insets(15));
        scriptActionsPanel.setSpacing(10);
        scriptPanel.setBottom(scriptActionsPanel);

        scriptResultsPanel.setOrientation(Orientation.VERTICAL);
        scriptResultsPanel.getItems().addAll(scriptPanel, resultsPanel);

        mainPanel.getItems().addAll(commandNavView, scriptResultsPanel);

        commandNavView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int startLineChar = scriptLineChar(scriptTextArea.getText(), newValue.getValue().getTokenImage().getStartLine());
            int endLineChar = scriptLineChar(scriptTextArea.getText(), newValue.getValue().getTokenImage().getEndLine());

            scriptTextArea.selectRange(endLineChar+newValue.getValue().getTokenImage().getEndColumn(),
                                       startLineChar+newValue.getValue().getTokenImage().getStartColumn()-1);
        });

        return mainPanel;
    }

    private Map<String, Object> createBinding() {
        if ( bindingsTable == null ) return Collections.emptyMap();

        return bindingsTable.getItems().stream().collect(Collectors.toMap(Binding::getName, Binding::getUniversalValue));
    }

    private Dialog bindingsDialog;
    private TableView<Binding> bindingsTable;
    private void editBindings() {
        if ( bindingsDialog != null ) {
            bindingsDialog.show();
            return;
        }

        BorderPane bindimgsMainPanel = new BorderPane();
        bindingsTable = new TableView<>();
        bindingsTable.setEditable(true);
        TableColumn<Binding, String> bindingNameColumn = createCol("Name");
        bindingNameColumn.setMinWidth(200);
        TableColumn<Binding, Object> bindingValueColumn = createCol("Value");
        bindingsTable.getColumns().addAll(bindingNameColumn, bindingValueColumn);


        Button addBindingButton = new Button("Add");
        addBindingButton.setMaxWidth(Double.MAX_VALUE);
        addBindingButton.setOnAction(value -> {
            Binding binding = new Binding();
            bindingsTable.getItems().add(binding);
        });
        Button removeBindingButton = new Button("Remove");
        removeBindingButton.setMaxWidth(Double.MAX_VALUE);
        removeBindingButton.setDisable(bindingsTable.getItems().isEmpty());
        VBox editButtonsPanel = new VBox(addBindingButton, removeBindingButton);
        editButtonsPanel.setSpacing(10);
        editButtonsPanel.setFillWidth(true);
        editButtonsPanel.setPadding(new Insets(0, 0, 0, 10));
        bindimgsMainPanel.setCenter(bindingsTable);
        bindimgsMainPanel.setRight(editButtonsPanel);

        bindingsDialog = new Dialog();
        bindingsDialog.setTitle("Bindings");
        bindingsDialog.setResizable(true);
        bindingsDialog.getDialogPane().setContent(bindimgsMainPanel);
        bindingsDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        bindingsDialog.initModality(Modality.WINDOW_MODAL);
        bindingsDialog.initOwner(mainScene.getWindow());
        bindingsDialog.getDialogPane().setPrefWidth(700);
        bindingsDialog.getDialogPane().setPrefHeight(400);
        bindingsDialog.show();
    }

    public static class Binding {
        private final StringProperty nameProperty = new SimpleStringProperty();
        private final StringProperty valueProperty = new SimpleStringProperty();

        public StringProperty nameProperty() {
            return nameProperty;
        }

        public String getName() {
            return nameProperty.get();
        }

        public StringProperty valueProperty() {
            return valueProperty;
        }

        public Object getUniversalValue() {
            return Universal.execute(valueProperty().get());
        }
    }

    private <T> TableColumn<Binding, T> createCol(final String title) {
        TableColumn<Binding, T> col = new TableColumn<>(title);
        col.setCellValueFactory(binding -> {
            if ( "Name".equals(title) ) {
//                binding.getValue()
//                SimpleObjectProperty<Date> property = new SimpleObjectProperty<>();
//
//                property.setValue(person.getValue().getDateOfBirth());
                return (ObservableValue<T>) binding.getValue().nameProperty();
            } else {
                return (ObservableValue<T>)binding.getValue().valueProperty();
            }
        });
        col.setCellFactory( (Callback)EditCell.<Binding, String>forTableColumn(new DefaultStringConverter()));
        col.setOnEditCommit( event -> {
            if ( "Name".equals(title) ) {
                final String value = (String)(event.getNewValue() != null ? event.getNewValue() : event.getOldValue());
                Binding binding = event.getTableView().getItems().get(event.getTablePosition().getRow());
                binding.nameProperty.setValue(value);
            } else {
                final String value = (String)(event.getNewValue() != null ? event.getNewValue() : event.getOldValue());
                Binding binding = event.getTableView().getItems().get(event.getTablePosition().getRow());
                binding.valueProperty.setValue(value);
            }
        });
        return col;
    }

    private int scriptLineChar(String script, int startLine) {
        int chNum = 0;
        int lineNum = 1;
        while ( chNum < script.length() && lineNum < startLine ) {
            if ( script.charAt(chNum++) == '\n' ) lineNum++;
        }

        return chNum;
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