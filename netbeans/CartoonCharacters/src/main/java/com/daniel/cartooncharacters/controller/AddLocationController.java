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
import com.daniel.cartooncharacters.task.SaveLocationTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This controller supports the view for adding a new cartoon location to the
 * database.
 *
 * @author Bryan Daniel
 */
public class AddLocationController {

    /**
     * The combo box for the cartoon selection
     */
    @FXML
    private ComboBox<String> cartoonNameComboBox;

    /**
     * The name of the cartoon location to add
     */
    @FXML
    private TextField locationNameTextField;

    /**
     * The description of the location to add
     */
    @FXML
    private TextArea locationDescriptionTextArea;

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * Called after construction to instantiate the input validation object and
     * populate the cartoon selections of the combo box.
     */
    @FXML
    public void initialize() {
        validator = new InputValidator();
        locationNameTextField.clear();
        locationNameTextField.setDisable(true);
        locationDescriptionTextArea.clear();
        locationDescriptionTextArea.setDisable(true);
        List<String> cartoonNames = new SimpleCartoonDataAccess().findAllCartoonNames();
        cartoonNameComboBox.getItems().clear();
        cartoonNameComboBox.getItems().add("");
        cartoonNameComboBox.getItems().addAll(cartoonNames);
        cartoonNameComboBox.getSelectionModel().selectFirst();
        cartoonNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                locationNameTextField.setDisable(false);
                locationDescriptionTextArea.setDisable(false);
            }
        });
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
        if (validator.inputValidForLocationCreation(cartoonNameComboBox.getSelectionModel().getSelectedItem(),
                locationNameTextField.getText(), locationDescriptionTextArea.getText())) {
            SaveLocationTask task = new SaveLocationTask(cartoonNameComboBox.getSelectionModel().getSelectedItem(),
                    locationNameTextField.getText(), locationDescriptionTextArea.getText());
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
