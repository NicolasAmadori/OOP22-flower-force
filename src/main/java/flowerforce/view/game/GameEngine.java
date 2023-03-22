package flowerforce.view.game;

import flowerforce.view.entities.EntityView;
import javafx.geometry.Dimension2D;

/**
 * Interface of the engine of the game view.
 */
public interface GameEngine {

    /**
     * 
     * @param entity to be added to entities buffer
     */
    void addEntity(EntityView entity);

    /**
     * 
     * @param entity to be removed from entities buffer
     */
    void removeEntity(EntityView entity);

    /**
     * This method clears the entities buffer.
     */
    void clearEntities();

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
}
