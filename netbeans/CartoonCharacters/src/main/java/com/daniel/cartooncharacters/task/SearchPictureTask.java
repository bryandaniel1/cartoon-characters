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
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * This class contains the logic for executing calls to a data access object in
 * a background thread and updating images on the FX application thread.
 *
 * @author Bryan Daniel
 */
public class SearchPictureTask extends Task<SimpleListProperty> {

    /**
     * The types of pictures to search for
     */
    public enum PictureType {
        CARTOON, LOCATION, CHARACTER
    }

    /**
     * The last name to search
     */
    private final Long entityIdentifier;

    /**
     * The type of picture
     */
    private final PictureType type;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param entityIdentifier the identifier of the entity
     * @param type the type of picture
     */
    public SearchPictureTask(Long entityIdentifier, PictureType type) {
        this.entityIdentifier = entityIdentifier;
        this.type = type;
    }

    /**
     * This method calls the data access object's picture search method. This
     * task is not performed on the FX application thread. The update to the
     * picture information in the UI is.
     *
     * @return the list of pictures found
     * @throws java.lang.Exception
     */
    @Override
    protected SimpleListProperty call() throws Exception {
        SimpleListProperty searchResults = null;
        switch (type) {
            case CARTOON:
                List<CartoonPicture> matchingPictures = new CartoonPictureDataAccess().findPictures(entityIdentifier);
                ObservableList<CartoonPicture> pictureList = FXCollections.observableArrayList();
                pictureList.addAll(matchingPictures);
                searchResults = new SimpleListProperty(pictureList);
                break;
            case LOCATION:
                List<LocationPicture> locationPictures = new LocationPictureDataAccess().findPictures(entityIdentifier);
                ObservableList<LocationPicture> locationPictureList = FXCollections.observableArrayList();
                locationPictureList.addAll(locationPictures);
                searchResults = new SimpleListProperty(locationPictureList);
                break;
            case CHARACTER:
                List<CharacterPicture> characterPictures = new CharacterPictureDataAccess().findPictures(entityIdentifier);
                ObservableList<CharacterPicture> characterPictureList = FXCollections.observableArrayList();
                characterPictureList.addAll(characterPictures);
                searchResults = new SimpleListProperty(characterPictureList);
                break;
            default:
                break;
        }
        return searchResults;
    }
}
