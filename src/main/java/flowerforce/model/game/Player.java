package flowerforce.model.game;

/**
 * The class representing the player with all his information.
 */
public interface Player {

    /**
     * Get the number of coins the player has.
     * @return an integer representing the number of coins
     */
    int getCoins();

    /**
     * Increment the number of coins the player has.
     * @param nCoins the number of coins to add to the total
     */
    void addCoins(int nCoins);

    /**
     * Decrement the number of coins the player has.
     * @param nCoins the number of coins to subtract from the total
     * @return true if the operation has been successful, false otherwise
     */
    boolean subtractCoins(int nCoins);

    /**
     * Get the score record of the player.
     * @return The integer representing the score record
     */
    int getScoreRecord();

    /**
     * Set a new value for the score record of the player.
     * @param score The integer representing the new score record
     * @return True if the operation was succesful, false otherwise
     */
    boolean setNewScoreRecord(int score);

    /**
     * Get the id of the last level the player has unlocked.
     * @return The integer representing the id of the level
     */
    int getLastUnlockedLevelId();

    /**
     * Unlock the next level.
     */
    void unlockedNextLevel();
}
