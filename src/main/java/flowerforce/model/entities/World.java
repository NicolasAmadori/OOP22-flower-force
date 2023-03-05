package flowerforce.model.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Models the world the game's played in.
 */
public class World {

    private final Player player;

    /**
     * Generates a world.
     * @param player the player that plays the game
     */
    public World(final Player player) {
        this.player = player;
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
        IntStream.rangeClosed(this.player.getLastUnlockedLevel() + 1, 100)
            .forEach(e -> levelsMap.put(e, false));
        return levelsMap;
    }
}
