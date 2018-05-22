/*
 * Copyright 2018 Bryan Daniel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daniel.cartooncharacters.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Used to provide dialog messages after task executions.
 *
 * @author Bryan Daniel
 */
public class MessageStage extends Stage {

    /**
     * The constant for minimum width of the message window
     */
    public static final int MIN_SCENE_WIDTH = 400;

    /**
     * The constant for minimum height of the message window
     */
    public static final int MIN_SCENE_HEIGHT = 250;

    /**
     * Creates the content for the scene to display a message indicating a task
     * result.
     *
     * @param label the label to display
     */
    public MessageStage(Label label) {
        BorderPane root = new BorderPane();
        root.getStylesheets().add(this.getClass().getResource("/styles/main.css").toExternalForm());
        root.getStyleClass().add("borderPane");
        root.setCenter(label);
        BorderPane.setAlignment(label, Pos.CENTER);
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> close());
        BorderPane.setAlignment(okButton, Pos.CENTER);
        root.setPadding(new Insets(10, 10, 50, 10));
        root.setBottom(okButton);
        setScene(new Scene(root, MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT));
        setTitle("Message");
    }
}
