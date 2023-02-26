package flowerforce.common;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

/**
 * A utility class that manages saving information to a JSON file or loading information from it.
 * @param <T> The type of the file to save on the file or to load from it
 */
public final class GameSaveManager<T> {

    private static final String SAVING_PATH = "C:/Users/Nicolas/Downloads/OOP22-flower-force";
    private static final String SAVING_FILE_NAME = "savings.json";
    private static final Gson GSON = new Gson();

    private Class<T> genericClass;

    /**
     * Create a new instance of the game saving manager.
     * @param genericClass the class of the generic type
     */
    public GameSaveManager(final Class<T> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * Save the player's information to a JSON format save file.
     * @param p The player to be saved to file.
     * @return True if the save operation was successful, false otherwise.
     */
    public boolean save(final T p) {
        try (FileWriter fw = new FileWriter(SAVING_PATH + File.separator + SAVING_FILE_NAME)) {
            fw.write(GSON.toJson(p));
            return true;

        } catch (IOException  e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reads the player's information save file, if it exists and returns an optional with the object of the generic type.
     * @return An optional containing the instance of the saved player read from file, 
     *  empty if the file does not exist or in case of an error during the read operation.
     */
    public Optional<T> load() {
        File file = new File(SAVING_PATH + File.separator + SAVING_FILE_NAME);

        if (!file.exists()) {
            return Optional.empty();
        }

        try (FileReader fr = new FileReader(file)) {
            return Optional.of(GSON.fromJson(fr, genericClass));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
