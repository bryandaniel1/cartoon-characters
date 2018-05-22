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
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

/**
 * This class holds details for a cartoon.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "cartoon")
public class Cartoon implements Serializable {

    private static final long serialVersionUID = -6896832804299724185L;

    /**
     * The cartoon identifier
     */
    private final LongProperty cartoonId = new SimpleLongProperty();

    /**
     * The title of the cartoon
     */
    private final StringProperty title = new SimpleStringProperty();

    /**
     * The description of the cartoon
     */
    private final StringProperty description = new SimpleStringProperty();

    /**
     * Get the value of cartoonId
     *
     * @return the value of cartoonId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CartoonSequence")
    @SequenceGenerator(name = "CartoonSequence", sequenceName = "cartoon_cartoon_id_seq", allocationSize = 1) 
    @Column(name = "cartoon_id")
    public Long getCartoonId() {
        return cartoonId.get();
    }

    /**
     * Set the value of cartoonId
     *
     * @param cartoonId new value of cartoonId
     */
    public void setCartoonId(Long cartoonId) {
        this.cartoonId.set(cartoonId);
    }

    /**
     * Get the cartoonId property
     *
     * @return the cartoonId property
     */
    public LongProperty cartoonIdProperty() {
        return cartoonId;
    }

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    @NaturalId
    @Column(name = "title")
    public String getTitle() {
        return title.get();
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Get the title property
     *
     * @return the title property
     */
    public StringProperty titleProperty() {
        return title;
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
