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

import com.daniel.cartooncharacters.task.SearchCharacterTask;
import com.daniel.cartooncharacters.util.ScreenChangeManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.task.StatisticsTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This controller handles events for character searches, resetting of form
 * fields, and displaying character details.
 *
 * @author Bryan Daniel
 */
public class SearchController {

    /**
     * The table to display cartoon character search results
     */
    @FXML
    private TableView<CartoonCharacter> characterTable;

    /**
     * The ID column for the cartoon character
     */
    @FXML
    private TableColumn<CartoonCharacter, Long> idColumn;

    /**
     * The character name column
     */
    @FXML
    private TableColumn<CartoonCharacter, String> characterNameColumn;

    /**
     * The column containing the cartoon title
     */
    @FXML
    private TableColumn<CartoonCharacter, String> cartoonTitleColumn;

    /**
     * The column for selecting character details
     */
    @FXML
    private TableColumn<CartoonCharacter, Button> selectColumn;

    /**
     * The cartoon character name search field
     */
    @FXML
    private TextField characterNameTextField;

    /**
     * The cartoon title search field
     */
    @FXML
    private TextField cartoonTitleTextField;

    /**
     * The progress indicator for a search
     */
    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * The list of characters to show in the search results table
     */
    private final SimpleListProperty characterList = new SimpleListProperty();

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * The screen changing object
     */
    private ScreenChangeManager screenChangeManager;

    /**
     * This method sets initial values for the search view and objects required
     * by this controller.
     */
    @FXML
    public void initialize() {
        characterTable.setItems(characterList);
        characterTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("characterId"));
        characterNameColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        cartoonTitleColumn.setCellValueFactory(new PropertyValueFactory<>("cartoonTitle"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("viewButton"));
        validator = new InputValidator();
        screenChangeManager = new ScreenChangeManager();
        cartoonTitleTextField.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cartoonTitleTextField.requestFocus();
            }
        });
    }

    /**
     * This method validates input data and shows an Alert dialog if the data is
     * invalid. Otherwise, the data access object is used to find matching
     * characters.
     *
     * @param event the action event
     */
    @FXML
    public void handleSearchAction(ActionEvent event) {
        characterList.clear();
        String characterName = characterNameTextField.getText();
        String cartoonTitle = cartoonTitleTextField.getText();
        if (validator.inputValidForSearch(characterName, cartoonTitle)) {
            SearchCharacterTask task = new SearchCharacterTask(characterName, cartoonTitle,
                    screenChangeManager);
            characterList.bind(task.valueProperty());
            progressIndicator.visibleProperty().bind(task.runningProperty());
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    /**
     * This method clears the character table and the text fields.
     *
     * @param event the action event
     */
    @FXML
    public void handleResetAction(ActionEvent event) {
        characterList.clear();
        characterNameTextField.clear();
        cartoonTitleTextField.clear();
    }

    /**
     * This method handles the selection of character gender statistics.
     *
     * @param event the action event
     */
    @FXML
    public void handleGenderStatistics(ActionEvent event) {
        if (validator.validateStatisticsSelection(characterList, characterTable)) {
            StatisticsTask task = new StatisticsTask(characterTable.getSelectionModel().getSelectedItem()
                    .getCharacterHome().getCartoon(), StatisticsTask.StatisticsType.GENDER,
                    (Stage) characterTable.getScene().getWindow());
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    /**
     * This method handles the selection of statistics for good and evil
     * characters.
     *
     * @param event the action event
     */
    @FXML
    public void handleGoodVsEvilStatistics(ActionEvent event) {
        if (validator.validateStatisticsSelection(characterList, characterTable)) {
            StatisticsTask task = new StatisticsTask(characterTable.getSelectionModel().getSelectedItem()
                    .getCharacterHome().getCartoon(), StatisticsTask.StatisticsType.GOOD_VS_EVIL,
                    (Stage) characterTable.getScene().getWindow());
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    /**
     * Handles the action for the menu option to add a new cartoon to the
     * database.
     *
     * @param event the action event
     */
    @FXML
    public void handleAddCartoon(ActionEvent event) {
        Stage addCartoonStage = new Stage();
        addCartoonStage.initModality(Modality.WINDOW_MODAL);
        addCartoonStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/AddCartoonView.fxml"));
            addCartoonStage.setScene(new Scene(root));
            addCartoonStage.setTitle("Add Cartoon");
            addCartoonStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to update a cartoon in the
     * database.
     *
     * @param event the action event
     */
    @FXML
    public void handleUpdateCartoon(ActionEvent event) {
        Stage updateCartoonStage = new Stage();
        updateCartoonStage.initModality(Modality.WINDOW_MODAL);
        updateCartoonStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/UpdateCartoonView.fxml"));
            updateCartoonStage.setScene(new Scene(root));
            updateCartoonStage.setTitle("Update Cartoon");
            updateCartoonStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to add a new cartoon location to
     * the database.
     *
     * @param event the action event
     */
    @FXML
    public void handleAddLocation(ActionEvent event) {
        Stage addCartoonStage = new Stage();
        addCartoonStage.initModality(Modality.WINDOW_MODAL);
        addCartoonStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/AddLocationView.fxml"));
            addCartoonStage.setScene(new Scene(root));
            addCartoonStage.setTitle("Add Location");
            addCartoonStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to update a cartoon location in
     * the database.
     *
     * @param event the action event
     */
    @FXML
    public void handleUpdateLocation(ActionEvent event) {
        Stage updateLocationStage = new Stage();
        updateLocationStage.initModality(Modality.WINDOW_MODAL);
        updateLocationStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/UpdateLocationView.fxml"));
            updateLocationStage.setScene(new Scene(root));
            updateLocationStage.setTitle("Update Location");
            updateLocationStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to add a new cartoon character to
     * the database.
     *
     * @param event the action event
     */
    @FXML
    public void handleAddCharacter(ActionEvent event) {
        Stage addCartoonStage = new Stage();
        addCartoonStage.initModality(Modality.WINDOW_MODAL);
        addCartoonStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/AddCharacterView.fxml"));
            addCartoonStage.setScene(new Scene(root));
            addCartoonStage.setTitle("Add Character");
            addCartoonStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to update a cartoon character in
     * the database.
     *
     * @param event the action event
     */
    @FXML
    public void handleUpdateCharacter(ActionEvent event) {
        Stage updateLocationStage = new Stage();
        updateLocationStage.initModality(Modality.WINDOW_MODAL);
        updateLocationStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/UpdateCharacterView.fxml"));
            updateLocationStage.setScene(new Scene(root));
            updateLocationStage.setTitle("Update Character");
            updateLocationStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to add a new picture to the
     * cartoon database.
     *
     * @param event the action event
     */
    @FXML
    public void handleAddPicture(ActionEvent event) {
        Stage addPictureStage = new Stage();
        addPictureStage.initModality(Modality.WINDOW_MODAL);
        addPictureStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/AddPictureView.fxml"));
            addPictureStage.setScene(new Scene(root));
            addPictureStage.setTitle("Add Picture");
            addPictureStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the action for the menu option to delete a picture in the
     * database.
     *
     * @param event the action event
     */
    @FXML
    public void handleDeletePicture(ActionEvent event) {
        Stage deletePictureStage = new Stage();
        deletePictureStage.initModality(Modality.WINDOW_MODAL);
        deletePictureStage.initOwner(characterTable.getScene().getWindow());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/DeletePictureView.fxml"));
            deletePictureStage.setScene(new Scene(root));
            deletePictureStage.setTitle("Delete Picture");
            deletePictureStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method handles the close menu option by exiting the program.
     *
     * @param event the action event
     */
    @FXML
    public void handleCloseAction(ActionEvent event) {
        Platform.exit();
    }
}
