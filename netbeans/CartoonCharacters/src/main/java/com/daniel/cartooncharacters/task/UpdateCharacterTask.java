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
import com.daniel.cartooncharacters.data.CharacterDemographicDataAccess;
import com.daniel.cartooncharacters.data.SimpleCharacterDataAccess;
import com.daniel.cartooncharacters.data.SimpleCharacterDemographicDataAccess;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.util.MessageStage;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * This class contains the logic for executing calls to a data access object in
 * a background thread to update cartoon character information.
 *
 * @author Bryan Daniel
 */
public class UpdateCharacterTask extends Task<Void> {

    /**
     * The cartoon character to add
     */
    private final CartoonCharacter character;

    /**
     * The demographic data for the character
     */
    private final CharacterDemographic characterDemographic;

    /**
     * The data access object to retrieve and update character information
     */
    private final CharacterDataAccess characterDataAccess;

    /**
     * The data access object to retrieve and update character demographic
     * information
     */
    private final CharacterDemographicDataAccess demographicDataAccess;

    /**
     * The indicator of success or failure
     */
    private boolean successful;
    
    /**
     * The message to display
     */
    private String updateMessage;

    /**
     * This constructor sets the value for the instance variables.
     *
     *
     * @param character the character to update
     * @param characterDemographic the demographic information to update
     */
    public UpdateCharacterTask(CartoonCharacter character, CharacterDemographic characterDemographic) {
        this.character = character;
        this.characterDemographic = characterDemographic;
        characterDataAccess = new SimpleCharacterDataAccess();
        demographicDataAccess = new SimpleCharacterDemographicDataAccess();
    }

    @Override
    protected Void call() throws Exception {
        CartoonCharacter cartoonToUpdate = characterDataAccess.findCartoonCharacter(character.getCharacterName());
        if (cartoonToUpdate != null) {
            cartoonToUpdate.setCharacterHome(character.getCharacterHome());
            cartoonToUpdate.setCharacterName(character.getCharacterName());
            cartoonToUpdate.setDescription(character.getDescription());
            CharacterDemographic demographicToUpdate = demographicDataAccess.getCharacterDemographic(character);
            if (demographicToUpdate != null) {
                demographicToUpdate.setGender(characterDemographic.getGender());
                demographicToUpdate.setVillain(characterDemographic.getVillain());
                successful = characterDataAccess.updateCharacter(cartoonToUpdate, demographicToUpdate);
                updateMessage = successful ? "Character updated successfully." : "Error! Character update failed.";
            } else {
                updateMessage = "The demographic data for this character does not exist.";
                successful = false;
            }
        } else {
            updateMessage = "The character does not exist.";
            successful = false;
        }
        return null;
    }

    @Override
    protected void succeeded() {
        Label label;
        if (successful) {
            label = new Label(updateMessage);
        } else {
            label = new Label(updateMessage);
        }
        MessageStage messageStage = new MessageStage(label);
        messageStage.show();
    }
}
