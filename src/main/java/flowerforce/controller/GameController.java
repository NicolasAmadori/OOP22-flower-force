package flowerforce.controller;

import java.util.Map;

/**
 * The Controller that interacts with Views and Models, updating the game in the Game Loop.
 */
public interface GameController {

    /**
     * Get the number of coins the Player has.
     * @return Integer rappresenting the number of coins the player has
     */
    int getPlayerCoins();

    /**
     * Get all the levels ids and their state, locked or unlocked.
     * @return A Map where levels ids are the keys, and levels locked/unlocked states are the values
     */
    Map<Integer, Boolean> getLevelIds();

    /**
     * Start a new level Game.
     * @param levelId The level id to start the new game
     */
    void startLevelGame(int levelId);

    /**
     * Start a new game in infinite mode.
     */
    void startInfiniteGame();
}
