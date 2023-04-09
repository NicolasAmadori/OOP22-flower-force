package flowerforce.common;

import java.io.File;
import java.net.URL;
import java.util.NoSuchElementException;

/**
 * Utility class to get resources complete path.
 */
public final class ResourceFinder {

    private static final String RESOURCE_PARENT_PATH = "/".concat("flowerforce").concat("/").concat("game");

    private static final String SOUNDS_FOLDER_NAME = "sounds";

    private static final String FXML_FOLDER_NAME = "fxml";

    private static final String SAVING_FOLDER_NAME = "savings";

    private static final String IMAGES_FOLDER_NAME = "images";

    private static final String COMMON_IMAGES_FOLDER_NAME = "common";

    private static final String PLANTS_IMAGES_FOLDER_NAME = "plants";

    private static final String ZOMBIES_IMAGES_FOLDER_NAME = "zombies";

    private static final String BULLETS_IMAGES_FOLDER_NAME = "bullets";

    private static final String USER_HOME_PATH = System.getProperty("user.home");
    private ResourceFinder() {

    }

    /**
     * Get the image path for a common element.
     * @param filename the name of the file
     * @return the absolute path of the common element
     */
    public static URL getCommonImagePath(final String filename) {
        return getImagePath(COMMON_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the image for a Plant.
     * @param filename the name of the file
     * @return the absolute path of the plant image
     */
    public static URL getPlantImagePath(final String filename) {
        return getImagePath(PLANTS_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the image for a Zombie.
     * @param filename the name of the file
     * @return the absolute path of the zombie image
     */
    public static URL getZombieImagePath(final String filename) {
        return getImagePath(ZOMBIES_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the image for a Bullet.
     * @param filename the name of the file
     * @return the absolute path of the bullet image
     */
    public static URL getBulletImagePath(final String filename) {
        return getImagePath(BULLETS_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the complete path of a generic image (not plant, zombie or bullet).
     * @param filename The complete name of the image requested
     * @return A string representing the absolute path of the image file
     */
    private static URL getImagePath(final String filename) {
        return getURL(getImagesFolderPath().concat("/").concat(filename));
    }

    /**
     * Get the complete path of a fxml file.
     * @param filename The complete name of the fxml file requested
     * @return A string representing the absolute path of the fxml file
     */
    public static URL getFXMLPath(final String filename) {
        return getURL(getFXMLFolderPath().concat("/").concat(filename));
    }

    /**
     * Get the complete path of a sound file.
     * @param filename The complete name of the sound file requested
     * @return A string representing the absolute path of the sound file
     */
    public static URL getSoundPath(final String filename) {
        return getURL(getSoundsFolderPath().concat("/").concat(filename));
    }

    /**
     * Get the complete path of a saving file.
     * @param filename The complete name of the saving file requested
     * @return A string representing the absolute path of the saving file
     */
    public static String getSavingFilePath(final String filename) {
        return getSavingFolderPath().concat(File.separator).concat(filename);
    }

    private static String getImagesFolderPath() {
        return RESOURCE_PARENT_PATH.concat("/").concat(IMAGES_FOLDER_NAME);
    }

    private static String getSoundsFolderPath() {
        return RESOURCE_PARENT_PATH.concat("/").concat(SOUNDS_FOLDER_NAME);
    }

    private static String getFXMLFolderPath() {
        return RESOURCE_PARENT_PATH.concat("/").concat(FXML_FOLDER_NAME);
    }

    private static String getSavingFolderPath() {
        return USER_HOME_PATH
                .concat(File.separator).concat(".FlowerForce")
                .concat(File.separator).concat(SAVING_FOLDER_NAME);
    }

    private static URL getURL(final String relativePath) {
        final URL url = ResourceFinder.class.getResource(relativePath);
        if (url == null) {
            throw new NoSuchElementException("The requested resource does not exists (" + relativePath + ").");
        }
        return url;
    }
}
