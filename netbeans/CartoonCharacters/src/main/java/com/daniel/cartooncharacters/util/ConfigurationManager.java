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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class handles configuration properties needed for application
 * functionality.
 *
 * @author Bryan Daniel
 */
public class ConfigurationManager {

    /**
     * The algorithm used for encryption
     */
    private static final String ALGORITHM = "AES";

    /**
     * The key used for encryption
     */
    private static final String ENCRYPTION_KEY = "16-character key";

    /**
     * The name of the configuration directory
     */
    public static final String CONFIGURATION_DIRECTORY = "config";

    /**
     * The name of the configuration file
     */
    public static final String CONFIGURATION_FILE = "configuration.xml";

    /**
     * The database connection properties
     */
    private static ConnectionProperties connectionProperties;

    /**
     * Private constructor - not called
     */
    public ConfigurationManager() {
    }

    /**
     * This method reads the configuration file to find and set the connection
     * property values.
     */
    public static void findConnectionProperties() {

        Logger logger = LogManager.getLogger(ConfigurationManager.class);
        InputStream input = null;
        try {
            String driverStringValue = null;
            String hostValue = null;
            String databaseNameValue = null;
            String portValue = null;
            String usernameValue = null;
            String passwordValue = null;

            //input = ConfigurationManager.class.getResourceAsStream("/config/configuration.xml");
            input = new FileInputStream(new File(new File(System.getProperty("user.dir")),
                    CONFIGURATION_DIRECTORY + File.separator + CONFIGURATION_FILE));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(input);

            /* Normalize the nodes */
            doc.getDocumentElement().normalize();

            /* Find the element list*/
            NodeList nodeList = doc.getElementsByTagName(ConnectionProperties.DRIVER_STRING);

            /* There should only be one item in the element list. */
            Node n = nodeList.item(0);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                driverStringValue = e.getTextContent();
            }

            /* Find the element list*/
            nodeList = doc.getElementsByTagName(ConnectionProperties.HOST);

            /* There should only be one item in the element list. */
            n = nodeList.item(0);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                hostValue = e.getTextContent();
            }

            /* Find the element list*/
            nodeList = doc.getElementsByTagName(ConnectionProperties.DATABASE_NAME);

            /* There should only be one item in the element list. */
            n = nodeList.item(0);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                databaseNameValue = e.getTextContent();
            }

            /* Find the element list*/
            nodeList = doc.getElementsByTagName(ConnectionProperties.PORT);

            /* There should only be one item in the element list. */
            n = nodeList.item(0);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                portValue = e.getTextContent();
            }

            /* Find the element list*/
            nodeList = doc.getElementsByTagName(ConnectionProperties.USERNAME);

            /* There should only be one item in the element list. */
            n = nodeList.item(0);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                usernameValue = e.getTextContent();
            }

            /* Find the element list*/
            nodeList = doc.getElementsByTagName(ConnectionProperties.PASSWORD);

            /* There should only be one item in the element list. */
            n = nodeList.item(0);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                passwordValue = e.getTextContent();
            }

            connectionProperties = new ConnectionProperties(driverStringValue,
                    hostValue, databaseNameValue, portValue, usernameValue, decrypt(passwordValue));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            logger.error(
                    "ParserConfigurationException or SAXException or IOException in findConnectionProperties method.", ex);
        } catch (Exception ex) {
            logger.error("Exception occurred in findConnectionProperties method.", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    logger.error("An exception occurred in findConnectionProperties method.", ex);
                }
            }
        }
    }

    /**
     * This method returns the connection properties. If the properties have not
     * been initialized, the findConnectionProperties method is called first.
     *
     * @return the connection properties
     */
    public static ConnectionProperties getConnectionProperties() {
        if (connectionProperties == null) {
            findConnectionProperties();
        }
        return connectionProperties;
    }

    /**
     * This method decrypts the given string.
     *
     * @param encryptedString the string to decrypt
     * @return the decrypted string
     * @throws Exception
     */
    private static String decrypt(String encryptedString) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM));
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
        return new String(decryptedBytes);
    }
}
