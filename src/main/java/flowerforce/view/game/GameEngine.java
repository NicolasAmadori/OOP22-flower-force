package flowerforce.view.game;

import flowerforce.view.entities.CardView;
import javafx.geometry.Dimension2D;

import java.util.List;

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
    Dimension2D getYardDimension();

    /**
     * This method can be called to show the outcome of the game.
     * @param isWon true if the game is won, false otherwise
     */
    void over(boolean isWon);

    /**
     * This method should be called as soon as the controller starts a new game.
     * @param cardViews to be loaded into the game
     */
    void loadCards(List<CardView> cardViews);
}
