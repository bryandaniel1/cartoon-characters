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

import com.daniel.cartooncharacters.data.LocationDataAccess;
import com.daniel.cartooncharacters.data.SimpleLocationDataAccess;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

/**
 * This class contains the logic for executing calls to the data access object
 * in a background thread and to search for a cartoon location and return the
 * details of the location found.
 *
 * @author Bryan Daniel
 */
public class SearchLocationTask extends Task<Void> {

    /**
     * The name of the location to find
     */
    private final String locationName;

    /**
     * The text area to update
     */
    private final TextArea locationDescription;

    /**
     * The data access object to retrieve character information
     */
    private final LocationDataAccess dataAccess;

    /**
     * The indicator of success or failure
     */
    private boolean successful;

    /**
     * The cartoon location to find
     */
    private CartoonLocation cartoonLocation;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param locationName the location name
     * @param locationDescription the location description
     */
    public SearchLocationTask(String locationName, TextArea locationDescription) {
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        dataAccess = new SimpleLocationDataAccess();
    }

    @Override
    protected Void call() throws Exception {
        cartoonLocation = dataAccess.findCartoonLocation(locationName);
        successful = cartoonLocation != null;
        return null;
    }

    @Override
    protected void succeeded() {
        if (successful) {
            locationDescription.setText(cartoonLocation.getDescription());
        } else {
            locationDescription.setText("Cartoon search failed.");
        }
    }
}
