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

import com.daniel.cartooncharacters.data.SimpleCartoonDataAccess;
import com.daniel.cartooncharacters.data.SimpleLocationDataAccess;
import com.daniel.cartooncharacters.entity.Cartoon;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

/**
 * Handles the selection of a cartoon to populate a location name combo box and
 * return the cartoon entity.
 *
 * @author Bryan Daniel
 */
public class SelectCartoonTask extends Task<Cartoon> {

    /**
     * The combo box for the location selection
     */
    private final ComboBox<String> locationNameComboBox;

    /**
     * The name of the cartoon for the locations
     */
    private final String cartoonName;

    /**
     * The locations found for the cartoon
     */
    private List<String> locations;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param cartoonName the name of the cartoon
     * @param locationName the location name combo box
     */
    public SelectCartoonTask(String cartoonName, ComboBox<String> locationName) {
        this.cartoonName = cartoonName;
        this.locationNameComboBox = locationName;
    }

    @Override
    protected Cartoon call() throws Exception {
        Cartoon cartoon = new SimpleCartoonDataAccess().findCartoon(cartoonName);
        locations = new SimpleLocationDataAccess().findCartoonLocationNames(cartoon);
        return cartoon;
    }

    @Override
    protected void succeeded() {
        locationNameComboBox.getItems().clear();
        locationNameComboBox.getItems().add("");
        locationNameComboBox.getItems().addAll(locations);
        locationNameComboBox.getSelectionModel().selectFirst();
    }
}
