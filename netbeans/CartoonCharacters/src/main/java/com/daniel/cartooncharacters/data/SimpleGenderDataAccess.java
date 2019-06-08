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

import com.daniel.cartooncharacters.entity.Gender;
import com.daniel.cartooncharacters.util.SessionUtil;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * This GenderDataAccess implementation retrieves all gender information from
 * the database.
 *
 * @author Bryan Daniel
 */
public class SimpleGenderDataAccess implements GenderDataAccess {

    /**
     * The logger for this class
     */
    private Logger logger;

    /**
     * Sets the value for the logger.
     */
    public SimpleGenderDataAccess() {
        logger = LogManager.getLogger(SimpleGenderDataAccess.class);
    }

    @Override
    public List<Gender> findAllGenders() {
        List<Gender> cartoons = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            cartoons = session.createCriteria(Gender.class).list();
        } catch (HibernateException he) {
            logger.error("HibernateException exception occurred during SimpleGenderDataAccess.findAllGenders.", he);
        } catch (Exception e) {
            logger.error("Exception occurred during SimpleGenderDataAccess.findAllGenders.", e);
        } finally {
            SessionUtil.close(session);
        }
        return cartoons;
    }
}
