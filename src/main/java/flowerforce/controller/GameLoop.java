package flowerforce.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import flowerforce.model.game.Game;
import flowerforce.view.game.GameEngine;

/**
 * GameLoop class that run the game.
 */
public final class GameLoop implements Runnable {

    private static final long SECOND_IN_MILLISECOND = 1_000_000_000;
    private final GameEngine gameEngine;
    private final Game model;
    private final long timeSlice;
    private long lastUpdateTime;
    private long timeAccumulator;
    private Boolean updated = false;
    private Boolean running = true;

    /**
     * Instantiate a new GameLoop giving it the model and the GameEngine it will communicate with.
     * @param gameEngine The gameEngine to render on the view
     * @param model The GameModel to get the game information
     * @param framesPerSecond the frame rate the game must be run
     */
    @SuppressFBWarnings(
            value = {
                    "EI_EXPOSE_REP2"
            },
            justification = "I need to access and modify the exact instance of the Game model to update it"
    )
    public GameLoop(final GameEngine gameEngine, final Game model, final int framesPerSecond) {
        super();
        this.gameEngine = gameEngine;
        this.model = model;
        this.timeSlice = SECOND_IN_MILLISECOND / framesPerSecond;
        lastUpdateTime = System.nanoTime();
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (running) {
            if (!this.model.isOver()) {
                final long elapsedTime = System.nanoTime() - lastUpdateTime;

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
            } else {
                this.gameEngine.over(this.model.result());
                this.running = false;
            }
        }
    }
}
