package flowerforce.controller;

public interface Controller {

    int getPlayerCoins();

    int getSunCounter();

    void StartNewLevelGame(final int levelId);

    void startNewInfiniteGame();
}
