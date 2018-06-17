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

import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.task.FindAllCartoonsTask;
import com.daniel.cartooncharacters.task.SavePictureTask;
import com.daniel.cartooncharacters.task.SavePictureTask.PictureType;
import com.daniel.cartooncharacters.task.SelectCartoonTask;
import com.daniel.cartooncharacters.task.SelectCharacterTask;
import com.daniel.cartooncharacters.task.SelectLocationTask;
import com.daniel.cartooncharacters.validation.InputValidator;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;

/**
 * This controller supports the view for adding a new picture to the cartoon
 * database.
 *
 * @author Bryan Daniel
 */
public class AddPictureController {

    /**
     * The save button
     */
    @FXML
    private Button savePictureButton;

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
     * The combo box for the character selection
     */
    @FXML
    private ComboBox<String> characterNameComboBox;

    /**
     * The choice box for the picture type selection
     */
    @FXML
    private ChoiceBox<PictureType> pictureTypeChoiceBox;

    /**
     * The button for selecting a picture to add
     */
    @FXML
    private Button selectPictureButton;

    /**
     * The node displaying the picture to add
     */
    @FXML
    private ImageView imagePreview;

    /**
     * The label for the cartoon combo box
     */
    @FXML
    private Label chooseCartoonLabel;

    /**
     * The label for the location combo box
     */
    @FXML
    private Label chooseLocationLabel;

    /**
     * The label for the character combo box
     */
    @FXML
    private Label chooseCharacterLabel;

    /**
     * The property for the cartoon character
     */
    private ObjectProperty<CartoonCharacter> cartoonCharacter;

    /**
     * The property for the cartoon
     */
    private ObjectProperty<Cartoon> cartoon;

    /**
     * The property for the location
     */
    private ObjectProperty<CartoonLocation> cartoonLocation;

    /**
     * The image file
     */
    private File imageFile;

    /**
     * The format of the image file
     */
    private String formatName;

    /**
     * The image to save
     */
    private BufferedImage newImage;

    /**
     * The input validation object
     */
    private InputValidator validator;

    /**
     * Called after construction to instantiate the input validation object and
     * populate the cartoon selections of the combo box.
     */
    @FXML
    public void initialize() {
        validator = new InputValidator();
        cartoonCharacter = new SimpleObjectProperty();
        cartoon = new SimpleObjectProperty();
        cartoonLocation = new SimpleObjectProperty();
        savePictureButton.setDisable(true);
        cartoonNameComboBox.setVisible(false);
        chooseCartoonLabel.setVisible(false);
        locationNameComboBox.setVisible(false);
        chooseLocationLabel.setVisible(false);
        characterNameComboBox.setVisible(false);
        chooseCharacterLabel.setVisible(false);
        selectPictureButton.setVisible(false);
        pictureTypeChoiceBox.getItems().setAll(PictureType.values());
        createListeners();
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
     * Handles the action for the "Save Picture" button.
     *
     * @param event the action event
     */
    @FXML
    void handleSaveAction(ActionEvent event) {
        SavePictureTask task = new SavePictureTask(imageFile, pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().get(), 
                cartoon.get(), cartoonLocation.get(), cartoonCharacter.get());
        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * Defines the listeners for the combo and choice boxes. For each selection,
     * the database is queried to return information filtered by the selection.
     */
    private void createListeners() {
        pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            savePictureButton.setDisable(true);
            imageFile = null;
            formatName = null;
            newImage = null;
            imagePreview.setImage(null);
            cartoonNameComboBox.getItems().clear();
            locationNameComboBox.getItems().clear();
            characterNameComboBox.getItems().clear();
            switch (newValue) {
                case CARTOON:
                    new Thread(new FindAllCartoonsTask(cartoonNameComboBox)).start();
                    cartoonNameComboBox.setVisible(true);
                    chooseCartoonLabel.setVisible(true);
                    locationNameComboBox.setVisible(false);
                    chooseLocationLabel.setVisible(false);
                    characterNameComboBox.setVisible(false);
                    chooseCharacterLabel.setVisible(false);
                    selectPictureButton.setVisible(false);
                    break;
                case LOCATION:
                    new Thread(new FindAllCartoonsTask(cartoonNameComboBox)).start();
                    cartoonNameComboBox.setVisible(true);
                    chooseCartoonLabel.setVisible(true);
                    locationNameComboBox.setVisible(true);
                    chooseLocationLabel.setVisible(true);
                    locationNameComboBox.setDisable(true);
                    characterNameComboBox.setVisible(false);
                    chooseCharacterLabel.setVisible(false);
                    selectPictureButton.setVisible(false);
                    break;
                case CHARACTER:
                    new Thread(new FindAllCartoonsTask(cartoonNameComboBox)).start();
                    cartoonNameComboBox.setVisible(true);
                    chooseCartoonLabel.setVisible(true);
                    locationNameComboBox.setVisible(true);
                    chooseLocationLabel.setVisible(true);
                    locationNameComboBox.setDisable(true);
                    characterNameComboBox.setVisible(true);
                    chooseCharacterLabel.setVisible(true);
                    characterNameComboBox.setDisable(true);
                    selectPictureButton.setVisible(false);
                    break;
                default:
                    break;
            }
        });
        cartoonNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isBlank(newValue)) {
                SelectCartoonTask task = new SelectCartoonTask(newValue, locationNameComboBox);
                cartoon.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                imageFile = null;
                formatName = null;
                newImage = null;
                imagePreview.setImage(null);
                savePictureButton.setDisable(true);
                imagePreview.setImage(null);
                if (PictureType.CARTOON == pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().get()) {
                    selectPictureButton.setVisible(true);
                } else {
                    locationNameComboBox.setDisable(false);
                    characterNameComboBox.getItems().clear();
                    characterNameComboBox.setDisable(true);
                }
            }
        });
        locationNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isBlank(newValue)) {
                SelectLocationTask task = new SelectLocationTask(newValue, characterNameComboBox, cartoon.get());
                cartoonLocation.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                imageFile = null;
                formatName = null;
                newImage = null;
                imagePreview.setImage(null);
                savePictureButton.setDisable(true);
                imagePreview.setImage(null);
                if (PictureType.LOCATION == pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().get()) {
                    selectPictureButton.setVisible(true);
                } else {
                    characterNameComboBox.setDisable(false);
                }
            }
        });
        characterNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isBlank(newValue)) {
                SelectCharacterTask task = new SelectCharacterTask(newValue, cartoonLocation.get());
                cartoonCharacter.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                imageFile = null;
                formatName = null;
                newImage = null;
                imagePreview.setImage(null);
                selectPictureButton.setVisible(true);
            }
        });
        selectPictureButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            imageFile = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
            if (imageFile != null) {
                openImageFile(imageFile);
                savePictureButton.setDisable(false);
            }
        });
    }

    /**
     * Opens and displays the given image in the image view.
     *
     * @param imageFile the image to open
     * @return true if successful, false otherwise
     */
    private boolean openImageFile(File imageFile) {
        if ((formatName = validator.imageFileValid(imageFile)) != null) {
            try {
                newImage = ImageIO.read(imageFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(newImage, formatName, byteArrayOutputStream);
                InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                imagePreview.setImage(new Image(inputStream));
                return true;
            } catch (IOException ex) {
                Logger.getLogger(AddPictureController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
