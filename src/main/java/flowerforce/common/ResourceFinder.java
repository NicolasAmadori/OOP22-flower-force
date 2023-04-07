package flowerforce.common;

import java.io.File;
import java.util.NoSuchElementException;

/**
 * Utility class to get resources complete path.
 */
public final class ResourceFinder {

    private static final String PROJECT_NAME = "OOP22-flower-force";

    private static final String PROJECT_FOLDER_ABSOLUTE_PATH =
            System.getProperty("user.dir").split(PROJECT_NAME)[0] + PROJECT_NAME;

    private static final String RESOURCES_FOLDER_PATH = "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "flowerforce" + File.separator + "game";

    private static final String RESOURCES_FOLDER_ABSOLUTE_PATH = PROJECT_FOLDER_ABSOLUTE_PATH
            + File.separator + RESOURCES_FOLDER_PATH;

    private static final String SOUNDS_FOLDER_NAME = "sounds";

    private static final String FXML_FOLDER_NAME = "fxml";

    private static final String SAVING_FOLDER_NAME = "savings";

    private static final String IMAGES_FOLDER_NAME = "images";

    private static final String COMMON_IMAGES_FOLDER_NAME = "common";

    private static final String PLANTS_IMAGES_FOLDER_NAME = "plants";

    private static final String ZOMBIES_IMAGES_FOLDER_NAME = "zombies";

    private static final String BULLETS_IMAGES_FOLDER_NAME = "bullets";


    private ResourceFinder() {

    }

    /**
     * Get the image path for a common element.
     * @param filename the name of the file
     * @return the absolute path of the common element
     */
    public static String getCommonImagePath(final String filename) {
        return getImagePath(COMMON_IMAGES_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the image for a Plant.
     * @param filename the name of the file
     * @return the absolute path of the plant image
     */
    public static String getPlantImagePath(final String filename) {
        return getImagePath(PLANTS_IMAGES_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the image for a Zombie.
     * @param filename the name of the file
     * @return the absolute path of the zombie image
     */
    public static String getZombieImagePath(final String filename) {
        return getImagePath(ZOMBIES_IMAGES_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the image for a Bullet.
     * @param filename the name of the file
     * @return the absolute path of the bullet image
     */
    public static String getBulletImagePath(final String filename) {
        return getImagePath(BULLETS_IMAGES_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the complete path of a generic image (not plant, zombie or bullet).
     * @param filename The complete name of the image requested
     * @return A string representing the absolute path of the image file
     */
    private static String getImagePath(final String filename) {
        final String completePath = getImagesFolderPath() + File.separator + filename;
        return checkPath(completePath);
    }

    /**
     * Get the complete path of a fxml file.
     * @param filename The complete name of the fxml file requested
     * @return A string representing the absolute path of the fxml file
     */
    public static String getFXMLPath(final String filename) {
        String completePath = getFXMLFolderPath() + File.separator + filename;
        checkPath(completePath); //Check if the file exist

        //Adapt the path to use with ClassLoader
        completePath = completePath
                .substring(getFXMLFolderPath().indexOf("flowerforce")) //get only the packet path
                .replace("\\", "/"); //replace window separator with linux separator
        return completePath;
    }

    /**
     * Get the complete path of a sound file.
     * @param filename The complete name of the sound file requested
     * @return A string representing the absolute path of the sound file
     */
    public static String getSoundPath(final String filename) {
        final String completePath = getSoundsFolderPath() + File.separator + filename;
        return checkPath(completePath);
    }

    /**
     * Get the complete path of a saving file.
     * @param filename The complete name of the saving file requested
     * @return A string representing the absolute path of the saving file
     */
    public static String getSavingFilePath(final String filename) {
        //do NOT check for file existence, because the saving file could be created in the moment
        return getSavingFolderPath() + File.separator + filename;
    }

    private static String getImagesFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + IMAGES_FOLDER_NAME;
    }

    private static String getSoundsFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + SOUNDS_FOLDER_NAME;
    }

    private static String getFXMLFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + FXML_FOLDER_NAME;
    }

    private static String getSavingFolderPath() {
        return RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + SAVING_FOLDER_NAME;
    }

    private static String checkPath(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            throw new NoSuchElementException("The requested resource does not exists (" + path + ").");
        }
        return path;
    }
}
