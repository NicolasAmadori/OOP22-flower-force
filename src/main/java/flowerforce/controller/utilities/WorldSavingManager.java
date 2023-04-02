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

    private WorldSavingManager() {

    }

    /**
     * This method get an instance of World from the saving files.
     * @return the instance of the World
     * @throws InstantiationException In case of missing saved files
     */
    public static World load() throws InstantiationException {
        final Optional<Player> p = loadPlayer();

        return new WorldImpl(p);
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
}
