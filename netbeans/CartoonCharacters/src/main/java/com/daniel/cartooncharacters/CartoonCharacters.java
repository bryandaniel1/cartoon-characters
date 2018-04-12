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
package com.daniel.cartooncharacters;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This is the application class for the cartoon characters program.
 *
 * @author Bryan Daniel
 */
public class CartoonCharacters extends Application {

    /**
     * The constant for minimum width
     */
    public static final int MIN_STAGE_WIDTH = 900;

    /**
     * The constant for minimum height
     */
    public static final int MIN_STAGE_HEIGHT = 500;

    /**
     * The location of a mouse press on the x-axis
     */
    private double xOffset = 0;

    /**
     * The location of a mouse press on the y-axis
     */
    private double yOffset = 0;

    /**
     * Executed when the program launches to set the stage values and define
     * listeners to handle a mouse drag event.
     *
     * @param stage the primary stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent firstView = FXMLLoader.load(getClass().getResource("/fxml/SearchView.fxml"));
        StackPane root = new StackPane(firstView);
        
        root.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((event) -> {
            if (event.getScreenY() == 0) {
                stage.setMaximized(true);
            }
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.setTitle("Cartoon Character Search");
        stage.centerOnScreen();
        stage.setMinHeight(MIN_STAGE_HEIGHT);
        stage.setMinWidth(MIN_STAGE_WIDTH);
        stage.show();
    }

    /**
     * The main method launches the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
