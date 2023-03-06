package flowerforce.model.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import flowerforce.model.game.Level;

/**
 * Models the world the game's played in.
 */
public class World {

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
        IntStream.rangeClosed(1, this.player.getLastUnlockedLevel())
            .forEach(e -> levelsMap.put(e, true));
        IntStream.rangeClosed(this.player.getLastUnlockedLevel() + 1, this.levelList.size())
            .forEach(e -> levelsMap.put(e, false));
        return levelsMap;
    }
}
