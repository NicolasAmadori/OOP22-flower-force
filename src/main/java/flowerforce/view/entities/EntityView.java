package flowerforce.view.entities;

import javafx.geometry.Point2D;

public interface EntityView {

    /**
     * 
     * @return the type of entity
     */
    EntityTypeView getEntityType();

    /**
     * 
     * @return the position to correctly draw the entity on the {@link GameEngine}
     */
    Point2D getPlacingPosition();

}
