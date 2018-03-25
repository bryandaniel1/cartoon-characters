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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class holds details for a cartoon character gender.
 * 
 * @author Bryan Daniel
 */
@Entity
@Table(name = "gender")
public class Gender implements Serializable {

    private static final long serialVersionUID = 8574581741171689973L;

    /**
     * The gender for a cartoon character
     */
    private final StringProperty gender = new SimpleStringProperty();
    
    /**
     * The description of the gender value
     */
    private final StringProperty description = new SimpleStringProperty();

    /**
     * Get the value of gender
     *
     * @return the value of gender
     */
    @Id
    @Column(name = "gender")
    public String getGender() {
        return gender.get();
    }

    /**
     * Set the value of gender
     *
     * @param gender new value of gender
     */
    public void setGender(String gender) {
        this.gender.set(gender);
    }

    /**
     * Get the gender property
     *
     * @return the gender property
     */
    public StringProperty genderProperty() {
        return gender;
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
}
