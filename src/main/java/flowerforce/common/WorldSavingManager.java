package flowerforce.common;

import flowerforce.model.game.World;
import flowerforce.model.game.Level;
import flowerforce.model.game.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WorldSavingManager {
    private static String PLAYER_FILE_NAME = "player";
    private static String INFINITELEVEL_FILE_NAME = "infiniteLevel";
    private static String LEVEL_FILE_PREFIX = "level";
    private final String SAVING_FOLDER_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator
            + "flowerforce" + File.separator + "game" + File.separator + "savings";
    public World load() throws InstantiationException {
        Optional<Player> p = loadPlayer();

        Level infiniteLevel = loadInfiniteLevel();

        List<Level> levels = loadLevels();

        return new World(p, levels, infiniteLevel);
    }

    public void save(World world) {
        SaveManager<Player> playerSaveManager = new SaveManager(Player.class, PLAYER_FILE_NAME);
        playerSaveManager.save(world.getPlayer());
    }

    private Optional<Player> loadPlayer() {
        SaveManager<Player> playerSaveManager = new SaveManager(Player.class, PLAYER_FILE_NAME);
        return playerSaveManager.load();
    }

    private Level loadInfiniteLevel() throws InstantiationException {
        SaveManager<Level> infiniteLevelSaveManager = new SaveManager(Level.class, INFINITELEVEL_FILE_NAME);
        Optional<Level> infiniteLevel = infiniteLevelSaveManager.load();
        if(infiniteLevel.isEmpty()) {
            throw new InstantiationException("Infinite level file has not been found.");
        }
        return infiniteLevel.get();
    }

    private List<Level> loadLevels() throws InstantiationException {
        File savingFolder = new File(SAVING_FOLDER_PATH);
        String[] levelNames = (String[]) Arrays.stream(savingFolder.listFiles())
                .filter(f -> f.getName().startsWith(LEVEL_FILE_PREFIX))
                .map(f -> f.getName())
                .toArray(); //saving just level save files, excluding player and infiniteLevel files

        List<Level> levels = new ArrayList<Level>();

        SaveManager<Level> levelSaveManager;
        for (String levelName : levelNames) {
            levelSaveManager = new SaveManager(Level.class, levelName);

            Optional<Level> loadedLevel = levelSaveManager.load();
            loadedLevel.ifPresent(l -> levels.add(l));
        }

        if (levels.isEmpty()) {
            throw new InstantiationException("Not enough levels are saved in the saving path.");
        }

        return levels;
    }
}
