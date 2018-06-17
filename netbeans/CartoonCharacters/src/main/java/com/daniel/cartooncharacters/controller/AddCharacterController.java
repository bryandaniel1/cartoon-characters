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
import com.daniel.cartooncharacters.data.SimpleGenderDataAccess;
import com.daniel.cartooncharacters.data.SimpleLocationDataAccess;
import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.entity.Gender;
import com.daniel.cartooncharacters.task.SaveCharacterTask;
import com.daniel.cartooncharacters.task.SelectCartoonTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This controller supports the view for creating a new cartoon character in the
 * database.
 *
 * @author Bryan Daniel
 */
public class AddCharacterController {

    /**
     * The combo box for the cartoon selection
     */
    @FXML
    private ComboBox<String> cartoonNameComboBox;

    /**
     * The combo box for the location name
     */
    @FXML
    private ComboBox<String> locationNameComboBox;

    /**
     * The field for character name
     */
    @FXML
    private TextField characterNameTextField;

    /**
     * The choice box for the genderChoiceBox
     */
    @FXML
    private ChoiceBox<String> genderChoiceBox;

    /**
     * The choice box for good or evil
     */
    @FXML
    private ChoiceBox<String> goodOrEvilChoiceBox;

    /**
     * The description of the character to add
     */
    @FXML
    private TextArea characterDescriptionTextArea;

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * The new cartoon character
     */
    private CartoonCharacter cartoonCharacter;

    /**
     * The property for the cartoon
     */
    private ObjectProperty<Cartoon> cartoon;

    /**
     * The property for the location
     */
    private ObjectProperty<CartoonLocation> cartoonLocation;

    /**
     * The new cartoon character demographic data
     */
    private CharacterDemographic characterDemographic;

    /**
     * The list of all genders
     */
    private List<Gender> genders;

    /**
     * Called after construction to instantiate the input validation object and
     * populate the selection controls.
     */
    @FXML
    public void initialize() {
        validator = new InputValidator();
        cartoonCharacter = new CartoonCharacter();
        cartoon = new SimpleObjectProperty();
        cartoonLocation = new SimpleObjectProperty();
        characterDemographic = new CharacterDemographic();
        characterDemographic.setCharacter(cartoonCharacter);
        locationNameComboBox.getItems().clear();
        locationNameComboBox.setDisable(true);
        characterNameTextField.clear();
        characterNameTextField.setDisable(true);
        genderChoiceBox.setDisable(true);
        goodOrEvilChoiceBox.setDisable(true);
        characterDescriptionTextArea.clear();
        characterDescriptionTextArea.setDisable(true);
        createListeners();
        List<String> cartoonNames = new SimpleCartoonDataAccess().findAllCartoonNames();
        cartoonNameComboBox.getItems().clear();
        cartoonNameComboBox.getItems().add("");
        cartoonNameComboBox.getItems().addAll(cartoonNames);
        cartoonNameComboBox.getSelectionModel().selectFirst();
        genders = new SimpleGenderDataAccess().findAllGenders();
        genderChoiceBox.getItems().clear();
        genders.forEach((g) -> {
            genderChoiceBox.getItems().add(g.getDescription());
        });
        genderChoiceBox.getSelectionModel().selectFirst();
        goodOrEvilChoiceBox.getItems().clear();
        goodOrEvilChoiceBox.getItems().add("good");
        goodOrEvilChoiceBox.getItems().add("evil");
        goodOrEvilChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     * Handles the action for the close button.
     *
     * @param event the action event
     */
    @FXML
    public void handleCloseAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action for the save button.
     *
     * @param event the action event
     */
    @FXML
    public void handleSaveAction(ActionEvent event) {

        if (validator.inputValidForCharacterCreation(cartoonNameComboBox.getSelectionModel().getSelectedItem(),
                locationNameComboBox.getSelectionModel().getSelectedItem(), characterNameTextField.getText(),
                characterDescriptionTextArea.getText())) {
            cartoonCharacter.setCharacterName(characterNameTextField.getText());
            cartoonCharacter.setDescription(characterDescriptionTextArea.getText());
            setGender(genderChoiceBox.getSelectionModel().getSelectedItem());
            setVillain(goodOrEvilChoiceBox.getSelectionModel().getSelectedItem());
            SaveCharacterTask task = new SaveCharacterTask(cartoonCharacter, cartoonLocation.get(), 
                    characterDemographic);
            Thread thread = new Thread(task);
            thread.start();
            initialize();
        }
    }

    /**
     * Defines the listeners for the combo boxes. For each combo box selection,
     * the database is queried to return character information filtered by the
     * selection.
     */
    private void createListeners() {
        locationNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                Thread thread = new Thread(new Task() {

                    @Override
                    protected Void call() throws Exception {
                        cartoonLocation.set(new SimpleLocationDataAccess().findCartoonLocation(newValue, cartoon.get()));
                        cartoonCharacter.setCharacterHome(cartoonLocation.get());
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        characterNameTextField.clear();
                        characterNameTextField.setDisable(false);
                        genderChoiceBox.setDisable(false);
                        goodOrEvilChoiceBox.setDisable(false);
                        characterDescriptionTextArea.clear();
                        characterDescriptionTextArea.setDisable(false);
                    }
                });
                thread.start();
            } else {
                characterNameTextField.clear();
                characterNameTextField.setDisable(true);
                genderChoiceBox.setDisable(true);
                goodOrEvilChoiceBox.setDisable(true);
                characterDescriptionTextArea.clear();
                characterDescriptionTextArea.setDisable(true);
            }
        });
        cartoonNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                SelectCartoonTask task = new SelectCartoonTask(newValue, locationNameComboBox);
                cartoon.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                locationNameComboBox.setDisable(false);
                characterNameTextField.clear();
                characterNameTextField.setDisable(true);
                genderChoiceBox.setDisable(true);
                goodOrEvilChoiceBox.setDisable(true);
                characterDescriptionTextArea.clear();
                characterDescriptionTextArea.setDisable(true);
            }
        });
    }

    /**
     * Sets the value for gender in the character demographic object.
     *
     * @param newValue the new value for gender
     */
    private void setGender(String newValue) {
        for (Gender g : genders) {
            if (g.getDescription().equals(newValue)) {
                characterDemographic.setGender(g);
                break;
            }
        }
    }

    /**
     * Sets the value for villain in the character demographic object.
     *
     * @param newValue the new value for villain
     */
    private void setVillain(String newValue) {
        if ("evil".equals(newValue)) {
            characterDemographic.setVillain(Boolean.TRUE);
        } else {
            characterDemographic.setVillain(Boolean.FALSE);
        }
    }
}
