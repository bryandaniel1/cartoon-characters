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
 * This class holds details for a cartoon picture.
 * 
 * @author Bryan Daniel
 */
@Entity
@Table(name = "cartoon_picture")
public class CartoonPicture implements Serializable {
    
    private static final long serialVersionUID = -212373910016273651L;
    
    /**
     * The cartoon picture identifier
     */
    private final LongProperty pictureId = new SimpleLongProperty();
    
    /**
     * The location of the picture file for this cartoon
     */
    private final StringProperty pictureLocation = new SimpleStringProperty();
    
    /**
     * The cartoon associated with this picture
     */
    private final ObjectProperty<Cartoon> cartoon = new SimpleObjectProperty<>();
    
    /**
     * The cartoon location associated with this picture
     */
    private final ObjectProperty<CartoonLocation> cartoonLocation = new SimpleObjectProperty<>();
    
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PictureSequence")
    @SequenceGenerator(name = "PictureSequence", sequenceName = "cartoon_picture_picture_id_seq", allocationSize = 1)
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
     * Get the value of cartoon
     *
     * @return the value of cartoon
     */
    @ManyToOne
    @JoinColumn(name="cartoon_id")
    public Cartoon getCartoon() {
        return cartoon.get();
    }

    /**
     * Set the value of cartoon
     *
     * @param cartoon new value of cartoon
     */
    public void setCartoon(Cartoon cartoon) {
        this.cartoon.set(cartoon);
    }

    /**
     * Get the cartoon property
     *
     * @return the cartoon property
     */
    public ObjectProperty<Cartoon> cartoonProperty() {
        return cartoon;
    }

    /**
     * Get the value of cartoonLocation
     *
     * @return the value of cartoonLocation
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    public CartoonLocation getCartoonLocation() {
        return cartoonLocation.get();
    }

    /**
     * Set the value of cartoonLocation
     *
     * @param cartoonLocation new value of cartoonLocation
     */
    public void setCartoonLocation(CartoonLocation cartoonLocation) {
        this.cartoonLocation.set(cartoonLocation);
    }

    /**
     * Get the cartoonLocation property
     *
     * @return the cartoonLocation property
     */
    public ObjectProperty<CartoonLocation> cartoonLocationProperty() {
        return cartoonLocation;
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
