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

import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.util.DatabaseUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * This PictureDataAccess implementation provides the functionality for
 * accessing the cartoon database to find pictures associated with cartoons.
 *
 * @author Bryan Daniel
 */
public class CartoonPictureDataAccess implements PictureDataAccess {

    /**
     * This method searches for pictures associated with a cartoon.
     *
     * @param entityIdentifier the identifier of the cartoon
     * @return the list of pictures found with cartoon identifiers matching the
     * parameter
     */
    @Override
    public List<CartoonPicture> findPictures(Long entityIdentifier) {

        ArrayList<CartoonPicture> pictures = new ArrayList<>();
        Session session = null;
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT picture ");
            queryString.append("FROM CartoonPicture picture ");
            queryString.append("WHERE picture.cartoon.cartoonId = :cartoonId ");

            session = DatabaseUtil.getNewSession();
            Query query = session.createQuery(queryString.toString());
            query.setLong("cartoonId", entityIdentifier);

            List list = query.list();
            list.forEach((o) -> {
                pictures.add((CartoonPicture) o);
            });

        } catch (HibernateException he) {
            Logger.getLogger(CartoonPictureDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during CartoonPictureDataAccess.findPictures.", he);
        } catch (Exception e) {
            Logger.getLogger(CartoonPictureDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during CartoonPictureDataAccess.findPictures.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return pictures;
    }
}
