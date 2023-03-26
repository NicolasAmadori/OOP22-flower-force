package flowerforce.model.game;

import java.util.Map;

/**
 * Models the world the game's played in.
 */
public interface World {

    /**
     * 
     * @return the current player
     */
    Player getPlayer();

    /**
     * 
     * @return a Map representing the levels that the player has unlocked.
     */
    Map<Integer, Boolean> getLevels();

    /**
     * Creates a level game.
     * @param levelId the level to create
     * @return the game to be played
     */
    Game createLevelGame(int levelId);

    /**
     * Creates an infinite game.
     * @return the game to be played
     */
    Game createInfiniteGame();

    /**
     * Returns the maximum number of frames per second the game must be set to.
     */
    int getRenderingInformations();
}
