package flowerforce.controller;

import flowerforce.model.World;
import flowerforce.view.GameView;
public class GameLoopImpl extends Thread implements GameLoop {

    private GameView view;
    private World model;
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
        while(true) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
