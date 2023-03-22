package flowerforce.controller;

/**
 * The class that updates the model state and send render commands to the GameEngine.
 */
public interface GameLoop {

    /**
     * Start the game loop.
     */
    void start();

    void singleTick();

    boolean isRunning();
}
