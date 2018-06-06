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
package com.daniel.cartooncharacters.validation;

import com.daniel.cartooncharacters.entity.CartoonCharacter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * This class validates input for controller functions.
 *
 * @author Bryan Daniel
 */
public class InputValidator {

    /**
     * This validation method tests that at least one input contains a value for
     * searching. If validation fails, an alert dialog is shown.
     *
     * @param characterName the cartoon character name
     * @param cartoonTitle the cartoon title
     * @return true if a non-empty value is given
     */
    public boolean inputValidForSearch(String characterName, String cartoonTitle) {
        if (characterName.isEmpty() && cartoonTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("No values provided");
            alert.setContentText("A character name or cartoon title must be given.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * This validation method tests that at all inputs are provided for creation
     * of a cartoon. If validation fails, an alert dialog is shown.
     *
     * @param cartoonName the name of the cartoon
     * @param cartoonDescription the description of the cartoon
     * @return true if all input is valid
     */
    public boolean inputValidForCartoonCreation(String cartoonName, String cartoonDescription) {
        if (cartoonName.isEmpty() || cartoonDescription.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Missing");
            alert.setContentText("A cartoon name and cartoon description must be given.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * This validation method tests that at all necessary inputs are provided
     * for creation of a cartoon location. If validation fails, an alert dialog
     * is shown.
     *
     * @param cartoonName the name of the cartoon
     * @param locationName the name of the location
     * @param locationDescription the description of the location
     * @return true if all input is valid
     */
    public boolean inputValidForLocationCreation(String cartoonName, String locationName,
            String locationDescription) {
        if (cartoonName.isEmpty() || locationName.isEmpty() || locationDescription.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Missing");
            alert.setContentText("A cartoon must be selected and location name and description must be given.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * This validation method tests that at all necessary inputs are provided
     * for creation of a cartoon location. If validation fails, an alert dialog
     * is shown.
     *
     * @param cartoonName the name of the cartoon
     * @param locationName the name of the location
     * @param characterName the name of the character
     * @param characterDescription the description of the character
     * @return true if all input is valid
     */
    public boolean inputValidForCharacterCreation(String cartoonName, String locationName, String characterName,
            String characterDescription) {
        if (cartoonName.isEmpty() || locationName.isEmpty() || characterName.isEmpty()
                || characterDescription.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Missing");
            alert.setContentText("All inputs must be provided for the character.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * This method ensures that a search result is selected for statistics
     * display. If validation fails, an alert dialog is shown.
     *
     * @param characterList the character list to validate
     * @param characterTable the character table to validate
     * @return true if a valid result is selected
     */
    public boolean validateStatisticsSelection(SimpleListProperty characterList,
            TableView<CartoonCharacter> characterTable) {
        if (characterList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("No values provided");
            alert.setContentText("A cartoon character must be selected from the search results.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        } else if (characterTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("No values provided");
            alert.setContentText("A cartoon character must be selected from the search results.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * This method ensures that the given file read from the file system is a
     * valid image. The file format name is returned after validation.
     *
     * @param image the image to validate
     * @return the format for the image file or null if validation fails
     */
    public String imageFileValid(File image) {
        String formatName = null;
        ImageInputStream imageInputStream;
        try {
            imageInputStream = ImageIO.createImageInputStream(image);
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
            while (imageReaders.hasNext()) {
                ImageReader reader = (ImageReader) imageReaders.next();
                formatName = reader.getFormatName();
            }
            if (formatName == null) {
                throw new IllegalArgumentException("Invalid File Format.");
            }
        } catch (IOException | IllegalArgumentException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid File");
            alert.setHeaderText("File Format Not Recognized");
            alert.setContentText("An image file must be selected.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
        return formatName;
    }
}
