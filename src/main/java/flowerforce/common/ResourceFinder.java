package flowerforce.common;

import java.io.File;
import java.util.NoSuchElementException;

/**
 * Utility class to get resources complete path.
 */
public final class ResourceFinder {

    private static final String PROJECT_FOLDER_ABSOLUTE_PATH = System.getProperty("user.dir");
    private static final String RESOURCES_FOLDER_PATH = "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "flowerforce" + File.separator + "game";
    private static final String RESOURCES_FOLDER_ABSOLUTE_PATH = PROJECT_FOLDER_ABSOLUTE_PATH
            + File.separator + RESOURCES_FOLDER_PATH;

    private static final String SOUNDS_FOLDER_NAME = "sounds";
    private static final String FXML_FOLDER_NAME = "fxml";
    private static final String SAVING_FOLDER_NAME = "savings";
    private static final String IMAGES_FOLDER_NAME = "images";
    private static final String PLANTS_FOLDER_NAME = "plants";
    private static final String ZOMBIES_FOLDER_NAME = "zombies";
    private static final String BULLETS_FOLDER_NAME = "bullets";

    private ResourceFinder() {

    }

    /**
     * Get the image for a Plant.
     * @param filename the name of the file
     * @return the absolute path of the plant image
     */
    public static String getPlantImagePath(final String filename) {
        return getImagePath(PLANTS_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the image for a Zombie.
     * @param filename the name of the file
     * @return the absolute path of the zombie image
     */
    public static String getZombieImagePath(final String filename) {
        return getImagePath(ZOMBIES_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the image for a Bullet.
     * @param filename the name of the file
     * @return the absolute path of the bullet image
     */
    public static String getBulletImagePath(final String filename) {
        return getImagePath(BULLETS_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the complete path of a generic image (not plant, zombie or bullet).
     * @param filename The complete name of the image requested
     * @return A string representing the absolute path of the image file
     */
    public static String getImagePath(final String filename) {
        final String completePath = getImagesFolderPath() + File.separator + filename;
        checkPath(completePath);
        return completePath;
    }

    /**
     * Get the complete path of a fxml file.
     * @param filename The complete name of the fxml file requested
     * @return A string representing the absolute path of the fxml file
     */
    public static String getFXMLPath(final String filename) {
        final String completePath = getFXMLFolderPath() + File.separator + filename;
        checkPath(completePath);
        return completePath;
    }

    /**
     * Get the complete path of a sound file.
     * @param filename The complete name of the sound file requested
     * @return A string representing the absolute path of the sound file
     */
    public static String getSoundPath(final String filename) {
        final String completePath = getSoundsFolderPath() + File.separator + filename;
        checkPath(completePath);
        return completePath;
    }

    /**
     * Get the complete path of a saving file.
     * @param filename The complete name of the saving file requested
     * @return A string representing the absolute path of the saving file
     */
    public static String getSavingFilePath(final String filename) {
        final String completePath = getSavingFolderPath() + File.separator + filename;
        checkPath(completePath);
        return completePath;
    }

    /**
     * Get the path of the images' folder.
     * @return the absolute path
     */
    public static String getImagesFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + IMAGES_FOLDER_NAME;
    }

    /**
     * Get the path of the sounds' folder.
     * @return the absolute path
     */
    public static String getSoundsFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + SOUNDS_FOLDER_NAME;
    }

    /**
     * Get the path of the fxml folder.
     * @return the absolute path
     */
    public static String getFXMLFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + FXML_FOLDER_NAME;
    }

    /**
     * Get the path of the savings folder.
     * @return the absolute path
     */
    public static String getSavingFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + SAVING_FOLDER_NAME;
    }

    private static void checkPath(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            throw new NoSuchElementException("The requested resource does not exists (" + path + ").");
        }
    }
}
