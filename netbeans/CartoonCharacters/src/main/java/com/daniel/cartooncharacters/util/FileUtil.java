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

import static com.daniel.cartooncharacters.task.SavePictureTask.BUFFER_LENGTH;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides utility methods for accessing the file system.
 *
 * @author Bryan Daniel
 */
public class FileUtil {

    /**
     * The name of the images directory
     */
    public static final String IMAGES_DIRECTORY = "img";

    /**
     * Private constructor - not called
     */
    public FileUtil() {
    }

    /**
     * Returns the file object for the specified image path stored in the
     * database.
     *
     * @param imagePath the path to the image
     * @return the image file
     */
    public static File getImageFile(String imagePath) {
        return new File(new File(System.getProperty("user.dir")), imagePath);
    }

    /**
     * Creates the image directory for the given cartoon if it does not exist.
     *
     * @param cartoonTitle the cartoon title
     */
    public static void createCartoonImageDirectory(String cartoonTitle) {

        // creating the top-levelimages folder for the application if it does not exist
        File imagesDirectory = new File(new File(System.getProperty("user.dir")),
                IMAGES_DIRECTORY);
        if (!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
            imagesDirectory.mkdir();
        }

        // creating the images folder for the cartoon if it does not exist
        File cartoonImagesDirectory = new File(new File(System.getProperty("user.dir")),
                IMAGES_DIRECTORY + File.separator + cartoonTitle);
        if (!cartoonImagesDirectory.exists() || !cartoonImagesDirectory.isDirectory()) {
            cartoonImagesDirectory.mkdir();
        }
    }

    /**
     * Returns a new file object built from the given parameters.
     *
     * @param cartoonTitle the cartoon title
     * @param imageName the image name
     * @return the new image file
     */
    public static File getNewImageFile(String cartoonTitle, String imageName) {
        return new File(new File(System.getProperty("user.dir")), IMAGES_DIRECTORY 
                + File.separator + cartoonTitle + File.separator + imageName);
    }

    /**
     * Returns a new image file path to store as a database record.
     *
     * @param cartoonTitle the cartoon title
     * @param imageName the image name
     * @return the new image file path
     */
    public static String getNewImagePath(String cartoonTitle, String imageName) {
        return File.separator + IMAGES_DIRECTORY + File.separator + cartoonTitle + File.separator + imageName;
    }

    /**
     * Copies the image file from the original location to the new location.
     *
     * @param original the original
     * @param newFile the new file
     * @throws IOException
     */
    public static void copyImage(File original, File newFile) throws IOException {

        try (InputStream inputStream = new FileInputStream(original);
                OutputStream outputStream = new FileOutputStream(newFile)) {

            byte[] buffer = new byte[BUFFER_LENGTH];
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE,
                    "An exception occurred while copying the image.", ex);
            throw new IOException("An exception occurred while copying the image.");
        }
    }
}
