package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models the info that must be saved about an {@link Entity} for external usage.
 * It also wraps these informations inside an unique istance.
 */
public interface EntityInfo {

    /**
     * 
     * @return the name of the entity
     */
    String getName();

    /**
     * 
     * @return the position of the entity
     */
    Point2D getPosition();

    /**
     * Position must be updated with the one inside of entity.
     * By calling this method the istance of {@link EntityInfo} will remain the same.
     * @param position to be set
     */
    void setPosition(Point2D position);

}
