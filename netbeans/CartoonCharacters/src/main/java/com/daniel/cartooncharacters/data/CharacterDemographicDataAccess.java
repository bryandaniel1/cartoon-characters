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
package com.daniel.cartooncharacters.data;

import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CharacterDemographic;

/**
 * Accesses the database to retrieve demographic information for a character.
 *
 * @author Bryan Daniel
 */
public interface CharacterDemographicDataAccess {

    /**
     * Retrieves the demographic information for the specified character.
     *
     * @param cartoonCharacter the character
     * @return the demographic information
     */
    public CharacterDemographic getCharacterDemographic(CartoonCharacter cartoonCharacter);
}
