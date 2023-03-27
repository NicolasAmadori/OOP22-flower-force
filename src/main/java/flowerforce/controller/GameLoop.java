package flowerforce.controller;

import flowerforce.model.game.Game;
import flowerforce.view.game.GameEngine;
import javafx.animation.AnimationTimer;

public final class GameLoop extends AnimationTimer {

    private static final long SECOND_IN_MILLISECOND = 1_000_000_000;
    private final GameEngine gameEngine;
    private final Game model;
    private final long timeSlice;
    private long lastUpdateTime;
    private long timeAccumulator = 0;
    private Boolean updated = false;

    /**
     * Instantiate a new GameLoop giving it the model and the GameEngine it will communicate with.
     * @param gameEngine The gameEngine to render on the view
     * @param model The GameModel to get the game information
     */
    public GameLoop(final GameEngine gameEngine, final Game model, final int framesPerSecond) {
        super();
        this.gameEngine = gameEngine;
        this.model = model;
        this.timeSlice = SECOND_IN_MILLISECOND / framesPerSecond;
        lastUpdateTime = System.nanoTime();
    }

    @Override
    public void handle(long now) {
        if (!this.model.isOver()) {
            final long elapsedTime = now - lastUpdateTime;

            lastUpdateTime += elapsedTime;
            timeAccumulator += elapsedTime;

            while (timeAccumulator > timeSlice) {
                this.model.update();
                updated = true;
                timeAccumulator -= timeSlice;
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
