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
package com.daniel.cartooncharacters.controller;

import com.daniel.cartooncharacters.task.SaveCartoonTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This controller supports the view for adding a new cartoon to the database.
 *
 * @author Bryan Daniel
 */
public class AddCartoonController {

    /**
     * The name of the cartoon to add
     */
    @FXML
    private TextField cartoonNameTextField;

    /**
     * The description of the cartoon to add
     */
    @FXML
    private TextArea cartoonDescriptionTextArea;

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * Called after construction to instantiate the input validation object.
     */
    @FXML
    public void initialize() {
        validator = new InputValidator();
    }

    /**
     * Handles the action for the close button.
     *
     * @param event the action event
     */
    @FXML
    void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action for the save button.
     *
     * @param event the action event
     */
    @FXML
    void handleSaveAction(ActionEvent event) {

        if (validator.inputValidForCartoonCreation(cartoonNameTextField.getText(), cartoonDescriptionTextArea.getText())) {
            SaveCartoonTask task = new SaveCartoonTask(cartoonNameTextField.getText(), cartoonDescriptionTextArea.getText());
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
