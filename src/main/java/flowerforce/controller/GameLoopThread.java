package flowerforce.controller;

import flowerforce.controller.utilities.InputHandler;
import flowerforce.model.game.Game;
import flowerforce.view.game.GameEngine;
import javafx.application.Platform;
public class GameLoopThread implements Runnable{

    private final GameEngine gameEngine;
    private final Game model;
    private final InputHandler inputHandler;
    private final long timeSlice;

    private Boolean updated = false;
    private long timeAccumulator = 0;

    /**
     * Instantiate a new GameLoop giving it the model and the GameEngine it will communicate with.
     * @param gameEngine The gameEngine to render on the view
     * @param model The GameModel to get the game information
     */
    public GameLoopThread(final GameEngine gameEngine, final Game model, InputHandler inputHandler, final int fps) {
        this.gameEngine = gameEngine;
        this.model = model;
        this.inputHandler = inputHandler;
        this.timeSlice = 1_000_000_000 / fps;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();

        while (!this.model.isOver()) {
            final long actualTime = System.nanoTime();
            final long elapsedTime = actualTime - lastUpdateTime;

            lastUpdateTime += elapsedTime;
            this.timeAccumulator += elapsedTime;

            manageInput();
            update();
            render();
        }

        showEndGame();
    }

    private void manageInput() {
        InputHandler copyInputHandler = new InputHandler(this.inputHandler);
        while(copyInputHandler.hasNextPlacedCell()) {
            var placedCell = copyInputHandler.getNextPlacedCell();
            this.model.placePlant(placedCell.getKey(), placedCell.getValue().getKey(), placedCell.getValue().getValue());
        }

        while(copyInputHandler.hasNextRemovedCell()) {
            var removedCell = copyInputHandler.getNextRemovedCell();
            this.model.removePlant(removedCell.getKey(), removedCell.getValue());
        }
    }

    private void update() {
        while (this.timeAccumulator > this.timeSlice) {
            this.model.update();
            this.updated = true;
            this.timeAccumulator -= this.timeSlice;
        }
    }

    private void render() {
        if (this.updated) {
            Platform.runLater(() -> {
                this.gameEngine.render();
            });
            this.updated = false;
        }
    }

    private void showEndGame() {
        Platform.runLater(() -> {
            this.gameEngine.over(this.model.result());
        });
    }
}
