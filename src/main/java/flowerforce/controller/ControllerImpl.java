package flowerforce.controller;

import flowerforce.common.WorldSavingManager;
import flowerforce.model.game.World;
import flowerforce.view.GameEngine;

public class ControllerImpl implements Controller {

    private GameEngine gameEngine;
    private final World world;

    public ControllerImpl() {
        try {
            this.world = WorldSavingManager.load();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);//TODO: change
        }
    }

    @Override
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public int getPlayerCoins() {
        return this.world.getCoins();
    }

    @Override
    public int getSunCounter() {
        //return world.getSunCounter();
        return 0;
    }

    @Override
    public int getLastUnlockedLevelId() {
        return 0;
    }

    @Override
    public int updateSunCounter() {
        return 0;
    }

    @Override
    public void placePlant(int row, int col) {
        //this.world.placePlant(row, col);
    }

    @Override
    public void StartNewLevelGame(int levelId) {
        //final GameLoop gameLoop = new GameLoopImpl(this, this.world.createGame(levelId));
        //new Thread((Runnable) gameLoop).start();
    }

    @Override
    public void startNewInfiniteGame() {
        //final GameLoop gameLoop = new GameLoopImpl(GameEngine, this.world.StartNewGame());
        //new Thread((Runnable) gameLoop).start();
    }
}
