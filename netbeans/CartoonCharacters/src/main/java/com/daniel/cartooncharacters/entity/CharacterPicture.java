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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class holds details for a cartoon character picture.
 * 
 * @author Bryan Daniel
 */
@Entity
@Table(name = "character_picture")
public class CharacterPicture implements Serializable {

    private static final long serialVersionUID = -6996640209073489349L;
    
    /**
     * The cartoon picture identifier
     */
    private final LongProperty pictureId = new SimpleLongProperty();
    
    /**
     * The location of the picture file for this cartoon
     */
    private final StringProperty pictureLocation = new SimpleStringProperty();
    
    /**
     * The cartoon character associated with this picture
     */
    private final ObjectProperty<CartoonCharacter> cartoonCharacter = new SimpleObjectProperty<>();
    
    /**
     * Get the value of pictureId
     *
     * @return the value of pictureId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CharacterPictureSequence")
    @SequenceGenerator(name = "CharacterPictureSequence", sequenceName = "character_picture_picture_id_seq", allocationSize = 1)
    @Column(name = "picture_id")
    public Long getPictureId() {
        return pictureId.get();
    }

    /**
     * Set the value of pictureId
     *
     * @param pictureId new value of pictureId
     */
    public void setPictureId(Long pictureId) {
        this.pictureId.set(pictureId);
    }

    /**
     * Get the pictureId property
     *
     * @return the pictureId property
     */
    public LongProperty pictureIdProperty() {
        return pictureId;
    }

    /**
     * Get the value of pictureLocation
     *
     * @return the value of pictureLocation
     */
    @Column(name = "picture_location")
    public String getPictureLocation() {
        return pictureLocation.get();
    }

    /**
     * Set the value of pictureLocation
     *
     * @param pictureLocation new value of pictureLocation
     */
    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation.set(pictureLocation);
    }

    /**
     * Get the pictureLocation property
     *
     * @return the pictureLocation property
     */
    public StringProperty pictureLocationProperty() {
        return pictureLocation;
    }

    /**
     * Get the value of cartoonCharacter
     *
     * @return the value of cartoonCharacter
     */
    @ManyToOne
    @JoinColumn(name = "character_id")
    public CartoonCharacter getCartoonCharacter() {
        return cartoonCharacter.get();
    }

    /**
     * Set the value of cartoonCharacter
     *
     * @param cartoonCharacter new value of cartoonCharacter
     */
    public void setCartoonCharacter(CartoonCharacter cartoonCharacter) {
        this.cartoonCharacter.set(cartoonCharacter);
    }

    /**
     * Get the cartoonCharacter property
     *
     * @return the cartoonCharacter property
     */
    public ObjectProperty<CartoonCharacter> cartoonCharacterProperty() {
        return cartoonCharacter;
    }
}
