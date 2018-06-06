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

import com.daniel.cartooncharacters.data.SimpleCartoonDataAccess;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

/**
 * Executed asynchronously to find all cartoon names and populate a specified
 * combo box with the results.
 *
 * @author Bryan Daniel
 */
public class FindAllCartoonsTask extends Task<Void> {

    /**
     * The cartoon name combo box
     */
    private final ComboBox<String> cartoonNameComboBox;

    /**
     * The cartoon names found
     */
    private List<String> cartoonNames;

    /**
     * Sets the value for the combo box.
     *
     * @param cartoonNameComboBox the combo box to populate
     */
    public FindAllCartoonsTask(ComboBox<String> cartoonNameComboBox) {
        this.cartoonNameComboBox = cartoonNameComboBox;
    }

    @Override
    protected Void call() throws Exception {
        cartoonNames = new SimpleCartoonDataAccess().findAllCartoonNames();
        return null;
    }

    @Override
    protected void succeeded() {
        if (cartoonNames != null) {
            cartoonNameComboBox.getItems().clear();
            cartoonNameComboBox.getItems().add("");
            cartoonNameComboBox.getItems().addAll(cartoonNames);
            cartoonNameComboBox.getSelectionModel().selectFirst();
        }
    }
}
