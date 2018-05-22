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
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.util.DatabaseUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * This CharacterDataAccess implementation provides the basic functionality for
 * accessing the cartoon database to find character data.
 *
 * @author Bryan Daniel
 */
public class SimpleCharacterDataAccess implements CharacterDataAccess {

    @Override
    public List<CartoonCharacter> findCartoonCharacters(String characterName, String cartoonTitle) {

        ArrayList<CartoonCharacter> matchingCharacters = new ArrayList<>();
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

            session = DatabaseUtil.getNewSession();
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
                    "HibernateException exception occurred during SimpleCharacterDataAccess.findCartoonCharacters.", he);
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDataAccess.findCartoonCharacters.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return matchingCharacters;
    }

    @Override
    public CartoonCharacter findCartoonCharacter(String characterName) {
        CartoonCharacter cartoonCharacter = null;
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            Criteria crit = session.createCriteria(CartoonCharacter.class);
            crit.add(Restrictions.eq("characterName", characterName));
            cartoonCharacter = (CartoonCharacter) crit.uniqueResult();
        } catch (HibernateException he) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during SimpleCharacterDataAccess.findCartoonCharacter.", he);
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDataAccess.findCartoonCharacter.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return cartoonCharacter;
    }
    
    @Override
    public List<String> findCartoonCharacterNames(CartoonLocation cartoonLocation) {
        List<String> cartoonCharacterNames = null;
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            cartoonCharacterNames = session.createCriteria(CartoonCharacter.class)
                    .add(Restrictions.eq("characterHome.locationId", cartoonLocation.getLocationId()))
                    .setProjection(Projections.property("characterName"))
                    .list();
        } catch (HibernateException he) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during SimpleCharacterDataAccess.findCartoonCharacterNames.", he);
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDataAccess.findCartoonCharacterNames.", e);
        } finally {
            DatabaseUtil.close(session);
        }
        return cartoonCharacterNames;
    }

    @Override
    public boolean addCharacter(CartoonCharacter cartoonCharacter, CharacterDemographic characterDemographic) {
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            session.getTransaction().begin();
            session.persist(cartoonCharacter);
            session.persist(characterDemographic);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during SimpleCharacterDataAccess.addCharacter.", he);
            return false;
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDataAccess.addCharacter.", e);
            return false;
        } finally {
            DatabaseUtil.close(session);
        }
        return true;
    }

    @Override
    public boolean updateCharacter(CartoonCharacter cartoonCharacter, CharacterDemographic characterDemographic) {
        Session session = null;
        try {
            session = DatabaseUtil.getNewSession();
            session.getTransaction().begin();
            session.merge(cartoonCharacter);
            session.merge(characterDemographic);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during SimpleCharacterDataAccess.updateCharacter.", he);
            return false;
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDataAccess.updateCharacter.", e);
            return false;
        } finally {
            DatabaseUtil.close(session);
        }
        return true;
    }
}
