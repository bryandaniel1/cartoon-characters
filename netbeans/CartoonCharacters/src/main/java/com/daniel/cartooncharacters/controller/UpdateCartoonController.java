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

import com.daniel.cartooncharacters.data.SimpleCartoonDataAccess;
import com.daniel.cartooncharacters.task.SearchCartoonTask;
import com.daniel.cartooncharacters.task.UpdateCartoonTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * This controller supports the view for updating an existing cartoon in the
 * database.
 *
 * @author Bryan Daniel
 */
public class UpdateCartoonController {

    /**
     * The cartoon description to update
     */
    @FXML
    private TextArea cartoonDescriptionTextArea;

    /**
     * The combo box for the cartoon selection
     */
    @FXML
    private ComboBox<String> cartoonNameComboBox;

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * Initializes this controller class by populating the combo box and
     * establishing the event handler for the cartoon name selection.
     */
    @FXML
    public void initialize() {
        validator = new InputValidator();
        ArrayList<String> cartoonNames = new ArrayList<>();
        List<String> cartoons = new SimpleCartoonDataAccess().findAllCartoonNames();
        cartoons.forEach((cartoon) -> {
            cartoonNames.add(cartoon);
        });
        cartoonNameComboBox.getItems().add("");
        cartoonNameComboBox.getItems().addAll(cartoonNames);
        cartoonNameComboBox.getSelectionModel().selectFirst();
        cartoonNameComboBox.setOnAction((event) -> {
            String selectedCartoon = cartoonNameComboBox.getSelectionModel().getSelectedItem();
            SearchCartoonTask cartoonSearchTask = new SearchCartoonTask(selectedCartoon, cartoonDescriptionTextArea);
            Thread thread = new Thread(cartoonSearchTask);
            thread.start();
        });
    }

    /**
     * Handles the action for the cancel button by closing the update window.
     *
     * @param event the action event
     */
    @FXML
    void handleCancelAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action for the save button by validating the input before
     * calling the update task.
     *
     * @param event the action event
     */
    @FXML
    void handleSaveAction(ActionEvent event) {

        if (validator.inputValidForCartoonCreation(cartoonNameComboBox.getValue(), cartoonDescriptionTextArea.getText())) {
            UpdateCartoonTask task = new UpdateCartoonTask(cartoonNameComboBox.getValue(), cartoonDescriptionTextArea.getText());
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
