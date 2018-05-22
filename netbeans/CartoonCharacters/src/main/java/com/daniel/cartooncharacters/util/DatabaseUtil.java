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

import com.daniel.cartooncharacters.entity.Cartoon;
import com.daniel.cartooncharacters.entity.CartoonCharacter;
import com.daniel.cartooncharacters.entity.CartoonLocation;
import com.daniel.cartooncharacters.entity.CartoonPicture;
import com.daniel.cartooncharacters.entity.CharacterDemographic;
import com.daniel.cartooncharacters.entity.Gender;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * This class provides utility methods for database access functions.
 *
 * @author Bryan Daniel
 */
public class DatabaseUtil {

    /**
     * Holds the resources for database connections
     */
    public static Map<Session, StandardServiceRegistry> connectionResources = new ConcurrentHashMap<>();

    /**
     * Private constructor - not called
     */
    public DatabaseUtil() {
    }

    /**
     * Creates a new Hibernate session using the configuration information and
     * returns it.
     *
     * @return a new Hibernate session
     */
    public static Session getNewSession() {
        Configuration config = DatabaseUtil.getConfiguration();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        connectionResources.put(session, serviceRegistry);
        return session;
    }

    /**
     * Closes the given Hibernate session and the registry object associated
     * with it. These objects are also removed from the connection resources
     * map.
     *
     * @param session the session to close
     */
    public static void close(Session session) {
        StandardServiceRegistry serviceRegistry = null;
        if (session != null) {
            serviceRegistry = connectionResources.remove(session);
            session.close();
        }
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    /**
     * This method sets and returns the Hibernate configuration properties for
     * database connections.
     *
     * @return the configuration
     */
    private static Configuration getConfiguration() {

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
        return config;
    }
}
