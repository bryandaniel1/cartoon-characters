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
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.util.SessionUtil;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * This LocationDataAccess implementation provides the functionality for
 * accessing the cartoon database to find, store, and update information on
 * cartoon locations.
 *
 * @author Bryan Daniel
 */
public class SimpleLocationDataAccess implements LocationDataAccess {

    /**
     * The logger for this class
     */
    private Logger logger;

    /**
     * Sets the value for the logger.
     */
    public SimpleLocationDataAccess() {
        logger = LogManager.getLogger(SimpleLocationDataAccess.class);
    }

    @Override
    public List<String> findCartoonLocationNames(Cartoon cartoon) {
        List<String> cartoonLocations = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            cartoonLocations = session.createCriteria(CartoonLocation.class)
                    .add(Restrictions.eq("cartoon.cartoonId", cartoon.getCartoonId()))
                    .setProjection(Projections.property("locationName"))
                    .list();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleLocationDataAccess.findCartoonLocationNames.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleLocationDataAccess.findCartoonLocationNames.", e);
        } finally {
            SessionUtil.close(session);
        }
        return cartoonLocations;
    }

    @Override
    public CartoonLocation findCartoonLocation(String locationName, Cartoon cartoon) {
        CartoonLocation cartoonLocation = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(CartoonLocation.class);
            criteria.add(Restrictions.ilike("locationName", locationName))
                    .add(Restrictions.eq("cartoon", cartoon));
            cartoonLocation = (CartoonLocation) criteria.uniqueResult();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleLocationDataAccess.findCartoonLocation.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleLocationDataAccess.findCartoonLocation.", e);
        } finally {
            SessionUtil.close(session);
        }
        return cartoonLocation;
    }

    @Override
    public boolean addLocation(CartoonLocation cartoonLocation) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            session.persist(cartoonLocation);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleLocationDataAccess.addLocation.", he);
            return false;
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleLocationDataAccess.addLocation.", e);
            return false;
        } finally {
            SessionUtil.close(session);
        }
        return true;
    }

    @Override
    public boolean updateLocation(CartoonLocation cartoonLocation) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            session.merge(cartoonLocation);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleLocationDataAccess.updateLocation.", he);
            return false;
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleLocationDataAccess.updateLocation.", e);
            return false;
        } finally {
            SessionUtil.close(session);
        }
        return true;
    }
}
