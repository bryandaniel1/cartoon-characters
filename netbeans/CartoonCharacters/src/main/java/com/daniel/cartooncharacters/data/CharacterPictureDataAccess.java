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
package com.daniel.cartooncharacters.data;

import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CharacterPicture;
import com.daniel.cartooncharacters.util.DatabaseUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * This PictureDataAccess implementation provides the functionality for
 * accessing the cartoon database to find pictures associated with cartoon
 * characters.
 *
 * @author Bryan Daniel
 */
public class CharacterPictureDataAccess {

    /**
     * This method searches for pictures associated with a cartoon character.
     *
     * @param entityIdentifier the identifier of the character
     * @return the list of pictures found with character identifiers matching
     * the parameter
     */
    public List<CharacterPicture> findPictures(Long entityIdentifier) {

        ArrayList<CharacterPicture> pictures = new ArrayList<>();
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            Criteria criteria = session.createCriteria(CharacterPicture.class);
            criteria.add(Restrictions.eq("cartoonCharacter.characterId", entityIdentifier));
            List list = criteria.list();
            list.forEach((o) -> {
                pictures.add((CharacterPicture) o);
            });

        } catch (HibernateException he) {
            Logger.getLogger(CharacterPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during CharacterPictureDataAccess.findPictures.", he);
        } catch (Exception e) {
            Logger.getLogger(CharacterPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during CharacterPictureDataAccess.findPictures.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return pictures;
    }

    /**
     * Finds and returns the cartoon character picture with values matching the
     * given image location and cartoon character.
     *
     * @param newImagePath the image location
     * @param cartoonCharacter the cartoon character
     * @return the picture found
     */
    public CharacterPicture findCharacterPicture(String newImagePath, CartoonCharacter cartoonCharacter) {
        CharacterPicture characterPictureFound = null;
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            Criteria criteria = session.createCriteria(CharacterPicture.class);
            criteria.add(Restrictions.eq("pictureLocation", newImagePath));
            criteria.add(Restrictions.eq("cartoonCharacter", cartoonCharacter));
            characterPictureFound = (CharacterPicture) criteria.uniqueResult();
        } catch (HibernateException he) {
            Logger.getLogger(CharacterPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during CharacterPictureDataAccess.findCharacterPicture.", he);
        } catch (Exception e) {
            Logger.getLogger(CharacterPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during CharacterPictureDataAccess.findCharacterPicture.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return characterPictureFound;
    }

    /**
     * This method adds a new character picture record to the database.
     *
     * @param picture the character picture to add
     * @return true if successful, false otherwise
     */
    public boolean savePicture(CharacterPicture picture) {
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            session.getTransaction().begin();
            session.persist((CharacterPicture) picture);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            Logger.getLogger(CartoonPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during CharacterPictureDataAccess.savePicture.", he);
        } catch (Exception e) {
            Logger.getLogger(CartoonPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during CharacterPictureDataAccess.savePicture.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return false;
    }
}
