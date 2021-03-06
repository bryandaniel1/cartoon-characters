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
import com.daniel.cartooncharacters.entity.LocationPicture;
import com.daniel.cartooncharacters.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
     * The logger for this class
     */
    private Logger logger;
    
    /**
     * Sets the value for the logger.
     */
    public LocationPictureDataAccess(){
        logger = LogManager.getLogger(LocationPictureDataAccess.class);
    }

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
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(LocationPicture.class);
            criteria.add(Restrictions.eq("cartoonLocation.locationId", entityIdentifier));
            List list = criteria.list();
            list.forEach((o) -> {
                pictures.add((LocationPicture) o);
            });
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during LocationPictureDataAccess.findPictures.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during LocationPictureDataAccess.findPictures.", e);
        } finally {
            SessionUtil.close(session);
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
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(LocationPicture.class);
            criteria.add(Restrictions.eq("pictureLocation", newImagePath));
            criteria.add(Restrictions.eq("cartoonLocation", cartoonLocation));
            locationPictureFound = (LocationPicture) criteria.uniqueResult();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during LocationPictureDataAccess.findLocationPicture.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during LocationPictureDataAccess.findLocationPicture.", e);
        } finally {
            SessionUtil.close(session);
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
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            session.persist((LocationPicture) picture);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during LocationPictureDataAccess.savePicture.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during LocationPictureDataAccess.savePicture.", e);
        } finally {
            SessionUtil.close(session);
        }
        return false;
    }

    /**
     * This method deletes all records of the specified location picture from
     * the location picture table.
     *
     * @param pictureLocation the path of the location picture
     * @return true if successful, false otherwise
     */
    public boolean deletePicture(String pictureLocation) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            Criteria criteria = session.createCriteria(LocationPicture.class);
            criteria.add(Restrictions.eq("pictureLocation", pictureLocation));
            List<LocationPicture> picturesToDelete = (List<LocationPicture>) criteria.list();
            for (LocationPicture pictureToDelete : picturesToDelete) {
                session.delete((LocationPicture) pictureToDelete);
            }
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during LocationPictureDataAccess.deletePicture.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during LocationPictureDataAccess.deletePicture.", e);
        } finally {
            SessionUtil.close(session);
        }
        return false;
    }
}
