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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.task.PictureSearchTask;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This controller provides data for the character details view.
 *
 * @author Bryan Daniel
 */
public class CharacterController implements Initializable {

    /**
     * The text area for the character ID
     */
    @FXML
    private TextArea characterIdDetail;

    /**
     * The text area for the character name
     */
    @FXML
    private TextArea characterNameDetail;

    /**
     * The text area for the character description
     */
    @FXML
    private TextArea descriptionDetail;

    /**
     * The text area for the location of the character picture
     */
    @FXML
    private TextArea pictureLocationDetail;

    /**
     * The hyperlink for the character home details
     */
    @FXML
    private Hyperlink characterHomeDetail;

    /**
     * The button for returning to the search screen
     */
    @FXML
    private Button characterHide;

    /**
     * The image view for the cartoon character
     */
    @FXML
    private ImageView characterImage;

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
     *
     * @param url the URL
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        characterIdDetail.setText(character.getCharacterId().toString());
        characterNameDetail.setText(character.getCharacterName());
        descriptionDetail.setText(character.getDescription());

        PictureSearchTask task = new PictureSearchTask(character.getCharacterId(),
                PictureSearchTask.PictureType.CHARACTER);
        pictureList.bind(task.valueProperty());
        pictureList.addListener((observable, oldValue, newValue) -> {
            pictureLocationDetail.setText(((List<CartoonPicture>) newValue).get(0).getPictureLocation());
        });
        pictureLocationDetail.textProperty().addListener((observable, oldValue, newValue) -> {
            characterImage.setImage(new Image(HomeController.class.getResourceAsStream(newValue)));
        });
        characterHomeDetail.setText(character.getCharacterHome().getLocationName());
        characterHomeDetail.setOnAction((event) -> this.handleHomeSelect(event));
        characterHide.setOnAction((event) -> this.handleCharacterHide(event));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
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
}
