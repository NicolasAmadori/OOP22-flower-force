package flowerforce.common;

import java.io.File;
import java.net.URL;
import java.util.NoSuchElementException;

/**
 * Utility class to get resources url by just giving the name of them.
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
     * @return The URL instance of the common image file
     */
    public static URL getCommonImageURL(final String filename) {
        return getImageURL(COMMON_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the image for a Plant.
     * @param filename the name of the file
     * @return The URL instance of the plant image file
     */
    public static URL getPlantImageURL(final String filename) {
        return getImageURL(PLANTS_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the image for a Zombie.
     * @param filename the name of the file
     * @return The URL instance of the zombie image file
     */
    public static URL getZombieImageURL(final String filename) {
        return getImageURL(ZOMBIES_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the image for a Bullet.
     * @param filename the name of the file
     * @return The URL instance of the bullet image file
     */
    public static URL getBulletImageURL(final String filename) {
        return getImageURL(BULLETS_IMAGES_FOLDER_NAME.concat("/").concat(filename));
    }

    /**
     * Get the complete path of a generic image (not plant, zombie or bullet).
     * @param filename The complete name of the image requested
     * @return The URL instance of the image file
     */
    private static URL getImageURL(final String filename) {
        return getResourceURL(getImagesFolderPath().concat("/").concat(filename));
    }

    /**
     * Get the complete path of a fxml file.
     * @param filename The complete name of the fxml file requested
     * @return The URL instance of the fxml file
     */
    public static URL getFXMLURL(final String filename) {
        return getResourceURL(getFXMLFolderPath().concat("/").concat(filename));
    }

    /**
     * Get the complete path of a sound file.
     * @param filename The complete name of the sound file requested
     * @return The URL instance of the sound file
     */
    public static URL getSoundURL(final String filename) {
        return getResourceURL(getSoundsFolderPath().concat("/").concat(filename));
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
                .concat(File.separator).concat(".flowerforce")
                .concat(File.separator).concat(SAVING_FOLDER_NAME);
    }

    private static URL getResourceURL(final String relativePath) {
        final URL url = ResourceFinder.class.getResource(relativePath);
        if (url == null) {
            throw new NoSuchElementException("The requested resource does not exists (" + relativePath + ").");
        }
        return url;
    }
}
