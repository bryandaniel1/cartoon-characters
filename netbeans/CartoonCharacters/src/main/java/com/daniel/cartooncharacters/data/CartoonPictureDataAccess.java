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

import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonPicture;
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
 * accessing the cartoon database to find pictures associated with cartoons.
 *
 * @author Bryan Daniel
 */
public class CartoonPictureDataAccess {

    /**
     * The logger for this class
     */
    private Logger logger;
    
    /**
     * Sets the value for the logger.
     */
    public CartoonPictureDataAccess(){
        logger = LogManager.getLogger(CartoonPictureDataAccess.class);
    }

    /**
     * This method searches for pictures associated with a cartoon.
     *
     * @param entityIdentifier the identifier of the cartoon
     * @return the list of pictures found with cartoon identifiers matching the
     * parameter
     */
    public List<CartoonPicture> findPictures(Long entityIdentifier) {

        ArrayList<CartoonPicture> pictures = new ArrayList<>();
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(CartoonPicture.class);
            criteria.add(Restrictions.eq("cartoon.cartoonId", entityIdentifier));
            List list = criteria.list();
            list.forEach((o) -> {
                pictures.add((CartoonPicture) o);
            });

        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during CartoonPictureDataAccess.findPictures.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during CartoonPictureDataAccess.findPictures.", e);
        } finally {
            SessionUtil.close(session);
        }
        return pictures;
    }

    /**
     * Finds and returns the cartoon picture with values matching the given
     * location and cartoon.
     *
     * @param newImagePath the image location
     * @param cartoon the cartoon for the image
     * @return the picture found
     */
    public CartoonPicture findCartoonPicture(String newImagePath, Cartoon cartoon) {
        CartoonPicture cartoonPictureFound = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(CartoonPicture.class);
            criteria.add(Restrictions.eq("pictureLocation", newImagePath));
            criteria.add(Restrictions.eq("cartoon", cartoon));
            cartoonPictureFound = (CartoonPicture) criteria.uniqueResult();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during CartoonPictureDataAccess.findCartoonPicture.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during CartoonPictureDataAccess.findCartoonPicture.", e);
        } finally {
            SessionUtil.close(session);
        }
        return cartoonPictureFound;
    }

    /**
     * This method adds a new cartoon picture record to the database.
     *
     * @param picture the cartoon picture to add
     * @return true if successful, false otherwise
     */
    public boolean savePicture(CartoonPicture picture) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            session.persist((CartoonPicture) picture);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during CartoonPictureDataAccess.savePicture.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during CartoonPictureDataAccess.savePicture.", e);
        } finally {
            SessionUtil.close(session);
        }
        return false;
    }

    /**
     * This method deletes all records of the specified cartoon picture from the
     * cartoon picture table.
     *
     * @param pictureLocation the path of the cartoon picture
     * @return true if successful, false otherwise
     */
    public boolean deletePicture(String pictureLocation) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            Criteria criteria = session.createCriteria(CartoonPicture.class);
            criteria.add(Restrictions.eq("pictureLocation", pictureLocation));
            List<CartoonPicture> picturesToDelete = (List<CartoonPicture>) criteria.list();
            for (CartoonPicture pictureToDelete : picturesToDelete) {
                session.delete((CartoonPicture) pictureToDelete);
            }
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during CartoonPictureDataAccess.deletePicture.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during CartoonPictureDataAccess.deletePicture.", e);
        } finally {
            SessionUtil.close(session);
        }
        return false;
    }
}
