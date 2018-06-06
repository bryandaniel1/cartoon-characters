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
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import javafx.concurrent.Task;

/**
 * Handles the selection of a cartoon character name to find and return the
 * character data.
 *
 * @author Bryan Daniel
 */
public class SelectCharacterTask extends Task<CartoonCharacter> {

    /**
     * The characters found for the location
     */
    private final String characterName;
    
    /**
     * The location for the character
     */
    private final CartoonLocation cartoonLocation;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param characterName the character name
     * @param cartoonLocation the location for the character
     */
    public SelectCharacterTask(String characterName, CartoonLocation cartoonLocation) {
        this.characterName = characterName;
        this.cartoonLocation = cartoonLocation;
    }

    @Override
    protected CartoonCharacter call() throws Exception {
        return new SimpleCharacterDataAccess().findCartoonCharacter(characterName, cartoonLocation);
    }
}
