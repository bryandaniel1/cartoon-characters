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
package com.daniel.cartooncharacters.validation;

import com.daniel.cartooncharacters.entity.CartoonCharacter;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * This class validates input for character name, cartoon title, and result
 * selection.
 *
 * @author Bryan Daniel
 */
public class InputValidator {

    /**
     * This validation method tests that at least one input contains a value for
     * searching. If validation fails, an alert dialog is shown.
     *
     * @param characterName the cartoon character name
     * @param cartoonTitle the cartoon title
     * @return true if a non-empty value is given
     */
    public boolean inputGivenForSearch(String characterName, String cartoonTitle) {
        if (characterName.isEmpty() && cartoonTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("No values provided");
            alert.setContentText("A character name or cartoon title must be given.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * This method ensures that a search result is selected for statistics
     * display. If validation fails, an alert dialog is shown.
     *
     * @param characterList the character list to validate
     * @param characterTable the character table to validate
     * @return true if a valid result is selected
     */
    public boolean validateStatisticsSelection(SimpleListProperty characterList,
            TableView<CartoonCharacter> characterTable) {
        if (characterList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("No values provided");
            alert.setContentText("A cartoon character must be selected from the search results.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        } else if (characterTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(true);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("No values provided");
            alert.setContentText("A cartoon character must be selected from the search results.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
