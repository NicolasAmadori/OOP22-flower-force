package flowerforce.controller;

import flowerforce.model.World;
import flowerforce.view.game.FlowerForceApplication;

public class ControllerImpl implements Controller {

    private final FlowerForceApplication application;
    private final World world;

    public ControllerImpl(FlowerForceApplication application) {
        this.application = application;
        this.world = new World(100);

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
    public void StartNewLevelGame(int levelId) {
        final GameLoop gameLoop = new GameLoopImpl(this, this.world);
        new Thread((Runnable) gameLoop).start();
    }

    @Override
    public void startNewInfiniteGame() {
        final GameLoop gameLoop = new GameLoopImpl(this, this.world);
        new Thread((Runnable) gameLoop).start();
    }
}
