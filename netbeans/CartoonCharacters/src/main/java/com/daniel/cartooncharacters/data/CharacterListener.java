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
import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

/**
 * Listens for update and insert events to log changes to the CartoonCharacter
 * table.
 *
 * @author Bryan Daniel
 */
public class CharacterListener implements PreUpdateEventListener, PreInsertEventListener {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 8064296390312987223L;

    /**
     * The logger for this class
     */
    private Logger logger;
    
    /**
     * Sets the value for the logger.
     */
    public CharacterListener(){
        logger = LogManager.getLogger(CharacterListener.class);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
        if (preUpdateEvent.getEntity() instanceof CartoonCharacter) {
            CartoonCharacter characterUpdated = (CartoonCharacter) preUpdateEvent.getEntity();
            String logMessage = MessageFormat.format("Character updated - ID: {0} Name: {1}", 
                    characterUpdated.getCharacterId(), characterUpdated.getCharacterName());
            logger.info(logMessage);
        }
        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        if (preInsertEvent.getEntity() instanceof CartoonCharacter) {
            CartoonCharacter characterAdded = (CartoonCharacter) preInsertEvent.getEntity();
            String logMessage = MessageFormat.format("Character added - ID: {0} Name: {1}", 
                    characterAdded.getCharacterId(), characterAdded.getCharacterName());
            logger.info(logMessage);
        }
        return false;
    }
}
