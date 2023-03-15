package flowerforce.view.entities;

import javafx.geometry.Point2D;

public interface EntityView {

    /**
     * @return the cost of entity
     */
    int cost();

    /**
     * @return the image path of the entity
     */
    String pathImage();

    /**
     * @return the position to correctly draw the entity on the {@link GameEngine}
     */
    Point2D getPlacingPosition();

}
