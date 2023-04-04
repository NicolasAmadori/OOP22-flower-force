package flowerforce.controller;

import flowerforce.model.entities.EntityInfo;
import flowerforce.model.game.Game;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;

import java.util.Map;
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
     * Get the progress percentage of the game.
     * @return a number from 0.0 to 1.0 representing the percentage
     */
    double getProgressState();

    /**
     * Place a new plant in a specified position.
     * @param cardView The card view representing the plant to place
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
     * 
     * @param cardView to shop
     * @return true if the plant has been purchased correctly, false otherwise
     */
    boolean buyPlant(CardView cardView);

    /**
     * Get all the plants to draw.
     * @return A set of EntityView.
     */
    Set<EntityView> getPlacedPlants();

    /**
     * Get all the zombies to draw.
     * @return A set of EntityView.
     */
    Set<EntityView> getPlacedZombies();

    /**
     * Get all the zombies to draw.
     * @return A set of EntityView.
     */
    Set<EntityView> getPlacedBullets();

    /**
     * Get all the entities that received damage.
     * @return A set of entityInfo
     */
    Set<EntityView> getDamagedEntities();

    /**
     * All the cards that are enabled to select.
     * @return A set of integer, representing the indexes of the enable cards.
     */
    Set<CardView> getEnabledCards();

    /**
     * 
     * @return the plants that can be purchased
     */
    Map<CardView, Boolean> getPurchasablePlants();

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
