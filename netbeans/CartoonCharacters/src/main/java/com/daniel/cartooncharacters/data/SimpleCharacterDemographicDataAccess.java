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
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.util.SessionUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * This CharacterDemographicDataAccess implementation accesses the database to
 * retrieve demographic information for a character.
 *
 * @author Bryan Daniel
 */
public class SimpleCharacterDemographicDataAccess implements CharacterDemographicDataAccess {

    @Override
    public CharacterDemographic getCharacterDemographic(CartoonCharacter cartoonCharacter) {
        CharacterDemographic characterDemographic = null;
        Session session = null;
        try {
            session = SessionUtil.getNewSession();
            Criteria criteria = session.createCriteria(CharacterDemographic.class);
            criteria.add(Restrictions.eq("character.characterId", cartoonCharacter.getCharacterId()));
            characterDemographic = (CharacterDemographic) criteria.uniqueResult();
        } catch (HibernateException he) {
            Logger.getLogger(SimpleCharacterDemographicDataAccess.class.getName()).log(Level.INFO,
                    "HibernateException exception occurred during SimpleCharacterDemographicDataAccess.getCharacterDemographic.", he);
        } catch (Exception e) {
            Logger.getLogger(SimpleCharacterDemographicDataAccess.class.getName()).log(Level.INFO,
                    "Exception occurred during SimpleCharacterDemographicDataAccess.getCharacterDemographic.", e);
        } finally {
            SessionUtil.close(session);
        }
        return characterDemographic;
    }    
}
