package flowerforce.model.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * Models the world the game's played in.
 */
public class World {

    private final static int ROWS = 5;
    private final static int COLS = 7;

    private final Player player;
    private final List<Level> levelList;

    /**
     * Generates a world.
     * @param player the player that plays the game
     * @param levelList the list of all levels
     */
    public World(final Player player, final List<Level> levelList) {
        this.player = player;
        this.levelList = levelList;
    }

    /**
     * 
     * @return the player's coins
     */
    public int getCoins() {
        return this.player.getCoins();
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
    Game createLevelGame(final int levelId, final int width, final int height) {
        final Level level = this.levelList.stream()
                                .filter(x -> x.getLevelId() == levelId)
                                .findAny()
                                .get();
        return new GameImpl(level, ROWS, COLS, width, height);
    }

    /**
     * Creates an infinite game.
     * @return the game to be played
     */
    Game createInfiniteGame(final int height, final int length) {
        return null;
    }
}
