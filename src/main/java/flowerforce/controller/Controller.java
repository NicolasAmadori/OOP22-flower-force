package flowerforce.controller;

import flowerforce.view.entities.CardView;
import flowerforce.view.entities.EntityView;
import flowerforce.view.game.GameEngine;

import java.util.List;
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
     * Get the id of the last level the player has unlocked.
     * @return The id of the last level unlocked
     */
    int getLastUnlockedLevelId();

    /**
     * Start a new game for a specified level.
     * @param levelId The id of the level to play
     */
    void startNewLevelGame(int levelId);

    /**
     * Start a new game in infinite mode.
     */
    void startNewInfiniteGame();

    /**
     * Set up the GameEngine that the controller will communicate with.
     * @param gameEngine The GameEngine of the GameScene
     */
    void setGameEngine(GameEngine gameEngine);

    /**
     * Get the number of currently present suns in the game.
     * @return the integer representing the number of suns.
     */
    int getSunCounter();

    /**
     * Place a new plant in a specified position.
     * @param plantId The id of the plant to place
     * @param row The row index in which to place the plant
     * @param col The column index in which to place the plant
     */
    void placePlant(int plantId, int row, int col);

    Set<EntityView> getPlacedEntities();

    List<CardView> getCards();

    Set<Integer> getEnabledCards();

    int getTotalRows();

    int getTotalColumns();
}
