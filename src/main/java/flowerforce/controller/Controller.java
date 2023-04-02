package flowerforce.controller;

import flowerforce.model.game.Game;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;
import java.util.Set;

/**
 * This is the class that connects the view and the model sides, making them communicate.
 */
public interface Controller {

    /**
     * Get the number of coins the player has.
     * @return the integer representing the number of coins
     */
    int getPlayerCoins();

    /**
     * Get the score record the player has obtained.
     * @return The integer representing the score record
     */
    int getPlayerScoreRecord();

    /**
     * Get the id of the last level the player has unlocked.
     * @return The id of the last level unlocked
     */
    int getLastUnlockedLevelId();

    /**
     * Get the number of frames per second the game must run.
     * @return the integer representing the fps number
     */
    int getFramesPerSecond();

    /**
     * Start a new game for a specified level.
     * @param levelId The id of the level to play
     * @return the level game instance
     */
    Game startNewLevelGame(int levelId);

    /**
     * Start a new game in infinite mode.
     * @return the infinite game instance
     */
    Game startNewInfiniteGame();

    /**
     * Set up the GameEngine that the controller will communicate with.
     * @param gameEngine The GameEngine of the GameScene
     */
    void setGameEngine(GameEngine gameEngine);

    /**
     * Get the game engine instance to call rendering methods.
     * @return The gameEngine instance
     */
    GameEngine getGameEngine();

    /**
     * Get the number of currently present suns in the game.
     * @return the integer representing the number of suns.
     */
    int getSunCounter();

    /**
     * Get the actual score of the game.
     * @return an integer representing the score of the game
     */
    int getScore();

    /**
     * Place a new plant in a specified position.
     * @param plantId The id of the plant to place
     * @param row The row index in which to place the plant
     * @param col The column index in which to place the plant
     * @return true if the plant was placed correctly, false otherwise
     */
    boolean placePlant(CardView cardView, int row, int col);

    /**
     * Remove a placed plant.
     * @param row The row of the plant
     * @param col The column of the plant
     * @return True if a plant was present in that position, false otherwise
     */
    boolean removePlant(int row, int col);

    /**
     * Get all the entities to draw.
     * @return A set of EntityView.
     */
    Set<EntityView> getPlacedEntities();

    /**
     * All the cards that are enabled to select.
     * @return A set of integer, representing the indexes of the enable cards.
     */
    Set<CardView> getEnabledCards();

    /**
     * Get the number of rows available.
     * @return an integer representing the number of rows of the game
     */
    int getTotalRows();

    /**
     * Get the number of columns available.
     * @return an integer representing the number of columns of the game
     */
    int getTotalColumns();

    /**
     * Save the actual state of the game.
     */
    void save();
}
