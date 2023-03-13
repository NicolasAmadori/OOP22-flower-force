package flowerforce.controller;

import flowerforce.common.WorldSavingManager;
import flowerforce.model.game.World;
import flowerforce.view.game.GameEngine;

import javafx.geometry.Dimension2D;

/**
 * This is an implementation of {@link Controller}.
 */
public final class ControllerImpl implements Controller {

    private GameEngine gameEngine;
    private final World world;

    /**
     * Create a new instance of Controller.
     */
    public ControllerImpl() {
        try {
            this.world = WorldSavingManager.load();
            System.out.println(this.world.getCoins());
        } catch (InstantiationException e) {
            throw new RuntimeException(e); //TODO: change
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEngine(final GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerCoins() {
        return this.world.getPlayer().getCoins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSunCounter() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastUnlockedLevelId() {
        return this.world.getPlayer().getLastUnlockedLevelId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placePlant(final int plantId, final int row, final int col) {
        //this.world.placePlant(row, col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewLevelGame(final int levelId) {
        Dimension2D fieldDimension = this.gameEngine.getFieldSize();
        final GameLoop gameLoop = new GameLoopImpl(this.gameEngine,
                this.world.createLevelGame(levelId,
                        (int) fieldDimension.getWidth(), (int) fieldDimension.getHeight())); //TODO: update
        new Thread((Runnable) gameLoop).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewInfiniteGame() {
        //final GameLoop gameLoop = new GameLoopImpl(GameEngine, this.world.StartNewGame());
        //new Thread((Runnable) gameLoop).start();
    }
}
