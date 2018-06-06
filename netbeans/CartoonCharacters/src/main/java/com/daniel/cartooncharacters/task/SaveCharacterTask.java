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

import com.daniel.cartooncharacters.data.CharacterDataAccess;
import com.daniel.cartooncharacters.data.SimpleCharacterDataAccess;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.util.MessageStage;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * This class contains the logic for executing calls to a data access object in
 * a background thread to search and save cartoon character data.
 *
 * @author Bryan Daniel
 */
public class SaveCharacterTask extends Task<Void> {

    /**
     * The cartoon character to add
     */
    private final CartoonCharacter character;

    /**
     * The location for the character
     */
    private final CartoonLocation cartoonLocation;

    /**
     * The demographic data for the character
     */
    private final CharacterDemographic characterDemographic;

    /**
     * The data access object to retrieve and update character information
     */
    private final CharacterDataAccess dataAccess;

    /**
     * The indicator of success or failure
     */
    private boolean successful;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param character the character to add
     * @param cartoonLocation the location for the character
     * @param characterDemographic the demographic data for the character
     */
    public SaveCharacterTask(CartoonCharacter character, CartoonLocation cartoonLocation,
            CharacterDemographic characterDemographic) {
        this.character = character;
        this.cartoonLocation = cartoonLocation;
        this.characterDemographic = characterDemographic;
        dataAccess = new SimpleCharacterDataAccess();
    }

    @Override
    protected Void call() throws Exception {
        CartoonCharacter existingCharacter = dataAccess.findCartoonCharacter(character.getCharacterName(),
                cartoonLocation);
        if (existingCharacter != null) {
            successful = false;
        } else {
            successful = dataAccess.addCharacter(character, characterDemographic);
        }
        return null;
    }

    @Override
    protected void succeeded() {
        Label label;
        if (successful) {
            label = new Label("Character saved successfully.");
        } else {
            label = new Label("Error! The character could not be saved.");
        }
        MessageStage messageStage = new MessageStage(label);
        messageStage.show();
    }
}
