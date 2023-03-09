package flowerforce.controller;

import flowerforce.model.World;
import flowerforce.view.GameView;
public class GameLoopImpl implements GameLoop, Runnable {

    private GameView view;
    private World model;

    private static final int FPS = 60;

    private static final long TIME_SLICE = 1000_000_000 / FPS;

    private boolean isRunning = false;

    public GameLoopImpl(Controller controller, World model) {
        this.view = view;
        this.model = model;

    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void start() {

    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void run() {
        this.isRunning = true;

        long lastUpdateTime = System.nanoTime();
        long timeAccumulator = 0;

        while(this.isRunning) {
            long actualTime = System.nanoTime();
            long elapsedTime = actualTime - lastUpdateTime;
            lastUpdateTime += elapsedTime;
            timeAccumulator += elapsedTime;

            while(timeAccumulator > TIME_SLICE){
                model.update();
                timeAccumulator -= TIME_SLICE;
            }

            updateAndRenderView();
        }
    }

    private void updateAndRenderView() {
        //view.render();//Sent to the view the command to render all the elements of the GUI
    }
}
