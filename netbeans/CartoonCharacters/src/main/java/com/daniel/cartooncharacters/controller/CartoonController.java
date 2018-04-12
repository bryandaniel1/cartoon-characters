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
import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.task.PictureSearchTask;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This controller provides data for the detailed view of a cartoon.
 *
 * @author Bryan Daniel
 */
public class CartoonController implements Initializable {

    /**
     * The text area for the cartoon ID
     */
    @FXML
    private TextArea cartoonIdDetail;

    /**
     * The text area for the cartoon title
     */
    @FXML
    private TextArea titleDetail;

    /**
     * The text area for the cartoon description
     */
    @FXML
    private TextArea descriptionDetail;

    /**
     * The text area for the cartoon picture location
     */
    @FXML
    private TextArea pictureLocationDetail;

    /**
     * The button for returning to the location details screen
     */
    @FXML
    private Button cartoonHide;

    /**
     * The image view for the cartoon
     */
    @FXML
    private ImageView cartoonImage;

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
     *
     * @param location the URL
     * @param resources the resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartoonIdDetail.setText(cartoon.getCartoonId().toString());
        titleDetail.setText(cartoon.getTitle());
        descriptionDetail.setText(cartoon.getDescription());

        PictureSearchTask task = new PictureSearchTask(cartoon.getCartoonId(),
                PictureSearchTask.PictureType.CARTOON);
        pictureList.bind(task.valueProperty());
        pictureList.addListener((observable, oldValue, newValue) -> {
            pictureLocationDetail.setText(((List<CartoonPicture>) newValue).get(0).getPictureLocation());
        });
        pictureLocationDetail.textProperty().addListener((observable, oldValue, newValue) -> {
            cartoonImage.setImage(new Image(HomeController.class.getResourceAsStream(newValue)));
        });
        cartoonHide.setOnAction((event) -> this.handleCartoonHide(event));

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
}
