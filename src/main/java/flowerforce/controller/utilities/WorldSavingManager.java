package flowerforce.controller.utilities;

import flowerforce.model.game.World;
import flowerforce.model.game.WorldImpl;
import flowerforce.model.game.PlayerImpl;
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
     */
    public static World load() {
        final Optional<PlayerImpl> p = loadPlayer();

        return new WorldImpl(Optional.ofNullable(p.orElse(null)));
    }

    /**
     * Save the instance of World on files.
     * @param world The instance of World to save
     */
    public static void save(final World world) {
        final SaveManager<PlayerImpl> playerSaveManager = new SaveManager<>(PlayerImpl.class, PLAYER_FILE_NAME);
        playerSaveManager.save((PlayerImpl) world.getPlayer());
    }

    private static Optional<PlayerImpl> loadPlayer() {
        final SaveManager<PlayerImpl> playerSaveManager = new SaveManager<>(PlayerImpl.class, PLAYER_FILE_NAME);
        return playerSaveManager.load();
    }
}
