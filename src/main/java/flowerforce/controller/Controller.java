package flowerforce.controller;

import flowerforce.view.GameEngine;
public interface Controller {

    void setGameEngine(GameEngine gameEngine);
    int getPlayerCoins();

    int getSunCounter();

    int getLastUnlockedLevelId();

    int updateSunCounter();

    void placePlant(int row, int col);

    void StartNewLevelGame(final int levelId);

    void startNewInfiniteGame();
}
