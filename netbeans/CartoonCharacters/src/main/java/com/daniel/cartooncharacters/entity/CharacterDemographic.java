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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class holds demographic data for a cartoon character.
 *
 * @author Bryan Daniel
 */
@Entity
@Table(name = "character_demographic")
public class CharacterDemographic implements Serializable {

    private static final long serialVersionUID = 1044049422544578414L;

    /**
     * The character demographic identifier
     */
    private final LongProperty demographicId = new SimpleLongProperty();

    /**
     * The gender for a cartoon character
     */
    private final ObjectProperty<Gender> gender = new SimpleObjectProperty();

    /**
     * The indication of a villainous character
     */
    private final BooleanProperty villain = new SimpleBooleanProperty();

    /**
     * The cartoon character for this demographic data
     */
    private final ObjectProperty<CartoonCharacter> character = new SimpleObjectProperty();

    /**
     * Get the value of demographicId
     *
     * @return the value of demographicId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DemographicSequence")
    @SequenceGenerator(name = "DemographicSequence", sequenceName = "character_demographic_demographic_id_seq", allocationSize = 1)
    @Column(name = "demographic_id")
    public Long getDemographicId() {
        return demographicId.get();
    }

    /**
     * Set the value of demographicId
     *
     * @param demographicId new value of pictureId
     */
    public void setDemographicId(Long demographicId) {
        this.demographicId.set(demographicId);
    }

    /**
     * Get the demographicId property
     *
     * @return the demographicId property
     */
    public LongProperty demographicIdProperty() {
        return demographicId;
    }

    /**
     * Get the value of gender
     *
     * @return the value of gender
     */
    @ManyToOne
    @JoinColumn(name = "gender")
    public Gender getGender() {
        return gender.get();
    }

    /**
     * Set the value of gender
     *
     * @param gender new value of gender
     */
    public void setGender(Gender gender) {
        this.gender.set(gender);
    }

    /**
     * Get the gender property
     *
     * @return the gender property
     */
    public ObjectProperty<Gender> genderProperty() {
        return gender;
    }

    /**
     * Get the value of villain
     *
     * @return the value of villain
     */
    @Column(name = "villain")
    public Boolean getVillain() {
        return villain.get();
    }

    /**
     * Set the value of villain
     *
     * @param villain new value of villain
     */
    public void setVillain(Boolean villain) {
        this.villain.set(villain);
    }

    /**
     * Get the villain property
     *
     * @return the villain property
     */
    public BooleanProperty villainProperty() {
        return villain;
    }

    /**
     * Get the value of character
     *
     * @return the value of character
     */
    @OneToOne
    @JoinColumn(name = "character_id")
    public CartoonCharacter getCharacter() {
        return character.get();
    }

    /**
     * Set the value of character
     *
     * @param character new value of character
     */
    public void setCharacter(CartoonCharacter character) {
        this.character.set(character);
    }

    /**
     * Get the character property
     *
     * @return the character property
     */
    public ObjectProperty<CartoonCharacter> characterProperty() {
        return character;
    }
}
