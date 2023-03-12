package flowerforce.controller;

import flowerforce.common.WorldSavingManager;
import flowerforce.model.game.World;
import flowerforce.view.game.GameEngine;

import javafx.geometry.Dimension2D;

public class ControllerImpl implements Controller {

    private GameEngine gameEngine;
    private final World world;

    public ControllerImpl() {
        try {
            this.world = WorldSavingManager.load();
            System.out.println(this.world.getCoins());
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
        return this.world.getPlayer().getCoins();
    }

    @Override
    public int getSunCounter() {
        return 0;
    }

    @Override
    public int getLastUnlockedLevelId() {
        return this.world.getPlayer().getLastUnlockedLevelId();
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
    public void startNewLevelGame(int levelId) {
        Dimension2D fieldDimension = this.gameEngine.getFieldSize();
        final GameLoop gameLoop = new GameLoopImpl(this.gameEngine, this.world.createLevelGame(levelId, (int) fieldDimension.getWidth(), (int) fieldDimension.getHeight()));//TODO
        new Thread((Runnable) gameLoop).start();
    }

    @Override
    public void startNewInfiniteGame() {
        //final GameLoop gameLoop = new GameLoopImpl(GameEngine, this.world.StartNewGame());
        //new Thread((Runnable) gameLoop).start();
    }
}
