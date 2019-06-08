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
package com.daniel.cartooncharacters.util;

import com.daniel.cartooncharacters.controller.CartoonController;
import com.daniel.cartooncharacters.controller.CharacterController;
import com.daniel.cartooncharacters.controller.HomeController;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;

/**
 * This class controls the screen-change operations for search details views.
 *
 * @author Bryan Daniel
 */
public class ScreenChangeManager {

    /**
     * The view of the character details
     */
    private Region characterView;

    /**
     * The view of the cartoon location details
     */
    private Region locationView;

    /**
     * The view of the cartoon details
     */
    private Region cartoonView;

    /**
     * This method shows the view for character details.
     *
     * @param character the character to view
     * @param event the action event
     */
    public void showCharacterDetails(CartoonCharacter character, ActionEvent event) {

        if (characterView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CharacterView.fxml"));
                loader.setController(new CharacterController(character, this));
                characterView = loader.load();
                showRegion(characterView, event);
            } catch (IOException ex) {
                LogManager.getLogger(ScreenChangeManager.class).error(
                        "IOException occurred in showCharacterDetails method.", ex);
            }
        }
    }

    /**
     * This method hides the view for character details and removes it from the
     * root node.
     *
     * @param event the action event
     */
    public void hideCharacterDetails(ActionEvent event) {
        hideRegion(characterView, event);
        characterView = null;
    }

    /**
     * This shows the view for the cartoon location for the given character.
     *
     * @param character the character
     * @param event the action event
     */
    public void showLocationDetails(CartoonCharacter character, ActionEvent event) {

        if (locationView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomeView.fxml"));
                loader.setController(new HomeController(character.getCharacterHome(), this));
                locationView = loader.load();
                showRegion(locationView, event);
            } catch (IOException ex) {
                LogManager.getLogger(ScreenChangeManager.class).error(
                        "IOException occurred in showLocationDetails method.", ex);
            }
        }
    }

    /**
     * This method hides the view for cartoon location details and removes it
     * from the root node.
     *
     * @param event the action event
     */
    public void hideLocationDetails(ActionEvent event) {
        hideRegion(locationView, event);
        locationView = null;
    }

    /**
     * This method shows the view for the cartoon associated with the given
     * location.
     *
     * @param location the location
     * @param event the action event
     */
    public void showCartoonDetails(CartoonLocation location, ActionEvent event) {

        if (cartoonView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CartoonView.fxml"));
                loader.setController(new CartoonController(location.getCartoon(), this));
                cartoonView = loader.load();
                showRegion(cartoonView, event);
            } catch (IOException ex) {
                LogManager.getLogger(ScreenChangeManager.class).error(
                        "IOException occurred in showCartoonDetails method.", ex);
            }
        }
    }

    /**
     * This method hides the view for cartoon details and removes it from the
     * root node.
     *
     * @param event the action event
     */
    public void hideCartoonDetails(ActionEvent event) {
        hideRegion(cartoonView, event);
        cartoonView = null;
    }

    /**
     * This method defines the animation to show the desired view.
     *
     * @param regionToShow the region to view
     * @param event the action event
     */
    private void showRegion(Region regionToShow, ActionEvent event) {

        StackPane root = (StackPane) getParentNode(event);
        root.getChildren().add(regionToShow);
        double width = root.getWidth();
        KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(regionToShow.translateXProperty(), width));
        KeyFrame end = new KeyFrame(Duration.seconds(1),
                new KeyValue(regionToShow.translateXProperty(), 0));
        Timeline slide = new Timeline(start, end);
        slide.play();
    }

    /**
     * This method defines the animation to hide the given region from view and
     * remove it from the root node.
     *
     * @param regionToHide the region to hide
     * @param event the action event
     */
    private void hideRegion(Region regionToHide, ActionEvent event) {

        StackPane root = (StackPane) getParentNode(event);
        double width = root.getWidth();
        KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(regionToHide.translateXProperty(), 0));
        KeyFrame end = new KeyFrame(Duration.seconds(1),
                new KeyValue(regionToHide.translateXProperty(), width));
        Timeline slide = new Timeline(start, end);
        slide.setOnFinished(e -> root.getChildren().remove(regionToHide));
        slide.play();
    }

    /**
     * This method takes the given action event and finds the root node for the
     * scene.
     *
     * @param event the action event
     * @return the parent node
     */
    private Parent getParentNode(ActionEvent event) {
        return ((Node) event.getTarget()).getScene().getRoot();
    }
}
