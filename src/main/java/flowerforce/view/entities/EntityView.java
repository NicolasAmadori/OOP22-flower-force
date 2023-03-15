package flowerforce.view.entities;

import flowerforce.view.game.GameEngine;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * Models a view entity
 */
public interface EntityView {

    /**
     * @return if it's present, return the cost of entity
     */
    Optional<Integer> getCost();

    /**
     * @return the image path of the entity
     */
    ImageView getImageView();

    /**
     * @return the position to correctly draw the entity on the {@link GameEngine}
     */
    Point2D getPlacingPosition();


}
