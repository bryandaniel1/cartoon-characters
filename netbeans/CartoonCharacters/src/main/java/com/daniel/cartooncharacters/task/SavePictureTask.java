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
package com.daniel.cartooncharacters.task;

import com.daniel.cartooncharacters.data.CartoonPictureDataAccess;
import com.daniel.cartooncharacters.data.CharacterPictureDataAccess;
import com.daniel.cartooncharacters.data.LocationPictureDataAccess;
import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.entity.CharacterPicture;
import com.daniel.cartooncharacters.entity.LocationPicture;
import com.daniel.cartooncharacters.util.FileUtil;
import com.daniel.cartooncharacters.util.MessageStage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * This class contains the logic for executing calls to the appropriate data
 * access object in a background thread to save a picture record.
 *
 * @author Bryan Daniel
 */
public class SavePictureTask extends Task<Void> {

    /**
     * The enum indicating picture types
     */
    public enum PictureType {
        CARTOON("Cartoon"),
        LOCATION("Location"),
        CHARACTER("Character");

        private final String type;

        private PictureType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    /**
     * The length of the buffer used for file copying
     */
    public static final int BUFFER_LENGTH = 8192;

    /**
     * The image file
     */
    private final File imageFile;

    /**
     * The type of picture to save
     */
    private final PictureType type;

    /**
     * The cartoon for the image, if the image is a cartoon type
     */
    private final Cartoon cartoon;

    /**
     * The location for the image, if the image is a location type
     */
    private final CartoonLocation cartoonLocation;

    /**
     * The character for the image, if the image is a character type
     */
    private final CartoonCharacter cartoonCharacter;

    /**
     * The indication of successful or failure
     */
    private boolean successful;

    /**
     * Sets values for the instance variables.
     *
     * @param imageFile the image file
     * @param type the type of image to save
     * @param cartoon the cartoon
     * @param cartoonLocation the location
     * @param cartoonCharacter the character
     */
    public SavePictureTask(File imageFile, PictureType type, Cartoon cartoon,
            CartoonLocation cartoonLocation, CartoonCharacter cartoonCharacter) {
        this.imageFile = imageFile;
        this.type = type;
        this.cartoon = cartoon;
        this.cartoonLocation = cartoonLocation;
        this.cartoonCharacter = cartoonCharacter;
    }

    @Override
    protected Void call() throws Exception {
        successful = false;
        File newImageFile = FileUtil.getNewImageFile(cartoon.getTitle(), imageFile.getName());
        if (!newImageFile.exists()) {
            FileUtil.createCartoonImageDirectory(cartoon.getTitle());
            try {
                FileUtil.copyImage(imageFile, newImageFile);
            } catch (IOException ex) {
                Logger.getLogger(SavePictureTask.class.getName()).log(Level.SEVERE,
                        MessageFormat.format("An exception occured while saving the image, {0}.",
                                imageFile.getName()), ex);
            }
        }
        String newImagePath = FileUtil.getNewImagePath(cartoon.getTitle(), imageFile.getName());
        switch (type) {
            case CARTOON:
                saveCartoonPicture(newImagePath);
                break;
            case LOCATION:
                saveLocationPicture(newImagePath);
                break;
            case CHARACTER:
                saveCharacterPicture(newImagePath);
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    protected void succeeded() {
        Label label;
        if (successful) {
            label = new Label("Picture saved successfully.");
        } else {
            label = new Label("Error! The picture could not be saved.");
        }
        MessageStage messageStage = new MessageStage(label);
        messageStage.show();
    }

    /**
     * Saves the new cartoon image to the specified file path.
     *
     * @param newImagePath the new image file path
     */
    private void saveCartoonPicture(String newImagePath) {
        CartoonPictureDataAccess cartoonPictureDataAccess = new CartoonPictureDataAccess();
        CartoonPicture existingCartoonPicture = cartoonPictureDataAccess.findCartoonPicture(newImagePath,
                cartoon);
        if (existingCartoonPicture == null) {
            CartoonPicture cartoonPicture = new CartoonPicture();
            cartoonPicture.setPictureLocation(newImagePath);
            cartoonPicture.setCartoon(cartoon);
            if (cartoonPictureDataAccess.savePicture(cartoonPicture)) {
                successful = true;
            }
        }
    }

    /**
     * Saves the new cartoon location image to the specified file path.
     *
     * @param newImagePath the new image file path
     */
    private void saveLocationPicture(String newImagePath) {
        LocationPictureDataAccess locationPictureDataAccess = new LocationPictureDataAccess();
        LocationPicture existingLocationPicture = locationPictureDataAccess.findLocationPicture(newImagePath,
                cartoonLocation);
        if (existingLocationPicture == null) {
            LocationPicture locationPicture = new LocationPicture();
            locationPicture.setPictureLocation(newImagePath);
            locationPicture.setCartoonLocation(cartoonLocation);
            if (locationPictureDataAccess.savePicture(locationPicture)) {
                successful = true;
            }
        }
    }

    /**
     * Saves the new cartoon character image to the specified file path.
     *
     * @param newImagePath the new image file path
     */
    private void saveCharacterPicture(String newImagePath) {
        CharacterPictureDataAccess characterPictureDataAccess = new CharacterPictureDataAccess();
        CharacterPicture existingCharacterPicture = characterPictureDataAccess.findCharacterPicture(newImagePath,
                cartoonCharacter);
        if (existingCharacterPicture == null) {
            CharacterPicture characterPicture = new CharacterPicture();
            characterPicture.setPictureLocation(newImagePath);
            characterPicture.setCartoonCharacter(cartoonCharacter);
            if (characterPictureDataAccess.savePicture(characterPicture)) {
                successful = true;
            }
        }
    }
}
