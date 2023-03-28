package flowerforce.controller;

import flowerforce.model.game.Game;
import flowerforce.view.game.GameEngine;
import javafx.application.Platform;
public class GameLoopThread implements Runnable{

    private final GameEngine gameEngine;
    private final Game model;
    private final int fps;
    private final long timeSlice;

    /**
     * Instantiate a new GameLoop giving it the model and the GameEngine it will communicate with.
     * @param gameEngine The gameEngine to render on the view
     * @param model The GameModel to get the game information
     */
    public GameLoopThread(final GameEngine gameEngine, final Game model, final int fps) {
        this.gameEngine = gameEngine;
        this.model = model;
        this.fps = fps;
        this.timeSlice = 1_000_000_000 / this.fps;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();
        long timeAccumulator = 0;
        Boolean updated = false;

        while (!this.model.isOver()) {
            final long actualTime = System.nanoTime();
            final long elapsedTime = actualTime - lastUpdateTime;

            lastUpdateTime += elapsedTime;
            timeAccumulator += elapsedTime;

            while (timeAccumulator > timeSlice) {
                this.model.update();
                updated = true;
                timeAccumulator -= timeSlice;
            }

            if (updated) {
                System.out.println(Thread.currentThread().getName());
                Platform.runLater(() -> {
                    System.out.println(Thread.currentThread().getName());
                    this.gameEngine.render();
                });
                updated = false;
            }
        }
        this.gameEngine.over(this.model.result());
    }
}
