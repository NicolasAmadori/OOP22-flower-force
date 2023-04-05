package flowerforce.model.entities;

import javafx.geometry.Point2D;

/**
 * Models the info that must be saved about an {@link Entity} in order to recognise it using maps.
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
     * 
     * @param position to be set
     */
    void setPosition(Point2D position);

}
