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
 * This class holds details for a cartoon location.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "cartoon_location")
public class CartoonLocation implements Serializable {

    private static final long serialVersionUID = -531718114792384574L;

    /**
     * The cartoon location identifier
     */
    private final LongProperty locationId = new SimpleLongProperty();

    /**
     * The name of the cartoon location
     */
    private final StringProperty locationName = new SimpleStringProperty();

    /**
     * The description of the cartoon location
     */
    private final StringProperty description = new SimpleStringProperty();

    /**
     * The cartoon for this location
     */
    private final ObjectProperty<Cartoon> cartoon = new SimpleObjectProperty<>();

    /**
     * Get the value of locationId
     *
     * @return the value of locationId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocationSequence")
    @SequenceGenerator(name = "LocationSequence", sequenceName = "cartoon_location_location_id_seq", allocationSize = 1)
    @Column(name = "location_id")
    public Long getLocationId() {
        return locationId.get();
    }

    /**
     * Set the value of locationId
     *
     * @param locationId new value of locationId
     */
    public void setLocationId(Long locationId) {
        this.locationId.set(locationId);
    }

    /**
     * Get the locationId property
     *
     * @return the locationId property
     */
    public LongProperty locationIdProperty() {
        return locationId;
    }

    /**
     * Get the value of locationName
     *
     * @return the value of locationName
     */
    @Column(name = "location_name")
    public String getLocationName() {
        return locationName.get();
    }

    /**
     * Set the value of locationName
     *
     * @param locationName new value of locationName
     */
    public void setLocationName(String locationName) {
        this.locationName.set(locationName);
    }

    /**
     * Get the locationName property
     *
     * @return the locationName property
     */
    public StringProperty locationNameProperty() {
        return locationName;
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
}
