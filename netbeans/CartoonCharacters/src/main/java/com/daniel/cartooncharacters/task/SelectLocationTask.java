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

import com.daniel.cartooncharacters.data.SimpleCharacterDataAccess;
import com.daniel.cartooncharacters.data.SimpleLocationDataAccess;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

/**
 * Handles the selection of a cartoon location to populate a combo box for
 * character selection.
 *
 * @author Bryan Daniel
 */
public class SelectLocationTask extends Task<Void> {

    /**
     * The combo box for the character selection
     */
    private final ComboBox<String> characterNameComboBox;

    /**
     * The name of the location for the characters
     */
    private final String locationName;

    /**
     * The characters found for the location
     */
    private List<String> characterNames;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param locationName the name of the location
     * @param characterName the character name combo box
     */
    public SelectLocationTask(String locationName, ComboBox<String> characterName) {
        this.locationName = locationName;
        this.characterNameComboBox = characterName;
    }

    @Override
    protected Void call() throws Exception {
        CartoonLocation location = new SimpleLocationDataAccess().findCartoonLocation(locationName);
        characterNames = new SimpleCharacterDataAccess().findCartoonCharacterNames(location);
        return null;
    }

    @Override
    protected void succeeded() {
        characterNameComboBox.getItems().clear();
        characterNameComboBox.getItems().add("");
        characterNameComboBox.getItems().addAll(characterNames);
        characterNameComboBox.getSelectionModel().selectFirst();
    }
}
