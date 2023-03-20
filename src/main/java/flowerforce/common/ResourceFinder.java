package flowerforce.common;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.Optional;

public class ResourceFinder {

    private static String PROJECT_FOLDER_ABSOLUTE_PATH = System.getProperty("user.dir");
    private static String RESOURCES_FOLDER_PATH = "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "flowerforce" + File.separator + "game";
    private static String RESOURCES_FOLDER_ABSOLUTE_PATH = PROJECT_FOLDER_ABSOLUTE_PATH + File.separator + RESOURCES_FOLDER_PATH;

    private static String IMAGES_FOLDER_NAME = "images";
    private static String FXML_FOLDER_NAME = "fxml";
    private static String SAVING_FOLDER_NAME = "savings";
    public static Optional<String> getImagePath(final String filename) {
        return returnOptional(RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + IMAGES_FOLDER_NAME + File.separator + filename);
    }

    public static Optional<String> getFXMLPath(final String filename) {
        return returnOptional(RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + FXML_FOLDER_NAME + File.separator + filename);
    }

    public static Optional<String> getSavingFilePath(final String filename) {
        return returnOptional(RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + SAVING_FOLDER_NAME + File.separator + filename);
    }

    private static Optional<String> returnOptional(final String path) {
        final File file = new File(path);
        return file.exists() ? Optional.of(path) : Optional.empty();
    }
}
