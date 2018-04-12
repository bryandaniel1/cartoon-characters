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

import com.daniel.cartooncharacters.task.CharacterSearchTask;
import com.daniel.cartooncharacters.util.ScreenChangeManager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.task.StatisticsTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

/**
 * This controller handles events for character searches, resetting of form
 * fields, and displaying character details.
 *
 * @author Bryan Daniel
 */
public class SearchController implements Initializable {

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
    private TextField characterNameSearch;

    /**
     * The cartoon title search field
     */
    @FXML
    private TextField cartoonTitleSearch;

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
     *
     * @param url the URL
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        characterTable.setItems(characterList);
        characterTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("characterId"));
        characterNameColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        cartoonTitleColumn.setCellValueFactory(new PropertyValueFactory<>("cartoonTitle"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("viewButton"));
        validator = new InputValidator();
        screenChangeManager = new ScreenChangeManager();
        cartoonTitleSearch.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cartoonTitleSearch.requestFocus();
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
        String characterName = characterNameSearch.getText();
        String cartoonTitle = cartoonTitleSearch.getText();
        if (validator.inputGivenForSearch(characterName, cartoonTitle)) {
            CharacterSearchTask task = new CharacterSearchTask(characterName, cartoonTitle,
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
        characterNameSearch.clear();
        cartoonTitleSearch.clear();
    }

    /**
     * This method handles the selection of character gender statistics.
     *
     * @param event the action event
     */
    @FXML
    void handleGenderStatistics(ActionEvent event) {
        if (validator.validateStatisticsSelection(characterList, characterTable)) {
            StatisticsTask task = new StatisticsTask(characterTable.getSelectionModel().getSelectedItem()
                    .getCharacterHome().getCartoon(), StatisticsTask.StatisticsType.GENDER,
                    (Stage) characterTable.getScene().getWindow());
            Thread thread = new Thread(task);
            thread.setDaemon(true);
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
    void handleGoodVsEvilStatistics(ActionEvent event) {
        if (validator.validateStatisticsSelection(characterList, characterTable)) {
            StatisticsTask task = new StatisticsTask(characterTable.getSelectionModel().getSelectedItem()
                    .getCharacterHome().getCartoon(), StatisticsTask.StatisticsType.GOOD_VS_EVIL,
                    (Stage) characterTable.getScene().getWindow());
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
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
