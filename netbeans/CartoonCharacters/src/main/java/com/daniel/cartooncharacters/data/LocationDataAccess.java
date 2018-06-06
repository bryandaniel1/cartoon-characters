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
import com.daniel.cartooncharacters.entity.CartoonLocation;
import java.util.List;

/**
 * This interface provides the functionality for accessing the cartoon database
 * to find, store, and update information on cartoon locations.
 *
 * @author Bryan Daniel
 */
public interface LocationDataAccess {

    /**
     * This method retrieves names of all cartoon locations for the specified
     * cartoon.
     *
     * @param cartoon the cartoon for the list of locations
     * @return the list of all cartoon locations in the specified cartoon
     */
    public List<String> findCartoonLocationNames(Cartoon cartoon);

    /**
     * This method searches for a cartoon with a name matching the given name.
     *
     * @param locationName the name of the location
     * @param cartoon the cartoon for the location
     * @return the cartoon location found with a name matching the specified
     * name
     */
    public CartoonLocation findCartoonLocation(String locationName, Cartoon cartoon);

    /**
     * This method adds a new cartoon location to the database.
     *
     * @param cartoonLocation the cartoon location to add
     * @return the cartoon found with a title matching the name
     */
    public boolean addLocation(CartoonLocation cartoonLocation);

    /**
     * This method updates an existing cartoon location in the database.
     *
     * @param cartoonLocation the location to update
     * @return true if successful, false otherwise
     */
    public boolean updateLocation(CartoonLocation cartoonLocation);
}
