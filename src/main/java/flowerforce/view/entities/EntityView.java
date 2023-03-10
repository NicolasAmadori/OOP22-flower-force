package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public interface EntityView {

    /**
     * 
     * @return the position to correctly draw the entity on the {@link GameEngine}
     */
    Point2D getPlacingPosition();

    /**
     * 
     * @return the entity image to display on the {@link GameEngine}
     */
    Image getImage();

}
