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
package com.daniel.cartooncharacters.util;

import com.daniel.cartooncharacters.data.CharacterListener;
import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.entity.CharacterPicture;
import com.daniel.cartooncharacters.entity.Gender;
import com.daniel.cartooncharacters.entity.LocationPicture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

/**
 * This class establishes the configuration of the Hibernate session factory and
 * provides utility methods for accessing and closing sessions.
 *
 * @author Bryan Daniel
 */
public class SessionUtil {

    /**
     * The Hibernate session factory
     */
    private static final SessionFactory SESSION_FACTORY;

    static {
        ConnectionProperties properties = ConfigurationManager.getConnectionProperties();
        StringBuilder urlString = new StringBuilder();
        urlString.append(properties.getProperty(ConnectionProperties.DRIVER_STRING));
        urlString.append(properties.getProperty(ConnectionProperties.HOST));
        urlString.append(":");
        urlString.append(properties.getProperty(ConnectionProperties.PORT));
        urlString.append("/");
        urlString.append(properties.getProperty(ConnectionProperties.DATABASE_NAME));

        Configuration config = new Configuration();
        config.addAnnotatedClass(Cartoon.class);
        config.addAnnotatedClass(CartoonLocation.class);
        config.addAnnotatedClass(CartoonCharacter.class);
        config.addAnnotatedClass(CartoonPicture.class);
        config.addAnnotatedClass(LocationPicture.class);
        config.addAnnotatedClass(CharacterPicture.class);
        config.addAnnotatedClass(Gender.class);
        config.addAnnotatedClass(CharacterDemographic.class);
        config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        config.setProperty("hibernate.connection.url", urlString.toString());
        config.setProperty("hibernate.connection.username", properties.getProperty(ConnectionProperties.USERNAME));
        config.setProperty("hibernate.connection.password", properties.getProperty(ConnectionProperties.PASSWORD));
        config.setProperty("hibernate.show_sql", "true");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        config.setProperty("hibernate.current_session_context_class", "thread");
        config.setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        config.setProperty("hibernate.c3p0.min_size", "10");
        config.setProperty("hibernate.c3p0.max_size", "100");
        config.setProperty("hibernate.c3p0.acquire_increment", "1");
        config.setProperty("hibernate.c3p0.max_statements", "100");
        config.setProperty("hibernate.c3p0.idle_test_period", "300");
        config.setProperty("hibernate.c3p0.timeout", "3000");
        config.setProperty("hibernate.c3p0.testConnectionOnCheckout", "true");
        config.setProperty("hibernate.c3p0.preferredTestQuery", "SELECT 1");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();
        SESSION_FACTORY = config.buildSessionFactory(serviceRegistry);

        EventListenerRegistry registry = ((SessionFactoryImpl) SESSION_FACTORY).getServiceRegistry().getService(
                EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(new CharacterListener());
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(new CharacterListener());
    }

    /**
     * Private constructor - not called
     */
    public SessionUtil() {
    }

    /**
     * Creates a new Hibernate session using the session factory and returns it.
     *
     * @return a new Hibernate session
     */
    public static Session getNewSession() {
        return SESSION_FACTORY.openSession();
    }

    /**
     * Closes the given Hibernate session.
     *
     * @param session the session to close
     */
    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException he) {
                Logger.getLogger(SessionUtil.class.getName()).log(Level.SEVERE,
                        "An exception occurred while closing the session.", he);
            }
        }
    }
}
