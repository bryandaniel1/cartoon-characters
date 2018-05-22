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

import com.daniel.cartooncharacters.data.CartoonDataAccess;
import com.daniel.cartooncharacters.data.SimpleCartoonDataAccess;
import com.daniel.cartooncharacters.entity.Cartoon;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

/**
 * Searches for a cartoon in the database and populates a text area with the
 * description of the cartoon found.
 *
 * @author Bryan Daniel
 *
 */
public class SearchCartoonTask extends Task<Void> {

    /**
     * The cartoon title to search
     */
    private final String cartoonTitle;

    /**
     * The text area to update
     */
    private final TextArea cartoonDescription;

    /**
     * The data access object to retrieve character information
     */
    private final CartoonDataAccess dataAccess;

    /**
     * The indicator of success or failure
     */
    private boolean successful;

    /**
     * The cartoon to find
     */
    private Cartoon cartoon;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param cartoonTitle the cartoon title
     * @param cartoonDescription the cartoon description
     */
    public SearchCartoonTask(String cartoonTitle, TextArea cartoonDescription) {
        this.cartoonTitle = cartoonTitle;
        this.cartoonDescription = cartoonDescription;
        dataAccess = new SimpleCartoonDataAccess();
    }

    /**
     * This method calls the data access object's cartoon search method. This
     * task is not performed on the FX application thread. The update to the
     * user interface occurs in the succeeded method.
     *
     * @throws java.lang.Exception
     */
    @Override
    protected Void call() throws Exception {
        cartoon = dataAccess.findCartoon(cartoonTitle);
        successful = cartoon != null;
        return null;
    }

    @Override
    protected void succeeded() {
        if (successful) {
            cartoonDescription.setText(cartoon.getDescription());
        } else {
            cartoonDescription.setText("Cartoon search failed.");
        }
    }
}
