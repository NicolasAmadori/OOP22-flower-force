package flowerforce.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public interface EntityView {

    /**
     * 
     * @return the position to correctly draw the entity on the {@link GameView}
     */
    Point2D getPlacingPosition();

    /**
     * 
     * @return the entity image to display on the {@link GameView}
     */
    Image getImage();

}
