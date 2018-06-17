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
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.entity.CharacterPicture;
import com.daniel.cartooncharacters.entity.LocationPicture;
import com.daniel.cartooncharacters.task.DeletePictureTask;
import com.daniel.cartooncharacters.task.FindAllCartoonsTask;
import com.daniel.cartooncharacters.task.SavePictureTask.PictureType;
import com.daniel.cartooncharacters.task.SearchPictureTask;
import com.daniel.cartooncharacters.task.SelectCartoonTask;
import com.daniel.cartooncharacters.task.SelectCharacterTask;
import com.daniel.cartooncharacters.task.SelectLocationTask;
import com.daniel.cartooncharacters.util.FileUtil;
import java.io.File;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
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
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 * This controller supports the view for deleting a picture from the file system
 * and its records from the database.
 *
 * @author Bryan Daniel
 */
public class DeletePictureController {

    /**
     * The choice box for the picture type selection
     */
    @FXML
    private ChoiceBox<PictureType> pictureTypeChoiceBox;

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
     * The label for the picture combo box
     */
    @FXML
    private Label choosePictureLabel;

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
     * The combo box for the picture selection
     */
    @FXML
    private ComboBox<String> selectPictureComboBox;

    /**
     * The delete button
     */
    @FXML
    private Button deletePictureButton;

    /**
     * The node displaying the picture to delete
     */
    @FXML
    private ImageView imagePreview;

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
     * The list of pictures to show in the character details view
     */
    private final SimpleListProperty pictureList = new SimpleListProperty();

    /**
     * The image file
     */
    private File imageFile;

    /**
     * Called after construction to instantiate the input validation object and
     * populate the cartoon selections of the combo box.
     */
    @FXML
    public void initialize() {
        cartoonCharacter = new SimpleObjectProperty();
        cartoon = new SimpleObjectProperty();
        cartoonLocation = new SimpleObjectProperty();
        deletePictureButton.setDisable(true);
        cartoonNameComboBox.setVisible(false);
        chooseCartoonLabel.setVisible(false);
        locationNameComboBox.setVisible(false);
        chooseLocationLabel.setVisible(false);
        characterNameComboBox.setVisible(false);
        chooseCharacterLabel.setVisible(false);
        selectPictureComboBox.setVisible(false);
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
     * Handles the action for the delete button.
     *
     * @param event the action event
     */
    @FXML
    void handleDeleteAction(ActionEvent event) {
        PictureType type = pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().get();
        switch (type) {
            case CARTOON:
                for (CartoonPicture picture : (List<CartoonPicture>) pictureList) {
                    if (picture.getPictureLocation().equals(selectPictureComboBox.getSelectionModel()
                            .selectedItemProperty().get())) {
                        DeletePictureTask task = new DeletePictureTask(imageFile, type,
                                picture, null, null);
                        Thread thread = new Thread(task);
                        thread.start();
                        break;
                    }
                }
                break;
            case LOCATION:
                for (LocationPicture picture : (List<LocationPicture>) pictureList) {
                    if (picture.getPictureLocation().equals(selectPictureComboBox.getSelectionModel()
                            .selectedItemProperty().get())) {
                        DeletePictureTask task = new DeletePictureTask(imageFile, type,
                                null, picture, null);
                        Thread thread = new Thread(task);
                        thread.start();
                        break;
                    }
                }
                break;
            case CHARACTER:
                for (CharacterPicture picture : (List<CharacterPicture>) pictureList) {
                    if (picture.getPictureLocation().equals(selectPictureComboBox.getSelectionModel()
                            .selectedItemProperty().get())) {
                        DeletePictureTask task = new DeletePictureTask(imageFile, type,
                                null, null, picture);
                        Thread thread = new Thread(task);
                        thread.start();
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Defines the listeners for the combo and choice boxes. For each selection,
     * the database is queried to return information filtered by the selection.
     */
    private void createListeners() {
        pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deletePictureButton.setDisable(true);
            imageFile = null;
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
                    choosePictureLabel.setVisible(false);
                    selectPictureComboBox.setVisible(false);
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
                    choosePictureLabel.setVisible(false);
                    selectPictureComboBox.setVisible(false);
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
                    choosePictureLabel.setVisible(false);
                    selectPictureComboBox.setVisible(false);
                    break;
                default:
                    break;
            }
        });
        cartoonNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isBlank(newValue)) {
                SelectCartoonTask task = new SelectCartoonTask(newValue, locationNameComboBox);
                cartoon.unbind();
                cartoon.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                imageFile = null;
                imagePreview.setImage(null);
                deletePictureButton.setDisable(true);
                imagePreview.setImage(null);
            }
        });
        cartoon.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (PictureType.CARTOON == pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().get()) {
                    SearchPictureTask searchPictureTask = new SearchPictureTask(cartoon.get().getCartoonId(),
                            SearchPictureTask.PictureType.CARTOON);
                    pictureList.unbind();
                    pictureList.clear();
                    pictureList.bind(searchPictureTask.valueProperty());
                    pictureList.addListener((searchObservable, searchOldValue, searchNewValue) -> {
                        if (searchNewValue != null) {
                            selectPictureComboBox.getItems().clear();
                            ((List<CartoonPicture>) pictureList).forEach((picture) -> {
                                selectPictureComboBox.getItems().add(picture.getPictureLocation());
                            });
                        }
                    });
                    Thread thread = new Thread(searchPictureTask);
                    thread.start();
                    choosePictureLabel.setVisible(true);
                    selectPictureComboBox.setVisible(true);
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
                cartoonLocation.unbind();
                cartoonLocation.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                imageFile = null;
                imagePreview.setImage(null);
                deletePictureButton.setDisable(true);
                imagePreview.setImage(null);
            }
        });
        cartoonLocation.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (PictureType.LOCATION == pictureTypeChoiceBox.getSelectionModel().selectedItemProperty().get()) {
                    SearchPictureTask searchPictureTask = new SearchPictureTask(cartoonLocation.get().getLocationId(),
                            SearchPictureTask.PictureType.LOCATION);
                    pictureList.unbind();
                    pictureList.clear();
                    pictureList.bind(searchPictureTask.valueProperty());
                    pictureList.addListener((searchObservable, searchOldValue, searchNewValue) -> {
                        if (searchNewValue != null) {
                            selectPictureComboBox.getItems().clear();
                            ((List<LocationPicture>) pictureList).forEach((picture) -> {
                                selectPictureComboBox.getItems().add(picture.getPictureLocation());
                            });
                        }
                    });
                    Thread thread = new Thread(searchPictureTask);
                    thread.start();
                    choosePictureLabel.setVisible(true);
                    selectPictureComboBox.setVisible(true);
                } else {
                    characterNameComboBox.setDisable(false);
                }
            }
        });
        characterNameComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isBlank(newValue)) {
                SelectCharacterTask task = new SelectCharacterTask(newValue, cartoonLocation.get());
                cartoonCharacter.unbind();
                cartoonCharacter.bind(task.valueProperty());
                Thread thread = new Thread(task);
                thread.start();
                imageFile = null;
                imagePreview.setImage(null);
                deletePictureButton.setDisable(true);
                choosePictureLabel.setVisible(true);
                selectPictureComboBox.setVisible(true);
            }
        });
        cartoonCharacter.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SearchPictureTask searchPictureTask = new SearchPictureTask(newValue.getCharacterId(),
                        SearchPictureTask.PictureType.CHARACTER);
                pictureList.unbind();
                pictureList.clear();
                pictureList.bind(searchPictureTask.valueProperty());
                pictureList.addListener((searchObservable, searchOldValue, searchNewValue) -> {
                    if (searchNewValue != null) {
                        selectPictureComboBox.getItems().clear();
                        ((List<CharacterPicture>) pictureList).forEach((picture) -> {
                            selectPictureComboBox.getItems().add(picture.getPictureLocation());
                        });
                    }
                });
                Thread thread = new Thread(searchPictureTask);
                thread.start();
            }
        });
        selectPictureComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isBlank(newValue)) {
                imageFile = FileUtil.getImageFile(newValue);
                imagePreview.setImage(new Image(imageFile.toURI().toString()));
                deletePictureButton.setDisable(false);
            }
        });
    }
}
