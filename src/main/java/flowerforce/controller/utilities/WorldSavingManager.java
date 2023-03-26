package flowerforce.controller.utilities;

import flowerforce.common.ResourceFinder;
import flowerforce.model.game.World;
import flowerforce.model.game.WorldImpl;
import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;
import flowerforce.model.game.Level;
import flowerforce.model.game.LevelImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This is the class that handles saving and loading of the entire world to/from a file.
 */
public final class WorldSavingManager {
    private static final String PLAYER_FILE_NAME = "player";
    private static final String INFINITELEVEL_FILE_NAME = "infiniteLevel";
    private static final String LEVEL_FILE_PREFIX = "level";

    private WorldSavingManager() {

    }

    /**
     * This method get an instance of World from the saving files.
     * @return the instance of the World
     * @throws InstantiationException In case of missing saved files
     */
    public static World load() throws InstantiationException {
        final Optional<Player> p = loadPlayer();

        final Level infiniteLevel = loadInfiniteLevel();

        final List<Level> levels = loadLevels();

        return new WorldImpl(p, levels, infiniteLevel);
    }

    /**
     * Save the instance of World on files.
     * @param world The instance of World to save
     */
    public static void save(final World world) {
        final SaveManager<Player> playerSaveManager = new SaveManager(PlayerImpl.class, PLAYER_FILE_NAME);
        playerSaveManager.save(world.getPlayer());
    }

    private static Optional<Player> loadPlayer() {
        final SaveManager<Player> playerSaveManager = new SaveManager(PlayerImpl.class, PLAYER_FILE_NAME);
        return playerSaveManager.load();
    }

    private static Level loadInfiniteLevel() throws InstantiationException {
        final SaveManager<Level> infiniteLevelSaveManager = new SaveManager(LevelImpl.class, INFINITELEVEL_FILE_NAME);
        final Optional<Level> infiniteLevel = infiniteLevelSaveManager.load();
        if (infiniteLevel.isEmpty()) {
            throw new InstantiationException("Infinite level file has not been found.");
        }
        return infiniteLevel.get();
    }

    private static List<Level> loadLevels() throws InstantiationException {
        final File savingFolder = new File(ResourceFinder.getSavingFolderPath());
        final List<String> levelNames = Arrays.stream(savingFolder.listFiles())
                .map(f -> f.getName())//get just the names
                .map(n -> n.split("\\..")[0])//Remove extension from the names
                .filter(f -> f.startsWith(LEVEL_FILE_PREFIX))//Get just levels names
                .toList();

        final List<Level> levels = new ArrayList<>();

        SaveManager<Level> levelSaveManager;
        for (final String levelName : levelNames) {
            levelSaveManager = new SaveManager(LevelImpl.class, levelName);

            final Optional<Level> loadedLevel = levelSaveManager.load();
            loadedLevel.ifPresent(l -> levels.add(l));
        }

        if (levels.isEmpty()) {
            throw new InstantiationException("Not enough levels are saved in the saving path.");
        }

        return levels;
    }
}
