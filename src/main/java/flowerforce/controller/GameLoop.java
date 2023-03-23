package flowerforce.controller;

import flowerforce.model.game.Game;
import flowerforce.view.game.GameEngine;
import javafx.animation.AnimationTimer;

public final class GameLoop extends AnimationTimer {

    private final GameEngine gameEngine;
    private final Game model;
    private static final int FPS = 60;
    private static final long TIME_SLICE = 1_000_000_000 / FPS;

    private long lastUpdateTime;
    private long timeAccumulator = 0;
    private Boolean updated = false;

    /**
     * Instantiate a new GameLoop giving it the model and the GameEngine it will communicate with.
     * @param gameEngine The gameEngine to render on the view
     * @param model The GameModel to get the game information
     */
    public GameLoop(final GameEngine gameEngine, final Game model) {
        super();
        this.gameEngine = gameEngine;
        this.model = model;
        lastUpdateTime = System.nanoTime();
    }

    @Override
    public void handle(long now) {
        if (!this.model.isOver()) {
            final long actualTime = now;
            final long elapsedTime = actualTime - lastUpdateTime;

            lastUpdateTime += elapsedTime;
            timeAccumulator += elapsedTime;

            while (timeAccumulator > TIME_SLICE) {
                this.model.update();
                updated = true;
                timeAccumulator -= TIME_SLICE;
            }

            if (updated) {
                this.gameEngine.render();
                updated = false;
            }
        }
        else {
            this.gameEngine.over(this.model.result());
            this.stop();
        }
    }
}
