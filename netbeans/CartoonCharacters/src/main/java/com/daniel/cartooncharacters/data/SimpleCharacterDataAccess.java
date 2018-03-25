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

import java.util.ArrayList;
import java.util.List;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.util.DatabaseUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * This CharacterDataAccess implementation provides the basic functionality for
 * accessing the cartoon database to find character data.
 *
 * @author Bryan Daniel
 */
public class SimpleCharacterDataAccess implements CharacterDataAccess {

    /**
     * This method searches for cartoon characters with values matching the
     * given name and cartoon title.
     *
     * @param characterName the name of the cartoon character
     * @param cartoonTitle the title of the cartoon
     * @return the list of characters found with values matching the parameters
     */
    @Override
    public List<CartoonCharacter> findCartoonCharacters(String characterName, String cartoonTitle) {

        ArrayList<CartoonCharacter> matchingCharacters = new ArrayList<>();
        StandardServiceRegistry serviceRegistry = null;
        Session session = null;
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT ch ");
            queryString.append("FROM CartoonCharacter ch, CartoonLocation l, Cartoon c ");
            queryString.append("WHERE ch.characterHome.locationId = l.locationId ");
            queryString.append("AND l.cartoon.cartoonId = c.cartoonId ");
            if (!characterName.isEmpty()) {
                queryString.append("AND LOWER(ch.characterName) LIKE :characterName ");
            }
            if (!cartoonTitle.isEmpty()) {
                queryString.append("AND LOWER(c.title) LIKE :title ");
            }

            Configuration config = DatabaseUtil.getConfiguration();
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();
            SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();
            Query query = session.createQuery(queryString.toString());
            if (!characterName.isEmpty()) {
                query.setString("characterName", "%" + characterName.toLowerCase() + "%");
            }
            if (!cartoonTitle.isEmpty()) {
                query.setString("title", "%" + cartoonTitle.toLowerCase() + "%");
            }

            List list = query.list();
            list.forEach((o) -> {
                matchingCharacters.add((CartoonCharacter) o);
            });

        } catch (HibernateException he) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during SimpleCharacterDataAccess.findCharacters.", he);
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDataAccess.findCharacters.", e);
        } finally {
            if (session != null) {
                session.close();
            }
            if (serviceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
            }
        }
        return matchingCharacters;
    }
}
