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

import com.daniel.cartooncharacters.data.CartoonDataAccess;
import com.daniel.cartooncharacters.data.LocationDataAccess;
import com.daniel.cartooncharacters.data.SimpleCartoonDataAccess;
import com.daniel.cartooncharacters.data.SimpleLocationDataAccess;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.util.MessageStage;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * This class contains the logic for executing calls to a data access object in
 * a background thread to update cartoon location information.
 *
 * @author Bryan Daniel
 */
public class UpdateLocationTask extends Task<Void> {

    /**
     * The name of the cartoon for the location
     */
    private final String cartoonName;

    /**
     * The location name
     */
    private final String locationName;

    /**
     * The location description
     */
    private final String locationDescription;

    /**
     * The data access object to retrieve and update cartoon information
     */
    private final CartoonDataAccess cartoonDataAccess;

    /**
     * The data access object to retrieve and update location information
     */
    private final LocationDataAccess locationDataAccess;

    /**
     * The indicator of success or failure
     */
    private boolean successful;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param cartoonName the name of the cartoon for the location
     * @param locationName the location name
     * @param locationDescription the location description
     */
    public UpdateLocationTask(String cartoonName, String locationName, String locationDescription) {
        this.cartoonName = cartoonName;
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        cartoonDataAccess = new SimpleCartoonDataAccess();
        locationDataAccess = new SimpleLocationDataAccess();
    }

    @Override
    protected Void call() throws Exception {
        CartoonLocation cartoonLocation = locationDataAccess.findCartoonLocation(locationName);
        if (cartoonLocation != null) {
            cartoonLocation.setCartoon(cartoonDataAccess.findCartoon(cartoonName));
            cartoonLocation.setLocationName(locationName);
            cartoonLocation.setDescription(locationDescription);
            successful = locationDataAccess.updateLocation(cartoonLocation);
        } else {
            successful = false;
        }
        return null;
    }

    @Override
    protected void succeeded() {
        Label label;
        if (successful) {
            label = new Label("Location updated successfully.");
        } else {
            label = new Label("The location does not exist.");
        }
        MessageStage messageStage = new MessageStage(label);
        messageStage.show();
    }
}
