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

import java.util.List;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CharacterDemographic;

/**
 * This interface provides the functionality for accessing the cartoon database
 * to find character data.
 *
 * @author Bryan Daniel
 */
public interface CharacterDataAccess {

    /**
     * This method searches for cartoon characters with values similar to the
     * given name and cartoon title.
     *
     * @param characterName the name of the cartoon character
     * @param cartoonTitle the title of the cartoon
     * @return the list of characters found with values matching the parameters
     */
    public List<CartoonCharacter> findCartoonCharacters(String characterName, String cartoonTitle);

    /**
     * This method searches for a cartoon with a name exactly matching the given
     * name.
     *
     * @param characterName the name of the character
     * @param cartoonLocation the character's location
     * @return the cartoon character found with a name matching the specified
     * name
     */
    public CartoonCharacter findCartoonCharacter(String characterName, CartoonLocation cartoonLocation);

    /**
     * This method search for and returns character names associated with the
     * specified location.
     *
     * @param cartoonLocation the cartoon location
     * @return the list of character names for the given location
     */
    public List<String> findCartoonCharacterNames(CartoonLocation cartoonLocation);

    /**
     * This method adds a new cartoon character to the database.
     *
     * @param cartoonCharacter the cartoon character to add
     * @param characterDemographic the character demographic to add
     * @return the cartoon found with a title matching the name
     */
    public boolean addCharacter(CartoonCharacter cartoonCharacter, CharacterDemographic characterDemographic);

    /**
     * This method updates an existing cartoon character in the database.
     *
     * @param cartoonCharacter the character to update
     * @param characterDemographic the character demographic to update
     * @return true if successful, false otherwise
     */
    public boolean updateCharacter(CartoonCharacter cartoonCharacter, CharacterDemographic characterDemographic);
}
