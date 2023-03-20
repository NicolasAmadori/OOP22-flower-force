package flowerforce.view.game;

import javafx.geometry.Dimension2D;

/**
 * Interface of the engine of the game view.
 */
public interface GameEngine {

    /**
     * This method can be called to refresh the view.
     */
    void render();

    /**
     * 
     * @return the dimensions of the yard view
     */
    Dimension2D getYardSize();

    /**
     * This method can be called to show the outcome of the game.
     */
    void over(boolean isWon);

    /**
     *
     * @return the factor to resize correctly the entity images
     */
    double getImageResizeFactor();
}
