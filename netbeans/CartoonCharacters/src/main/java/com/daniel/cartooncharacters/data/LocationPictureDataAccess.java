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

import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.entity.LocationPicture;
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
 * locations.
 *
 * @author Bryan Daniel
 */
public class LocationPictureDataAccess {

    /**
     * This method searches for pictures associated with a cartoon location.
     *
     * @param entityIdentifier the identifier of the location
     * @return the list of pictures found with location identifiers matching the
     * parameter
     */
    public List<LocationPicture> findPictures(Long entityIdentifier) {

        ArrayList<LocationPicture> pictures = new ArrayList<>();
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            Criteria criteria = session.createCriteria(LocationPicture.class);
            criteria.add(Restrictions.eq("cartoonLocation.locationId", entityIdentifier));
            List list = criteria.list();
            list.forEach((o) -> {
                pictures.add((LocationPicture) o);
            });
        } catch (HibernateException he) {
            Logger.getLogger(LocationPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during LocationPictureDataAccess.findPictures.", he);
        } catch (Exception e) {
            Logger.getLogger(LocationPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during LocationPictureDataAccess.findPictures.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return pictures;
    }

    /**
     * Finds and returns the cartoon location picture with values matching the
     * given image location and cartoon location.
     *
     * @param newImagePath the image location
     * @param cartoonLocation the cartoon location
     * @return the picture found
     */
    public LocationPicture findLocationPicture(String newImagePath, CartoonLocation cartoonLocation) {
        LocationPicture locationPictureFound = null;
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            Criteria criteria = session.createCriteria(LocationPicture.class);
            criteria.add(Restrictions.eq("pictureLocation", newImagePath));
            criteria.add(Restrictions.eq("cartoonLocation", cartoonLocation));
            locationPictureFound = (LocationPicture) criteria.uniqueResult();
        } catch (HibernateException he) {
            Logger.getLogger(LocationPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during LocationPictureDataAccess.findLocationPicture.", he);
        } catch (Exception e) {
            Logger.getLogger(LocationPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during LocationPictureDataAccess.findLocationPicture.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return locationPictureFound;
    }

    /**
     * This method adds a new location picture record to the database.
     *
     * @param picture the location picture to add
     * @return true if successful, false otherwise
     */
    public boolean savePicture(LocationPicture picture) {
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            session.getTransaction().begin();
            session.persist((LocationPicture) picture);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            Logger.getLogger(CartoonPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during LocationPictureDataAccess.savePicture.", he);
        } catch (Exception e) {
            Logger.getLogger(CartoonPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during LocationPictureDataAccess.savePicture.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return false;
    }
}
