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

import com.daniel.cartooncharacters.entity.Cartoon;
import java.util.List;

/**
 * This interface provides the functionality for accessing the cartoon database
 * to find, store, and update information on cartoons.
 *
 * @author Bryan Daniel
 */
public interface CartoonDataAccess {

    /**
     * This method retrieves names of all cartoons from the database.
     *
     * @return the list of all cartoon names in the database
     */
    public List<String> findAllCartoonNames();

    /**
     * This method searches for a cartoon with a title matching the given name.
     *
     * @param cartoonName the name of the cartoon
     * @return the cartoon found with a title matching the name
     */
    public Cartoon findCartoon(String cartoonName);

    /**
     * This method adds a new cartoon to the database.
     *
     * @param cartoon the cartoon to add
     * @return the cartoon found with a title matching the name
     */
    public boolean addCartoon(Cartoon cartoon);

    /**
     * This method updates an existing cartoon in the database.
     *
     * @param cartoon the cartoon to update
     * @return true if successful, false otherwise
     */
    public boolean updateCartoon(Cartoon cartoon);

}
