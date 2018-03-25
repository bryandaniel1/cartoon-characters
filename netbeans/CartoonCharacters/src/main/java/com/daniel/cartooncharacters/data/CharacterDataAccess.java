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

/**
 * This interface provides the functionality for accessing the cartoon
 * database to find character data.
 *
 * @author Bryan Daniel
 */
public interface CharacterDataAccess {

    /**
     * This method searches for cartoon characters with values matching the
     * given name and cartoon title.
     *
     * @param characterName the name of the cartoon character
     * @param cartoonTitle the title of the cartoon
     * @return the list of characters found with values matching the parameters
     */
    public List<CartoonCharacter> findCartoonCharacters(String characterName, String cartoonTitle);
}
