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
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.entity.CharacterPicture;
import com.daniel.cartooncharacters.entity.LocationPicture;
import com.daniel.cartooncharacters.util.FileUtil;
import com.daniel.cartooncharacters.util.MessageStage;
import java.io.File;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * Asynchronously deletes a picture file from the file system and removes all
 * records of the picture from the database.
 *
 * @author Bryan Daniel
 */
public class DeletePictureTask extends Task<Void> {

    /**
     * The image file
     */
    private final File imageFile;

    /**
     * The type of picture to delete
     */
    private final SavePictureTask.PictureType type;

    /**
     * The cartoon image to delete
     */
    private final CartoonPicture cartoonPicture;

    /**
     * The location image to delete
     */
    private final LocationPicture locationPicture;

    /**
     * The character image to delete
     */
    private final CharacterPicture characterPicture;

    /**
     * The indication of successful or failure
     */
    private boolean successful;

    /**
     * Sets values for the instance variables.
     *
     * @param imageFile the image file
     * @param type the type of image to save
     * @param cartoonPicture the cartoon picture
     * @param locationPicture the location picture
     * @param characterPicture the character picture
     */
    public DeletePictureTask(File imageFile, SavePictureTask.PictureType type, CartoonPicture cartoonPicture,
            LocationPicture locationPicture, CharacterPicture characterPicture) {
        this.imageFile = imageFile;
        this.type = type;
        this.cartoonPicture = cartoonPicture;
        this.locationPicture = locationPicture;
        this.characterPicture = characterPicture;
    }

    @Override
    protected Void call() throws Exception {
        successful = false;
        if (FileUtil.deleteImage(imageFile)) {
            switch (type) {
                case CARTOON:
                    deleteCartoonPicture(cartoonPicture.getPictureLocation());
                    deleteLocationPicture(cartoonPicture.getPictureLocation());
                    deleteCharacterPicture(cartoonPicture.getPictureLocation());
                    break;
                case LOCATION:
                    deleteCartoonPicture(locationPicture.getPictureLocation());
                    deleteLocationPicture(locationPicture.getPictureLocation());
                    deleteCharacterPicture(locationPicture.getPictureLocation());
                    break;
                case CHARACTER:
                    deleteCartoonPicture(characterPicture.getPictureLocation());
                    deleteLocationPicture(characterPicture.getPictureLocation());
                    deleteCharacterPicture(characterPicture.getPictureLocation());
                    break;
                default:
                    break;
            }
        }
        return null;
    }

    @Override
    protected void succeeded() {
        Label label;
        if (successful) {
            label = new Label("The picture was deleted successfully.");
        } else {
            label = new Label("An error occurred while deleting the picture.");
        }
        MessageStage messageStage = new MessageStage(label);
        messageStage.show();
    }

    /**
     * Deletes the cartoon picture associated with the specified picture
     * location.
     *
     * @param pictureLocation the path of the picture to delete
     */
    private void deleteCartoonPicture(String pictureLocation) {
        CartoonPictureDataAccess cartoonPictureDataAccess = new CartoonPictureDataAccess();
        successful = cartoonPictureDataAccess.deletePicture(pictureLocation);
    }

    /**
     * Deletes the location picture associated with the specified picture
     * location.
     *
     * @param pictureLocation the path of the picture to delete
     */
    private void deleteLocationPicture(String pictureLocation) {
        LocationPictureDataAccess locationPictureDataAccess = new LocationPictureDataAccess();
        successful = locationPictureDataAccess.deletePicture(pictureLocation);
    }

    /**
     * Deletes the character picture associated with the specified picture
     * location.
     *
     * @param pictureLocation the path of the picture to delete
     */
    private void deleteCharacterPicture(String pictureLocation) {
        CharacterPictureDataAccess characterPictureDataAccess = new CharacterPictureDataAccess();
        successful = characterPictureDataAccess.deletePicture(pictureLocation);
    }
}
