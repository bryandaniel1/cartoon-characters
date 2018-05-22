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
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.task.SearchPictureTask;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This controller provides data for the detailed view of a cartoon location.
 *
 * @author Bryan Daniel
 */
public class HomeController {

    /**
     * The text area for the cartoon location ID
     */
    @FXML
    private TextArea locationIdTextArea;

    /**
     * The text area for the cartoon location name
     */
    @FXML
    private TextArea locationNameTextArea;

    /**
     * The text area for the cartoon location description
     */
    @FXML
    private TextArea descriptionTextArea;

    /**
     * The text area for the location of the cartoon location picture
     */
    @FXML
    private TextArea pictureLocationTextArea;

    /**
     * The hyperlink for the cartoon details
     */
    @FXML
    private Hyperlink cartoonHyperlink;

    /**
     * The button for returning to the character details screen
     */
    @FXML
    private Button locationHideButton;

    /**
     * The image view for the cartoon location
     */
    @FXML
    private ImageView locationImage;

    /**
     * The location to view
     */
    private final CartoonLocation location;

    /**
     * The screen changing object
     */
    private final ScreenChangeManager screenChangeManager;

    /**
     * The list of pictures to show in the location details view
     */
    private final SimpleListProperty pictureList = new SimpleListProperty();

    /**
     * This constructor sets the value for the cartoon location to view and the
     * value for the screen change manager.
     *
     * @param location the location
     * @param screenChangeManager the screen change manager
     */
    public HomeController(CartoonLocation location, ScreenChangeManager screenChangeManager) {
        this.location = location;
        this.screenChangeManager = screenChangeManager;
    }

    /**
     * This method sets the values for the cartoon location details view.
     */
    @FXML
    public void initialize() {
        locationIdTextArea.setText(location.getLocationId().toString());
        locationNameTextArea.setText(location.getLocationName());
        descriptionTextArea.setText(location.getDescription());

        SearchPictureTask task = new SearchPictureTask(location.getLocationId(),
                SearchPictureTask.PictureType.LOCATION);
        pictureList.bind(task.valueProperty());
        pictureList.addListener((observable, oldValue, newValue) -> {
            pictureLocationTextArea.setText(((List<CartoonPicture>) newValue).get(0).getPictureLocation());
        });
        pictureLocationTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            locationImage.setImage(new Image(HomeController.class.getResourceAsStream(newValue)));
        });
        cartoonHyperlink.setText(location.getCartoon().getTitle());
        cartoonHyperlink.setOnAction((event) -> this.handleCartoonSelect(event));
        locationHideButton.setOnAction((event) -> this.handleLocationHide(event));

        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * This method handles the click event for the cartoon Hyperlink.
     *
     * @param event the action event
     */
    private void handleCartoonSelect(ActionEvent event) {
        screenChangeManager.showCartoonDetails(location, event);
    }

    /**
     * This method handles the click event for the back button to return to the
     * character details view.
     *
     * @param event the action event
     */
    private void handleLocationHide(ActionEvent event) {
        screenChangeManager.hideLocationDetails(event);
    }
}
