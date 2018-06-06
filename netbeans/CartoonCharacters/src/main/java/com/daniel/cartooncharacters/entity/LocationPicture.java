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
 * This class holds details for a cartoon location picture.
 * 
 * @author Bryan Daniel
 */
@Entity
@Table(name = "location_picture")
public class LocationPicture implements Serializable {

    private static final long serialVersionUID = 8184948361937717842L;
    
    /**
     * The cartoon picture identifier
     */
    private final LongProperty pictureId = new SimpleLongProperty();
    
    /**
     * The location of the picture file for this cartoon
     */
    private final StringProperty pictureLocation = new SimpleStringProperty();
    
    /**
     * The cartoon location associated with this picture
     */
    private final ObjectProperty<CartoonLocation> cartoonLocation = new SimpleObjectProperty<>();
    
    /**
     * Get the value of pictureId
     *
     * @return the value of pictureId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocationPictureSequence")
    @SequenceGenerator(name = "LocationPictureSequence", sequenceName = "location_picture_picture_id_seq", allocationSize = 1)
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
}
