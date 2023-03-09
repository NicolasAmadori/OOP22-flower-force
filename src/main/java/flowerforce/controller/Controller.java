package flowerforce.controller;

public interface Controller {

    int getPlayerCoins();

    int getSunCounter();

    int updateSunCounter();

    void placePlant(int row, int col);

    void StartNewLevelGame(final int levelId);

    void startNewInfiniteGame();
}
