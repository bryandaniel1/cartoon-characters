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
 * This CartoonDataAccess implementation provides the functionality for
 * accessing the cartoon database to find, store, and update information on
 * cartoons.
 *
 * @author Bryan Daniel
 */
public class SimpleCartoonDataAccess implements CartoonDataAccess {

    /**
     * The logger for this class
     */
    private Logger logger;

    /**
     * Sets the value for the logger.
     */
    public SimpleCartoonDataAccess() {
        logger = LogManager.getLogger(SimpleCartoonDataAccess.class);
    }

    @Override
    public List<String> findAllCartoonNames() {

        List<String> cartoons = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            cartoons = session.createCriteria(Cartoon.class)
                    .setProjection(Projections.property("title")).list();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleCartoonDataAccess.findAllCartoons.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleCartoonDataAccess.findAllCartoons.", e);
        } finally {
            SessionUtil.close(session);
        }
        return cartoons;
    }

    @Override
    public Cartoon findCartoon(String cartoonName) {

        Cartoon cartoon = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(Cartoon.class);
            criteria.add(Restrictions.eq("title", cartoonName));
            cartoon = (Cartoon) criteria.uniqueResult();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleCartoonDataAccess.findCartoon.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleCartoonDataAccess.findCartoon.", e);
        } finally {
            SessionUtil.close(session);
        }
        return cartoon;
    }

    @Override
    public boolean addCartoon(Cartoon cartoon) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            session.persist(cartoon);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleCartoonDataAccess.addCartoon.", he);
            return false;
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleCartoonDataAccess.addCartoon.", e);
            return false;
        } finally {
            SessionUtil.close(session);
        }
        return true;
    }

    @Override
    public boolean updateCartoon(Cartoon cartoon) {
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            session.getTransaction().begin();
            session.merge(cartoon);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleCartoonDataAccess.updateCartoon.", he);
            return false;
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleCartoonDataAccess.updateCartoon.", e);
            return false;
        } finally {
            SessionUtil.close(session);
        }
        return true;
    }
}
