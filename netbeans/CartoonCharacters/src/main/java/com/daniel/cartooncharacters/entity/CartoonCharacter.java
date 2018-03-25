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
package com.daniel.cartooncharacters.entity;

import java.io.Serializable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This class holds details for a cartoon character.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "cartoon_character")
public class CartoonCharacter implements Serializable {

    private static final long serialVersionUID = 6055485302322018808L;

    /**
     * The cartoon character identifier
     */
    private final LongProperty characterId = new SimpleLongProperty();

    /**
     * The character name
     */
    private final StringProperty characterName = new SimpleStringProperty();

    /**
     * The character description
     */
    private final StringProperty description = new SimpleStringProperty();

    /**
     * The character's home in the cartoon
     */
    private final ObjectProperty<CartoonLocation> characterHome = new SimpleObjectProperty<>();

    /**
     * The button to view character details
     */
    private final ObjectProperty<Button> viewButton = new SimpleObjectProperty<>();

    /**
     * The default constructor creates a button to view character details.
     */
    public CartoonCharacter() {
        setViewButton(new Button("Details"));
    }

    /**
     * Get the value of characterId
     *
     * @return the value of characterId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CharacterSequence")
    @SequenceGenerator(name = "CharacterSequence", sequenceName = "cartoon_character_character_id_seq", allocationSize = 1)
    @Column(name = "character_id")
    public Long getCharacterId() {
        return characterId.get();
    }

    /**
     * Set the value of characterId
     *
     * @param characterId new value of characterId
     */
    public void setCharacterId(Long characterId) {
        this.characterId.set(characterId);
    }

    /**
     * Get the characterId property
     *
     * @return the characterId property
     */
    public LongProperty characterIdProperty() {
        return characterId;
    }

    /**
     * Get the value of characterName
     *
     * @return the value of characterName
     */
    @Column(name = "character_name")
    public String getCharacterName() {
        return characterName.get();
    }

    /**
     * Set the value of characterName
     *
     * @param characterName new value of characterName
     */
    public void setCharacterName(String characterName) {
        this.characterName.set(characterName);
    }

    /**
     * Get the characterName property
     *
     * @return the characterName property
     */
    public StringProperty characterNameProperty() {
        return characterName;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    @Column(name = "description")
    public String getDescription() {
        return description.get();
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Get the description property
     *
     * @return the description property
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Get the value of characterHome
     *
     * @return the value of characterHome
     */
    @ManyToOne
    @JoinColumn(name = "character_home")
    public CartoonLocation getCharacterHome() {
        return characterHome.get();
    }

    /**
     * Set the value of characterHome
     *
     * @param characterHome new value of characterHome
     */
    public void setCharacterHome(CartoonLocation characterHome) {
        this.characterHome.set(characterHome);
    }

    /**
     * Get the characterHome property
     *
     * @return the characterHome property
     */
    public ObjectProperty<CartoonLocation> characterHomeProperty() {
        return characterHome;
    }

    /**
     * Get the value of viewButton
     *
     * @return the value of viewButton
     */
    @Transient
    public Button getViewButton() {
        return viewButton.get();
    }

    /**
     * Set the value of viewButton
     *
     * @param viewButton new value of viewButton
     */
    public void setViewButton(Button viewButton) {
        this.viewButton.set(viewButton);
    }

    /**
     * Get the viewButton property
     *
     * @return the viewButton property
     */
    public ObjectProperty<Button> viewButtonProperty() {
        return viewButton;
    }

    /**
     * This method returns the cartoon title for the character if available.
     *
     * @return the cartoon title
     */
    @Transient
    public String getCartoonTitle() {
        if (characterHome.get() != null && characterHome.get().getCartoon() != null) {
            return characterHome.get().getCartoon().getTitle();
        }
        return null;
    }
}
