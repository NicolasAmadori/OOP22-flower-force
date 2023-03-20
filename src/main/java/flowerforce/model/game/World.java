package flowerforce.model.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * Models the world the game's played in.
 */
public class World {

    private static final int NEW_PLAYER_COINS = 0;
    private static final int NEW_PLAYER_RECORD = 0;
    private static final int NEW_PLAYER_LAST_UNLOCKED_LEVEL = 1;

    private final Player player;
    private final List<Level> levelList;
    private final Level infiniteModeLevel;

    /**
     * Generates a world.
     * @param player the player that plays the game
     * @param levelList the list of all levels
     * @param infiniteModeLevel the level that models the infinite mode
     */
    public World(final Optional<Player> player, final List<Level> levelList, final Level infiniteModeLevel) {
        this.player = player.orElse(new PlayerImpl(NEW_PLAYER_COINS, NEW_PLAYER_RECORD, NEW_PLAYER_LAST_UNLOCKED_LEVEL));
        this.levelList = levelList;
        this.infiniteModeLevel = infiniteModeLevel;

    }

    /**
     * 
     * @return the current player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * 
     * @return a Map representing the levels that the player has unlocked.
     */
    public Map<Integer, Boolean> getLevels() {
        final Map<Integer, Boolean> levelsMap = new HashMap<>();
        IntStream.rangeClosed(1, this.player.getLastUnlockedLevelId())
            .forEach(e -> levelsMap.put(e, true));
        IntStream.rangeClosed(this.player.getLastUnlockedLevelId() + 1, this.levelList.size())
            .forEach(e -> levelsMap.put(e, false));
        return levelsMap;
    }

    /**
     * Creates a level game.
     * @param levelId the level to create
     * @return the game to be played
     */
    public Game createLevelGame(final int levelId) {
        final Level level = this.levelList.stream()
                                .filter(x -> x.getLevelId() == levelId)
                                .findAny()
                                .get();
        return new GameImpl(level, this);
    }

    /**
     * Creates an infinite game.
     * @return the game to be played
     */
    public Game createInfiniteGame() {
        return new GameImpl(infiniteModeLevel, this);
    }
}
