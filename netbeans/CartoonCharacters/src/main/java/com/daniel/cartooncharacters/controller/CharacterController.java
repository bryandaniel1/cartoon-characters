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

import com.daniel.cartooncharacters.util.ScreenChangeManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.task.SearchPictureTask;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This controller provides data for the character details view.
 *
 * @author Bryan Daniel
 */
public class CharacterController {

    /**
     * The percentage value for half opaque
     */
    public static final double HALF_OPAQUE = 0.5;

    /**
     * The text area for the character ID
     */
    @FXML
    private TextArea characterIdTextArea;

    /**
     * The text area for the character name
     */
    @FXML
    private TextArea characterNameTextArea;

    /**
     * The text area for the character description
     */
    @FXML
    private TextArea descriptionTextArea;

    /**
     * The text area for the location of the character picture
     */
    @FXML
    private TextArea pictureLocationTextArea;

    /**
     * The hyperlink for the character home details
     */
    @FXML
    private Hyperlink characterHomeHyperlink;

    /**
     * The button for returning to the search screen
     */
    @FXML
    private Button characterHideButton;

    /**
     * The image view for the cartoon character
     */
    @FXML
    private ImageView characterImage;

    /**
     * The right picture navigation arrow
     */
    @FXML
    private Button rightArrowButton;

    /**
     * The left picture navigation arrow
     */
    @FXML
    private Button leftArrowButton;

    /**
     * The character to view
     */
    private final CartoonCharacter character;

    /**
     * The screen changing object
     */
    private final ScreenChangeManager screenChangeManager;

    /**
     * The list of pictures to show in the character details view
     */
    private final SimpleListProperty pictureList = new SimpleListProperty();

    /**
     * The index of the picture shown
     */
    private int currentPictureIndex;

    /**
     * This constructor sets the value for the character to view and the value
     * for the screen change manager.
     *
     * @param character the character
     * @param screenChangeManager the screen change manager
     */
    public CharacterController(CartoonCharacter character, ScreenChangeManager screenChangeManager) {
        this.character = character;
        this.screenChangeManager = screenChangeManager;
    }

    /**
     * This method sets the values for the character details view.
     */
    @FXML
    public void initialize() {
        characterIdTextArea.setText(character.getCharacterId().toString());
        characterNameTextArea.setText(character.getCharacterName());
        descriptionTextArea.setText(character.getDescription());

        SearchPictureTask task = new SearchPictureTask(character.getCharacterId(),
                SearchPictureTask.PictureType.CHARACTER);
        pictureList.bind(task.valueProperty());
        pictureList.addListener((observable, oldValue, newValue) -> {
            currentPictureIndex = 0;
            pictureLocationTextArea.setText(((List<CartoonPicture>) newValue).get(currentPictureIndex)
                    .getPictureLocation());
            if (pictureList.size() > 1) {
                rightArrowButton.setDisable(false);
                rightArrowButton.setVisible(true);
            }
        });
        pictureLocationTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            characterImage.setImage(new Image(HomeController.class.getResourceAsStream(newValue)));
        });
        characterHomeHyperlink.setText(character.getCharacterHome().getLocationName());
        characterHomeHyperlink.setOnAction((event) -> this.handleHomeSelect(event));
        characterHideButton.setOnAction((event) -> this.handleCharacterHide(event));

        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * This method handles the click event for the character home hyperlink.
     *
     * @param event the action event
     */
    private void handleHomeSelect(ActionEvent event) {
        screenChangeManager.showLocationDetails(character, event);
    }

    /**
     * This method handles the click event for the back button to return to the
     * search screen.
     *
     * @param event the action event
     */
    private void handleCharacterHide(ActionEvent event) {
        screenChangeManager.hideCharacterDetails(event);
    }

    /**
     * Handles the mouse entered event for a button.
     *
     * @param event the mouse event to handle
     */
    @FXML
    void handleMouseEntered(MouseEvent event) {
        ((Button) event.getTarget()).setOpacity(HALF_OPAQUE);
    }

    /**
     * Handles the mouse exited event for a button.
     *
     * @param event the mouse event to handle
     */
    @FXML
    void handleMouseExited(MouseEvent event) {
        ((Button) event.getTarget()).setOpacity(0);
    }

    /**
     * Handles the mouse click event for the the left arrow button.
     *
     * @param event the mouse event to handle
     */
    @FXML
    void handleLeftArrowClick(ActionEvent event) {
        pictureLocationTextArea.setText(((CartoonPicture) pictureList.get(--currentPictureIndex))
                .getPictureLocation());
        rightArrowButton.setDisable(false);
        rightArrowButton.setVisible(true);
        if (currentPictureIndex == 0) {
            leftArrowButton.setDisable(true);
            leftArrowButton.setVisible(false);
        }
    }

    /**
     * Handles the mouse click event for the the right arrow button.
     *
     * @param event the mouse event to handle
     */
    @FXML
    void handleRightArrowClick(ActionEvent event) {
        pictureLocationTextArea.setText(((CartoonPicture) pictureList.get(++currentPictureIndex))
                .getPictureLocation());
        leftArrowButton.setDisable(false);
        leftArrowButton.setVisible(true);
        if (pictureList.size() <= (currentPictureIndex + 1)) {
            rightArrowButton.setDisable(true);
            rightArrowButton.setVisible(false);
        }
    }
}
