package flowerforce.view.entities;

import flowerforce.view.game.GameEngine;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

/**
 * Models a view entity
 */
public interface EntityView {

    /**
     * @return the cost of entity
     */
    int cost();

    /**
     * @return the image path of the entity
     */
    ImageView getImageView();

    /**
     * @return the position to correctly draw the entity on the {@link GameEngine}
     */
    Point2D getPlacingPosition();

}
