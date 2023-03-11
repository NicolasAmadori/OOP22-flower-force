package flowerforce.controller;

import flowerforce.model.World;
import flowerforce.view.game.GameEngine;

public class ControllerImpl implements Controller {

    private GameEngine gameEngine;
    private final World world;

    public ControllerImpl() {
        this.world = new World(100);
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
        return world.getSunCounter();
    }

    @Override
    public int updateSunCounter() {
        return 0;
    }

    @Override
    public void placePlant(int row, int col) {
        this.world.placePlant(row, col);
    }

    @Override
    public void StartNewLevelGame(int levelId) {
        final GameLoop gameLoop = new GameLoopImpl(this, this.world.createGame(levelId));
        new Thread((Runnable) gameLoop).start();
    }

    @Override
    public void startNewInfiniteGame() {
        //final GameLoop gameLoop = new GameLoopImpl(GameEngine, this.world.StartNewGame());
        //new Thread((Runnable) gameLoop).start();
    }
}
