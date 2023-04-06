package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;


/**
 * Models a view entity.
 */
public interface EntityView {
    /**
     * @return the position to correctly draw the entity on the view.
     */
    Point2D getPlacingPosition();

    /**
     * @return the image of the entity to show in the field.
     */
    Image getPlaceableImage();

    /**
     * Set a new position for the entityView.
     * @param position to set
     */
    void setPosition(Point2D position);
}
