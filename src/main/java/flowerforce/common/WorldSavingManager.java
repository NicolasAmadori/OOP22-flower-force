package flowerforce.common;

import flowerforce.model.entities.World;
import flowerforce.model.game.Level;
import flowerforce.model.game.Player;
import flowerforce.model.game.PlayerImpl;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class WorldSavingManager {

    private final String SAVING_FOLDER_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator
            + "flowerforce" + File.separator + "game" + File.separator + "savings";
    public World load() {
        SaveManager<Player> playerSaveManager = new SaveManager(Player.class, "player");

        Optional<Player> p = playerSaveManager.load();

        File folder = new File(SAVING_FOLDER_PATH);
        File[] levelSavingFiles = (File[]) Arrays.stream(folder.listFiles()).filter(f -> f.getName().startsWith("level")).toArray();//saving just level save files

        List<Level> levels = new ArrayList<Level>();

        SaveManager<Level> levelSaveManager;
        for (File levelFile : levelSavingFiles) {
            levelSaveManager = new SaveManager(Level.class, levelFile.getName());

            Optional<Level> loadedLevel = levelSaveManager.load();
            loadedLevel.ifPresent(l -> levels.add(l));
        }

        return new World(levels, p);
    }

    public void save(World world) {
        SaveManager<Player> playerSaveManager = new SaveManager(Player.class, "player");

        playerSaveManager.save(world.getPlayer());

        List<Level> levels = world.getLevels();

        SaveManager<Level> levelSaveManager;
        for (Level level : levels) {
            levelSaveManager = new SaveManager(Level.class, "level" + level.getLevelId());

            levelSaveManager.save(level);
        }
    }
}
