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

import static com.daniel.cartooncharacters.controller.CharacterController.HALF_OPAQUE;
import com.daniel.cartooncharacters.util.ScreenChangeManager;
import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.task.SearchPictureTask;
import com.daniel.cartooncharacters.util.FileUtil;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This controller provides data for the detailed view of a cartoon.
 *
 * @author Bryan Daniel
 */
public class CartoonController {

    /**
     * The text area for the cartoon ID
     */
    @FXML
    private TextArea cartoonIdTextArea;

    /**
     * The text area for the cartoon title
     */
    @FXML
    private TextArea titleTextArea;

    /**
     * The text area for the cartoon description
     */
    @FXML
    private TextArea descriptionTextArea;

    /**
     * The text area for the cartoon picture location
     */
    @FXML
    private TextArea pictureLocationTextArea;

    /**
     * The button for returning to the location details screen
     */
    @FXML
    private Button cartoonHideButton;

    /**
     * The image view for the cartoon
     */
    @FXML
    private ImageView cartoonImage;

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
     * The cartoon to view
     */
    private final Cartoon cartoon;

    /**
     * The screen changing object
     */
    private final ScreenChangeManager screenChangeManager;

    /**
     * The list of pictures to show in the cartoon details view
     */
    private final SimpleListProperty pictureList = new SimpleListProperty();

    /**
     * The index of the picture shown
     */
    private int currentPictureIndex;

    /**
     * This constructor sets the value for the cartoon to view and the value for
     * the screen change manager.
     *
     * @param cartoon the cartoon
     * @param screenChangeManager the screen change manager
     */
    public CartoonController(Cartoon cartoon, ScreenChangeManager screenChangeManager) {
        this.cartoon = cartoon;
        this.screenChangeManager = screenChangeManager;
    }

    /**
     * This method sets the values for the cartoon details view.
     */
    @FXML
    public void initialize() {
        cartoonIdTextArea.setText(cartoon.getCartoonId().toString());
        titleTextArea.setText(cartoon.getTitle());
        descriptionTextArea.setText(cartoon.getDescription());

        SearchPictureTask task = new SearchPictureTask(cartoon.getCartoonId(),
                SearchPictureTask.PictureType.CARTOON);
        pictureList.bind(task.valueProperty());
        pictureList.addListener((observable, oldValue, newValue) -> {
            currentPictureIndex = 0;
            pictureLocationTextArea.setText(((List<CartoonPicture>) newValue).get(0).getPictureLocation());
            if (pictureList.size() > 1) {
                rightArrowButton.setDisable(false);
                rightArrowButton.setVisible(true);
            }
        });
        pictureLocationTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            cartoonImage.setImage(new Image(FileUtil.getImageFile(newValue).toURI().toString()));
        });
        cartoonHideButton.setOnAction((event) -> this.handleCartoonHide(event));

        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * This method handles the click event for the back button to return to the
     * location details view.
     *
     * @param event the action event
     */
    private void handleCartoonHide(ActionEvent event) {
        screenChangeManager.hideCartoonDetails(event);
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
