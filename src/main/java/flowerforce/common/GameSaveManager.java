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

    private static final Gson GSON = new Gson(); //Instance to json text converter

    private final Class<T> genericClass; //class of the type to deserialize
    private final String savingFilePath; //path of the savingFile

    /**
     * Create a new instance of the game saving manager.
     * @param genericClass the class of the generic type
     * @param fileName the name for the saving file
     */
    public GameSaveManager(final Class<T> genericClass, final String fileName) {
        this.genericClass = genericClass;
        this.savingFilePath = System.getProperty("user.dir") + File.separator + fileName + ".json";
    }

    /**
     * Save the player's information to a JSON format save file.
     * @param p The player to be saved to file.
     * @return True if the save operation was successful, false otherwise.
     */
    public boolean save(final T p) {
        try (FileWriter fw = new FileWriter(savingFilePath)) {
            fw.write(GSON.toJson(p));
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Reads the player's information save file, if it exists and returns an optional with the object of the generic type.
     * @return An optional containing the instance of the saved player read from file, 
     *  empty if the file does not exist or in case of an error during the read operation.
     */
    public Optional<T> load() {
        final File file = new File(savingFilePath);

        if (!file.exists()) {
            return Optional.empty();
        }

        try (FileReader fr = new FileReader(file)) {
            return Optional.of(GSON.fromJson(fr, genericClass));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
