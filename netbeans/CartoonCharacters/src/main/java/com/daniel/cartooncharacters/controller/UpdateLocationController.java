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
import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.task.SelectCartoonTask;
import com.daniel.cartooncharacters.task.SearchLocationTask;
import com.daniel.cartooncharacters.task.UpdateLocationTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * This controller supports the view for updating an existing cartoon location
 * in the database.
 *
 * @author Bryan Daniel
 */
public class UpdateLocationController {

    /**
     * The combo box for the cartoon selection
     */
    @FXML
    private ComboBox<String> cartoonNameComboBox;

    /**
     * The combo box for the location selection
     */
    @FXML
    private ComboBox<String> locationNameComboBox;

    /**
     * The description of the location to update
     */
    @FXML
    private TextArea locationDescriptionTextArea;

    /**
     * The property for the cartoon
     */
    private ObjectProperty<Cartoon> cartoon;

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * Called after construction to instantiate the input validation object and
     * populating the cartoon selections of the combo box.
     */
    @FXML
    public void initialize() {
        validator = new InputValidator();
        cartoon = new SimpleObjectProperty();
        locationNameComboBox.getItems().clear();
        locationNameComboBox.setDisable(true);
        locationDescriptionTextArea.clear();
        locationDescriptionTextArea.setDisable(true);
        List<String> cartoonNames = new SimpleCartoonDataAccess().findAllCartoonNames();
        createListeners();
        cartoonNameComboBox.getItems().clear();
        cartoonNameComboBox.getItems().add("");
        cartoonNameComboBox.getItems().addAll(cartoonNames);
        cartoonNameComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Handles the action for the cancel button.
     *
     * @param event the action event
     */
    @FXML
    public void handleCancelAction(ActionEvent event) {
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
    public void handleSaveAction(ActionEvent event) {
        if (validator.inputValidForLocationCreation(cartoonNameComboBox.getSelectionModel().getSelectedItem(),
                locationNameComboBox.getSelectionModel().getSelectedItem(), locationDescriptionTextArea.getText())) {
            UpdateLocationTask task = new UpdateLocationTask(cartoonNameComboBox.getSelectionModel().getSelectedItem(),
                    locationNameComboBox.getSelectionModel().getSelectedItem(), locationDescriptionTextArea.getText());
            Thread thread = new Thread(task);
            thread.start();
            initialize();
        }
    }

    /**
     * Defines the listeners for the combo boxes. For each combo box selection,
     * the database is queried to return location information filtered by the
     * selection.
     */
    private void createListeners() {
        locationNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                SearchLocationTask task = new SearchLocationTask(newValue, locationDescriptionTextArea, cartoon.get());
                Thread thread = new Thread(task);
                thread.start();
                locationDescriptionTextArea.clear();
                locationDescriptionTextArea.setDisable(false);
            }
        });
        cartoonNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                SelectCartoonTask task = new SelectCartoonTask(newValue, locationNameComboBox);
                cartoon.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                locationNameComboBox.setDisable(false);
                locationDescriptionTextArea.clear();
                locationDescriptionTextArea.setDisable(true);
            }
        });
    }
}
