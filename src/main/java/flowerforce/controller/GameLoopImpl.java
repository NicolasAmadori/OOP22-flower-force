package flowerforce.controller;

import flowerforce.model.World;
import flowerforce.view.GameView;
public class GameLoopImpl extends Thread implements GameLoop {

    private GameView view;
    private World model;

    private static final int FPS = 60;

    private static final long TIME_SLICE = 1000_000_000 / FPS;

    private boolean isRunning = false;

    public GameLoopImpl(GameView view, World model) {
        this.view = view;
        this.model = model;

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
        view.setSunCounter(model.getSunCounter());//esempio di utilizzo

        //view.setEntities(model.getEntities());//esempio, bisognerà passare alla view tutte le view delle entità utilizzando un converter

        view.render();//Sent to the view the command to render all the elements of the GUI
    }
}
