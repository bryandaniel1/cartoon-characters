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
import com.daniel.cartooncharacters.data.PictureDataAccess;
import com.daniel.cartooncharacters.entity.CartoonPicture;
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
public class PictureSearchTask extends Task<SimpleListProperty> {

    /**
     * The types of pictures to search for
     */
    public enum PictureType {
        CARTOON, LOCATION, CHARACTER
    }

    /**
     * The list of pictures to show in the search results table
     */
    private final ObservableList<CartoonPicture> pictureList = FXCollections.observableArrayList();

    /**
     * The last name to search
     */
    private final Long entityIdentifier;

    /**
     * The data access object to retrieve pictures
     */
    private PictureDataAccess dataAccess;

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
    public PictureSearchTask(Long entityIdentifier, PictureType type) {
        this.entityIdentifier = entityIdentifier;
        this.type = type;
        switch (type) {
            case CARTOON:
                dataAccess = new CartoonPictureDataAccess();
                break;
            case LOCATION:
                dataAccess = new LocationPictureDataAccess();
                break;
            case CHARACTER:
                dataAccess = new CharacterPictureDataAccess();
                break;
            default:
                break;
        }
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
        if (dataAccess != null) {
            List<CartoonPicture> matchingPictures = dataAccess.findPictures(entityIdentifier);
            pictureList.addAll(matchingPictures);
        }
        return new SimpleListProperty(pictureList);
    }
}
