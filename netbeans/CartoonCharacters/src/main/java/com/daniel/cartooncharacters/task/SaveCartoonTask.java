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
import com.daniel.cartooncharacters.util.MessageStage;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * This class contains the logic for executing calls to the data access object
 * in a background thread to search and save cartoon data.
 *
 * @author Bryan Daniel
 */
public class SaveCartoonTask extends Task<Void> {

    /**
     * The cartoon name
     */
    private final String cartoonName;

    /**
     * The cartoon description
     */
    private final String cartoonDescription;

    /**
     * The data access object to retrieve and persist cartoon information
     */
    private final CartoonDataAccess dataAccess;

    /**
     * The indicator of success or failure
     */
    private boolean successful;

    /**
     * This constructor sets the value for the instance variables.
     *
     * @param cartoonName the cartoon name
     * @param cartoonDescription the cartoon description
     */
    public SaveCartoonTask(String cartoonName, String cartoonDescription) {
        this.cartoonName = cartoonName;
        this.cartoonDescription = cartoonDescription;
        dataAccess = new SimpleCartoonDataAccess();
    }

    @Override
    protected Void call() throws Exception {
        Cartoon cartoon = dataAccess.findCartoon(cartoonName);
        if (cartoon != null) {
            successful = false;
        } else {
            cartoon = new Cartoon();
            cartoon.setTitle(cartoonName);
            cartoon.setDescription(cartoonDescription);
            successful = dataAccess.addCartoon(cartoon);
        }
        return null;
    }

    @Override
    protected void succeeded() {
        Label label;
        if (successful) {
            label = new Label("Cartoon saved successfully.");
        } else {
            label = new Label("The cartoon already exists.");
        }
        MessageStage messageStage = new MessageStage(label);
        messageStage.show();
    }
}
