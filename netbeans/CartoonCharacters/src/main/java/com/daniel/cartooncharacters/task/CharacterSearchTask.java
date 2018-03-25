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

import com.daniel.cartooncharacters.util.ScreenChangeManager;
import java.util.List;
import com.daniel.cartooncharacters.data.SimpleCharacterDataAccess;
import com.daniel.cartooncharacters.entity.CartoonCharacter;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import com.daniel.cartooncharacters.data.CharacterDataAccess;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * This class contains the logic for executing calls to the data access object
 * in a background thread and updating the character list on the FX application
 * thread.
 *
 * @author Bryan Daniel
 *
 */
public class CharacterSearchTask extends Task<SimpleListProperty> {

    /**
     * The list of characters to show in the search results table
     */
    private final ObservableList<CartoonCharacter> characterList = FXCollections.observableArrayList();

    /**
     * The first name to search
     */
    private final String characterName;

    /**
     * The last name to search
     */
    private final String cartoonTitle;

    /**
     * The data access object to retrieve character information
     */
    private final CharacterDataAccess dataAccess;

    /**
     * The screen changing object
     */
    private final ScreenChangeManager screenChangeManager;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param characterName the character name
     * @param cartoonTitle the cartoon title
     * @param screenChangeManager the screen change manager
     */
    public CharacterSearchTask(String characterName, String cartoonTitle,
            ScreenChangeManager screenChangeManager) {
        this.characterName = characterName;
        this.cartoonTitle = cartoonTitle;
        this.screenChangeManager = screenChangeManager;
        dataAccess = new SimpleCharacterDataAccess();
    }

    /**
     * This method calls the data access object's character search method. This
     * task is not performed on the FX application thread. The update to the
     * character list is.
     *
     * @return the list of matching characters
     * @throws java.lang.Exception
     */
    @Override
    protected SimpleListProperty call() throws Exception {
        List<CartoonCharacter> matchingCharacters = dataAccess.findCartoonCharacters(characterName, cartoonTitle);
        matchingCharacters.forEach((character) -> {
            character.getViewButton().setOnAction((ActionEvent e) -> {
                showDetails(character, e);
            });
        });
        characterList.addAll(matchingCharacters);
        return new SimpleListProperty(characterList);
    }

    /**
     * This method uses the ScreenChangeManager object to launch the view to
     * show details of the given character.
     *
     * @param character the character to view
     */
    private void showDetails(CartoonCharacter character, ActionEvent event) {
        screenChangeManager.showCharacterDetails(character, event);
    }
}
